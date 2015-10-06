package com.luzhanov.gamebook.dao;

import com.luzhanov.gamebook.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/context.xml"})
@Transactional
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void insertAndReadBook() throws Exception {
        Book book = new Book("nameA", "authB");

        bookMapper.insertBook(book);
        assertThat(book.getBookId()).isNotNull();

        Book readBook = bookMapper.selectBookById(book.getBookId());
        assertThat(readBook).isNotNull();
        assertThat(readBook.getBookId()).isEqualTo(book.getBookId());
        assertThat(readBook.getName()).isEqualTo("nameA");
        assertThat(readBook.getAuthor()).isEqualTo("authB");
    }

}
