/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.OrdenMedica;

public class OrdenMedicaDAO {
    private final String ARCHIVO_JSON;
    private final Gson gson;
    
    public OrdenMedicaDAO() {
        this("C:\\Users\\HP\\Desktop\\Farma-Salud\\src\\resources\\data\\ordenmedica.json"); // Default path, can be changed
    }
    
    public OrdenMedicaDAO(String filePath) {
        this.ARCHIVO_JSON = filePath;
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
        ensureFileExists();
    }
    
    private void ensureFileExists() {
        File file = new File(ARCHIVO_JSON);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                // Initialize with empty array if file is new
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        }
    }
    
    public List<OrdenMedica> cargarTodas() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<OrdenMedica>>(){}.getType();
            List<OrdenMedica> ordenesMedicas = gson.fromJson(reader, tipoLista);
            return ordenesMedicas != null ? ordenesMedicas : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al cargar órdenes médicas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
   public boolean guardarOrdenMedica(OrdenMedica ordenMedica) {
    if (ordenMedica == null || ordenMedica.getIdOrden() == null) {
        System.err.println("Orden nula o sin ID. No se guarda.");
        return false;
    }

    List<OrdenMedica> ordenesMedicas = cargarTodas();

    // Elimina cualquier orden existente con el mismo ID
    ordenesMedicas = ordenesMedicas.stream()
        .filter(om -> !om.getIdOrden().equals(ordenMedica.getIdOrden()))
        .collect(Collectors.toList());

    // Agrega la nueva orden
    ordenesMedicas.add(ordenMedica);

    System.out.println("Guardando orden. Total en lista: " + ordenesMedicas.size());

    return guardarTodas(ordenesMedicas);
}

    
    public boolean guardarTodas(List<OrdenMedica> ordenesMedicas) {
        if (ordenesMedicas == null) {
            return false;
        }
        
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
    gson.toJson(ordenesMedicas, writer);
    System.out.println("Orden guardada en JSON");
    return true;
} catch (Exception e) {
    System.err.println("Error al guardar órdenes médicas: " + e.getMessage());
    e.printStackTrace();
    return false;
}

    }
    
    public boolean existeCodigo(String idOrden) {
        if (idOrden == null || idOrden.trim().isEmpty()) {
            return false;
        }
        
        return cargarTodas().stream()
            .anyMatch(om -> idOrden.equalsIgnoreCase(om.getIdOrden()));
    }
    
    public String generarCodigoUnico() {
        List<OrdenMedica> ordenesMedicas = cargarTodas();
        int maxNumero = 0;
        
        for (OrdenMedica orden : ordenesMedicas) {
            try {
                String codigo = orden.getIdOrden();
                if (codigo != null && codigo.startsWith("Orden.")) {
                    int numero = Integer.parseInt(codigo.substring(6));
                    if (numero > maxNumero) {
                        maxNumero = numero;
                    }
                }
            } catch (NumberFormatException e) {
                // Ignore malformed codes
            }
        }
        
        return String.format("Orden.%03d", maxNumero + 1);
    }
    
    // Additional useful methods
    public boolean eliminarOrden(String idOrden) {
        if (idOrden == null) return false;
        
        List<OrdenMedica> ordenesMedicas = cargarTodas().stream()
            .filter(om -> !om.getIdOrden().equals(idOrden))
            .collect(Collectors.toList());
            
        return guardarTodas(ordenesMedicas);
    }
    
    public OrdenMedica buscarPorId(String idOrden) {
        return cargarTodas().stream()
            .filter(om -> om.getIdOrden().equals(idOrden))
            .findFirst()
            .orElse(null);
    }
}
