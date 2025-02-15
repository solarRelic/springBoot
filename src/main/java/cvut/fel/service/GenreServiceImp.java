package cvut.fel.service;

import cvut.fel.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImp implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImp(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
}
