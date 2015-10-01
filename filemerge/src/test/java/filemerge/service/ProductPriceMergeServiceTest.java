package filemerge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static filemerge.TestDataUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/context.xml"})
public class ProductPriceMergeServiceTest {

    @Autowired
    ProductPriceMergeService productPriceMergeService;

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullProductsIs() {
        InputStream pricesInputStream = inputStreamFromString(preparePricesSortedCsv());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        productPriceMergeService.mergeSortedProductPricesToCsvFile(null, pricesInputStream, outputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullPricesIs() {
        InputStream productsInputStream = inputStreamFromString(prepareProductsSortedCsv());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        productPriceMergeService.mergeSortedProductPricesToCsvFile(productsInputStream, null, outputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullOs() {
        InputStream productsInputStream = inputStreamFromString(prepareProductsSortedCsv());
        InputStream pricesInputStream = inputStreamFromString(preparePricesSortedCsv());

        productPriceMergeService.mergeSortedProductPricesToCsvFile(productsInputStream, pricesInputStream, null);
    }

    @Test
    public void mergePricesFromTwoSortedSources() throws Exception {
        InputStream productsInputStream = inputStreamFromString(prepareProductsSortedCsv());
        InputStream pricesInputStream = inputStreamFromString(preparePricesSortedCsv());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        productPriceMergeService.mergeSortedProductPricesToCsvFile(productsInputStream, pricesInputStream, outputStream);

        String result = outputStream.toString();
        assertThat(result).isNotEmpty();
        String[] lines = result.split("\n");

        assertThat(lines).hasSize(4);

        assertThat(lines[0]).isEqualTo("PRODUCT_ID,PRODUCT_DESCRIPTION,PRICE");
        assertThat(lines[1]).isEqualTo("1,PRODUCT 1,7.25,17.25");
        assertThat(lines[2]).isEqualTo("2,PRODUCT 2,5.25,15.25");
        assertThat(lines[3]).isEqualTo("3,PRODUCT 3,16.25,6.25");
    }

    @Test
    public void mergeProductsWithExtraPricesInTheMiddle() throws Exception {
        String products = "PRODUCT_ID,PRODUCT_DESCRIPTION\n" +
                "1,PRODUCT 1\n"+
                "4,PRODUCT 4";

        InputStream productsInputStream = inputStreamFromString(products);

        String prices = "PRODUCT_ID,DATE,PRICE\n" +
                "1,2015-03-01,7.25\n" +
                "1,2015-03-02,17.25\n" +
                "2,2015-03-01,78.25\n" +
                "2,2015-03-02,78.25\n" +
                "3,2015-03-01,33.25\n" +
                "3,2015-03-02,34.25\n" +
                "4,2015-02-02,16.25\n" +
                "4,2015-02-01,6.25";
        InputStream pricesInputStream = inputStreamFromString(prices);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        productPriceMergeService.mergeSortedProductPricesToCsvFile(productsInputStream, pricesInputStream, outputStream);

        String result = outputStream.toString();
        assertThat(result).isNotEmpty();
        String[] lines = result.split("\n");

        assertThat(lines).hasSize(3);

        assertThat(lines[0]).isEqualTo("PRODUCT_ID,PRODUCT_DESCRIPTION,PRICE");
        assertThat(lines[1]).isEqualTo("1,PRODUCT 1,7.25,17.25");
        assertThat(lines[2]).isEqualTo("4,PRODUCT 4,16.25,6.25");
    }

    @Test
    public void mergeProductsWithExtraPricesInTheEnd() throws Exception {
        String products = "PRODUCT_ID,PRODUCT_DESCRIPTION\n" +
                "1,PRODUCT 1\n"+
                "2,PRODUCT 2";

        InputStream productsInputStream = inputStreamFromString(products);

        String prices = "PRODUCT_ID,DATE,PRICE\n" +
                "1,2015-03-01,7.25\n" +
                "1,2015-03-02,17.25\n" +
                "2,2015-03-01,78.25\n" +
                "2,2015-03-02,71.25\n" +
                "3,2015-03-01,33.25\n" +
                "3,2015-03-02,34.25\n" +
                "4,2015-02-02,16.25\n" +
                "4,2015-02-01,6.25";
        InputStream pricesInputStream = inputStreamFromString(prices);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        productPriceMergeService.mergeSortedProductPricesToCsvFile(productsInputStream, pricesInputStream, outputStream);

        String result = outputStream.toString();
        assertThat(result).isNotEmpty();
        String[] lines = result.split("\n");

        assertThat(lines).hasSize(3);

        assertThat(lines[0]).isEqualTo("PRODUCT_ID,PRODUCT_DESCRIPTION,PRICE");
        assertThat(lines[1]).isEqualTo("1,PRODUCT 1,7.25,17.25");
        assertThat(lines[2]).isEqualTo("2,PRODUCT 2,78.25,71.25");
    }


    @Test
    public void mergeProductsWithoutPricesIfNoPricesLeft() throws Exception {
        String products = "PRODUCT_ID,PRODUCT_DESCRIPTION\n" +
                "1,PRODUCT 1\n"+
                "2,PRODUCT 2\n"+
                "3,PRODUCT 3";

        InputStream productsInputStream = inputStreamFromString(products);

        String prices = "PRODUCT_ID,DATE,PRICE\n" +
                "1,2015-03-01,7.25\n" +
                "1,2015-03-02,17.25";
        InputStream pricesInputStream = inputStreamFromString(prices);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        productPriceMergeService.mergeSortedProductPricesToCsvFile(productsInputStream, pricesInputStream, outputStream);

        String result = outputStream.toString();
        assertThat(result).isNotEmpty();
        String[] lines = result.split("\n");

        assertThat(lines).hasSize(4);

        assertThat(lines[0]).isEqualTo("PRODUCT_ID,PRODUCT_DESCRIPTION,PRICE");
        assertThat(lines[1]).isEqualTo("1,PRODUCT 1,7.25,17.25");
        assertThat(lines[2]).isEqualTo("2,PRODUCT 2");
        assertThat(lines[3]).isEqualTo("3,PRODUCT 3");
    }

    @Test
    public void mergeProductsWithoutPrices() throws Exception {
        String products = "PRODUCT_ID,PRODUCT_DESCRIPTION\n" +
        "1,PRODUCT 1\n"+
        "2,PRODUCT 2\n"+
        "3,PRODUCT 3\n"+
        "4,PRODUCT 4";

        InputStream productsInputStream = inputStreamFromString(products);

        String prices = "PRODUCT_ID,DATE,PRICE\n" +
                "1,2015-03-01,7.25\n" +
                "1,2015-03-02,17.25\n" +
                "4,2015-02-02,16.25\n" +
                "4,2015-02-01,6.25";
        InputStream pricesInputStream = inputStreamFromString(prices);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        productPriceMergeService.mergeSortedProductPricesToCsvFile(productsInputStream, pricesInputStream, outputStream);

        String result = outputStream.toString();
        assertThat(result).isNotEmpty();
        String[] lines = result.split("\n");

        assertThat(lines).hasSize(5);

        assertThat(lines[0]).isEqualTo("PRODUCT_ID,PRODUCT_DESCRIPTION,PRICE");
        assertThat(lines[1]).isEqualTo("1,PRODUCT 1,7.25,17.25");
        assertThat(lines[2]).isEqualTo("2,PRODUCT 2");
        assertThat(lines[3]).isEqualTo("3,PRODUCT 3");
        assertThat(lines[4]).isEqualTo("4,PRODUCT 4,16.25,6.25");
    }

}
