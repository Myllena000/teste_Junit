package com.myllena.locadora.repository;

import com.myllena.locadora.entity.CarroEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("teste")
class CarroRepositoryTest {

    @Autowired
    private CarroRepository repository;

    @Test
    void saveCar(){
        var carro = new CarroEntity("Sedan", 200);
        repository.save(carro);

        assertNotNull(carro.getId());
    }
}