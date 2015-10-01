package filemerge.testdata;

import filemerge.file.writer.AbstractCsvWriter;
import filemerge.model.Product;

import java.io.OutputStream;

public class ProductWriter extends AbstractCsvWriter<Product> {

    public ProductWriter(OutputStream os) {
        super(os);
    }

    @Override
    public String getHeaderLine() {
        return "PRODUCT_ID,PRODUCT_DESCRIPTION";
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

    @Override
    public String getLineForObject(Product dataModel) {
        StringBuilder sb = new StringBuilder();

        sb.append(dataModel.getId()).append(CSV_SEPARATOR);
        sb.append(dataModel.getDescription());

        return sb.toString();
    }
}
