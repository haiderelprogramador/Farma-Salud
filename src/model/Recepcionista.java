package model;

import java.time.LocalDate;

public class Recepcionista extends Persona {
    private String codigoEmpleado;
    private LocalDate fechaContratacion;
    private String horario;

    public Recepcionista(
        String numeroDocumento, 
        String nombres, 
        String apellidos, 
        LocalDate fechaNacimiento, 
        String sexo, 
        String eps, 
        String email, 
        String celular,
        String contraseña,
        String codigoEmpleado, // Nuevo parámetro
        LocalDate fechaContratacion,
        String horario
    ) {
        super(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, eps, email, celular, contraseña);
        this.codigoEmpleado = codigoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.horario = horario;
    }

    // Getters y setters
    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public String getHorario() {
        return horario;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}