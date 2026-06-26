package com.myllena.locadora.controller;

import com.myllena.locadora.entity.CarroEntity;
import com.myllena.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CarroController.class)
class CarroControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarroService service;

    @Test
    void deveTentarSalvar() throws Exception {
        CarroEntity carro = new CarroEntity(1L, "Gol", 205.0, 2000);

        Mockito.when(service.salvarCarro(Mockito.any())).thenReturn(carro);

        // cenário
        String json = """
                {
                "modelo" : "Gol",
                "valorDiaria" : 205.0,
                "ano" : 2000
                }
                """;

        //execução
        ResultActions result = mvc.perform(
                post("/carros")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json)
        );
        // verificação
        result
                .andExpect(MockMvcResultMatchers.status().isCreated()) // $ é a raiz do JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorDiaria").value(205.0));
    }

    @Test
    void deveMostrarCarro() throws Exception {
        Mockito.when(service.buscarPorId(Mockito.any())).
                thenReturn(new CarroEntity(1L, "Civic", 310, 2026));

        mvc.perform(
                MockMvcRequestBuilders.get("/carros/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Civic"));

    }


}