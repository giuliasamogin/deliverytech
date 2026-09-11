package com.deliverytech.delivery_api.dto.resposta;

import com.deliverytech.delivery_api.validation.ValidCEP;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.deliverytech.delivery_api.validation.ValidTelefone;

public class RestauranteDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @ValidTelefone
    private String telefone;

    @ValidCEP
    private String cep;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}