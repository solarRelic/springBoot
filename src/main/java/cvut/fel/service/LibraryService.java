package cvut.fel.service;

import cvut.fel.model.Book;
import cvut.fel.model.Library;

public interface LibraryService {
    Library findById(Long id);
    Library addLibrary(Library library);
    boolean hasBook(Library library, Book book);
    boolean addBookToLib(Library library, Book book);
}

