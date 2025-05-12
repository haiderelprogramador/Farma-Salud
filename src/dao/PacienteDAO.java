package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Paciente;

public class PacienteDAO {
    private static final String ARCHIVO_JSON = "C:\\Users\\Maria liz\\Music\\Farma-Salud\\src\\resources\\data\\pacientes.json";
    private final Gson gson;
    
    public PacienteDAO() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    }
    
    // Método para asegurar que el archivo exista
    private void asegurarArchivoExiste() {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            try {
                archivo.getParentFile().mkdirs();
                archivo.createNewFile();
                guardarTodos(new ArrayList<>());
            } catch (IOException e) {
                System.err.println("Error al crear archivo JSON: " + e.getMessage());
            }
        }
    }
    
    // Cargar todos los pacientes desde el archivo JSON
    public List<Paciente> cargarTodos() {
        asegurarArchivoExiste();
        File archivo = new File(ARCHIVO_JSON);
        
        if (archivo.length() == 0) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Paciente>>(){}.getType();
            List<Paciente> pacientes = gson.fromJson(reader, tipoLista);
            return pacientes != null ? pacientes : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean guardarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo");
        }
        
        try {
            List<Paciente> pacientes = cargarTodos();
            pacientes.add(paciente);
            guardarTodos(pacientes);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar paciente: " + e.getMessage());
            return false;
        }
    }
    public Paciente buscarPorEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
        return null;
    }
    
    List<Paciente> pacientes = cargarTodos();
    return pacientes.stream()
        .filter(Objects::nonNull)
        .filter(p -> email.equalsIgnoreCase(p.getEmail()))
        .findFirst()
        .orElse(null);
}
    
    // Guardar todos los pacientes en el archivo JSON
    public void guardarTodos(List<Paciente> pacientes) {
        if (pacientes == null) {
            throw new IllegalArgumentException("La lista de pacientes no puede ser nula");
        }
        
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(pacientes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar pacientes: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar los pacientes", e);
        }
    }
    
    // Eliminar un paciente por número de documento
    public boolean eliminarPaciente(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("Número de documento no puede ser nulo o vacío");
        }

        try {
            List<Paciente> pacientes = cargarTodos();
            boolean removed = pacientes.removeIf(p -> 
                p != null && numeroDocumento.equals(p.getNumeroDocumento())
            );
            
            if (removed) {
                guardarTodos(pacientes);
            }
            
            return removed;
        } catch (Exception e) {
            System.err.println("Error al eliminar paciente: " + e.getMessage());
            return false;
        }
    }

    // Buscar paciente por número de documento
    public Paciente buscarPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return null;
        }
        
        List<Paciente> pacientes = cargarTodos();
        return pacientes.stream()
            .filter(Objects::nonNull)
            .filter(p -> documento.equals(p.getNumeroDocumento()))
            .findFirst()
            .orElse(null);
    }
    
    // Actualizar información de un paciente
    public boolean actualizarPaciente(String documentoOriginal, Paciente pacienteActualizado) {
        if (documentoOriginal == null || pacienteActualizado == null) {
            return false;
        }

        try {
            List<Paciente> pacientes = cargarTodos();
            
            for (int i = 0; i < pacientes.size(); i++) {
                Paciente p = pacientes.get(i);
                if (p != null && documentoOriginal.equals(p.getNumeroDocumento())) {
                    pacientes.set(i, pacienteActualizado);
                    guardarTodos(pacientes);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
            return false;
        }
    }
    
    // Clase adaptadora para manejar LocalDate en Gson
    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value != null) {
                out.value(value.format(formatter));
            } else {
                out.nullValue();
            }
        }
        
        @Override 
        public LocalDate read(JsonReader in) throws IOException {
            String date = in.nextString();
            if (date == null || date.trim().isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Fecha inválida encontrada en JSON: " + date);
                return null;
            }
        }
    }
}