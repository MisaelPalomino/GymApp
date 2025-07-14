package com.soft.gymapp.dominio.planesentrenamiento;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Mejora para permitir autoincremento
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private int repeticiones;

    @Column(nullable = false)
    private int series;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rutina_id", nullable = false)
    private Rutina rutina;

    // Constructor requerido por JPA
    public Ejercicio() {
        //terminar constructor
    }

    // Estilo 1: Cookbook — método reusable y autocontenido
    public float calcularCaloriasQuemadas(int duracionMinutos) {
        final float MET = 6.0f; // valor ficticio de intensidad
        final float pesoPromedio = 70.0f; // kg
        return (MET * 3.5f * pesoPromedio / 200) * duracionMinutos;
    }

    // Estilo 2: Error/Exception Handling
    public void validarDatos() {
        if (repeticiones <= 0 || series <= 0) {
            throw new IllegalArgumentException("Repeticiones y series deben ser mayores a 0.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del ejercicio no puede estar vacío.");
        }
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getRepeticiones() { return repeticiones; }
    public void setRepeticiones(int repeticiones) { this.repeticiones = repeticiones; }

    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }

    public Rutina getRutina() { return rutina; }
    public void setRutina(Rutina rutina) { this.rutina = rutina; }

    // Estilo 3: Things — comportamiento definido como objeto "calculador"
    public String resumenEjercicio() {
        return String.format("Ejercicio: %s (%d x %d)", nombre, series, repeticiones);
    }

    // Estilo 4: Persistent Tables — integración con JPA correctamente configurada
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ejercicio)) return false;
        Ejercicio that = (Ejercicio) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
