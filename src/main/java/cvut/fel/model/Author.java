package cvut.fel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(mappedBy = "contracts",
            fetch = FetchType.EAGER)
    private List<Publisher> publishers = new ArrayList<>();

    @ManyToMany(mappedBy = "authors")
    private List<Book> bookList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    protected Author(){}

    public Author(String email, String firstname, String surname) {
        this.email = email;
        this.firstname = firstname;
        this.surname = surname;
    }

}
