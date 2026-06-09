package com.myllena.locadora.repository;

import com.myllena.locadora.entity.CarroEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("teste")
class CarroRepositoryTest {

    @Autowired
    private CarroRepository repository;

    @Test
    void saveCar() {
        var carro = new CarroEntity("Sedan", 200);
        repository.save(carro);

        assertNotNull(carro.getId());
    }

    // COM SQL
    @Test
    @Sql("/sql/carros.sql")
    void listarModelo() {
        List<CarroEntity> lista = repository.findByModelo("SUV");

        var carro = lista.stream().findFirst().get();

        assertEquals(1, lista.size());
        Assertions.assertThat(carro.getModelo()).isEqualTo("SUV");

    }
}