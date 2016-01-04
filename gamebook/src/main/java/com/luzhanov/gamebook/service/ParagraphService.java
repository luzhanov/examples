package com.luzhanov.gamebook.service;

import com.luzhanov.gamebook.model.Paragraph;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class ParagraphService {

    public Long createParagraph(Paragraph paragraph) {
        //todo: implement

        return null;
    }

    public Paragraph getParagraph(Long bookId, Long paragraphId) {
        //todo: implement

        return new Paragraph(0L, RandomStringUtils.random(1000));
    }


    //todo: write update

    //todo: write delete

}
