package cine.oasis.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF para poder usar Postman/Swagger sin bloqueos
                .csrf(csrf -> csrf.disable())

                // 2. Configuración de acceso a rutas
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso total a la consola de H2 para que no salga la pantalla gris
                        .requestMatchers("/h2-console/**").permitAll()

                        // Reglas de negocio del cine
                        .requestMatchers(HttpMethod.GET, "/api/peliculas/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/peliculas/**").hasRole("ADMIN") // POST, PUT, DELETE solo para ADMIN

                        .anyRequest().authenticated()
                )

                // 3. CORRECCIÓN PARA H2: Permitir que la consola se cargue en marcos (frames)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // 4. Activar login básico
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                // Usuario Administrador
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin123")
                        .roles("ADMIN")
                        .build(),
                // Usuario normal (solo puede ver)
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user123")
                        .roles("USER")
                        .build()
        );
    }
}