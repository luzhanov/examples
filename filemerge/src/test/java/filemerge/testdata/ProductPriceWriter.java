package filemerge.testdata;

import filemerge.file.writer.AbstractCsvWriter;
import filemerge.model.Price;

import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class ProductPriceWriter extends AbstractCsvWriter<Price> {

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public ProductPriceWriter(OutputStream os) {
        super(os);
    }

    @Override
    public String getHeaderLine() {
        return "PRODUCT_ID,DATE,PRICE";
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

    @Override
    public String getLineForObject(Price dataModel) {
        StringBuilder sb = new StringBuilder();

        sb.append(dataModel.getProductId()).append(CSV_SEPARATOR);
        sb.append(df.format(dataModel.getDate())).append(CSV_SEPARATOR);
        sb.append(dataModel.getPrice().toString());

        return sb.toString();
    }
}
