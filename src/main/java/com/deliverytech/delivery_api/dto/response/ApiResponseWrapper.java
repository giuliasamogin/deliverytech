package com.deliverytech.delivery_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "wrapper padrão para respostas da API")
public class ApiResponseWrapper<T> {

    @Schema(description = "Indica se a operação foi bem-sucedida", example = "true")
    private boolean success;

    @Schema(description = "Dados da resposta")
    private T data;

    @Schema(description = "Mensagem descritiva", example = "Operação realizada com sucesso")
    private String message;

    @Schema(description = "Timestamp da resposta", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;

    public ApiResponseWrapper() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseWrapper(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}