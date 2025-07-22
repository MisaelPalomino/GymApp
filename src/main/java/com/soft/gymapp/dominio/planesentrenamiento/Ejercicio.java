package com.soft.gymapp.dominio.planesentrenamiento;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        // Constructor vacío para JPA
    }

    // Método de cálculo de calorías quemadas
    public float calcularCaloriasQuemadas(int duracionMinutos) {
        final float MET = 6.0f;
        final float PESO_PROMEDIO_KG = 70.0f;
        final float FACTOR = 3.5f / 200;
        return MET * FACTOR * PESO_PROMEDIO_KG * duracionMinutos;
    }

    // Validación sin excepciones (usando Notification)
    public Notification validarDatos() {
        Notification notification = new Notification();

        if (nombre == null || nombre.trim().isEmpty()) {
            notification.addError("El nombre del ejercicio no puede estar vacío.");
        }

        if (series <= 0) {
            notification.addError("Las series deben ser mayores a 0.");
        }

        if (repeticiones <= 0) {
            notification.addError("Las repeticiones deben ser mayores a 0.");
        }

        return notification;
    }

    // Resumen del ejercicio
    public String obtenerResumen() {
        return String.format("Ejercicio: %s (%d series de %d repeticiones)", nombre, series, repeticiones);
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

    // Clase interna Notification para recolectar errores
    public static class Notification {
        private final List<String> errores = new ArrayList<>();

        public void addError(String error) {
            errores.add(error);
        }

        public boolean tieneErrores() {
            return !errores.isEmpty();
        }

        public List<String> getErrores() {
            return errores;
        }
    }
}
