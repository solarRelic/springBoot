package cvut.fel.service;

import cvut.fel.model.Book;

import cvut.fel.exception.FieldInvalidException;
import cvut.fel.exception.FieldMissingException;
import cvut.fel.exception.NotFoundException;
import cvut.fel.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(Long id){
        if (id == null)
            throw new FieldInvalidException("id is null");
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BOOK_NOT_FOUND"));
    }

    public List<Book> findByName(String name) {
        if (name == null)
            throw new FieldInvalidException("name is null");
        List<Book> booksWithName = bookRepository.findByName(name);
        if (booksWithName.isEmpty()) {
            throw new NotFoundException("0", "No books with such name: " + name);
        } else {
            return booksWithName;
        }
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book addBook(Book bookToAdd) {
        if (bookToAdd == null) {
            throw new FieldMissingException();
        }
        return bookRepository.save(bookToAdd);
    }

}
