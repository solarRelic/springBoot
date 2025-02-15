package cvut.fel.service;

import cvut.fel.model.Address;
import cvut.fel.exception.FieldMissingException;
import cvut.fel.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImp implements AddressService{
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImp(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    @Override
    public Address addAddress(Address address) {
        if (address == null) {
            throw new FieldMissingException();
        }
        return addressRepository.save(address);
    }
}
