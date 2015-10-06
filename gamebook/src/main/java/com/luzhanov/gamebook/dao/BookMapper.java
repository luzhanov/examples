package com.luzhanov.gamebook.dao;

import com.luzhanov.gamebook.model.Book;

import java.util.List;

public interface BookMapper {

    List<Book> selectAllBooks();

    Book selectBookById(Long bookId);

    void insertBook(Book book);

}
