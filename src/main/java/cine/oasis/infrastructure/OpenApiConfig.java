package cine.oasis.infrastructure; // Ajusta según tu paquete real

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Servidor de producción (Railway)
        Server productionServer = new Server();
        productionServer.setUrl("https://oasis-production-c8f5.up.railway.app");
        productionServer.setDescription("Servidor de Producción en Railway");

        // Servidor local (IntelliJ)
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Servidor Local de Desarrollo");

        return new OpenAPI()
                .info(new Info()
                        .title("Oasis Cinema API")
                        .version("1.0")
                        .description("Documentación detallada de la API de Oasis Cinema para gestión de películas y usuarios."))
                .servers(List.of(productionServer, localServer));
    }
}