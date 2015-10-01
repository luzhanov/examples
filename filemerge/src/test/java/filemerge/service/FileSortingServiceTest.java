package filemerge.service;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static filemerge.TestDataUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("StringBufferReplaceableByString")
public class FileSortingServiceTest {

    FileSortingService csvSortService = new FileSortingService();

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullInputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csvSortService.sortCsvFileByFirstField(null, baos);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullOutputStream() {
        InputStream inputStream = inputStreamFromString("test");
        csvSortService.sortCsvFileByFirstField(inputStream, null);
    }

    @Test
    public void returnsEmptyResultOnEmptyInput() {
        InputStream inputStream = inputStreamFromString("");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csvSortService.sortCsvFileByFirstField(inputStream, baos);

        String sortedCsv = baos.toString();
        assertThat(sortedCsv).isEmpty();
    }

    @Test
    public void preservesFileHeaderAfterSorting() throws Exception {
        InputStream inputStream = inputStreamFromString(preparePricesUnsortedCsv());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        csvSortService.sortCsvFileByFirstField(inputStream, baos);

        String sortedCsv = baos.toString();
        String[] lines = sortedCsv.split("\n");
        assertThat(lines).hasSize(4);

        assertThat(lines[0]).isEqualTo("PRODUCT_ID,DATE,PRICE");
    }

    @Test
    public void sortsSmallPricesCsv() throws Exception {
        InputStream inputStream = inputStreamFromString(preparePricesUnsortedCsv());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        csvSortService.sortCsvFileByFirstField(inputStream, baos);

        String sortedCsv = baos.toString();
        String[] lines = sortedCsv.split("\n");
        assertThat(lines).hasSize(4);

        assertThat(lines[1]).startsWith("1,");
        assertThat(lines[2]).startsWith("2,");
        assertThat(lines[3]).startsWith("3,");
    }

    @Test
    public void sortsSmallProductsCsv() throws Exception {
        InputStream inputStream = inputStreamFromString(prepareProductsUnsortedCsv());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        csvSortService.sortCsvFileByFirstField(inputStream, baos);

        String sortedCsv = baos.toString();
        String[] lines = sortedCsv.split("\n");
        assertThat(lines).hasSize(4);

        assertThat(lines[0]).isEqualTo("PRODUCT_ID,PRODUCT_DESCRIPTION");
        assertThat(lines[1]).startsWith("1,");
        assertThat(lines[2]).startsWith("2,");
        assertThat(lines[3]).startsWith("3,");
    }

    @Test
    public void sortsBigInputData() throws Exception {
        InputStream inputStream = inputStreamFromString(preparePricesUnsortedCsv(98000));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        csvSortService.sortCsvFileByFirstField(inputStream, baos);

        String sortedCsv = baos.toString();
        String[] lines = sortedCsv.split("\n");
        assertThat(lines).hasSize(98001);

        assertThat(lines[2]).isEqualTo("1,2015-01-01,15.25");
        assertThat(lines[3]).isEqualTo("10,2015-01-01,105.25");
        assertThat(lines[98000]).isEqualTo("9999,2015-01-01,99995.25");
    }

}
