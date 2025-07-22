package com.soft.gymapp.dominio.planesentrenamiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanEntrenamientoRepositorio extends JpaRepository<PlanEntrenamiento, Integer> {
    // Métodos personalizados opcionales, si los necesitas.
}
