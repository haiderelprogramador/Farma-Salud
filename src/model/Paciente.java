package model;

import java.time.LocalDate;

public class Paciente extends Persona {
    private String tipoSangre;
    private String tipoDocumento;
    private String antecedentes;

    public Paciente(
        String numeroDocumento, 
        String nombres, 
        String apellidos, 
        LocalDate fechaNacimiento, 
        String sexo, 
        String eps, 
        String email, 
        String celular,
        String contraseña,
        String tipoDocumento,
        String tipoSangre,
        String antecedentes
    ) {
        super(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, eps, email, celular, contraseña);
        
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento no puede ser nulo o vacío");
        }
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento no puede ser nulo o vacío");
        }
        
        this.tipoDocumento = tipoDocumento;
        this.tipoSangre = tipoSangre;
        this.antecedentes = antecedentes;
    }

    // Getters y Setters
    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento no puede ser nulo o vacío");
        }
        this.tipoDocumento = tipoDocumento;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }
}