package cvut.fel.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
public class AuthorCreate {
    @NotEmpty
    @Size(max = 100)
    private String firstname;
    @NotEmpty
    private String surname;
    @NotNull
    private Long publisher;
    @NotEmpty
    private List<Long> books;
}
