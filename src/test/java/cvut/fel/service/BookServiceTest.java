package cvut.fel.service;

import cvut.fel.model.Book;
import cvut.fel.exception.FieldMissingException;
import cvut.fel.exception.NotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest()
public class BookServiceTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private BookService service;

	@BeforeEach
	public void setUp(){
	}

	@Test
	public void findById_existingBook_bookRetrieved(){
		// Arrange
		Book book = generateBook();

		// Act
		Book foundBook = service.findById(book.getId());

		//Assert
		assertSame(book, foundBook);
	}

	@Test(expected = NotFoundException.class)
	public void findById_wrongBookId_expectException(){
		// Arrange
		Long bookId = Long.MIN_VALUE;

		// Act
		Book foundBook = service.findById(bookId);


		fail("Exception not thrown.");
	}

	@Test
	public void findByName_existingBook_bookRetrieved() {
		// Arrange
		Book book = generateBook();

		// Act
		List<Book> retrieved = service.findByName(book.getName());

		// Assert
		assertSame(book, retrieved.get(0));
	}

	@Test(expected = NotFoundException.class)
	public void findByName_nonExistingBook_thrownException() {
		// Arrange
		String randName = "..4fl2';";

		// Act
		List<Book> retrieved = service.findByName(randName);

		fail("Exception not thrown");
	}

	@Test
	public void findAll_existingBooks_allFound() {
		// Arrange
		List<Book> generatedBooks = generateBooks();

		// Act
		List<Book> retrieved = service.findAll();

		//Assert
		int matches = 0;
		for (Book generatedBook : generatedBooks) {
			for (Book retrievedBook : retrieved) {
				if (Objects.equals(generatedBook, retrievedBook)) {
					matches++;
					break;
				}
			}
		}
		assertEquals(matches, generatedBooks.size());
	}

	@Test
	public void addBook_addingBook_bookAdded() {
		// Arrange
		Book book = generateBook();

		// Act
		service.addBook(book);
		Book addedBook = service.findById(book.getId());

		// Assert
		assertSame(book, addedBook);
	}

	@Test(expected = FieldMissingException.class)
	public void addBook_addingNull_thrownException() {
		// Arrange
		Book bookNull = null;

		// Act
		service.addBook(bookNull);

		fail("Exception not thrown");
	}

	private Book generateBook(){
		Book book = new Book("name");
		book.setISBN("1010");
		em.persist(book);
		return book;
	}

	private List<Book> generateBooks(){
		List<Book> allBooks = new ArrayList<>();
		Book book1 = new Book("book1");
		book1.setISBN("1010");
		allBooks.add(book1);
		Book book2 = new Book("book2");
		book2.setISBN("2020");
		allBooks.add(book2);
		Book book3 = new Book("book3");
		book3.setISBN("3030");
		allBooks.add(book3);

		em.persist(book1);
		em.persist(book2);
		em.persist(book3);

		return allBooks;
	}

}
