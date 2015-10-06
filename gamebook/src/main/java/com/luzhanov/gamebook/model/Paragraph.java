package com.luzhanov.gamebook.model;

public class Paragraph {

    private Long paragraphId;
    private Long bookId;
    private Long number = 0L;
    private String text = "";

    public Paragraph() {
    }

    public Paragraph(Long number, String text) {
        this.number = number;
        this.text = text;
    }

    public Paragraph(Long paragraphId, Long bookId, Long number, String text) {
        this.paragraphId = paragraphId;
        this.bookId = bookId;
        this.number = number;
        this.text = text;
    }

    public Long getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(Long paragraphId) {
        this.paragraphId = paragraphId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
