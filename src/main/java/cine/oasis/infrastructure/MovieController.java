package cine.oasis.infrastructure;

import cine.oasis.services.MovieService;
import cine.oasis.domain.Movie;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) { this.service = service; }

    @Operation(summary = "LISTAR - (Cualquiera)")
    @GetMapping
    public List<Movie> listar() { return service.listar(); }

    @Operation(summary = "CREAR - (Solo ADMIN)")
    @PostMapping
    public Movie crear(@RequestBody Movie movie) { return service.guardar(movie); }

    @Operation(summary = "EDITAR - (Solo ADMIN)")
    @PutMapping("/{id}")
    public Movie editar(@PathVariable Long id, @RequestBody Movie movie) {
        return service.actualizar(id, movie);
    }

    @Operation(summary = "BORRAR - (Solo ADMIN)")
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) { service.eliminar(id); }
}