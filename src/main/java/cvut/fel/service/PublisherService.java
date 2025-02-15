package cvut.fel.service;

import cvut.fel.model.Book;
import cvut.fel.model.Publisher;

import java.util.Optional;

public interface PublisherService {
    Publisher addPublisher(Publisher publisher);
    Optional<Publisher> findById(Long id);
    boolean publish(Publisher publisher, Book book);
}
