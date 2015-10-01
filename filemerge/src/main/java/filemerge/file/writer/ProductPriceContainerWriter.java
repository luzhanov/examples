package filemerge.file.writer;

import filemerge.model.PriceGroup;

import java.io.OutputStream;
import java.math.BigDecimal;

public class ProductPriceContainerWriter extends AbstractCsvWriter<PriceGroup> {

    public ProductPriceContainerWriter(OutputStream os) {
        super(os);
    }

    @Override
    public String getHeaderLine() {
        return "PRODUCT_ID,PRODUCT_DESCRIPTION,PRICE";
    }

    @Override
    public String getLineForObject(PriceGroup dataModel) {
        StringBuilder sb = new StringBuilder();

        sb.append(dataModel.getId().toString()).append(CSV_SEPARATOR);
        sb.append(dataModel.getDescription());
        for (BigDecimal bigDecimal : dataModel.getPrices()) {
            sb.append(CSV_SEPARATOR);
            sb.append(bigDecimal.toString());
        }

        return sb.toString();
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

}
