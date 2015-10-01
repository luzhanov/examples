package filemerge.file.writer;

import java.io.OutputStream;

public class StringArrayWriter extends AbstractCsvWriter<String[]> {

    public StringArrayWriter(OutputStream os) {
        super(os);
    }

    @Override
    protected String getHeaderLine() {
        throw new UnsupportedOperationException("getHeaderLine() should not be called for StringArrayWriter");
    }

    @Override
    protected String getLineForObject(String[] array) {
        boolean firstValue = true;

        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            if (!firstValue) {
                sb.append(CSV_SEPARATOR);
            } else {
                firstValue = false;
            }

            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    protected boolean hasHeader() {
        return false;
    }
}
