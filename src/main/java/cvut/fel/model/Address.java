package cvut.fel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "house_number")
    private int houseNumber;

    @Column(name = "post_code")
    private String postcode;

    @Column(name = "street")
    private String street;
}
