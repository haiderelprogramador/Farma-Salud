package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import model.Enfermedad;

public class EnfermedadesDAO {
    private static final String ARCHIVO_JSON = "C:\\Users\\usuario\\OneDrive\\Escritorio\\farmaSalud-software\\src\\resources\\data\\enfermedades.json";
    // Configurar Gson para manejar nombres de campo con guiones bajos
    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    
    /**
     * Carga todas las enfermedades desde el archivo JSON
     * @return Lista de enfermedades
     */
    public List<Enfermedad> cargarTodasEnfermedades() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            if (new File(ARCHIVO_JSON).length() == 0) {
                return new ArrayList<>();
            }

            JsonElement jsonElement = JsonParser.parseReader(reader);
            List<Enfermedad> enfermedades = new ArrayList<>();
            
            if (jsonElement.isJsonArray()) {
                Type listType = new TypeToken<ArrayList<Enfermedad>>(){}.getType();
                enfermedades = gson.fromJson(jsonElement, listType);
            } else if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("enfermedades")) {
                    JsonArray enfermedadesArray = jsonObject.getAsJsonArray("enfermedades");
                    Type listType = new TypeToken<ArrayList<Enfermedad>>(){}.getType();
                    enfermedades = gson.fromJson(enfermedadesArray, listType);
                    
                    // Verificación adicional para asegurar que los IDs se carguen correctamente
                    for (int i = 0; i < enfermedadesArray.size(); i++) {
                        JsonObject enfermedadObj = enfermedadesArray.get(i).getAsJsonObject();
                        if (enfermedadObj.has("id_enfermedad")) {
                            int id = enfermedadObj.get("id_enfermedad").getAsInt();
                            if (i < enfermedades.size()) {
                                enfermedades.get(i).setIdEnfermedad(id);
                            }
                        }
                    }
                }
            }
            
            // Verificación de IDs
            for (Enfermedad enfermedad : enfermedades) {
                if (enfermedad.getIdEnfermedad() <= 0) {
                    System.err.println("Advertencia: Enfermedad con ID inválido: " + enfermedad.getNombre());
                }
            }

            return enfermedades;
        } catch (IOException e) {
            System.err.println("Error al cargar enfermedades: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Guarda una nueva enfermedad en el archivo JSON
     * @param enfermedad La enfermedad a guardar
     * @return true si se guardó correctamente, false si hubo error
     */
    public boolean guardarEnfermedad(Enfermedad enfermedad) {
        if (enfermedad == null || enfermedad.getIdEnfermedad() <= 0) {
            System.err.println("Error: La enfermedad o su ID son inválidos");
            return false;
        }
        
        if (existeIdEnfermedad(enfermedad.getIdEnfermedad())) {
            System.err.println("Error: Ya existe una enfermedad con el ID " + enfermedad.getIdEnfermedad());
            return false;
        }
        
        List<Enfermedad> enfermedades = cargarTodasEnfermedades();
        enfermedades.add(enfermedad);
        return guardarListaEnfermedades(enfermedades);
    }
    
    /**
     * Guarda la lista completa de enfermedades en el archivo JSON
     * @param enfermedades Lista de enfermedades a guardar
     * @return true si se guardó correctamente, false si hubo error
     */
    public boolean guardarListaEnfermedades(List<Enfermedad> enfermedades) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = gson.toJsonTree(enfermedades).getAsJsonArray();
            
            // Verificar que los ID se guarden correctamente
            for (int i = 0; i < enfermedades.size(); i++) {
                JsonObject enfermedadObj = jsonArray.get(i).getAsJsonObject();
                enfermedadObj.addProperty("id_enfermedad", enfermedades.get(i).getIdEnfermedad());
            }
            
            jsonObject.add("enfermedades", jsonArray);
            gson.toJson(jsonObject, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar enfermedades: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza una enfermedad existente
     * @param idOriginal ID original de la enfermedad
     * @param enfermedadActualizada Enfermedad con los datos actualizados
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizarEnfermedad(int idOriginal, Enfermedad enfermedadActualizada) {
        if (idOriginal <= 0) {
            System.err.println("Error: ID original inválido");
            return false;
        }
        
        if (enfermedadActualizada == null || enfermedadActualizada.getIdEnfermedad() <= 0) {
            System.err.println("Error: Enfermedad actualizada o su ID es inválido");
            return false;
        }

        List<Enfermedad> enfermedades = cargarTodasEnfermedades();
        boolean encontrado = false;
        
        for (int i = 0; i < enfermedades.size(); i++) {
            Enfermedad enfermedadExistente = enfermedades.get(i);
            if (idOriginal == enfermedadExistente.getIdEnfermedad()) {
                enfermedades.set(i, enfermedadActualizada);
                encontrado = true;
                break;
            }
        }
        
        if (encontrado) {
            return guardarListaEnfermedades(enfermedades);
        } else {
            System.err.println("No se encontró enfermedad con ID: " + idOriginal);
            return false;
        }
    }
    
    /**
     * Verifica si existe una enfermedad con el ID especificado
     * @param idEnfermedad ID a verificar
     * @return true si existe, false si no existe
     */
    public boolean existeIdEnfermedad(int idEnfermedad) {
        if (idEnfermedad <= 0) {
            return false;
        }
        
        List<Enfermedad> enfermedades = cargarTodasEnfermedades();
        return enfermedades.stream()
                         .anyMatch(e -> idEnfermedad == e.getIdEnfermedad());
    }
    
    /**
     * Genera un nuevo ID para enfermedad
     * @return Nuevo ID numérico secuencial
     */
   public int generarNuevoId() {
        List<Enfermedad> enfermedades = cargarTodasEnfermedades();
        int maxId = 0;
        
        for (Enfermedad enfermedad : enfermedades) {
            if (enfermedad.getIdEnfermedad() > maxId) {
                maxId = enfermedad.getIdEnfermedad();
            }
        }
        
        return maxId + 1;
    }

    /**
     * Elimina una enfermedad por su ID
     * @param idEnfermedad ID de la enfermedad a eliminar
     * @return true si se eliminó correctamente, false si hubo error
     */
    public boolean eliminarEnfermedad(int idEnfermedad) {
        if (idEnfermedad <= 0) {
            System.err.println("Error: ID de enfermedad inválido");
            return false;
        }

        List<Enfermedad> enfermedades = cargarTodasEnfermedades();
        boolean encontrado = false;

        Iterator<Enfermedad> iterator = enfermedades.iterator();
        while (iterator.hasNext()) {
            Enfermedad enfermedad = iterator.next();
            if (idEnfermedad == enfermedad.getIdEnfermedad()) {
                iterator.remove();
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            return guardarListaEnfermedades(enfermedades);
        } else {
            System.err.println("No se encontró enfermedad con ID: " + idEnfermedad);
            return false;
        }
    }
    
    /**
     * Busca una enfermedad por su ID
     * @param idEnfermedad ID a buscar
     * @return La enfermedad encontrada o null si no existe
     */
    public Enfermedad buscarEnfermedadPorId(int idEnfermedad) {
        if (idEnfermedad <= 0) {
            return null;
        }
        
        List<Enfermedad> enfermedades = cargarTodasEnfermedades();
        return enfermedades.stream()
                         .filter(e -> idEnfermedad == e.getIdEnfermedad())
                         .findFirst()
                         .orElse(null);
    }
    
    /**
     * Busca enfermedades que coincidan con el criterio de búsqueda
     * @param criterio Texto para buscar en todos los campos
     * @return Lista de enfermedades que coinciden con el criterio
     */
    public List<Enfermedad> buscarEnfermedades(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return cargarTodasEnfermedades();
        }

        String criterioLower = criterio.toLowerCase();
        List<Enfermedad> enfermedades = cargarTodasEnfermedades();

        return enfermedades.stream()
            .filter(e -> 
                (e.getNombre() != null && e.getNombre().toLowerCase().contains(criterioLower)) ||
                (e.getTipo() != null && e.getTipo().toLowerCase().contains(criterioLower)) ||
                (e.getSintomas() != null && e.getSintomas().stream()
                    .anyMatch(s -> s != null && s.toLowerCase().contains(criterioLower))) ||
                (e.getCausas() != null && e.getCausas().stream()
                    .anyMatch(c -> c != null && c.toLowerCase().contains(criterioLower))))
            .collect(Collectors.toList());
    }
    
    /**
     * Filtra enfermedades por tipo
     * @param tipo Tipo de enfermedad a filtrar
     * @return Lista de enfermedades del tipo especificado
     */
    public List<Enfermedad> filtrarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String tipoLower = tipo.toLowerCase();
        List<Enfermedad> enfermedades = cargarTodasEnfermedades();
        
        return enfermedades.stream()
                         .filter(e -> e.getTipo() != null && 
                                     e.getTipo().toLowerCase().equals(tipoLower))
                         .collect(Collectors.toList());
    }
}