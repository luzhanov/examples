package com.luzhanov.gamebook.model;

public class GameSession {

    private Long id;
    private Long userId;
    private Long bookId;
    private Long currentParagraph;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getCurrentParagraph() {
        return currentParagraph;
    }

    public void setCurrentParagraph(Long currentParagraph) {
        this.currentParagraph = currentParagraph;
    }
}
