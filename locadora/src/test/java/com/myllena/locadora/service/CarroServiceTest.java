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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;

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
    void deveDarErroComValor() {
        CarroEntity carro = new CarroEntity("Sedan", 0.0, 2025);

        var erro = catchThrowable(() -> service.salvarCarro(carro));

        assertThat(erro).isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveAtualizarCarro() {

        CarroEntity carroExistente = new CarroEntity("HRV", 50.0, 2025);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(carroExistente));

        var carroAlterado = new CarroEntity("Ford Fiesta", 50.0, 2000);
        Mockito.when(repository.save(Mockito.any())).thenReturn(carroAlterado);

        var CarroTeste = service.alterarCarro(1L, carroAlterado);

        assertEquals(CarroTeste.getAno(), 2000);
        assertEquals(CarroTeste.getModelo(), "Ford Fiesta");

        Mockito.verify(repository, times(1)).save(Mockito.any());
    }

    @Test
    void deveDarErroAoAlterar() {
        var carro = new CarroEntity("GOL", 85.0, 2021);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> service.alterarCarro(1L, carro));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }


    @Test
    void deveExcluirCarro() {
        CarroEntity carro = new CarroEntity("HRV", 200.0, 2025);
        carro.setId(1L);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(carro));

        service.deletarCarro(1L);

        Mockito.verify(repository, times(1)).deleteById(Mockito.any());

    }

    @Test
    void deveDarErroAoExcluirCarro() {
        var id = 1L;

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> service.deletarCarro(id));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(repository, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    void devebuscarCarroPeloID() {
        CarroEntity carro = new CarroEntity("HRV", 200.0, 2025);
        carro.setId(1L);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(carro));

        var buscar = service.buscarPorId(1L);

        assertEquals("HRV", carro.getModelo());
        assertThat(buscar.getValorDiaria()).isEqualTo(200.0);

        Mockito.verify(repository, times(1)).findById(Mockito.any());
    }

    @Test
    void deveDarErroAoBuscarPorID() {
        Long id = 1L;

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId(id));

        Mockito.verify(repository).findById(Mockito.any());
    }

    @Test
    void deveListarTodos() {
        CarroEntity carro1 = new CarroEntity(1L, "HRV", 200.0, 2025);
        CarroEntity carro2 = new CarroEntity(2L, "GOL", 140.0, 2021);

        List<CarroEntity> list = List.of(carro1, carro2);
        Mockito.when(repository.findAll()).thenReturn(list);

        var listarTodos = service.listarTodos();

        assertThat(listarTodos).hasSize(2);
        Mockito.verify(repository, times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }
}