package cvut.fel.dto;

import cvut.fel.model.Address;
import cvut.fel.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PublisherDTO extends AbstractDTO{
    private Address address;
    private List<Book> books;

    public PublisherDTO(PublisherDTO dto) {
        super(dto);
        address = dto.getAddress();
        books = dto.getBooks();
    }

    @Override
    protected AbstractDTO clone() {
        return new PublisherDTO(this);
    }
}
