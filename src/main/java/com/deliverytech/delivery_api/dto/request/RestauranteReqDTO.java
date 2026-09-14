package com.deliverytech.delivery_api.dto.request;

import com.deliverytech.delivery_api.validation.ValidCategoria;
import com.deliverytech.delivery_api.validation.ValidTelefone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class RestauranteReqDTO {

    @Schema(description = "Nome do restaurante", example = "Pizza Express", required = true)
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Schema(description = "Categoria do restaurante", example = "Italiana",
            allowableValues = {"Italiana", "Brasileira", "Japonesa", "Mexicana", "Árabe"})
    @NotBlank(message = "Categoria é obrigatória")
    @ValidCategoria
    private String categoria;

    @Schema(description = "Endereço completo do restaurante", example = "Rua das Flores, 123 - Centro")
    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 200, message = "Endereço não pode exceder 200 caracteres")
    private String endereco;

    @NotBlank(message = "Telefone é obrigatório")
    @ValidTelefone
    private String telefone;

    @Schema(description = "Taxa de entrega em reais", example = "5.50", minimum = "0")
    @NotNull(message = "Taxa de entrega é obrigatória")
    @DecimalMin(value = "0.0", inclusive = false, message = "Taxa de entrega deve ser positiva")
    @DecimalMax(value = "50.0", message = "Taxa de entrega não pode exceder R$50,00")
    private BigDecimal taxaEntrega;

    @Schema(description = "Tempo estimado de entrega em minutos", example = "45", minimum = "10", maximum = "120")
    @NotNull(message = "Tempo de entrega é obrigatório")
    @Min(value = 10, message = "Tempo mínimo é 10 minutos")
    @Max(value = 120, message = "Tempo máximo é 120 minutos")
    private Integer tempoEntrega;

    @Schema(description = "Horário de funcionamento", example = "08:00-22:00")
    @NotBlank(message = "Horário de funcionamento é obrigatório")
    private String horarioFuncionamento;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public BigDecimal getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(BigDecimal taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public Integer getTempoEntrega() { return tempoEntrega; }
    public void setTempoEntrega(Integer tempoEntrega) { this.tempoEntrega = tempoEntrega; }

    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }
}