package com.oasis.infrastructure; // Asegúrate que el package coincida con tu carpeta

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<h1>Bienvenido al API de Cine Oasis</h1><p>Desplegado correctamente en Railway.</p>";
    }
}