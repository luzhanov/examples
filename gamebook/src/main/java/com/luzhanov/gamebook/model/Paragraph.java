package com.luzhanov.gamebook.model;

public class Paragraph {

    private Long id;
    private Long bookId;
    private Long number;
    private String text;

    public Paragraph() {
    }

    public Paragraph(Long id, Long bookId, Long number, String text) {
        this.id = id;
        this.bookId = bookId;
        this.number = number;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
