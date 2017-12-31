package com.serpear.crawler;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository repository;

    @Test
    public void testFindBySerialNumber() {
        Scraper scraper = mock(Scraper.class);
        when(scraper.scrape()).thenReturn(new HashMap<String, HashMap<String, String>>());
        Book book1 = new Book("The Adventures of Jack", "123", "http://books/catalogue/jack.html", new BigDecimal("50.95"));
        Book book2 = new Book("The Adventures of Joe", "321", "http://books/catalogue/joe.html", new BigDecimal("55.95"));
        entityManager.persist(book1);
        entityManager.persist(book2);

        List<Book> queriedBooks = repository.findBySerialNumber("123");

        assertThat(queriedBooks.get(0)).isEqualTo(book1);
    }
}