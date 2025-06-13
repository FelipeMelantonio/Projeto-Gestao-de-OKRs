package com.teckmack.gestor_okrs.repository;

import com.teckmack.gestor_okrs.model.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjetivoRepository extends JpaRepository<Objetivo, Long> {
}