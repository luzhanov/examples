package filemerge.file.reader;

import filemerge.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PriceReader extends AbstractCsvBufferedReader<Price> {

    private static Logger logger = LoggerFactory.getLogger(PriceReader.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public PriceReader(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public Price createObjectFromCSVData(String[] strings) {
        Price productDescription = new Price();

        productDescription.setProductId(Long.parseLong(strings[0]));

        try {
            productDescription.setDate(DATE_FORMAT.parse(strings[1]));
        } catch (ParseException e) {
            logger.error("Parse exception during date parse", e);
        }

        BigDecimal price = new BigDecimal(strings[2]).setScale(2, BigDecimal.ROUND_HALF_UP);
        productDescription.setPrice(price);

        return productDescription;
    }
}
