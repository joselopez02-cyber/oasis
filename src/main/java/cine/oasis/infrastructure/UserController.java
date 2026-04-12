package cine.oasis.infrastructure;

import cine.oasis.domain.User;
import cine.oasis.domain.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    // 1. REGISTRO (POST): Libre para todos 🆕
    @PostMapping
    public User register(@RequestBody User newUser) {
        log.info("Intentando registrar usuario: {}", newUser.getDni());
        newUser.setRole("USER"); // Forzamos el rol por defecto
        return userRepository.save(newUser);
    }

    // 2. LISTAR (GET): Solo ADMIN 📋
    @GetMapping
    public List<User> getAll() {
        log.info("ADMIN consultando lista de usuarios.");
        return userRepository.findAll();
    }

    // 3. ACTUALIZAR (PUT): Solo ADMIN 🔄
    @Operation(summary = "Actualizar un usuario (Solo ADMIN)")
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User userDetails) {
        log.info("ADMIN actualizando usuario ID: {}", id);

        return userRepository.findById(id).map(user -> {
            user.setNombre(userDetails.getNombre());
            user.setApellido(userDetails.getApellido());
            user.setDni(userDetails.getDni());
            user.setTelefono(userDetails.getTelefono());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // 4. ELIMINAR (DELETE): Solo ADMIN 🗑️
    @Operation(summary = "Eliminar un usuario (Solo ADMIN)")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("ADMIN eliminando usuario ID: {}", id);
        userRepository.deleteById(id);
    }

    // 5. LOGOUT: Para Swagger 🚪
    @Operation(summary = "Cerrar sesión")
    @PostMapping("/logout-manual")
    public void logoutManual() {
        log.info("Cierre de sesión solicitado.");
    }
}