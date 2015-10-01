package filemerge.service;

import filemerge.file.writer.StringArrayWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static filemerge.file.writer.AbstractCsvWriter.CSV_SEPARATOR;

@Service
public class FileSortingService {

    private static final int ROWS_PER_SINGLE_STEP = 10000;
    public static final String CHUNK_FILE_PREFIX = "temp_chunk";
    public static final int NULL_INDEX = -1;
    public static final String NEW_LINE = "\n";

    private static Logger logger = LoggerFactory.getLogger(FileSortingService.class);

    public void sortCsvFileByFirstField(InputStream inputFileStream, OutputStream outputStream) {
        if (inputFileStream == null) {
            throw new IllegalArgumentException("inputFileStream is null");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("outputStream is null");
        }

        try {
            ChunkFileGenerator fileGenerator = new ChunkFileGenerator(inputFileStream);
            fileGenerator.generateChunkFiles();
            fileGenerator.close();

            String headerRow = fileGenerator.getHeaderRow();
            int chunkFilesCnt = fileGenerator.getChunkFilesCnt();

            if (chunkFilesCnt > 0) {
                ChunkFilesSortMerger chunkFilesSortMerger = new ChunkFilesSortMerger(outputStream, chunkFilesCnt, headerRow);
                chunkFilesSortMerger.mergeChunkFiles();
                chunkFilesSortMerger.close();
            }
        } catch (IOException e) {
            logger.error("Error during file sorting", e);
        } finally {
            try {
                inputFileStream.close();
                outputStream.close();
            } catch (IOException e1) {
                logger.error("Error during stream closing", e1);
            }
        }
    }

    private static class ChunkFileGenerator {
        private String headerRow;
        private int chunkFilesCnt = 0;

        private final BufferedReader inputFileReader;
        private final StringArrayFirstFieldComparator comparator = new StringArrayFirstFieldComparator();

        public ChunkFileGenerator(InputStream inputFileStream)  {
            this.inputFileReader = new BufferedReader(new InputStreamReader(inputFileStream, StandardCharsets.UTF_8));
        }

        public String getHeaderRow() {
            return headerRow;
        }

        public int getChunkFilesCnt() {
            return chunkFilesCnt;
        }

        public void generateChunkFiles() throws IOException {
            headerRow = inputFileReader.readLine();
            if (headerRow == null) {
                logger.warn("File for sorting is empty");
                return;
            }

            String[] currentRow = headerRow.split(CSV_SEPARATOR);
            List<String[]> bufferList = new ArrayList<>();

            while (currentRow != null) {
                for (int i = 0; i < ROWS_PER_SINGLE_STEP; i++) {
                    String line = inputFileReader.readLine();
                    if (line == null) {
                        currentRow = null;
                        break;
                    }
                    currentRow = line.split(CSV_SEPARATOR);
                    bufferList.add(currentRow);
                }

                Collections.sort(bufferList, comparator);

                String fileName = CHUNK_FILE_PREFIX + chunkFilesCnt + ".csv";
                saveBufferToChunkFile(bufferList, fileName);
                chunkFilesCnt++;
                bufferList.clear();
            }
        }

        public void close() throws IOException {
            inputFileReader.close();
        }

        private void saveBufferToChunkFile(List<String[]> arraysList, String filename) throws IOException {
            FileOutputStream fos = new FileOutputStream(new File(filename));

            StringArrayWriter writer = new StringArrayWriter(fos);
            for (String[] strings : arraysList) {
                writer.writeObject(strings);
            }

            writer.close();
            fos.close();
        }
    }

    private static class ChunkFilesSortMerger {
        private final ArrayList<FileReader> readers = new ArrayList<>();
        private final ArrayList<BufferedReader> buffReaders = new ArrayList<>();

        private final ArrayList<String> topDataRows = new ArrayList<>();
        private final int chunkFilesCount;

        private final String headerRow;

        private final OutputStreamWriter writer;

        private boolean dataRowsExists = false;

        public ChunkFilesSortMerger(OutputStream outputStream, int chunkFilesCount, String headerRow) throws IOException {
            this.writer = new OutputStreamWriter(outputStream);
            this.chunkFilesCount = chunkFilesCount;
            this.headerRow = headerRow;
            prepareReaders();
        }

        private void prepareReaders() throws IOException {
            for (int i = 0; i < chunkFilesCount; i++) {
                FileReader fileReader = new FileReader(getChunkName(i));
                readers.add(fileReader);
                buffReaders.add(new BufferedReader(fileReader));

                // get the first rows
                String firstLine = buffReaders.get(i).readLine();
                if (firstLine != null) {
                    dataRowsExists = true;
                }
                topDataRows.add(firstLine);
            }
        }

        private String getChunkName(int i) {
            return CHUNK_FILE_PREFIX + i  + ".csv";
        }

        private void writeHeader() throws IOException {
            writer.append(headerRow).append(NEW_LINE);
        }

        public void mergeChunkFiles() throws IOException {
            writeHeader();

            while (dataRowsExists) {
                int minimalIdx = getMinimalIndexForFirstField();

                if (minimalIdx == NULL_INDEX) {
                    dataRowsExists = false;
                } else {
                    String minimalIdxRow = topDataRows.get(minimalIdx);
                    writer.append(minimalIdxRow).append(NEW_LINE);

                    String nextLine = buffReaders.get(minimalIdx).readLine();
                    topDataRows.set(minimalIdx, nextLine);
                }
            }
        }

        private int getMinimalIndexForFirstField() {
            int minIdx = NULL_INDEX;
            String minValue = null;

            for (int i = 0; i < topDataRows.size(); i++) {
                String currentLine = topDataRows.get(i);
                if (currentLine != null) {
                    String[] currentRow = currentLine.split(CSV_SEPARATOR);
                    String firstField = currentRow[0];

                    if (firstField != null) {
                        if (minValue == null) {
                            minValue = firstField;
                            minIdx = i;
                        } else {
                            if (minValue.compareTo(firstField) > 0) {
                                minValue = firstField;
                                minIdx = i;
                            }
                        }
                    }
                }
            }
            return minIdx;
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        public void close() throws IOException {
            //closing readers
            for (BufferedReader buffReader : buffReaders) {
                buffReader.close();
            }
            for (FileReader reader : readers) {
                reader.close();
            }

            //deleting chunk files
            for (int i = 0; i < chunkFilesCount; i++) {
                File chunkFile = new File(getChunkName(i));
                chunkFile.delete();
            }

            writer.close();
        }
    }
}
