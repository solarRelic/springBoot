package cvut.fel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "library")
@Getter
@Setter
public class Library extends AbstractEntity{
    @Id
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER)
    private List<Book> bookList = new ArrayList<>();
}
