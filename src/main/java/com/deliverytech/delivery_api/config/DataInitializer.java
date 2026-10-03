package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.enums.Role;
import com.deliverytech.delivery_api.model.Usuario;
import com.deliverytech.delivery_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByEmail("admin@delivery.com")) {
            Usuario usuario = Usuario.builder()
                    .email("admin@delivery.com")
                    .senha(passwordEncoder.encode(adminPassword))
                    .nome("Administrador")
                    .role(Role.ADMIN)
                    .build();

            usuarioRepository.save(usuario);
            System.out.println("Usuário admin criado com sucesso!");
        }
    }
}