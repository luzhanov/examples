package filemerge;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("StringBufferReplaceableByString")
public class TestDataUtils {

    public static InputStream inputStreamFromString(String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    public static String preparePricesUnsortedCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCT_ID,DATE,PRICE").append("\n");
        sb.append("2,2015-01-01,5.25").append("\n");
        sb.append("3,2015-02-01,6.25").append("\n");
        sb.append("1,2015-03-01,7.25").append("\n");

        return sb.toString();
    }

    public static String preparePricesUnsortedCsv(int count) {
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCT_ID,DATE,PRICE").append("\n");

        List<String> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String value = i + ",2015-01-01," + i + "5.25\n";
            values.add(value);
        }

        Collections.shuffle(values);

        for (String value : values) {
            sb.append(value);
        }

        return sb.toString();
    }

    public static String preparePricesSortedCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCT_ID,DATE,PRICE").append("\n");
        sb.append("1,2015-03-01,7.25").append("\n");
        sb.append("1,2015-03-02,17.25").append("\n");
        sb.append("2,2015-01-01,5.25").append("\n");
        sb.append("2,2015-01-02,15.25").append("\n");
        sb.append("3,2015-02-02,16.25").append("\n");
        sb.append("3,2015-02-01,6.25").append("\n");

        return sb.toString();
    }

    public static String prepareProductsUnsortedCsv() {
        StringBuilder sb = new StringBuilder();

        sb.append("PRODUCT_ID,PRODUCT_DESCRIPTION").append("\n");
        sb.append("3,PRODUCT 3").append("\n");
        sb.append("2,PRODUCT 2").append("\n");
        sb.append("1,PRODUCT 1").append("\n");

        return sb.toString();
    }

    public static String prepareProductsSortedCsv() {
        StringBuilder sb = new StringBuilder();

        sb.append("PRODUCT_ID,PRODUCT_DESCRIPTION").append("\n");
        sb.append("1,PRODUCT 1").append("\n");
        sb.append("2,PRODUCT 2").append("\n");
        sb.append("3,PRODUCT 3").append("\n");

        return sb.toString();
    }
}
