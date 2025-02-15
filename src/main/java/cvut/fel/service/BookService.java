package cvut.fel.service;

import cvut.fel.model.Book;

import java.util.List;

public interface BookService {

    Book findById(Long id);
    List<Book> findByName(String name);
    Book addBook(Book book);
    List<Book> findAll();
}
