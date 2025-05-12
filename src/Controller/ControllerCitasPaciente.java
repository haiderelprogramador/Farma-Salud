/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import com.toedter.calendar.JDateChooser;
import dao.CitasDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;
import dao.SalasDAO;
import dao.SedeDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Cita;
import model.Cita.EstadoCita;
import model.Medico;
import model.Paciente;
import model.Salas;
import model.Sede;

/**
 *
 * @author Maria liz
 */
public class ControllerCitasPaciente {
    private JLabel lblDocumento;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblEmail;
    private JComboBox cboTipoCita;
    private JComboBox cboMedico;
    private JComboBox cboEspecialidad;
    private JComboBox cboEstado;
    private JComboBox cboMotivoCita;
    private JComboBox cboEps;
    private JComboBox cboConsultorio;
    private JComboBox cboSede;
    private JComboBox cboHoraCita;
    private JTable tableCitas;
    private JDateChooser JDateFechaCita;
    private DefaultTableModel tableModelCitas;
    private CitasDAO citasDAO=new CitasDAO();
    private PacienteDAO pacienteDAO=new PacienteDAO();
    private MedicoDAO medicoDAO=new MedicoDAO();
    SedeDAO sedesDAO=new SedeDAO();
    SalasDAO salasDAO=new SalasDAO();

