package cvut.fel.dto;

import cvut.fel.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DTOMapper {
    BookDTO bookToDto(Book book);
    Book dtoToBook(BookDTO bookDTO);

    PublisherDTO publisherToDto(Publisher publisher);
    Publisher dtoToPublisher(PublisherDTO publisherDTO);

    LibraryDTO libraryToDto(Library library);
    Library dtoToLibrary(LibraryDTO libraryDTO);

    AuthorDTO authorToDto(Author author);
    Author dtoToAuthor(AuthorDTO authorDTO);
}
