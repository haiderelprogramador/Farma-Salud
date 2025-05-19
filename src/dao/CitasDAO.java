/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Cita;

/**
 *
 * @author Maria liz
 */
public interface CitasDAO {
      List<Cita> cargarTodos();
    List<Cita> obtenerCitaPorCodigo(String IdCitas);
    void guardarCita(Cita cita);
    void guardarTodos(List<Cita> citas);
    List<Cita> obtenerCitasPorPaciente(String documentoPaciente);
    List<Cita> obtenerCitasMedico(String documentoMedico);
    Cita obtenerCitaPorId(String idCita);
    Cita buscarPorId(String idCita);
    boolean eliminarCita(String IdCita);
    boolean actualizarCita(String idCitaOriginal, Cita citaActualizada);
    int contarCitasPorMedicoYFecha(String documentoMedico, LocalDate fecha);

}
