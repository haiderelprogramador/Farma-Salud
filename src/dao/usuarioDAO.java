package dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.*;
/*
public class usuarioDAO {

    // Usar rutas relativas o configurables
    private static final String JSON_BASE_PATH = "src/resources/data/";
    private static final String MEDICOS_JSON = JSON_BASE_PATH + "empleados.json";
    private static final String RECEPCIONISTAS_JSON = JSON_BASE_PATH + "recepcionista.json";
    private static final String ADMINISTRADORES_JSON = JSON_BASE_PATH + "usuarios.json";
    private static final String FARMACEUTICOS_JSON = JSON_BASE_PATH + "farmaceutica.json";
    private static final String PACIENTES_JSON = JSON_BASE_PATH + "pacientes.json";
    

    private static final String MEDICOS_JSON = "C:\\Users\\usuario\\OneDrive\\Escritorio\\farmaSalud-software\\src\\resources\\data\\empleados.json";
    private static final String RECEPCIONISTAS_JSON ="C:\\Users\\Maria liz\\Music\\Farma-Salud\\src\\resources\\data\\recepcionista.json";
    private static final String ADMINISTRADORES_JSON = "C:\\Users\\Maria liz\\Music\\Farma-Salud\\src\\resources\\data\\usuarios.json";
    private static final String FARMACEUTICOS_JSON = "C:\\Users\\Maria liz\\Music\\Farma-Salud\\src\\resources\\data\\farmaceutica.json";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Método unificado para validar credenciales
   /* public Object validarCredenciales(String email, String documento, String rol) {
        try {
            switch(rol) {
                case "Administrador":
                    return buscarAdministrador(email, documento);
                case "Doctor":
                    return buscarMedico(email, documento);
                case "Recepcionista":
                    return buscarRecepcionista(email, documento);
                case "Farmaceutica":
                    return buscarFarmaceutico(email, documento);
                case "Paciente":
                    return buscarPaciente(email, documento);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            return null;
        }
    }
  
    private Administrador buscarAdministrador(String email) throws Exception {
        try (FileReader reader = new FileReader(ADMINISTRADORES_JSON)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray usuariosJson = jsonObject.getAsJsonArray("usuarios");

            for (int i = 0; i < usuariosJson.size(); i++) {
                JsonObject usuarioJson = usuariosJson.get(i).getAsJsonObject();

                String userEmail = usuarioJson.get("email").getAsString();
               

                if (userEmail.equalsIgnoreCase(email)) {
                    return new Administrador(
                        usuarioJson.get("nombres").getAsString(),
                        usuarioJson.get("apellidos").getAsString(),
                        //  LocalDate.parse(usuarioJson.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        usuarioJson.get("sexo").getAsString(),
                        userEmail,
                        usuarioJson.get("celular").getAsString(),
                        usuarioJson.get("codigoEmpleado").getAsString(),
                        LocalDate.parse(usuarioJson.get("fechaContratacion").getAsString(), DATE_FORMATTER)
                    );
                }
            }
        }
        return null;
    }
*/
    
    private Medico buscarMedico(String email, String documento) throws Exception {
        try (FileReader reader = new FileReader(MEDICOS_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                if (json.get("email").getAsString().equalsIgnoreCase(email) &&
                    json.get("numeroDocumento").getAsString().equals(documento)) {
                    return new Medico(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("especialidad").getAsString(),
                        LocalDate.parse(json.get("fechaContratacion").getAsString(), DATE_FORMATTER),
                        json.get("horario").getAsString()
                    );
                }
            }
        }
        return null;
    }

    private Recepcionista buscarRecepcionista(String email, String documento) throws Exception {
        try (FileReader reader = new FileReader(RECEPCIONISTAS_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                if (json.get("email").getAsString().equalsIgnoreCase(email) &&
                    json.get("numeroDocumento").getAsString().equals(documento)) {
                    return new Recepcionista(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("eps").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("codigoEmpleado").getAsString(),
                        LocalDate.parse(json.get("fechaContratacion").getAsString(), DATE_FORMATTER),
                        json.get("turno").getAsString()
                    );
                }
            }
        }
        return null;
    }

    private Farmaceutica buscarFarmaceutico(String email, String documento) throws Exception {
        try (FileReader reader = new FileReader(FARMACEUTICOS_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                if (json.get("email").getAsString().equalsIgnoreCase(email) &&
                    json.get("numeroDocumento").getAsString().equals(documento)) {
                    return new Farmaceutica(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("eps").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("codigoEmpleado").getAsString(),
                        LocalDate.parse(json.get("fechaContratacion").getAsString(), DATE_FORMATTER),
                        json.get("turno").getAsString()
                    );
                }
            }
        }
        return null;
    }

    private Paciente buscarPaciente(String email, String documento) throws Exception {
        try (FileReader reader = new FileReader(PACIENTES_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                if (json.get("email").getAsString().equalsIgnoreCase(email) &&
                    json.get("numeroDocumento").getAsString().equals(documento)) {
                    return new Paciente(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("eps").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("tipoDocumento").getAsString(),
                        json.get("tipoSangre").getAsString(),
                        json.get("antecedentes").getAsString()
                    );
                }
            }
        }
        return null;
    }

    // Métodos para verificar roles (añadidos para mantener compatibilidad)
    public boolean esAdministrador(Object usuario) {
        return usuario instanceof Administrador;
    }
    
    public boolean esMedico(Object usuario) {
        return usuario instanceof Medico;
    }
    
    public boolean esRecepcionista(Object usuario) {
        return usuario instanceof Recepcionista;
    }
    
    public boolean esFarmaceutico(Object usuario) {
        return usuario instanceof Farmaceutica;
    }
    
    public boolean esPaciente(Object usuario) {
        return usuario instanceof Paciente;
    }
}*/