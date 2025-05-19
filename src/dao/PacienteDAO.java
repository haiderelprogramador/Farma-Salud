/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import model.Paciente;

<<<<<<< HEAD
/**
 *
 * @author Maria liz
 */
public interface PacienteDAO {
       boolean guardarPaciente(Paciente paciente);
    List<Paciente> cargarTodos();
=======
public interface  PacienteDAO {
    List<Paciente> cargarTodos();
    boolean guardarPaciente(Paciente paciente);
    void guardarTodos(List<Paciente> pacientes);
>>>>>>> e23e402391b91befe5ac65502aa173daafde32c2
    boolean eliminarPaciente(String numeroDocumento);
    Paciente buscarPorDocumento(String documento);
    boolean actualizarPaciente(String documentoOriginal, Paciente pacienteActualizado);
    Paciente buscarPorEmail(String email);
<<<<<<< HEAD
    void guardarTodos(List<Paciente> pacientes);
}
=======
}
>>>>>>> e23e402391b91befe5ac65502aa173daafde32c2
