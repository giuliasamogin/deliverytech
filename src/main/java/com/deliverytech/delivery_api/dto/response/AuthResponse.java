package com.deliverytech.delivery_api.dto.response;

public class AuthResponse {

    private String token;

    // Construtor vazio para desserialização
    public AuthResponse() {
    }

    // Construtor com token
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getters e setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}