package com.deliverytech.delivery_api.security;

import com.deliverytech.delivery_api.model.Usuario;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    // Obtém o usuário autêntico atual do contexto de segurança.
    public static Usuario getCurrentUser() {
        Authentication auth = SecurityContextHolder
            .getContext()
            .getAuthentication();
        
        if (auth != null && auth.getPrincipal() instanceof Usuario) {
            return (Usuario) auth.getPrincipal();
        }
        
        throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado");
    }

    // Retorna o ID do usuário autenticado.
    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    // Verifica se o usuário atual possui um determinado papel (role).
    // Exemplo: hasRole("RESTAURANTE") ou hasRole("CLIENTE")
    public static boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder
            .getContext()
            .getAuthentication();
            
        if (auth != null && auth.getAuthorities() != null) {
            return auth.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
        }
        
        return false;
    }
}