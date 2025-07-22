package com.soft.gymapp.dominio.planesentrenamiento;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "rutina")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String objetivo;

    @ManyToOne
    private PlanEntrenamiento planEntrenamiento;

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejercicio> ejercicios = new ArrayList<>();

    public Rutina() {
        // Constructor por defecto requerido por JPA
    }

    /**
     * Agrega un ejercicio validando entrada.
     * SRP: Método con responsabilidad única.
     */
    public void agregarEjercicio(Ejercicio ejercicio) {
        if (ejercicio == null) {
            throw new IllegalArgumentException("El ejercicio no puede ser null.");
        }
        ejercicios.add(ejercicio);
        ejercicio.setRutina(this);
    }

    /**
     * Elimina un ejercicio validando entrada.
     * SRP: Método separado para una única responsabilidad.
     */
    public void quitarEjercicio(Ejercicio ejercicio) {
        if (ejercicio == null || !ejercicios.contains(ejercicio)) {
            throw new IllegalArgumentException("El ejercicio no está en la rutina.");
        }
        ejercicios.remove(ejercicio);
        ejercicio.setRutina(null);
    }

    /**
     * Permite actualizar esta rutina con datos de otra.
     * OCP + SRP: Método extensible sin modificar código externo.
     */
    public void actualizarDatos(Rutina nuevaRutina) {
        if (nuevaRutina == null) {
            throw new IllegalArgumentException("La rutina nueva no puede ser null.");
        }
        this.nombre = nuevaRutina.getNombre();
        this.objetivo = nuevaRutina.getObjetivo();
        this.ejercicios.clear();
        for (Ejercicio ejercicio : nuevaRutina.getEjercicios()) {
            this.agregarEjercicio(ejercicio);
        }
    }

    /**
     * Devuelve la rutina formateada como String.
     * SRP: Solo muestra información.
     */
    public String mostrarRutina() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rutina: ").append(nombre).append(System.lineSeparator());
        sb.append("Objetivo: ").append(objetivo).append(System.lineSeparator());
        sb.append("Ejercicios:").append(System.lineSeparator());

        for (Ejercicio e : ejercicios) {
            sb.append("- ")
              .append(e.getNombre()).append(": ")
              .append(e.getDescripcion()).append(" (")
              .append(e.getSeries()).append(" series de ")
              .append(e.getRepeticiones()).append(" reps)")
              .append(System.lineSeparator());
        }

        return sb.toString();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public PlanEntrenamiento getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        this.planEntrenamiento = planEntrenamiento;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
