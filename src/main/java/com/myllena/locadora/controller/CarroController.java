package com.myllena.locadora.controller;

import com.myllena.locadora.entity.CarroEntity;
import com.myllena.locadora.exception.EntityNotFoundException;
import com.myllena.locadora.service.CarroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("carros")
public class CarroController {

    final CarroService service;

    public CarroController(CarroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CarroEntity entity) {
        try {
            CarroEntity carroSalvo = service.salvarCarro(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvo);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CarroEntity>> list() {
        try {
            List<CarroEntity> listarTodos = service.listarTodos();
            return ResponseEntity.ok().body(listarTodos);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<CarroEntity> buscarPorID(@PathVariable Long id) {
        try {
            CarroEntity carroEncontrado = service.buscarPorId(id);
            return ResponseEntity.ok(carroEncontrado);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroEntity> alterarCarro(@PathVariable Long id, @RequestBody CarroEntity entity) {
        try {
            service.alterarCarro(id, entity);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletarCarro(id);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
