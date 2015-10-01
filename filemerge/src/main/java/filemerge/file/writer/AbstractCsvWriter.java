package filemerge.file.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class AbstractCsvWriter<T> implements AutoCloseable {

    public static final String CSV_SEPARATOR = ",";

    private final OutputStreamWriter writer;

    private boolean firstWrite = true;

    public AbstractCsvWriter(OutputStream os) {
        if (os == null) {
            throw new IllegalArgumentException("OutputStream is null");
        }
        writer = new OutputStreamWriter(os);
    }

    public void close() throws IOException {
        writer.close();
    }

    public void writeObject(T object) throws IOException {
        if (object == null) {
            return;
        }

        if (firstWrite) {
            if (hasHeader()) {
                String headerLine = getHeaderLine();
                writer.write(headerLine);
                writer.write("\n");
            }
            firstWrite = false;
        } else {
            writer.write("\n");
        }

        String objectLine = getLineForObject(object);
        writer.write(objectLine);
    }

    protected abstract String getHeaderLine();

    protected abstract String getLineForObject(T dataModel);

    protected abstract boolean hasHeader();
}
