/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Maria liz
 */
public class OrdenMedica  {
 private int idOrden;
 private String dosis;
 private String descripcion;
 private Medico medico;
 private String fecha;
 private Paciente paciente;
 private List<Medicamento> medicamentos;
 
 public OrdenMedica(int idOrden,String dosis,String descripcion,Medico medico,String fecha,Paciente paciente,List<Medicamento> medicamentos){
    this.idOrden=idOrden;
    this.dosis=dosis;
    this.descripcion=descripcion;
    this.medico=medico;
    this.fecha=fecha;
    this.paciente=paciente;
 }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
 
 
 
    
    
}
  
    
  