    public void setLblDocumento(JLabel lblDocumento) {
        this.lblDocumento = lblDocumento;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public void setLblApellido(JLabel lblApellido) {
        this.lblApellido = lblApellido;
    }

    public void setLblEmail(JLabel lblEmail) {
        this.lblEmail = lblEmail;
    }

    public void setCboTipoCita(JComboBox cboTipoCita) {
        this.cboTipoCita = cboTipoCita;
    }

    public void setCboMedico(JComboBox cboMedico) {
        this.cboMedico = cboMedico;
    }

    public void setCboEspecialidad(JComboBox cboEspecialidad) {
        this.cboEspecialidad = cboEspecialidad;
    }

    public void setCboEstado(JComboBox cboEstado) {
        this.cboEstado = cboEstado;
    }

    public void setCboMotivoCita(JComboBox cboMotivoCita) {
        this.cboMotivoCita = cboMotivoCita;
    }

    public void setCboEps(JComboBox cboEps) {
        this.cboEps = cboEps;
    }

    public void setCboConsultorio(JComboBox cboConsultorio) {
        this.cboConsultorio = cboConsultorio;
    }

    public void setCboSede(JComboBox cboSede) {
        this.cboSede = cboSede;
    }

    public void setCboHoraCita(JComboBox cboHoraCita) {
        this.cboHoraCita = cboHoraCita;
    }

    public void setJDateFechaCita(JDateChooser JDateFechaCita) {
        this.JDateFechaCita = JDateFechaCita;
        configurarDateChooser();
    }
    
   public void setTablaCitas(JTable tableCitas) {
        this.tableCitas = tableCitas;
        this.tableModelCitas = (DefaultTableModel) tableCitas.getModel();
    }
     public  void initTableModelCita() {
    if (tableCitas == null) {
        throw new IllegalStateException("La tabla de citas no ha sido inicializada");
    }
    
    tableModelCitas = new DefaultTableModel(
    new Object[]{ "Documento", "Nombre", "Apellido", "Eps", "Telefono", "Id Cita", "Hora Cita", 
                 "Motivo", "Fecha Cita", "Tipo Cita", "Consultorio", "Estado", 
                 "id Medico", "NombreMedico", "ApellidoMedico", "Especialidad", "Sede" }, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    tableCitas.setModel(tableModelCitas); 
    }
    public void cargarCitasPacienteEnTabla() {
    if (tableCitas == null || tableModelCitas == null) {
        throw new IllegalStateException("La tabla de citas no ha sido inicializada.");
    }
    
    tableModelCitas.setRowCount(0);
    
    
    List<Cita> citas = citasDAO.cargarTodos();
    for (Cita cita : citas) {
        Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
        Medico medico=medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
         String nombreSala = (cita.getSala() != null) ? cita.getSala().getNombreSala() : "No asignado";
         String nombreSede = (cita.getSede() != null) ? cita.getSede().getNombreSede() : "No asignada";
        if (paciente != null && medico !=null) {
            Object[] row = {
                paciente.getNumeroDocumento(),
                paciente.getNombres(),
                paciente.getApellidos(),
                paciente.getEps(),
                paciente.getCelular(),
                cita.getIdCita(),
                cita.getHora(),
                cita.getMotivo(),
                cita.getFechaCita(),
                cita.getTipoCita(),
                nombreSala,
                cita.getEstado().toString(),
                medico.getNumeroDocumento(),
                medico.getNombres(),
                medico.getApellidos(),
                medico.getEspecialidad(),
                nombreSede
            };
            tableModelCitas.addRow(row);
        }
    }
}  
   public void buscarCitaPorFecha(LocalDate fecha, String documentoPaciente) {
    if (tableCitas == null || tableModelCitas == null) {
        throw new IllegalStateException("La tabla de citas no está inicializada.");
    }

    tableModelCitas.setRowCount(0); // limpiar tabla

    List<Cita> citas = citasDAO.cargarTodos();

    for (Cita cita : citas) {
        if (cita.getFechaCita().isEqual(fecha) &&
            cita.getDocumentoPaciente().equals(documentoPaciente)) {

            Paciente paciente = pacienteDAO.buscarPorDocumento(documentoPaciente);
            Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
            String nombreSala = (cita.getSala() != null) ? cita.getSala().getNombreSala() : "No asignado";
            String nombreSede = (cita.getSede() != null) ? cita.getSede().getNombreSede() : "No asignada";

            Object[] row = {
                paciente.getNumeroDocumento(),
                paciente.getNombres(),
                paciente.getApellidos(),
                paciente.getEps(),
                paciente.getCelular(),
                cita.getIdCita(),
                cita.getHora(),
                cita.getMotivo(),
                cita.getFechaCita(),
                cita.getTipoCita(),
                nombreSala,
                cita.getEstado().toString(),
                medico.getNumeroDocumento(),
                medico.getNombres(),
                medico.getApellidos(),
                medico.getEspecialidad(),
                nombreSede
            };

            tableModelCitas.addRow(row);

            if (lblNombre != null) lblNombre.setText(paciente.getNombres());
            if (lblApellido != null) lblApellido.setText(paciente.getApellidos());
            if (lblEmail != null) lblEmail.setText(paciente.getEmail());
            if (cboEps != null) cboEps.setSelectedItem(paciente.getEps());
            if (cboConsultorio != null) cboConsultorio.setSelectedItem(nombreSala);
            if (cboSede != null) cboSede.setSelectedItem(nombreSede);
            if (cboEstado != null) cboEstado.setSelectedItem(cita.getEstado().toString());
            if (cboHoraCita != null) cboHoraCita.setSelectedItem(cita.getHora());
            if (cboMotivoCita != null) cboMotivoCita.setSelectedItem(cita.getMotivo());
            if (cboTipoCita != null) cboTipoCita.setSelectedItem(cita.getTipoCita());
            if (cboMedico != null) cboMedico.setSelectedItem(medico.getNombres() + " " + medico.getApellidos());
            if (cboEspecialidad != null) cboEspecialidad.setSelectedItem(medico.getEspecialidad());
        }
    }

    if (tableModelCitas.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null,
            "No tienes citas en la fecha seleccionada.",
            "Sin resultados",
            JOptionPane.INFORMATION_MESSAGE);
    }
}public void guardarCitaDesdePaciente(Paciente paciente, Medico medico, 
        String idCita, LocalDate fechaCita, String horaCita,
        String motivo, String tipoCita, EstadoCita estado,
        String nombreConsultorio, String nombreSede,
        ControllerCitas controllerCitas,
        ControllerCitasPaciente controllerPacienteCita) {

    try {
        // Validaciones básicas
        if (paciente == null || medico == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar paciente y médico");
            return;
        }

        if (idCita == null || idCita.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID de cita válido");
            return;
        }

        if (horaCita == null || motivo == null || tipoCita == null ||
            horaCita.trim().isEmpty() || motivo.trim().isEmpty() || tipoCita.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos de la cita");
            return;
        }

        // Buscar sala y sede
        Salas sala = salasDAO.buscarPorNombre(nombreConsultorio);
        if (sala == null) {
            JOptionPane.showMessageDialog(null, "Consultorio no válido");
            return;
        }

        Sede sede = sedesDAO.buscarPorNombre(nombreSede);
        if (sede == null) {
            JOptionPane.showMessageDialog(null, "Sede no válida");
            return;
        }

        // Validar si ya existe cita con ese ID
        boolean existe = citasDAO.cargarTodos().stream()
                .anyMatch(c -> c.getIdCita().equals(idCita));

        if (existe) {
            JOptionPane.showMessageDialog(null, "Ya existe una cita con este ID");
            return;
        }

        // Crear y guardar la cita
        Cita nuevaCita = new Cita(idCita, fechaCita, horaCita, motivo, tipoCita, sala,
                                  estado, paciente, medico, sede);
        nuevaCita.setDocumentoPaciente(paciente.getNumeroDocumento());
        nuevaCita.setDocumentoMedico(medico.getNumeroDocumento());

        citasDAO.guardarCita(nuevaCita);

        JOptionPane.showMessageDialog(null, "Cita registrada con éxito");

        // Actualizar las tablas en ambas vistas
        if (controllerCitas != null) {
            controllerCitas.cargarCitasEnTabla();
        }

        if (controllerPacienteCita != null) {
            controllerPacienteCita.buscarCitaPorFecha(fechaCita, paciente.getNumeroDocumento());
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al guardar la cita: " + e.getMessage());
        e.printStackTrace();
    }
}
public void buscarCitasPorFecha() {
    try {
        // Validar que se haya seleccionado una fecha
        if (JDateFechaCita.getDate() == null) {
            JOptionPane.showMessageDialog(null, 
                "Debe seleccionar una fecha para buscar", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener documento del paciente desde el label
        String documentoPaciente = lblDocumento.getText();
        if (documentoPaciente == null || documentoPaciente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "No se ha identificado al paciente",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir la fecha seleccionada a LocalDate
        LocalDate fechaSeleccionada = JDateFechaCita.getDate().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        
        // Obtener fecha actual
        LocalDate fechaActual = LocalDate.now();
        
        // Validar que la fecha seleccionada no sea anterior a la actual
        if (fechaSeleccionada.isBefore(fechaActual)) {
            JOptionPane.showMessageDialog(null, 
                "No se pueden buscar citas en fechas pasadas. Seleccione una fecha igual o posterior a hoy.", 
                "Fecha inválida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Limpiar la tabla antes de la búsqueda
        tableModelCitas.setRowCount(0);

        // Buscar citas para la fecha seleccionada y paciente
        List<Cita> citas = citasDAO.cargarTodos().stream()
            .filter(cita -> cita.getFechaCita().equals(fechaSeleccionada) &&
                           cita.getDocumentoPaciente().equals(documentoPaciente))
            .collect(Collectors.toList());

        // Mostrar resultados
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No se encontraron citas para la fecha seleccionada", 
                "Sin resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Cita cita : citas) {
                Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
                Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
                String nombreSede = (cita.getSede() != null) ? cita.getSede().getNombreSede() : "No asignada";
                String nombreSala = (cita.getSala() != null) ? cita.getSala().getNombreSala() : "No asignado";

                if (paciente != null && medico != null) {
                    Object[] row = {
                        // ... tus columnas como antes ...
                    };
                    tableModelCitas.addRow(row);
                }
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Error al buscar citas por fecha: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
public void configurarDateChooser() {
    // Obtener la fecha actual
    Date fechaActual = new Date();
    
    // Configurar el JDateChooser para que no permita fechas anteriores a hoy
    JDateFechaCita.setMinSelectableDate(fechaActual);
    
    // Opcional: Configurar un formateador de fecha
    JDateFechaCita.setDateFormatString("yyyy-MM-dd");
}

}
