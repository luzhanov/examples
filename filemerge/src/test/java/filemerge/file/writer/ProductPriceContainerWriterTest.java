package filemerge.file.writer;

import filemerge.model.PriceGroup;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductPriceContainerWriterTest {

    @Test
    public void writesHeaderRowAutomaticallyAfterFirstWrite() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ProductPriceContainerWriter writer = new ProductPriceContainerWriter(baos);

        writer.writeObject(new PriceGroup(1L, "name1", new BigDecimal(1.1), new BigDecimal(1.2)));
        writer.close();

        String result = baos.toString();
        String[] lines = result.split("\n");

        assertThat(result).isNotNull();
        assertThat(lines).hasSize(2);

        assertThat(lines[0]).isEqualTo("PRODUCT_ID,PRODUCT_DESCRIPTION,PRICE");
    }

    @Test
    public void shouldWriteProductPriceContainerToStream() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ProductPriceContainerWriter writer = new ProductPriceContainerWriter(baos);

        writer.writeObject(new PriceGroup(1L, "name1", new BigDecimal(1.1), new BigDecimal(1.2)));
        writer.writeObject(new PriceGroup(2L, "name2", new BigDecimal(2.1), new BigDecimal(2.2)));
        writer.close();

        String result = baos.toString();
        String[] lines = result.split("\n");

        assertThat(lines).hasSize(3);
        assertThat(lines[2]).isEqualTo("2,name2,2.10,2.20");
    }

    @Test
    public void returnsEmptyResultOnNullDataWrite() throws Exception  {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ProductPriceContainerWriter writer = new ProductPriceContainerWriter(baos);

        writer.writeObject(null);
        writer.close();

        String result = baos.toString();
        assertThat(result).isEqualTo("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullStreamInput() throws Exception  {
        new ProductPriceContainerWriter(null);
    }

}
