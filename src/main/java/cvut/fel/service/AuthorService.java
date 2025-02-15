package cvut.fel.service;

import cvut.fel.model.Author;
import cvut.fel.model.Publisher;

import java.util.Optional;

public interface AuthorService {
    //    Optional<Publisher> addBook(Publisher publisher);
    Optional<Author> findById(Long id);
    Author addAuthor(Author author);
    void upd(Author author);
    boolean signContract(Author author, Publisher publisher);
}
