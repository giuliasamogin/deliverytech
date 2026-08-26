package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String olaMundo() {
        return "Deu certo! Meu primeiro projeto Java e Spring Boot está funcionando! 🚀";
    }
}