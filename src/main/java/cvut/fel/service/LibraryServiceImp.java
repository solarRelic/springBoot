package cvut.fel.service;

import cvut.fel.model.Book;
import cvut.fel.model.Library;
import cvut.fel.exception.FieldInvalidException;
import cvut.fel.exception.FieldMissingException;
import cvut.fel.repository.BookRepository;
import cvut.fel.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LibraryServiceImp implements LibraryService{
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryServiceImp(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Library findById(Long id) {
        if (id == null) throw new FieldInvalidException("No such lib id: ");
        Library lib = libraryRepository.findById(id)
                .orElseThrow(() -> new FieldInvalidException("No lib found with id: " + id));
        return lib;
    }

    @Override
    public Library addLibrary(Library library) {
        if (library == null) throw new FieldMissingException();
        return libraryRepository.save(library);
    }

    public boolean hasBook(Library library, Book book) {
        if (library == null || book == null) throw new FieldMissingException();
        Optional<Library> libOpt = libraryRepository.findById(library.getId());
        Optional<Book> bookOpt = bookRepository.findById(book.getId());
        if (libOpt.isPresent() && bookOpt.isPresent()) {
            Library lib = libOpt.get();
            Book bookToAdd = bookOpt.get();
            List<Book> booksInLib = lib.getBookList();
            for (Book b : booksInLib) {
                if (Objects.equals(b.getId(), bookToAdd.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean addBookToLib(Library library, Book book) {
        if (library == null || book == null) throw new FieldMissingException();
        Optional<Library> libOpt = libraryRepository.findById(library.getId());
        Optional<Book> bookOpt = bookRepository.findById(book.getId());
        if (libOpt.isPresent() && bookOpt.isPresent()) {
            Library lib = libOpt.get();
            Book bookToAdd = bookOpt.get();
            if (!hasBook(lib, bookToAdd)) {
                lib.getBookList().add(bookToAdd);
                bookToAdd.setLibrary(lib);
                libraryRepository.save(lib);
                bookRepository.save(bookToAdd);
                return true;
            }
        }
        return false;
    }
}
