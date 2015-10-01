package filemerge.file.reader;

import filemerge.model.Product;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static filemerge.TestDataUtils.inputStreamFromString;
import static filemerge.TestDataUtils.prepareProductsUnsortedCsv;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ProductReaderTest {

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullInput() {
        new ProductReader(null);
    }

    @Test
    public void returnsNullOnEmptyInput() throws Exception {
        InputStream is = inputStreamFromString("");
        ProductReader productReader = new ProductReader(is);

        Product product = productReader.readObject();
        assertThat(product).isNull();
    }

    @Test
    public void skipsHeaderRow() throws Exception {
        InputStream is = inputStreamFromString("PRODUCT_ID,PRODUCT_DESCRIPTION" + "\n" + "3,PRODUCT 3" + "\n");
        ProductReader productReader = new ProductReader(is);

        List<Product> productList = new ArrayList<>();
        Product product;
        while ((product = productReader.readObject()) != null) {
            productList.add(product);
        }

        assertThat(productList).hasSize(1);
    }

    @Test
    public void readsPrices() throws Exception {
        InputStream is = inputStreamFromString(prepareProductsUnsortedCsv());

        ProductReader productReader = new ProductReader(is);

        List<Product> priceList = new ArrayList<>();
        Product product;
        while ((product = productReader.readObject()) != null) {
            priceList.add(product);
        }

        assertThat(priceList).hasSize(3);

        Product product0 = priceList.get(0);

        assertThat(product0.getId()).isEqualTo(3);
        assertThat(product0.getDescription()).isEqualTo("PRODUCT 3");
    }

}
