package com.deliverytech.delivery_api.dto;

public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String Nome) { this.nome = nome; } 

    public String getTelefone() { return telefone; }
    public void setTelefone(String Telefone) { this.telefone = telefone; } 

    public String getEndereço() { return endereco; }
    public void setEndereço(String Endereço) { this.endereco = endereco; } 

    public String getEmail() { return email; }
    public void setEmail(String Email) { this.email = email; } 

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

}