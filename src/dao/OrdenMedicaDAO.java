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
 private static final String ARCHIVO_JSON = "C:\\Users\\HP\\Desktop\\Farma-Salud\\src\\resources\\data\\ordenmedica.json";
 private Gson gson= new GsonBuilder().setPrettyPrinting().create();
 
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
   public void guardarOrdenMedica(OrdenMedica ordenMedica) {
   List<OrdenMedica>ordenesmedicas= cargarTodas();
   ordenesmedicas.add(ordenMedica);
   guardarTodas(ordenesmedicas);
}

    
    public void guardarTodas(List<OrdenMedica> ordenesMedicas) {
       try(FileWriter writer = new FileWriter(ARCHIVO_JSON)){
        gson.toJson(ordenesMedicas, writer);
     }catch(IOException e){
           System.out.println("Error al guaradar Cita: " + e.getMessage());
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
        try{
         if(idOrden == null || idOrden.trim().isEmpty()){
          throw new IllegalArgumentException("id orden no puede ser nulo o vacio");
         }
         List<OrdenMedica> ordenesmedicas= cargarTodas();
         boolean removed = ordenesmedicas.removeIf(m -> idOrden.equals(m.getIdOrden()));
         if(removed){
         guardarTodas(ordenesmedicas);
             System.out.println("Orden medica con id"+idOrden+"eliminada.");
         }
         
         return removed;
        }catch(Exception e){
            System.out.println("Error :"+e.getMessage());
     return false;

        }
    
    }
}

