package com.luzhanov.cassandrademo.repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.ImmutableSet;
import com.luzhanov.cassandrademo.CassandraConfig;
import com.luzhanov.cassandrademo.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
@Slf4j
public class BookRepositoryIntegrationTest {

    private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace "
            + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    private static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    private static final String DATA_TABLE_NAME = "book";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CassandraAdminOperations adminTemplate;

    @BeforeClass
    public static void startCassandraEmbedded() throws Exception {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();

        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);

        TimeUnit.SECONDS.sleep(4);
    }

    @AfterClass
    public static void stopCassandraEmbedded() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }

    @Before
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Book.class, new HashMap<>());
    }

    @After
    public void dropTable() throws Exception{
        adminTemplate.dropTable(CqlIdentifier.cqlId(DATA_TABLE_NAME));
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Book1", "Publisher1", ImmutableSet.of("tag1", "tag2"));
        bookRepository.save(ImmutableSet.of(javaBook));
        final Iterable<Book> books = bookRepository.findByTitleAndPublisher("Book1", "Publisher1");
        assertThat(javaBook.getId()).isEqualTo(books.iterator().next().getId());
    }

    @Test
    public void whenUpdatingBooks_thenAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Book1", "Publisher1", ImmutableSet.of("tag1", "tag2"));
        bookRepository.save(ImmutableSet.of(javaBook));

        javaBook.setTitle("Book2");
        bookRepository.save(ImmutableSet.of(javaBook));
        final Iterable<Book> updateBooks = bookRepository.findByTitleAndPublisher("Book2", "Publisher1");
        assertThat(javaBook.getTitle()).isEqualTo(updateBooks.iterator().next().getTitle());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void whenDeletingExistingBooks_thenNotAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Book1", "Publisher1", ImmutableSet.of("tag1", "tag2"));
        bookRepository.save(ImmutableSet.of(javaBook));
        bookRepository.delete(javaBook);

        final Iterable<Book> books = bookRepository.findByTitleAndPublisher("Book1", "Publisher1");
        assertThat(javaBook.getId()).isNotEqualTo(books.iterator().next().getId());
    }

    @Test
    public void whenSavingBooks_thenAllShouldAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Book1", "Publisher1", ImmutableSet.of("tag1", "tag2"));
        final Book dPatternBook = new Book(UUIDs.timeBased(), "Book2", "Publisher1", ImmutableSet.of("tag1", "tag2"));
        bookRepository.save(ImmutableSet.of(javaBook));
        bookRepository.save(ImmutableSet.of(dPatternBook));

        final Iterable<Book> books = bookRepository.findAll();
        int bookCount = 0;
        for (final Book book : books) {
            bookCount++;
        }
        assertThat(bookCount).isEqualTo(2);
    }

}    





