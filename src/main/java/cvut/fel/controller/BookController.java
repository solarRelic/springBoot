package cvut.fel.controller;

import cvut.fel.dto.BookDTO;
import cvut.fel.dto.DTOMapper;
import cvut.fel.model.Book;
import cvut.fel.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class BookController {

    private final BookService bookService;
    private final DTOMapper dtoMapper;

    @Autowired
    public BookController(BookService bookService, DTOMapper dtoMapper) {
        this.bookService = bookService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(dtoMapper.bookToDto(bookService.findById(id)));
    }

    @GetMapping("/book/{name}")
    public ResponseEntity<List<BookDTO>> getBooksByName(@PathVariable String name) {
        List<Book> booksWithName = bookService.findByName(name);
        List<BookDTO> booksWithNameDTO = new ArrayList<>();
        for (Book bk : booksWithName) {
            booksWithNameDTO.add(dtoMapper.bookToDto(bk));
        }
        return ResponseEntity.ok(booksWithNameDTO);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> allBooks = bookService.findAll();
        List<BookDTO> allBooksDTO = allBooks.stream()
                                            .map(x -> dtoMapper.bookToDto(x))
                                            .collect(Collectors.toList());
        return ResponseEntity.ok(allBooksDTO);
    }

    @PostMapping("/book")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        Book bookToAdd = dtoMapper.dtoToBook(bookDTO);
        Book bookAdded = bookService.addBook(bookToAdd);
        return ResponseEntity.ok(dtoMapper.bookToDto(bookAdded));
    }

    @PutMapping("/book/{bookID}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book bookToEdit = bookService.findById(id);
        if (bookDTO.getName() != null) {
            bookToEdit.setName(bookDTO.getName());
        }
        if (bookDTO.getISBN() != null) {
            bookToEdit.setISBN(bookDTO.getISBN());
        }
        Book updatedBook = bookService.addBook(bookToEdit);
        return ResponseEntity.ok(dtoMapper.bookToDto(updatedBook));
    }
}
