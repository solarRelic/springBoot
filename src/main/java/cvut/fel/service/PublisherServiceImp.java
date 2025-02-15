package cvut.fel.service;

import cvut.fel.model.Book;
import cvut.fel.model.Publisher;
import cvut.fel.exception.FieldMissingException;
import cvut.fel.repository.BookRepository;
import cvut.fel.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImp implements PublisherService{
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PublisherServiceImp(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }

    public Publisher addPublisher(Publisher publisher) {
        if (publisher == null) {
            throw new FieldMissingException();
        }
        return publisherRepository.save(publisher);
    }

    @Override
    @Transactional
    public boolean publish(Publisher publisher, Book book) {
        if (book == null || publisher == null) {
            throw new FieldMissingException();
        }
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisher.getId());
        if (publisherOptional.isPresent()) {
            Publisher curPublisher = publisherOptional.get();
            if (curPublisher.getBooks().contains(book)) return false;
            Book curBook = bookRepository.save(book);

            curBook.setPublisher(curPublisher);
            List<Book> bookList = curPublisher.getBooks();
            bookList.add(curBook);

            publisherRepository.save(curPublisher);
            bookRepository.save(curBook);
            return true;
        }
        return false;
    }
}
