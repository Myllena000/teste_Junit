package com.myllena.locadora.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ClienteTest {

    @Test
     void deveCriarClienteComNome(){
        Cliente cliente = new Cliente("Douglas");

        String nome = cliente.getNome();
        assertThat(nome).isEqualTo("Douglas");
        assertThat(nome).isLessThan("DouglasS");

        assertNotNull(nome);
        assertTrue(nome.startsWith("D")); // se nome começa com D
        assertThat(nome.length()).isLessThan(10); // se nome é maior que 50 caracter
    }

    @Test
    void deveCriarClienteNull(){
        Cliente cliente = new Cliente(null);
        String nome = cliente.getNome();
        assertNull(nome);
    }
}
