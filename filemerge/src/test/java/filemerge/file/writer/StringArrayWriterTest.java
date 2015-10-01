package filemerge.file.writer;


import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class StringArrayWriterTest {

    @Test
    public void doesNotWritesHeaderRowAfterFirstWrite() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringArrayWriter writer = new StringArrayWriter(baos);

        writer.writeObject(new String[]{"aa", "bb", "cc"});
        writer.close();

        String result = baos.toString();
        String[] lines = result.split("\n");

        assertThat(result).isNotNull();
        assertThat(lines).hasSize(1);

        assertThat(lines[0]).isEqualTo("aa,bb,cc");
    }

    @Test
    public void writesStringArraysToStream() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringArrayWriter writer = new StringArrayWriter(baos);

        writer.writeObject(new String[]{"aa", "bb", "cc"});
        writer.writeObject(new String[]{"11", "22", "33"});
        writer.close();

        String result = baos.toString();
        String[] lines = result.split("\n");

        assertThat(lines).hasSize(2);
        assertThat(lines[0]).isEqualTo("aa,bb,cc");
        assertThat(lines[1]).isEqualTo("11,22,33");
    }

    @Test
    public void returnsEmptyResultOnNullDataWrite() throws Exception  {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringArrayWriter writer = new StringArrayWriter(baos);

        writer.writeObject(null);
        writer.close();

        String result = baos.toString();
        assertThat(result).isEqualTo("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullStreamInput() throws Exception  {
        new StringArrayWriter(null);
    }


}