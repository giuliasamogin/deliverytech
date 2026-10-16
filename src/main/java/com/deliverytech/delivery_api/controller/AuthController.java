package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.AuthRequest;
import com.deliverytech.delivery_api.dto.response.AuthResponse;
import com.deliverytech.delivery_api.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.deliverytech.delivery_api.dto.request.RegisterRequestDTO;
import com.deliverytech.delivery_api.dto.request.RegisterRestauranteDTO;
import com.deliverytech.delivery_api.enums.Role;
import com.deliverytech.delivery_api.service.AuthService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Login e geração de token JWT")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login", description = "Autentica o usuário e retorna um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @Operation(summary = "Cadastro de cliente", description = "Cria uma conta de cliente")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {

        if (authService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Este email já está cadastrado."));
        }

        // Por aqui só se cria conta de cliente. Restaurante tem rota própria (abaixo)
        // e administrador não se cria pelo site.
        if (request.getRole() != Role.CLIENTE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Tipo de conta não permitido."));
        }

        authService.criarUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Conta criada com sucesso."));
    }

    @Operation(summary = "Cadastro de restaurante", description = "Cria o restaurante e a conta de acesso dele de uma vez")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    @PostMapping("/register-restaurante")
    public ResponseEntity<?> registerRestaurante(@Valid @RequestBody RegisterRestauranteDTO request) {

        if (authService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Este email já está cadastrado."));
        }

        authService.criarRestauranteComUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Restaurante criado com sucesso."));
    }

}