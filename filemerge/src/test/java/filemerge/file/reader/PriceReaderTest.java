package filemerge.file.reader;

import filemerge.model.Price;
import org.junit.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static filemerge.TestDataUtils.inputStreamFromString;
import static filemerge.TestDataUtils.preparePricesUnsortedCsv;
import static org.assertj.core.api.Assertions.assertThat;

public class PriceReaderTest {

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullInput() {
        PriceReader priceReader = new PriceReader(null);
    }

    @Test
    public void returnsNullOnEmptyInput() throws Exception {
        InputStream is = inputStreamFromString("");
        PriceReader priceReader = new PriceReader(is);

        Price price = priceReader.readObject();
        assertThat(price).isNull();
    }

    @Test
    public void skipsHeaderRow() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCT_ID,DATE,PRICE").append("\n");
        sb.append("2,2015-01-01,5.25").append("\n");
        InputStream is = inputStreamFromString(sb.toString());
        PriceReader priceReader = new PriceReader(is);

        List<Price> priceList = new ArrayList<>();
        Price price;
        while ((price = priceReader.readObject()) != null) {
            priceList.add(price);
        }

        assertThat(priceList).hasSize(1);
    }

    @Test
    public void readsPrices() throws Exception {
        InputStream is = inputStreamFromString(preparePricesUnsortedCsv());

        PriceReader priceReader = new PriceReader(is);

        List<Price> priceList = new ArrayList<>();
        Price price;
        while ((price = priceReader.readObject()) != null) {
            priceList.add(price);
        }

        assertThat(priceList).hasSize(3);

        Price price0 = priceList.get(0);

        assertThat(price0.getProductId()).isEqualTo(2);
        assertThat(price0.getDate()).isCloseTo("2015-01-01", 3600 * 1000);
        BigDecimal expectedPrice = new BigDecimal(5.25).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertThat(price0.getPrice().compareTo(expectedPrice)).isEqualTo(0);
    }

}
