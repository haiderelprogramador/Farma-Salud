/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import farmasalud.view.Enfermedades;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Maria liz
 */
public class OrdenMedica  {
   private String diagnostico;
  
   private String reeceta;
   private Paciente paciente;
   private Medico Medico ;
  

    public OrdenMedica(String diagnostico, String reeceta, Paciente paciente, Medico Medico) {
        this.diagnostico = diagnostico;
        this.reeceta = reeceta;
        this.paciente = paciente;
        this.Medico = Medico;
            }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getReeceta() {
        return reeceta;
    }

    public void setReeceta(String reeceta) {
        this.reeceta = reeceta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return Medico;
    }

    public void setMedico(Medico Medico) {
        this.Medico = Medico;
    }


}
  
    
  

