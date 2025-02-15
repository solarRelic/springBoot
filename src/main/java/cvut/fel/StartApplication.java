package cvut.fel;

import cvut.fel.controller.AuthorController;
import cvut.fel.model.*;
import cvut.fel.exception.NotFoundException;
import cvut.fel.repository.*;
import cvut.fel.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class  StartApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorController authorController;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private AddressService addressService;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) {

        log.info("StartApplication...");

        log.info("Creating 4 books named: Java, Node, Python, Omgg. After that, realisation of 3 scenarios from sequence diagrams");
        Book book1 = new Book("Java");
        book1.setISBN("1");
        Book book2 = new Book("Node");
        book2.setISBN("2");
        Book book3 = new Book("Python");
        book3.setISBN("3");
        Book book4 = new Book("Omgg");
        book4.setISBN("4");
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        bookService.addBook(book4);

        log.debug("Testing findAll() method of bookService to find all previously created books.");
        System.out.println("\nfindAll()");
        List<Book> books = bookService.findAll();
        for (Book book : books) {
            System.out.println(book.toString());
        }
//        bookRepository.findAll().forEach(System.out::println);

        System.out.println("\nfindById(1L)");
        Book foundBook = bookService.findById(1L);
        System.out.println("Found: " + foundBook.toString());
//        bookRepository.findById(1L).ifPresent(System.out::println);

        System.out.println("\nfindByName('Node')");
        List<Book> foundBooks = bookService.findByName("Node");
        System.out.print("Found: ");
        for (Book b : foundBooks) {
            System.out.print(foundBook.toString() + " ");
        }
        System.out.println();
//        bookRepository.findByName("Node").forEach(System.out::println);
        System.out.println();

        // Author signs contract with a publisher =====================================================================================================================================
        log.debug("Creating Author -- name: Greg, surname: Smith, email: greg@gmail.com");
        Author author1 = new Author("greg@gmail.com", "Greg", "Smith");
        author1.setGender(Gender.MALE);
        author1.setId(1L);
        authorService.addAuthor(author1);
        System.out.println("\nGreg Smith's email: ");
        String tmp = authorRepository.findById(author1.getId()).get().getEmail();
        System.out.println(tmp);

        System.out.println("\nbefore adding: authorContracts('Greg')");
        List<Publisher> contracts = authorRepository.findById(author1.getId()).get().getPublishers();
        if (!contracts.isEmpty()) {
            for (Publisher p : contracts) {
                System.out.print(p.getName() + " ");
            }
            System.out.println();
        } else {
            log.warn("Author Greg does not have any contracts with publishers");
            System.out.println("No contracts :(");
        }

        log.debug("Adding new publisher : Oxford");
        Publisher publisher1 = new Publisher("Oxford");
        publisherService.addPublisher(publisher1);
        authorService.signContract(author1, publisher1);

        List<Publisher> contractss = authorRepository.findById(author1.getId()).get().getPublishers();
        System.out.println("\nafter adding: authorContracts('Greg')");
        if (!contractss.isEmpty()) {
            for (Publisher p : contractss) {
                System.out.print(p.getName() + " ");
            }
            System.out.println();
        } else {
            log.error("Publisher Oxford was not added to the author Greg Smith");
            System.out.println("No contracts :(");
        }
        System.out.println();

        // Publisher publishes new book =====================================================================================================================================
        Book newBook = new Book("Harry Potter");
        newBook.setISBN("0909");
        publisherService.publish(publisher1, newBook);

        Publisher updatedPublisher = publisherService.findById(publisher1.getId()).orElse(null);
        System.out.println("Adding new book " + newBook.getName() + " to publisher " + updatedPublisher.getName());
        if (updatedPublisher == null) throw new NotFoundException("1");
        List<Book> publisherBooks = updatedPublisher.getBooks();
        if (!publisherBooks.isEmpty()) {
            System.out.print(updatedPublisher.getName() + "'s books: ");
            for (Book b : publisherBooks) {
                System.out.print(b.getName() + " ");
            }
            System.out.println();
        } else {
            System.out.println(publisher1.getName() + " does not have any books.");
        }
//        System.out.println(bookService.findById(1L).toString());
        System.out.println();

        // Add book to library =====================================================================================================================================
        Address address = new Address();
        address.setHouseNumber(2710);
        address.setPostcode("16080");
        address.setStreet("Technicka");
        addressService.addAddress(address);

        Library lib = new Library();
        lib.setName("NTK");
        lib.setAddress(address);
        libraryService.addLibrary(lib);
        System.out.print(lib.getName() + "'s book list before adding the book \'"+book4.getName()+"\'" + " : ");
        for (Book b : libraryRepository.findById(lib.getId()).get().getBookList()) {
            System.out.print(b.getName() + " ");
        }
        System.out.println();

        libraryService.addBookToLib(lib, book4);

        System.out.println("Adding new book " + book4.getName() + " to the library " + lib.getName());
        System.out.print(lib.getName() + "'s book list after adding the book \'"+book4.getName()+"\'" + " : ");
        for (Book b : libraryRepository.findById(lib.getId()).get().getBookList()) {
            System.out.print(b.getName() + " ");
        }
        System.out.println();

        System.out.println();
    }
}