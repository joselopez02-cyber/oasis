package cine.oasis.services;

import cine.oasis.domain.Movie;
import cine.oasis.domain.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) { this.repository = repository; }

    public List<Movie> listar() {
        log.info("USER/ADMIN: Consultando cartelera");
        return repository.findAll();
    }

    public Movie guardar(Movie movie) {
        log.info("ADMIN: Creando película: {}", movie.getTitulo());
        return repository.save(movie);
    }

    public Movie actualizar(Long id, Movie movie) {
        log.info("ADMIN: Editando película ID: {}", id);
        movie.setId(id);
        return repository.save(movie);
    }

    public void eliminar(Long id) {
        log.warn("ADMIN: Eliminando película ID: {}", id);
        repository.deleteById(id);
    }
}