package cvut.fel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter
@Setter
public class Genre extends AbstractEntity {
    @Id
    private Long id;

    @OneToMany(mappedBy = "genre")
    private List<Book> books;

}

/*
 in folder resources create file import.sql. Fill two groups by heroes (gandalf power 10/ will 2/ magic 10; bilbo 3/10/1; thorin 10/5/0) a (Bolg 10/10/0; golfimbul 6/4/0, necromancer 3/3/10)
 */