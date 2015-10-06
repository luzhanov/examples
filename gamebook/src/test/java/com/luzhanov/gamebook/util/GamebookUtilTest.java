package com.luzhanov.gamebook.util;

import com.luzhanov.gamebook.model.Paragraph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.luzhanov.gamebook.util.GamebookUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GamebookUtilTest {

    @Test
    public void testPrintParagraphsNumberAndText() throws Exception {
        List<Paragraph> paragraphList = new ArrayList<>();
        paragraphList.add(new Paragraph(1L, "TextA"));
        paragraphList.add(new Paragraph(2L, "TextB"));
        paragraphList.add(new Paragraph(3L, "TextB"));

        printParagraphsNumberAndText(paragraphList);
    }

    @Test
    public void testFilterNonEmptyParagraphs() throws Exception {
        List<Paragraph> paragraphList = new ArrayList<>();
        paragraphList.add(new Paragraph(1L, "TextA"));
        paragraphList.add(new Paragraph(2L, ""));
        paragraphList.add(new Paragraph(3L, null));

        List<Paragraph> resultList = filterNonEmptyParagraphs(paragraphList);

        assertThat(resultList).hasSize(1);
        assertThat(resultList.get(0).getNumber()).isEqualTo(1L);
        assertThat(resultList.get(0).getText()).isEqualTo("TextA");
    }

    @Test
    public void testSortParagraphByNumber() throws Exception {
        List<Paragraph> paragraphList = new ArrayList<>();
        paragraphList.add(new Paragraph(3L, "TextA"));
        paragraphList.add(new Paragraph(1L, "TextB"));
        paragraphList.add(new Paragraph(2L, "TextC"));

        sortParagraphByNumber(paragraphList);
        assertThat(paragraphList).hasSize(3);
        assertThat(paragraphList.get(0).getNumber()).isEqualTo(1L);
        assertThat(paragraphList.get(1).getNumber()).isEqualTo(2L);
        assertThat(paragraphList.get(2).getNumber()).isEqualTo(3L);
    }
}
