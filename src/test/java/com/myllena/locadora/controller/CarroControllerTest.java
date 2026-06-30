package com.myllena.locadora.controller;

import com.myllena.locadora.entity.CarroEntity;
import com.myllena.locadora.exception.EntityNotFoundException;
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

import java.util.List;

import static org.mockito.Mockito.verify;
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
    void deveDarErroAoSalvarCarro() throws Exception {
        Mockito.when(service.salvarCarro(Mockito.any())).thenThrow(IllegalArgumentException.class);

        String json = """
                {
                 "modelo" : "HRV",
                "valorDiaria" : 131.0,
                "ano" : 2009
                }
                """;
        mvc.perform(MockMvcRequestBuilders.post("/carros")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
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

    @Test
    void deveDarErroAoBuscarCarroInexistente() throws Exception {
        Mockito.when(service.buscarPorId(Mockito.any())).thenThrow(EntityNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/carros/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deveListarCarros() throws Exception {
        var list = List.of(
                new CarroEntity(1L, "HRV", 200.0, 2025),
                new CarroEntity(2L, "GOL", 140.0, 2021)
        );
        Mockito.when(service.listarTodos()).thenReturn(list);

        mvc.perform(MockMvcRequestBuilders.get("/carros"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ano").value(2021));
    }

    @Test
    void deveAtualizarUmCarro() throws Exception {
        Mockito.when(service.alterarCarro(Mockito.any(), Mockito.any()))
                .thenReturn(new CarroEntity(1L, "Civic", 189.0, 2029));

        String json = """
                 {
                "modelo" : "Civic",
                "valorDiaria" : 189.0,
                "ano" : 2029
                }
                """;
        mvc.perform(MockMvcRequestBuilders.put("/carros/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(service).alterarCarro(Mockito.eq(1L), Mockito.any(CarroEntity.class));
    }

    @Test
    void deveDarErroAoAlterarCarro() throws Exception {
        Mockito.when(service.alterarCarro(Mockito.any(), Mockito.any()))
                .thenThrow(EntityNotFoundException.class);

        String json = """
                {
                 "modelo" : "HRV",
                "valorDiaria" : 131.0,
                "ano" : 2009
                }
                """;
        mvc.perform(MockMvcRequestBuilders.put("/carros/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deveDeletarCarro() throws Exception {
        Mockito.doNothing().when(service).deletarCarro(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.delete("/carros/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deveDarErroAoDeletar() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class)
                .when(service).deletarCarro(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.delete("/carros/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}