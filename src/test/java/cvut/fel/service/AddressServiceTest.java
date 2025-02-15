package cvut.fel.service;

import cvut.fel.model.Address;
import cvut.fel.exception.FieldMissingException;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AddressServiceTest {

    @Autowired
    private AddressService service;

    @Autowired
    private EntityManager em;

    private Address generateAddress() {
        Address address = new Address();
        address.setHouseNumber(123);
        address.setPostcode("12345");
        address.setStreet("Super Main Street");
        em.persist(address);
        return address;
    }

    @Test
    public void addAddress_validAddress_addressSaved() {
        // Arrange
        Address address = generateAddress();

        // Act
        Address savedAddress = service.addAddress(address);

        // Assert
        assertSame(address, savedAddress);
    }

    @Test(expected = FieldMissingException.class)
    public void addAddress_nullAddress_throwsFieldMissingException() {
        // Arrange
        Address address = null;

        // Act and Assert
        service.addAddress(null);

        fail("Exception not thrown.");
    }

}
