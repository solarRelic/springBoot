package cvut.fel.service;

import cvut.fel.model.Author;
import cvut.fel.model.Publisher;
import cvut.fel.exception.FieldInvalidException;
import cvut.fel.exception.FieldMissingException;
import cvut.fel.repository.AuthorRepository;
import cvut.fel.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AuthorServiceImp implements AuthorService{
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public AuthorServiceImp(AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public void upd(Author author) {
        if (author == null) {
            throw new FieldMissingException();
        }
        authorRepository.save(author);
    }

    @Override
    public Author addAuthor(Author author) {
        if (author == null) {
            throw new FieldMissingException();
        }
        return authorRepository.save(author);
    }

    @Transactional
    public boolean signContract(Author author, Publisher publisher) {
        if (author == null|| publisher == null) throw new FieldInvalidException("author or publisher is null");
        Optional<Author> authorOptional = authorRepository.findById(author.getId());
        Optional<Publisher> publisherOptional = publisherRepository.findById(publisher.getId());
        if (authorOptional.isPresent() && publisherOptional.isPresent()) {
            Author curAuthor = authorOptional.get();
            Publisher curPublisher = publisherOptional.get();

            if (!curAuthor.getPublishers().contains(curPublisher)) {
                curAuthor.getPublishers().add(curPublisher);
                curPublisher.getContracts().add(curAuthor);
                authorRepository.save(curAuthor);
                publisherRepository.save(curPublisher);
                return true;
            }

        }
        return false;
    }
}
