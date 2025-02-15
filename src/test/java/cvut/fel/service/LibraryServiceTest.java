package cvut.fel.service;

import cvut.fel.model.Address;
import cvut.fel.model.Book;
import cvut.fel.model.Library;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class LibraryServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private LibraryService service;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void addLibrary_validLibrary_libraryAdded() {
        // Arrange
        Library library = generateLibrary();

        // Act
        Library addedLibrary = service.addLibrary(library);

        // Assert
        assertSame(library, addedLibrary);
    }

    @Test(expected = FieldMissingException.class)
    public void addLibrary_nullLibrary_thrownException() {
        // Arrange
        Library nullLibrary = null;

        // Act
        service.addLibrary(nullLibrary);

        fail("Exception not thrown.");
    }

    @Test
    public void hasBook_libraryHasBook_returnsTrue() {
        // Arrange
        Library library = generateLibrary();
        Book book = generateBook();
        service.addBookToLib(library, book);

        // Act
        boolean result = service.hasBook(library, book);

        // Assert
        assertTrue(result);
    }

    @Test
    public void hasBook_libraryDoesNotHaveBook_returnsFalse() {
        // Arrange
        Library library = generateLibrary();
        Book book = generateBook();

        // Act
        boolean result = service.hasBook(library, book);

        // Assert
        assertFalse(result);
    }

    @Test(expected = FieldMissingException.class)
    public void hasBook_nullLibraryOrBook_thrownException() {
        // Arrange
        Library nullLibrary = null;
        Book nullBook = null;

        // Act
        service.hasBook(nullLibrary, nullBook);

        fail("Exception not thrown.");
    }

    @Test
    public void addBookToLib_validLibraryAndBook_bookAdded() {
        // Arrange
        Library library = generateLibrary();
        Book book = generateBook();

        // Act
        boolean result = service.addBookToLib(library, book);

        // Assert
        assertTrue(result);
        assertSame(library, book.getLibrary());
        assertTrue(library.getBookList().contains(book));
    }

    @Test
    public void addBookToLib_duplicateBook_bookNotAdded() {
        // Arrange
        Library library = generateLibrary();
        Book book = generateBook();
        service.addBookToLib(library, book);

        // Act
        boolean result = service.addBookToLib(library, book);

        // Assert
        assertFalse(result);
    }

    @Test(expected = FieldMissingException.class)
    public void addBookToLib_nullLibraryOrBook_thrownException() {
        // Arrange
        Library nullLibrary = null;
        Book nullBook = null;

        // Act
        service.addBookToLib(nullLibrary, nullBook);

        fail("Exception not thrown.");
    }

    private Library generateLibrary() {
        Address address = new Address();
        address.setStreet("Main Street");
        address.setHouseNumber(394);
        address.setPostcode("11000");
        em.persist(address);

        Library library = new Library();
        library.setName("NEXT");
        library.setAddress(address);
        em.persist(library);

        return library;
    }

    private Book generateBook() {
        Book book = new Book("Test Book");
        book.setISBN("12345");
        em.persist(book);
        return book;
    }
}

