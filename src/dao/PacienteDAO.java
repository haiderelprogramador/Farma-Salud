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

public interface  PacienteDAO {
    List<Paciente> cargarTodos();
    boolean guardarPaciente(Paciente paciente);
    void guardarTodos(List<Paciente> pacientes);
    boolean eliminarPaciente(String numeroDocumento);
    Paciente buscarPorDocumento(String documento);
    boolean actualizarPaciente(String documentoOriginal, Paciente pacienteActualizado);
    Paciente buscarPorEmail(String email);
}