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
import model.Recepcionista;

public class RecepcionistaDAO {

    private static final String ARCHIVO_JSON = "C:\\Users\\HP\\Desktop\\Farma-Salud\\src\\resources\\data\\recepcionista.json";
    private static RecepcionistaDAO instancia;


    private final Gson gson;
    
    private RecepcionistaDAO() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
        verificarYEstructurarArchivo();
    }
    
    public static synchronized RecepcionistaDAO getInstancia() {
        if (instancia == null) {
            instancia = new RecepcionistaDAO();
        }
        return instancia;
    }
    
    private void verificarYEstructurarArchivo() {
        try {
            File archivo = new File(ARCHIVO_JSON);
            File directorioPadre = archivo.getParentFile();
            
            if (directorioPadre != null && !directorioPadre.exists()) {
                if (!directorioPadre.mkdirs()) {
                    throw new IOException("No se pudo crear el directorio: " + directorioPadre.getAbsolutePath());
                }
            }
            
            if (!archivo.exists()) {
                try (FileWriter writer = new FileWriter(archivo)) {
                    gson.toJson(new ArrayList<Recepcionista>(), writer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al inicializar archivo JSON: " + e.getMessage());
            throw new RuntimeException("Error crítico al inicializar archivo de datos", e);
        }
    }
    
    public List<Recepcionista> cargarTodos() {
        File archivo = new File(ARCHIVO_JSON);
        
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Recepcionista>>(){}.getType();
            List<Recepcionista> recepcionistas = gson.fromJson(reader, tipoLista);
            return recepcionistas != null ? recepcionistas : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean guardarRecepcionista(Recepcionista recepcionista) {
        if (recepcionista == null) {
            throw new IllegalArgumentException("El recepcionista no puede ser nulo");
        }
        
        if (recepcionista.getNumeroDocumento() == null) {
            throw new IllegalArgumentException("El número de documento no puede ser nulo");
        }
        
        try {
            List<Recepcionista> recepcionistas = cargarTodos();
            
            boolean existe = recepcionistas.stream()
                .filter(Objects::nonNull)
                .anyMatch(r -> recepcionista.getNumeroDocumento().equals(r.getNumeroDocumento()));
            
            if (existe) {
                return false;
            }
            
            recepcionistas.add(recepcionista);
            guardarTodos(recepcionistas);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar recepcionista: " + e.getMessage());
            return false;
        }
    }
    
    public void guardarTodos(List<Recepcionista> recepcionistas) {
        if (recepcionistas == null) {
            throw new IllegalArgumentException("La lista de recepcionistas no puede ser nula");
        }
        
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(recepcionistas, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar recepcionistas: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar los recepcionistas", e);
        }
    }
    
    public boolean eliminarRecepcionista(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("Número de documento no puede ser nulo o vacío");
        }

        try {
            List<Recepcionista> recepcionistas = cargarTodos();
            boolean removed = recepcionistas.removeIf(r -> 
                r != null && numeroDocumento.equals(r.getNumeroDocumento())
            );
            
            if (removed) {
                guardarTodos(recepcionistas);
            }
            
            return removed;
        } catch (Exception e) {
            System.err.println("Error al eliminar recepcionista: " + e.getMessage());
            return false;
        }
    }
    
    public Recepcionista obtenerPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return null;
        }
        
        List<Recepcionista> recepcionistas = cargarTodos();
        return recepcionistas.stream()
            .filter(Objects::nonNull)
            .filter(r -> documento.equals(r.getNumeroDocumento()))
            .findFirst()
            .orElse(null);
    }
    
    public boolean actualizarRecepcionista(String documentoOriginal, Recepcionista recepcionistaActualizado) {
        if (documentoOriginal == null || recepcionistaActualizado == null) {
            return false;
        }

        if (recepcionistaActualizado.getNumeroDocumento() == null) {
            throw new IllegalArgumentException("El número de documento actualizado no puede ser nulo");
        }

        try {
            List<Recepcionista> recepcionistas = cargarTodos();
            
            // Verificar si el nuevo documento ya existe (si es diferente al original)
            if (!documentoOriginal.equals(recepcionistaActualizado.getNumeroDocumento())) {
                boolean existeNuevoDocumento = recepcionistas.stream()
                    .filter(Objects::nonNull)
                    .anyMatch(r -> recepcionistaActualizado.getNumeroDocumento().equals(r.getNumeroDocumento()));
                
                if (existeNuevoDocumento) {
                    return false;
                }
            }
            
            // Buscar y actualizar el recepcionista
            for (int i = 0; i < recepcionistas.size(); i++) {
                Recepcionista r = recepcionistas.get(i);
                if (r != null && documentoOriginal.equals(r.getNumeroDocumento())) {
                    recepcionistas.set(i, recepcionistaActualizado);
                    guardarTodos(recepcionistas);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar recepcionista: " + e.getMessage());
            return false;
        }
    }
    
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