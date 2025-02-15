package cvut.fel.controller;

import cvut.fel.dto.BookDTO;
import cvut.fel.dto.DTOMapper;
import cvut.fel.dto.PublisherDTO;
import cvut.fel.model.Book;
import cvut.fel.model.Publisher;
import cvut.fel.exception.FieldInvalidException;
import cvut.fel.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublisherController {
    private final PublisherService publisherService;
    private final DTOMapper dtoMapper;

    @Autowired
    public PublisherController(PublisherService publisherService, DTOMapper dtoMapper) {
        this.publisherService = publisherService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(dtoMapper.publisherToDto(
                publisherService.findById(id)
                        .orElseThrow(() -> new FieldInvalidException("Publisher not found with id: " + id))));
    }

    @PostMapping
    public ResponseEntity<PublisherDTO> addPublisher(@RequestBody PublisherDTO publisherDTO) {
        Publisher publisherToAdd = dtoMapper.dtoToPublisher(publisherDTO);
        Publisher addedPublisher = publisherService.addPublisher(publisherToAdd);
        return ResponseEntity.ok(dtoMapper.publisherToDto(addedPublisher));
    }

    @PostMapping("/{publisherId}/publish")
    public ResponseEntity<Boolean> publishBook(@PathVariable Long publisherId,
                                               @RequestBody BookDTO bookDTO) {
        Book book = dtoMapper.dtoToBook(bookDTO);
        Publisher publisher = publisherService.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + publisherId));
        boolean result = publisherService.publish(publisher, book);
        return ResponseEntity.ok(result);
    }

//    @PostMapping("/{publisherId}/author")
//    public ResponseEntity<Boolean> signContract(@PathVariable Long publisherId,
//                                                @RequestBody AuthorDTO authorDTO) {
//        Publisher publisher = publisherService.findById(publisherId)
//                .orElseThrow(() -> new RuntimeException("Publisher not found"));
//        Author author = dtoMapper.dtoToAuthor(authorDTO);
//
//        boolean signed = publisherService.signContract(publisher, author);
//        return ResponseEntity.ok(signed);
//    }
}

