package filemerge.file.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class AbstractCsvBufferedReader<T> implements AutoCloseable {

    public static final String CSV_SEPARATOR = ",";

    private final BufferedReader inputReader;

    private boolean firstRead = true;

    public AbstractCsvBufferedReader(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream is null");
        }

        this.inputReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public T readObject() throws IOException {
        //skipping header first
        if (firstRead) {
            inputReader.readLine();
            firstRead = false;
        }

        String nextLine = inputReader.readLine();
        if (nextLine != null) {
            return createObjectFromCsvString(nextLine);
        } else {
            return null;
        }
    }

    public void close() throws IOException {
        inputReader.close();
    }

    private T createObjectFromCsvString(String value) {
        String [] fields = value.split(CSV_SEPARATOR);
        return createObjectFromCSVData(fields);
    }

    protected abstract T createObjectFromCSVData(String[] strings);

}
