package com.soft.gymapp.dominio.planesentrenamiento;

import jakarta.persistence.*;
import java.util.*;

@Entity //  Persistent Tables: Clase mapeada a una tabla de BD
@Table(name = "rutina") //  Persistent Tables: Nombre de tabla explícito
public class Rutina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String objetivo;

    @ManyToOne //  Persistent Tables: Relaciones JPA
    private PlanEntrenamiento planEntrenamiento;

    //  Things: Una rutina está compuesta por varios ejercicios
    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejercicio> ejercicios = new ArrayList<>();

    public Rutina() {
        // Constructor por defecto requerido por JPA
    }

    //  Cookbook + Error Handling: Método con pasos definidos y validación
    public void agregarEjercicio(Ejercicio ejercicio) {
        if (ejercicio == null) {
            throw new IllegalArgumentException("El ejercicio no puede ser null."); //  Error Handling
        }
        ejercicios.add(ejercicio); // Cookbook: paso 1
        ejercicio.setRutina(this); // Cookbook: paso 2
    }

    //  Cookbook + Error Handling
    public void quitarEjercicio(Ejercicio ejercicio) {
        if (ejercicio == null || !ejercicios.contains(ejercicio)) {
            throw new IllegalArgumentException("El ejercicio no está en la rutina."); //  Error Handling
        }
        ejercicios.remove(ejercicio); //  Cookbook
        ejercicio.setRutina(null);
    }

    //  Cookbook + Things: Mostrar los datos de la rutina como entidad
   

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


    // Getters y setters
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
