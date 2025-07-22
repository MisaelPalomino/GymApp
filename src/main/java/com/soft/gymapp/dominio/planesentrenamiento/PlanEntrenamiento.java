package com.soft.gymapp.dominio.planesentrenamiento;

import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "plan_entrenamiento")
public class PlanEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    private int duracionSemanas;

    @OneToOne(optional = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "planEntrenamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rutina> rutinas = new ArrayList<>();

    @ManyToOne(optional = false)
    private Entrenador entrenador;

    public PlanEntrenamiento() {
        // Constructor vacío requerido por JPA
    }

    public PlanEntrenamiento(Date fechaInicio, int duracionSemanas, Cliente cliente, Entrenador entrenador) {
        this.fechaInicio = fechaInicio;
        this.duracionSemanas = duracionSemanas;
        this.cliente = cliente;
        this.entrenador = entrenador;
    }

    public void asignarRutina(Rutina rutina) {
        if (rutina == null) {
            throw new IllegalArgumentException("La rutina no puede ser nula.");
        }
        rutinas.add(rutina);
    }

    public Optional<Rutina> buscarRutinaPorId(int rutinaId) {
        return rutinas.stream()
                      .filter(rutina -> rutina.getId() == rutinaId)
                      .findFirst();
    }

    public void modificarRutina(Rutina rutinaModificada) {
        buscarRutinaPorId(rutinaModificada.getId()) 
        .ifPresent(rutinaExistente -> rutinaExistente.actualizarDatos(rutinaModificada));
    }

    public int getId() {
        return id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public int getDuracionSemanas() {
        return duracionSemanas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Rutina> getRutinas() {
        return Collections.unmodifiableList(rutinas);
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setDuracionSemanas(int duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }
}
