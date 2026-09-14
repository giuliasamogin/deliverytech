package com.deliverytech.delivery_api.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import java.util.Map;

@ControllerAdvice
public class SecurityExceptionHandler {

    // Trata falhas de autenticação (usuário não autenticado).
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleAuthenticationCredentialsNotFound(AuthenticationCredentialsNotFoundException ex) {
        return buildResponse("Credenciais inválidas ou ausentes", HttpStatus.UNAUTHORIZED);
    }

    // Trata erros genéricos de autenticação.
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleAuthentication(AuthenticationException ex) {
        return buildResponse("Falha na autenticação", HttpStatus.UNAUTHORIZED);
    }

    // Trata acessos não autorizados (usuário autenticado, mas sem permissão).
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse("Acesso negado", HttpStatus.FORBIDDEN);
    }

    // Método utilitário para construir uma resposta padronizada
    private Map<String, Object> buildResponse(String message, HttpStatus status) {
        return Map.of(
            "timestamp", LocalDateTime.now().toString(),
            "status", status.value(),
            "error", status.getReasonPhrase(),
            "message", message
        );
    }
}