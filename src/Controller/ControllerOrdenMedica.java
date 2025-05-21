/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAOImpl.OrdenMedicaDAOImpl;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Enfermedad;
import model.Medicamento;
import model.Medico;
import model.OrdenMedica;
import model.Paciente;
import dao.OrdenMedicaDAO;

/**
 *
 * @author HP
 */
public class ControllerOrdenMedica {
  private static ControllerOrdenMedica instance;

private final OrdenMedicaDAO ordenMedicaDAO = new OrdenMedicaDAOImpl();

    private JTextArea areaDiagnostico;
    private JTextArea areaMedicamentos;
    private Paciente pacienteSeleccionado;
    private Medico medicoSeleccionado;

    private ControllerOrdenMedica() {}

    public static ControllerOrdenMedica getInstance() {
        if (instance == null) {
            instance = new ControllerOrdenMedica();
        }
        return instance;
    }

    public void setAreaDiagnostico(JTextArea areaDiagnostico) {
        this.areaDiagnostico = areaDiagnostico;
    }

    public void setAreaMedicamentos(JTextArea areaMedicamentos) {
        this.areaMedicamentos = areaMedicamentos;
    }

    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }



    public void guardarOrdenMedicaDesdeFormulario() {
        try {
            if (pacienteSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Debe buscar un paciente primero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (medicoSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un médico.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (areaDiagnostico == null || areaMedicamentos == null) {
                JOptionPane.showMessageDialog(null, "Áreas de texto no están vinculadas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String diagnostico = areaDiagnostico.getText().trim();
            String receta = areaMedicamentos.getText().trim();

            if (diagnostico.isEmpty() || receta.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El diagnóstico y la receta no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            OrdenMedica orden = new OrdenMedica(
                diagnostico,
                receta,
                pacienteSeleccionado,
                medicoSeleccionado
             
            );

            ordenMedicaDAO.guardarOrdenMedica(orden); 
            JOptionPane.showMessageDialog(null, "Orden médica guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar orden médica: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
