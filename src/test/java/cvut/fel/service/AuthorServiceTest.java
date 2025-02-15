package cvut.fel.service;

import cvut.fel.model.Author;
import cvut.fel.model.Publisher;
import cvut.fel.exception.FieldInvalidException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthorServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AuthorService service;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void signContract_validAuthorAndPublisher_contractSigned() {
        // Arrange
        Author author = generateAuthor();
        Publisher publisher = generatePublisher();

        // Act
        boolean result = service.signContract(author, publisher);

        // Assert
        assertTrue(result);
        assertTrue(publisher.getContracts().contains(author));
        assertTrue(author.getPublishers().contains(publisher));

        assertEquals(1, publisher.getContracts().size());
        assertEquals(1, author.getPublishers().size());
    }

    @Test
    public void signContract_authorAlreadySigned_returnsFalse() {
        // Arrange
        Author author = generateAuthor();
        Publisher publisher = generatePublisher();

        // First contract signing
        service.signContract(author, publisher);

        // Act
        boolean result = service.signContract(author, publisher);

        // Assert
        assertFalse(result);
        assertEquals(1, publisher.getContracts().size()); // Ensure no duplicates
        assertEquals(1, author.getPublishers().size());
    }

    @Test(expected = FieldInvalidException.class)
    public void signContract_nullAuthorOrPublisher_throwsFieldMissingException() {
        // Arrange
        Author author = null;
        Publisher publisher = null;

        // Act
        service.signContract(author, publisher);

        fail("Exception not thrown.");
    }

    @Test
    public void signContract_nonExistentAuthorOrPublisher_returnsFalse() {
        // Arrange
        Author author = generateAuthor();
        Publisher publisher = generatePublisher();

        author.setId(-999L); // Set invalid ID
        publisher.setId(-989L); // Set invalid ID

        // Act
        boolean result = service.signContract(author, publisher);

        // Assert
        assertFalse(result); // Signing should fail for non-existent entities
    }


    private Author generateAuthor() {
        Author author = new Author("author@test.com", "Test", "Author");
//        author.setGender(Gender.MALE);
        em.persist(author);
        return author;
    }

    private Publisher generatePublisher() {
        Publisher publisher = new Publisher("SomeePub");
        em.persist(publisher);
        return publisher;
    }

}
