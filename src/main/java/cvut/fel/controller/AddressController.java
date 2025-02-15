package cvut.fel.controller;

import cvut.fel.dto.DTOMapper;
import cvut.fel.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    private final AddressService addressService;
    private final DTOMapper dtoMapper;

    @Autowired
    public AddressController(AddressService addressService, DTOMapper dtoMapper) {
        this.addressService = addressService;
        this.dtoMapper = dtoMapper;
    }

}
