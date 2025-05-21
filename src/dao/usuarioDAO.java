package dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.*;

public class usuarioDAO {

    private static final String JSON_BASE_PATH = "src/resources/data/";
    private static final String ADMINISTRADORES_JSON = JSON_BASE_PATH + "usuarios.json";
    private static final String MEDICOS_JSON = JSON_BASE_PATH + "medico.json";
    private static final String RECEPCIONISTAS_JSON = JSON_BASE_PATH + "recepcionistas.json";
    private static final String PACIENTES_JSON = JSON_BASE_PATH + "pacientes.json";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Object validarCredenciales(String email, String contraseña, String rol) {
        try {
            switch(rol) {
                case "Administrador":
                    return buscarAdministrador(email, contraseña);
                case "Doctor":
                    return buscarMedico(email, contraseña);
                case "Recepcionista":
                    return buscarRecepcionista(email, contraseña);
                case "Paciente":
                    return buscarPaciente(email, contraseña);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            return null;
        }
    }

    private Administrador buscarAdministrador(String email, String contraseña) throws Exception {
        try (FileReader reader = new FileReader(ADMINISTRADORES_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                
                if (json.get("email").getAsString().equalsIgnoreCase(email) && 
                    json.get("contraseña").getAsString().equals(contraseña)) {
                    
                    return new Administrador(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        json.get("codigoEmpleado").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("eps").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("contraseña").getAsString()
                    );
                }
            }
        }
        return null;
    }

    private Medico buscarMedico(String email, String contraseña) throws Exception {
        try (FileReader reader = new FileReader(MEDICOS_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                
                if (json.get("email").getAsString().equalsIgnoreCase(email) && 
                    json.get("contraseña").getAsString().equals(contraseña)) {
                    
                    return new Medico(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("contraseña").getAsString(),
                        json.get("especialidad").getAsString(),
                        LocalDate.parse(json.get("fechaContratacion").getAsString(), DATE_FORMATTER),
                        json.get("horario").getAsString()
                    );
                }
            }
        }
        return null;
    }

    private Recepcionista buscarRecepcionista(String email, String contraseña) throws Exception {
        try (FileReader reader = new FileReader(RECEPCIONISTAS_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                
                if (json.get("email").getAsString().equalsIgnoreCase(email) && 
                    json.get("contraseña").getAsString().equals(contraseña)) {
                    
                    return new Recepcionista(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("eps").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("contraseña").getAsString(),
                        json.get("codigoEmpleado").getAsString(),
                        LocalDate.parse(json.get("fechaContratacion").getAsString(), DATE_FORMATTER),
                        json.get("horario").getAsString()
                    );
                }
            }
        }
        return null;
    }

    
    private Paciente buscarPaciente(String email, String contraseña) throws Exception {
        try (FileReader reader = new FileReader(PACIENTES_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                
                if (json.get("email").getAsString().equalsIgnoreCase(email) && 
                    json.get("contraseña").getAsString().equals(contraseña)) {
                    int peso = json.has("Peso") && !json.get("Peso").isJsonNull() ? json.get("Peso").getAsInt() : 0;
                   double altura = json.has("Altura") && !json.get("Altura").isJsonNull() ? json.get("Altura").getAsDouble() : 0.0;

                    return new Paciente(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("eps").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("contraseña").getAsString(),
                        json.get("tipoDocumento").getAsString(),
                        json.get("tipoSangre").getAsString(),
                        json.get("antecedentes").getAsString(),
                          peso,
                          altura
                    );
                }
            }
        }
        return null;
    }

    // Métodos para verificar roles
    public boolean esAdministrador(Object usuario) {
        return usuario instanceof Administrador;
    }
    
    public boolean esMedico(Object usuario) {
        return usuario instanceof Medico;
    }
    
    public boolean esRecepcionista(Object usuario) {
        return usuario instanceof Recepcionista;
    }
    
    
    public boolean esPaciente(Object usuario) {
        return usuario instanceof Paciente;
    }
}