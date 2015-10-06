package com.luzhanov.gamebook.rest;

import com.luzhanov.gamebook.model.Book;
import com.luzhanov.gamebook.model.Paragraph;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameBookController {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/books")
    public List<Book> getBooksList() {
        //todo: implement

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "Book1", "Author1"));
        bookList.add(new Book(2L, "Book2", "Author2"));
        return bookList;
    }

    @RequestMapping("/books/{bookId}")
    public Book getBook(@PathVariable("bookId") Long bookId) {
        //todo: implement
        return new Book(bookId, "Some name", "Author name");
    }

    @RequestMapping("/books/{bookId}/paragraph/{paragraphId}")
    public Paragraph getParagraph(@PathVariable("bookId") Long bookId,
                                  @PathVariable("paragraphId") Long paragraphId) {
        //todo: implement
        return new Paragraph(paragraphId, bookId, 1L, "Once upon a time");
    }

}
