package com.myllena.locadora.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carro")
public class CarroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String modelo;
    private double valorDiaria;

    public CarroEntity() {
    }

    public CarroEntity(String nome, double valorDiaria) {
        this.modelo = nome;
        this.valorDiaria = valorDiaria;
    }

    public long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }
}
