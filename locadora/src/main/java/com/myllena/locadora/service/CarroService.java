package com.myllena.locadora.service;

import com.myllena.locadora.entity.CarroEntity;
import com.myllena.locadora.exception.EntityNotFoundException;
import com.myllena.locadora.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    private final CarroRepository repository;

    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    public CarroEntity salvarCarro(CarroEntity entity){
        if(entity.getValorDiaria() <= 0){
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }
        return repository.save(entity);
    }

    public CarroEntity alterarCarro(Long id, CarroEntity entity){
        var carroExiste = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não cadastrado"));

        carroExiste.setModelo(entity.getModelo());
        carroExiste.setAno(entity.getAno());
        carroExiste.setValorDiaria(entity.getValorDiaria());

       return repository.save(carroExiste);
    }

    public void deletarCarro(Long id){
        var carroExiste = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não cadastrado"));
         repository.deleteById(carroExiste.getId()); // ou por id da requisiçao
    }

    public CarroEntity buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não cadastrado"));
    }

    public List<CarroEntity> listarTodos(){
        return repository.findAll();
    }
}
