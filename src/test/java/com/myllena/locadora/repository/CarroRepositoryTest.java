package com.myllena.locadora.repository;

import com.myllena.locadora.entity.CarroEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("teste")
class CarroRepositoryTest {

    @Autowired
    private CarroRepository repository;

    private CarroEntity carro;

    @BeforeEach
    void setUp() {
        carro = new CarroEntity("HB20", 130.0, 2020);
    }

    @Test
    void saveCar() {
        repository.save(carro);

        assertNotNull(carro.getId());
    }

    @Test
    void buscarPorID() {
        var carroEntity = repository.save(carro);

        Optional<CarroEntity> carroSalvo = repository.findById(carroEntity.getId());

        Assertions.assertThat(carroSalvo.isPresent());
        assertThat(carroSalvo.get().getModelo()).isEqualTo("HB20");

    }

    @Test
    void atualizarCarro() {
        var carroEntity = repository.save(carro);
        carroEntity.setAno(2028);

        CarroEntity carroAtualizado = repository.save(carroEntity);

        assertThat(carroAtualizado.getAno()).isEqualTo(2028);
    }

    @Test
    void deletarCarro() {
        var carroEntity = repository.save(carro);
        repository.deleteById(carroEntity.getId());

        Optional<CarroEntity> list = repository.findById(carroEntity.getId());
        assertThat(list.isEmpty());
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

