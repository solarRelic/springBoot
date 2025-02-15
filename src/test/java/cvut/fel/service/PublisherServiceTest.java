package cvut.fel.service;

import cvut.fel.model.Book;
import cvut.fel.model.Publisher;
import cvut.fel.exception.FieldMissingException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class PublisherServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PublisherService service;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void findById_existingPublisher_publisherRetrieved() {
        // Arrange
        Publisher publisher = generatePublisher();

        // Act
        Optional<Publisher> foundPublisher = service.findById(publisher.getId());

        // Assert
        assertTrue(foundPublisher.isPresent());
        assertSame(publisher, foundPublisher.get());
    }

    @Test
    public void findById_nonExistingPublisher_emptyOptional() {
        // Arrange
        Long publisherId = Long.MIN_VALUE;

        // Act
        Optional<Publisher> foundPublisher = service.findById(publisherId);

        // Assert
        assertFalse(foundPublisher.isPresent());
    }

    @Test
    public void addPublisher_addingValidPublisher_publisherAdded() {
        // Arrange
        Publisher publisher = new Publisher("Valid Publisher");

        // Act
        Publisher addedPublisher = service.addPublisher(publisher);

        // Assert
        assertSame(publisher, addedPublisher);
    }

    @Test(expected = FieldMissingException.class)
    public void addPublisher_addingNullPublisher_thrownException() {
        // Arrange
        Publisher nullPublisher = null;

        // Act
        service.addPublisher(nullPublisher);

        fail("Exception not thrown.");
    }

    @Test
    public void publish_validPublisherAndBook_publishSuccessful() {
        // Arrange
        Publisher publisher = generatePublisher();
        Book book = generateBook();

        // Act
        boolean published = service.publish(publisher, book);

        // Assert
        assertTrue(published);
        assertSame(publisher, book.getPublisher());
        assertTrue(publisher.getBooks().contains(book));
    }

    @Test(expected = FieldMissingException.class)
    public void publish_nullPublisherOrBook_thrownException() {
        // Arrange
        Publisher nullPublisher = null;
        Book nullBook = null;

        // Act
        service.publish(nullPublisher, nullBook);

        fail("Exception not thrown.");
    }

    @Test
    public void publish_bookAlreadyPublishedWithPublisher_thrownException() {
        // Arrange
        Publisher publisher = generatePublisher();
        Book book = generateBook();
        service.publish(publisher, book);

        // Act
        boolean result = service.publish(publisher, book);

        // Assert
        assertFalse(result);
    }

    private Publisher generatePublisher() {
        Publisher publisher = new Publisher("Publisher");
        em.persist(publisher);
        return publisher;
    }

    private Book generateBook() {
        Book book = new Book("Book Name");
        book.setISBN("12345");
        em.persist(book);
        return book;
    }
}
