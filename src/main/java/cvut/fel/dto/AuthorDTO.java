package cvut.fel.dto;

import cvut.fel.model.Book;
import cvut.fel.model.Publisher;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorDTO {
    private String firstname;
    private List<Book> booksList;
    private List<Publisher> publishersList;

    public AuthorDTO(AuthorDTO dto) {
        firstname = dto.getFirstname();
        booksList = dto.getBooksList();
        publishersList = dto.getPublishersList();
    }
}
