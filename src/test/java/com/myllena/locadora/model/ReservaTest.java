package com.myllena.locadora.model;

import com.myllena.locadora.exception.ReservaInvalidaException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ReservaTest {

    private Carro carro;
    private Cliente cliente;

    @BeforeEach
    void SetUp() {
        Carro carro = new Carro("Kwid", 150);
        Cliente cliente = new Cliente("Doug");
    }

    @Test
    @Disabled // Ignora o teste
    void deveCriarUmaReserva() {
        var dias = 5;
        var reserva = new Reserva(carro, cliente, dias);

        assertThat(reserva).isNotNull();
    }

    @Test
    void deveDarErroAoCriarReserva() {

        //JUnit
        assertThrows(ReservaInvalidaException.class, () -> new Reserva(carro, cliente, 0));
        assertDoesNotThrow(() -> new Reserva(carro, cliente, 1));

        //AssertJ
        var erro = catchThrowable(() -> new Reserva(carro, cliente, 0));

        Assertions.assertThat(erro)
                .isInstanceOf(ReservaInvalidaException.class)
                .hasMessage("A Reserva não pode ter uma quantidade de dias menor que 1.");
    }

    @Test
    void deveCalcularTotalDoAluguel() {
        Reserva reserva = new Reserva(
                new Carro("dsfj", 300), cliente, 2);

        var total = reserva.calcularTotal();
        Assertions.assertThat(total).isEqualTo(600);
    }
}