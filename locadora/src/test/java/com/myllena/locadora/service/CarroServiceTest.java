package com.myllena.locadora.service;

import com.myllena.locadora.entity.CarroEntity;
import com.myllena.locadora.exception.EntityNotFoundException;
import com.myllena.locadora.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    private CarroService service;

    @Mock
    private CarroRepository repository;

    @Test
    void deveSalvarCarro() {
        CarroEntity carroSalvo = new CarroEntity("Sedan", 100.0, 2025);

        Mockito.when(repository.save(Mockito.any())).thenReturn(carroSalvo);

        var salvar = service.salvarCarro(carroSalvo);

        assertNotNull(salvar);
        assertEquals("Sedan", salvar.getModelo());
        assertEquals(salvar.getModelo(), "Sedan");

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void deveDarErroComValor(){
        CarroEntity carro = new CarroEntity("Sedan", 0.0, 2025);

        var erro = catchThrowable(() -> service.salvarCarro(carro) );

        assertThat(erro).isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveAtualizarCarro(){

        CarroEntity carroExistente = new CarroEntity("HRV", 50.0, 2025);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(carroExistente));

        var carroAlterado = new CarroEntity("Ford Fiesta", 50.0, 2000);
        Mockito.when(repository.save(Mockito.any())).thenReturn(carroAlterado);

        var CarroTeste = service.alterarCarro(1L, carroAlterado);

        assertEquals(CarroTeste.getAno(), 2000);
        assertEquals(CarroTeste.getModelo(), "Ford Fiesta");

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }
    @Test
    void deveDarErroAoAlterar(){
        var carro = new CarroEntity("GOL", 85.0, 2021);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> service.alterarCarro(1L, carro));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
    }










}