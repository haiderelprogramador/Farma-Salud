package dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminDAO {
    private static final String JSON_PATH = "src/resources/data/usuarios.json";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Obtiene un administrador por su email
     */
    public JsonObject obtenerAdministradorPorEmail(String email) throws IOException {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            JsonArray usuarios = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (int i = 0; i < usuarios.size(); i++) {
                JsonObject admin = usuarios.get(i).getAsJsonObject();
                if (admin.get("email").getAsString().equalsIgnoreCase(email)) {
                    return admin;
                }
            }
        }
        return null;
    }

    /**
     * Actualiza las credenciales del administrador
     */
    public boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña) throws IOException {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            JsonArray usuarios = JsonParser.parseReader(reader).getAsJsonArray();
            boolean encontrado = false;

            // Buscar y actualizar el administrador
            for (int i = 0; i < usuarios.size(); i++) {
                JsonObject admin = usuarios.get(i).getAsJsonObject();
                if (admin.get("email").getAsString().equalsIgnoreCase(emailActual)) {
                    admin.add("email", new JsonPrimitive(nuevoEmail));
                    admin.add("contraseña", new JsonPrimitive(nuevaContraseña));
                    encontrado = true;
                    break;
                }
            }

            // Guardar cambios si se encontró el administrador
            if (encontrado) {
                guardarJson(usuarios);
                return true;
            }
        }
        return false;
    }

    /**
     * Guarda el array modificado en el archivo JSON
     */
    private void guardarJson(JsonArray usuarios) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(JSON_PATH)) {
            gson.toJson(usuarios, writer);
        }
    }

    /**
     * Valida si el nuevo email ya existe (para evitar duplicados)
     */
    public boolean existeEmail(String email) throws IOException {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            JsonArray usuarios = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (int i = 0; i < usuarios.size(); i++) {
                JsonObject usuario = usuarios.get(i).getAsJsonObject();
                if (usuario.get("email").getAsString().equalsIgnoreCase(email)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean actualizarDatosAdministrador(String emailOriginal, JsonObject nuevosDatos) throws IOException {
    try (FileReader reader = new FileReader(JSON_PATH)) {
        JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
        boolean encontrado = false;

        // Buscar y actualizar el administrador
        for (int i = 0; i < array.size(); i++) {
            JsonObject admin = array.get(i).getAsJsonObject();
            if (admin.get("email").getAsString().equalsIgnoreCase(emailOriginal)) {
                // Actualizar todos los campos excepto email y contraseña (si quieres mantenerlos)
                admin.add("nombres", nuevosDatos.get("nombres"));
                admin.add("apellidos", nuevosDatos.get("apellidos"));
                admin.add("numeroDocumento", nuevosDatos.get("numeroDocumento"));
                admin.add("codigoEmpleado", nuevosDatos.get("codigoEmpleado"));
                admin.add("fechaNacimiento", nuevosDatos.get("fechaNacimiento"));
                admin.add("sexo", nuevosDatos.get("sexo"));
                admin.add("eps", nuevosDatos.get("eps"));
                admin.add("celular", nuevosDatos.get("celular"));
                
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            // Guardar cambios en el archivo JSON
            guardarJson(array);
            return true;
        }
        return false;
    }
}


}