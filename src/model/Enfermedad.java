/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class Enfermedad {
    private String idEnfermedad;
    private String nombreEnfermedad;
    private String tipoEnfermedad;
    private String sintomas;
    private String causaEnfermedad;

    

    public Enfermedad() {}

    public Enfermedad(String idEnfermedad, String nombreEnfermedad, String tipoEnfermedad, 
                     String sintomas, String causaEnfermedad) {
        this.idEnfermedad = idEnfermedad;
        this.nombreEnfermedad = nombreEnfermedad;
        this.tipoEnfermedad = tipoEnfermedad;
        this.sintomas = sintomas;
        this.causaEnfermedad = causaEnfermedad;
    }
    public String getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(String idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public String getTipoEnfermedad() {
        return tipoEnfermedad;
    }

    public void setTipoEnfermedad(String tipoEnfermedad) {
        this.tipoEnfermedad = tipoEnfermedad;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getCausaEnfermedad() {
        return causaEnfermedad;
    }

    public void setCausaEnfermedad(String causaEnfermedad) {
        this.causaEnfermedad = causaEnfermedad;
    }
 
}
