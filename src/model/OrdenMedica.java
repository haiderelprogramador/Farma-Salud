/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.List;

public class OrdenMedica {
    private String idOrden;
    private String dosis;
    private String diagnostico;
    private LocalDate fecha; // CAMBIADO: ahora es LocalDate
    private String medicamentos;

    // Puedes agregar paciente y médico si los usas
    // private Paciente paciente;
    // private Medico medico;

    private List<Enfermedad> enfermedades;

    public OrdenMedica(String idOrden, String diagnostico, String dosis, LocalDate fecha, String medicamentos) {
        this.idOrden = idOrden;
        this.diagnostico = diagnostico;
        this.dosis = dosis;
        this.fecha = fecha;
        this.medicamentos = medicamentos;
    }

    // Getters y setters
    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public List<Enfermedad> getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(List<Enfermedad> enfermedades) {
        this.enfermedades = enfermedades;
    }
}

