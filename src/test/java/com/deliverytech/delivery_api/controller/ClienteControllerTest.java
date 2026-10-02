package com.deliverytech.delivery_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.deliverytech.delivery_api.config.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class ClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void deveCriarClienteComSucesso() throws Exception {
        String json = "{"
            + "\"nome\":\"Alexandre\","
            + "\"email\":\"alexandre" + System.currentTimeMillis() + "@teste.com\","
            + "\"telefone\":\"11999998888\","
            + "\"endereco\":\"Rua das Flores, 123\""
            + "}";

        mockMvc.perform(post("/clientes")
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
            .contentType("application/json")
            .content(json))
            .andExpect(status().isBadRequest());
    }
}