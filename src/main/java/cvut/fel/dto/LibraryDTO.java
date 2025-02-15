package cvut.fel.dto;

import cvut.fel.model.Address;
import cvut.fel.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LibraryDTO extends AbstractDTO{
    private Address address;
    private List<Book> bookList;

    public LibraryDTO(LibraryDTO dto) {
        super(dto);
        address = dto.getAddress();
        bookList = dto.getBookList();
    }

    @Override
    protected AbstractDTO clone() {
        return new LibraryDTO(this);
    }
}
