package cvut.fel.controller;

import cvut.fel.dto.AuthorDTO;
import cvut.fel.dto.DTOMapper;
import cvut.fel.dto.PublisherDTO;
import cvut.fel.model.Author;
import cvut.fel.model.Publisher;
import cvut.fel.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final DTOMapper dtoMapper;

    @Autowired
    public AuthorController(AuthorService authorService, DTOMapper dtoMapper) {
        this.authorService = authorService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("author/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Optional<Author> authorOptional = authorService.findById(id);
        if (authorOptional.isPresent()) {
            return ResponseEntity.ok(dtoMapper.authorToDto(authorOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("author/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorService.findById(id);
        if (authorOptional.isPresent()) {
            Author authorToUpdate = authorOptional.get();
            if (authorDTO.getFirstname() != null) {
                authorToUpdate.setFirstname(authorDTO.getFirstname());
            }
            if (authorDTO.getBooksList() != null) {
                authorToUpdate.setBookList(authorDTO.getBooksList());
            }
            if (authorDTO.getPublishersList() != null) {
                authorToUpdate.setPublishers(authorDTO.getPublishersList());
            }
            authorService.upd(authorToUpdate);
            return ResponseEntity.ok(dtoMapper.authorToDto(authorToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/author")
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO authorDTO) {
        Author authorToAdd = dtoMapper.dtoToAuthor(authorDTO);
        Author savedAuthor = authorService.addAuthor(authorToAdd);
        return ResponseEntity.ok(dtoMapper.authorToDto(savedAuthor));
    }

    @PostMapping("author/{authorId}/publisher")
    public ResponseEntity<Boolean> signContract(@PathVariable Long authorId,
                                                @RequestBody PublisherDTO publisherDTO) {
        Optional<Author> author = authorService.findById(authorId);
        if (!author.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Publisher publisher = dtoMapper.dtoToPublisher(publisherDTO);

        boolean success = authorService.signContract(author.get(), publisher);
        return ResponseEntity.ok(success);
    }
}

