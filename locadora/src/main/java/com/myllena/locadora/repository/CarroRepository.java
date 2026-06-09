package com.myllena.locadora.repository;

import com.myllena.locadora.entity.CarroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<CarroEntity, Long> {

    List<CarroEntity> findByModelo(String modelo);
}
