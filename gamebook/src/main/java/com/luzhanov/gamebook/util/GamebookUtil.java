package com.luzhanov.gamebook.util;

import com.luzhanov.gamebook.model.Paragraph;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GamebookUtil {

    public static void printParagraphsNumberAndText(List<Paragraph> paragraphList) {
        paragraphList.forEach(p -> System.out.println(p.getNumber() + "\n" + p.getText() + "\n"));
    }

    public static List<Paragraph> filterNonEmptyParagraphs(List<Paragraph> paragraphList) {
        return paragraphList.stream()
                .filter(p -> (p.getText() != null && !p.getText().isEmpty()))
                .collect(Collectors.toList());
    }

    public static void sortParagraphByNumber(List<Paragraph> paragraphList) {
        Collections.sort(paragraphList,
                (p1, p2) -> p1.getNumber().compareTo(p2.getNumber()));
    }

}
