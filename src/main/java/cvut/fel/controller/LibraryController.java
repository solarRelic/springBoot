package cvut.fel.controller;

import cvut.fel.dto.BookDTO;
import cvut.fel.dto.DTOMapper;
import cvut.fel.dto.LibraryDTO;
import cvut.fel.model.Book;
import cvut.fel.model.Library;
import cvut.fel.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LibraryController {
    private final LibraryService libraryService;
    private final DTOMapper dtoMapper;

    @Autowired
    public LibraryController(LibraryService libraryService, DTOMapper dtoMapper) {
        this.libraryService = libraryService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("library/{id}")
    public ResponseEntity<LibraryDTO> getLibraryById(@PathVariable Long id) {
        Library library = libraryService.findById(id);
        return ResponseEntity.ok(dtoMapper.libraryToDto(library));
    }

    @PostMapping("/library")
    public ResponseEntity<LibraryDTO> addLibrary(@RequestBody LibraryDTO libraryDTO) {
        Library libraryToAdd = dtoMapper.dtoToLibrary(libraryDTO);
        Library savedLibrary = libraryService.addLibrary(libraryToAdd);
        return ResponseEntity.ok(dtoMapper.libraryToDto(savedLibrary));
    }

    @PostMapping("/library/{libraryId}/book")
    public ResponseEntity<Boolean> addBookToLibrary(@PathVariable Long libraryId, @RequestBody BookDTO bookDTO) {
        Library library = libraryService.findById(libraryId);
        Book book = dtoMapper.dtoToBook(bookDTO);
        boolean added = libraryService.addBookToLib(library, book);
        return ResponseEntity.ok(added);
    }
}
