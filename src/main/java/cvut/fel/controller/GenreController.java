package cvut.fel.controller;

import cvut.fel.dto.DTOMapper;
import cvut.fel.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController {
    private final GenreService genreService;
    private final DTOMapper dtoMapper;

    @Autowired
    public GenreController(GenreService genreService, DTOMapper dtoMapper) {
        this.genreService = genreService;
        this.dtoMapper = dtoMapper;
    }



}
