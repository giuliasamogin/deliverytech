package com.deliverytech.delivery_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.deliverytech.delivery_api.config.TestSecurityConfig;
import com.deliverytech.delivery_api.enums.Role;
import com.deliverytech.delivery_api.model.Usuario;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class ClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    // Um usuário "logado" como cliente, só para os testes
    private Usuario clienteLogado(String email) {
        return Usuario.builder()
            .email(email)
            .senha("senha-de-teste")
            .nome("Alexandre")
            .role(Role.CLIENTE)
            .build();
    }

    @Test
    void deveCriarClienteComSucesso() throws Exception {
        String email = "alexandre" + System.currentTimeMillis() + "@teste.com";
        String json = "{"
            + "\"nome\":\"Alexandre\","
            + "\"email\":\"" + email + "\","
            + "\"telefone\":\"11999998888\","
            + "\"endereco\":\"Rua das Flores, 123\""
            + "}";

        mockMvc.perform(post("/clientes")
            .with(user(clienteLogado(email)))
            .contentType("application/json")
            .content(json))
            .andExpect(status().isCreated());
    }

    @Test
    void naoDeveCriarClienteComTelefoneInvalido() throws Exception {
        String json = "{"
            + "\"nome\":\"Alexandre\","
            + "\"email\":\"invalido@teste.com\","
            + "\"telefone\":\"000\","
            + "\"endereco\":\"Rua das Flores, 123\""
            + "}";

        mockMvc.perform(post("/clientes")
            .with(user(clienteLogado("invalido@teste.com")))
            .contentType("application/json")
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void clienteComumNaoPodeListarTodosOsClientes() throws Exception {
        mockMvc.perform(get("/clientes")
            .with(user(clienteLogado("comum@teste.com"))))
            .andExpect(status().isForbidden());
    }

    @Test
    void clienteNaoPodeVerOsDadosDeOutroCliente() throws Exception {
        mockMvc.perform(get("/clientes/por-email")
            .param("email", "outra.pessoa@teste.com")
            .with(user(clienteLogado("comum@teste.com"))))
            .andExpect(status().isForbidden());
    }
}