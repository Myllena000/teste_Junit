package com.myllena.locadora.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarrosTest {

    @Test
    @DisplayName("Deve calcular o valor do aluguel") //nome que vc pode por no teste
    void deveCalcularValorAluguel() {

    // 1- cenário
    Carro carro = new Carro("Logan Sedan", 100.0);
    // 2- execução(do metodo)
    double total = carro.calcularAluguel(3);
    //3- verifição se o resultado faz sentido de acordo com a logica
        Assertions.assertEquals(300, total); //o assert verifica a igualdade
    }

    @Test
    @DisplayName("Deve valor do aluguel com desconto")
    void deveCalcularValorAlguelComDesconto(){

        Carro carro = new Carro("Polo", 100.0);
        int quantidadeDias = 5;
        double total = carro.calcularAluguel(quantidadeDias);
        Assertions.assertEquals(450, total);
    }
}