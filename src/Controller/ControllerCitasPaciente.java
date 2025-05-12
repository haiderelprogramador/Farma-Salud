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
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;
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
    private JTextField IdCita;
    private JLabel lblDocumento;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblEmail;
    private JComboBox cboTipoCita;
    private JComboBox cboEspecialidad;
    private JComboBox cboEstado;
    private JComboBox cboMotivoCita;
    private JLabel lblEps;
    private Medico medicoSeleccionado;
    private Paciente pacienteSeleccionado;
    private Paciente pacienteActual;
    private Sede sedeSeleccionada;
    private JComboBox cboConsultorio;
    private  Salas salaSeleccinada;
    private JComboBox cboSede;
    private JComboBox cboApellidoMedico;
    private JComboBox cboNombreMedico;
    private JComboBox cboEspecialdadMedico;
    private JComboBox cboHoraCita;
    private JTable tableCitas;
    private JDateChooser JDateFechaCita;
    private DefaultTableModel tableModelCitas;
    private Sede sedeSelecccionada;
    private CitasDAO citasDAO=new CitasDAO();
    private PacienteDAO pacienteDAO=new PacienteDAO();
    private MedicoDAO medicoDAO=new MedicoDAO();
    SedeDAO sedesDAO=new SedeDAO();
    SalasDAO salasDAO=new SalasDAO();

    public void setCboApellidoMedico(JComboBox cboApellidoMedico) {
        this.cboApellidoMedico = cboApellidoMedico;
    }

    public void setCboNombreMedico(JComboBox cboNombreMedico) {
        this.cboNombreMedico = cboNombreMedico;
         cargarNombresMedicosEnComboBox();
    }

    public void setCboEspecialdadMedico(JComboBox cboEspecialdadMedico) {
        this.cboEspecialdadMedico = cboEspecialdadMedico;
         cargarEspecialidadesMedicosEnComboBox();
    }
    

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

    public void setIdCita(JTextField IdCita) {
        this.IdCita = IdCita;
    }
    public void setJDateFechaCita(JDateChooser JDateFechaCita) {
    this.JDateFechaCita = JDateFechaCita;
    if (this.JDateFechaCita != null) {
        this.JDateFechaCita.setMinSelectableDate(new Date());
    }
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

    public void setCLblEps(JLabel lblEps) {
        this.lblEps = lblEps;
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

    public void setPacienteActual(Paciente pacienteActual) {
        this.pacienteActual = pacienteActual;
    }
       public void guardarCitaDesdeFormulario() {
        try {
        
            String idCita=IdCita.getText().toString();
            String fechaStr = JDateFechaCita.getDate().toString();
            String horaCita = cboHoraCita.getSelectedItem().toString();
            String motivo = cboMotivoCita.getSelectedItem().toString();
            String tipoCita = cboTipoCita.getSelectedItem().toString();
            EstadoCita estado = EstadoCita.valueOf(cboEstado.getSelectedItem().toString());   
           
            
             if (fechaStr.isEmpty() || horaCita.isEmpty() || motivo.isEmpty() || 
            tipoCita.equals("<Seleccione>")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
            
                    
       Date fechaDate = JDateFechaCita.getDate();
     if (fechaDate == null) {
      JOptionPane.showMessageDialog(null, "La fecha de la cita es obligatoria", 
        "Error", JOptionPane.ERROR_MESSAGE);
    return;
}
     String nombreSede = cboSede.getSelectedItem().toString();
if (nombreSede.equals("<Seleccione>")) {
    JOptionPane.showMessageDialog(null,
        "Debe seleccionar una sede válida",
        "Error",
        JOptionPane.ERROR_MESSAGE);
    return;
}

sedeSelecccionada = sedesDAO.buscarPorNombre(nombreSede);
if (sedeSelecccionada == null) {
    JOptionPane.showMessageDialog(null,
        "La sede seleccionada no existe",
        "Error",
        JOptionPane.ERROR_MESSAGE);
    return;
}


String nombreConsultorio = cboConsultorio.getSelectedItem().toString();

if (nombreConsultorio.equals("<Seleccione>")) {
    JOptionPane.showMessageDialog(null, 
        "Debe seleccionar un consultorio válido", 
        "Error", 
        JOptionPane.ERROR_MESSAGE);
    return;
}

salaSeleccinada = salasDAO.buscarPorNombre(nombreConsultorio);

if (salaSeleccinada == null) {
    JOptionPane.showMessageDialog(null, 
        "El consultorio seleccionado no existe", 
        "Error", 
        JOptionPane.ERROR_MESSAGE);
    return;
}



     LocalDate fechaCita = fechaDate.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate();
       if (existeCitaEnMismaHora(fechaCita, horaCita)) {
            JOptionPane.showMessageDialog(null,
                "Ya existe una cita programada para esta hora. Por favor seleccione otra hora.",
                "Hora no disponible",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean existe = citasDAO.cargarTodos().stream()
    .anyMatch(p -> p.getIdCita() != null && p.getIdCita().equals(IdCita));
            if (existe) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe una cita  con este codigo",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Cita nuevaCita = new Cita(
                idCita, 
                fechaCita, 
                horaCita, 
                motivo, 
                tipoCita, 
                salaSeleccinada,
                estado, 
                pacienteSeleccionado,
                medicoSeleccionado,
                sedeSeleccionada
                
                
            );
              nuevaCita.setDocumentoPaciente(pacienteSeleccionado.getNumeroDocumento());
              nuevaCita.setDocumentoMedico(medicoSeleccionado.getNumeroDocumento());
            citasDAO.guardarCita(nuevaCita);
           
            JOptionPane.showMessageDialog(null, "Cita guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
          cargarCitasPacienteEnTabla();
         
          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar cita: " + e.getMessage(),
                "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }    
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

    tableModelCitas.setRowCount(0); 
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
            if (lblEps != null) lblEps.setText(paciente.getEps());
            if (cboConsultorio != null) cboConsultorio.setSelectedItem(nombreSala);
            if (cboSede != null) cboSede.setSelectedItem(nombreSede);
            if (cboEstado != null) cboEstado.setSelectedItem(cita.getEstado().toString());
            if (cboHoraCita != null) cboHoraCita.setSelectedItem(cita.getHora());
            if (cboMotivoCita != null) cboMotivoCita.setSelectedItem(cita.getMotivo());
            if (cboTipoCita != null) cboTipoCita.setSelectedItem(cita.getTipoCita());
            if (cboEspecialidad != null) cboEspecialidad.setSelectedItem(medico.getEspecialidad());
            if (cboNombreMedico != null) cboNombreMedico.setSelectedItem(medico.getNombres());
            if (cboApellidoMedico != null) cboApellidoMedico.setSelectedItem(medico.getApellidos());
            if (cboEspecialdadMedico != null) cboEspecialdadMedico.setSelectedItem(medico.getEspecialidad());
        }
    }

    if (tableModelCitas.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null,
            "No tienes citas en la fecha seleccionada.",
            "Sin resultados",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
public void buscarCitasPorFecha() {
    try {
        if (JDateFechaCita == null) {
            JOptionPane.showMessageDialog(null, 
                "El selector de fecha no está configurado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (JDateFechaCita.getDate() == null) {
            JOptionPane.showMessageDialog(null, 
                "Por favor seleccione una fecha", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate fechaSeleccionada = JDateFechaCita.getDate().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

        tableModelCitas.setRowCount(0);

        List<Cita> citasEncontradas = citasDAO.cargarTodos().stream()
            .filter(cita -> cita.getFechaCita().equals(fechaSeleccionada))
            .collect(Collectors.toList());

        if (citasEncontradas.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No hay citas programadas para " + fechaSeleccionada, 
                "Resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Cita cita : citasEncontradas) {
                Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
                Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
                
                if (paciente != null && medico != null) {
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
                        cita.getSala() != null ? cita.getSala().getNombreSala() : "No asignado",
                        cita.getEstado().toString(),
                        medico.getNumeroDocumento(),
                        medico.getNombres(),
                        medico.getApellidos(),
                        medico.getEspecialidad(),
                        cita.getSede() != null ? cita.getSede().getNombreSede() : "No asignada"
                    };
                    tableModelCitas.addRow(row);
                }
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Error al buscar citas: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

public void configurarDateChooser() {
    Date fechaActual = new Date();
    
    JDateFechaCita.setMinSelectableDate(fechaActual);
    
    JDateFechaCita.setDateFormatString("yyyy-MM-dd");
}
public boolean existeCitaEnMismaHora(LocalDate fecha, String hora) {
    List<Cita> citas = citasDAO.cargarTodos();
    
    for (Cita cita : citas) {
        if (cita.getFechaCita().equals(fecha) && 
            cita.getHora().equals(hora) && 
            cita.getEstado() != EstadoCita.CANCELADA) {
            return true;
        }
    }
    return false;
}
 public boolean existeOtraCitaEnMismaHora(LocalDate fecha, String hora, String documentoMedico, String idCitaExcluir) {
    List<Cita> citas = citasDAO.cargarTodos();
    
    for (Cita cita : citas) {
        if (!cita.getIdCita().equals(idCitaExcluir)) { 
            if (cita.getFechaCita().equals(fecha) && 
                cita.getHora().equals(hora) && 
                cita.getDocumentoMedico().equals(documentoMedico) &&
                cita.getEstado() != EstadoCita.CANCELADA) {
                return true;
            }
        }
    }
    return false;
  }
public void configurarColoresTablaCitas() {
    DefaultTableCellRenderer rendererEstado = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component c = super.getTableCellRendererComponent(table, value, 
                    isSelected, hasFocus, row, column);
            
            String estado = value != null ? value.toString() : "";
            
            if (isSelected) {
                c.setBackground(new Color(57, 105, 138)); 
                c.setForeground(Color.WHITE);
            } else if ("CANCELADA".equalsIgnoreCase(estado)) {
                c.setBackground(Color.RED);
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());
            }
            
            return c;
        }
    };
    
    tableCitas.getColumnModel().getColumn(11).setCellRenderer(rendererEstado);
   }

 
  public void cargarCitasPorPaciente(String documentoPaciente) {
    if (tableCitas == null || tableModelCitas == null) {
        throw new IllegalStateException("La tabla de citas no está inicializada.");
    }

    tableModelCitas.setRowCount(0); 

    List<Cita> citasDelPaciente = citasDAO.cargarTodos().stream()
            .filter(cita -> cita.getDocumentoPaciente().equals(documentoPaciente))
            .collect(Collectors.toList());

    if (citasDelPaciente.isEmpty()) {
        JOptionPane.showMessageDialog(null, 
            "No tienes citas programadas.", 
            "Información", 
            JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    for (Cita cita : citasDelPaciente) {
        Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
        Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
        
        if (paciente != null && medico != null) {
            Object[] row = {
                cita.getIdCita(),
                cita.getFechaCita(),
                cita.getHora(),
                cita.getMotivo(),
                cita.getTipoCita(),
                cita.getSala(),
                cita.getEstado().toString(),
                medico.getNombres() + " " + medico.getApellidos(),
                medico.getEspecialidad()
            };
            tableModelCitas.addRow(row);
        }
    }
  }
  public void cargarSalasEnComboBox(JComboBox<String> comboBox) {
    if (comboBox == null) {
        System.err.println("Error: El JComboBox de sala es nulo");
        return;
    }

        try {
            comboBox.removeAllItems();
            comboBox.addItem("<Seleccione>");
            
            List<Salas> sala = salasDAO.cargarTodasSalas();
            
            
            
            for (Salas salas : sala) {
                if (salas != null && salas.getNombreSala() != null && !salas.getNombreSala().trim().isEmpty()) {
                    comboBox.addItem(salas.getNombreSala());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar consultorio en ComboBox: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar los consultorio: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
  public void cargarSedesEnComboBox(JComboBox<String> comboBox) {
    if (comboBox == null) {
        System.err.println("Error: El JComboBox de sedes es nulo");
        return;
    }

        try {
            comboBox.removeAllItems();
            comboBox.addItem("<Seleccione>");
            
            List<Sede> sedes = sedesDAO.cargarTodasSedes();
            
            
            
            for (Sede sede : sedes) {
                if (sede != null && sede.getNombreSede() != null && !sede.getNombreSede().trim().isEmpty()) {
                    comboBox.addItem(sede.getNombreSede());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar sedes en ComboBox: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar las sedes: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
  

public void cargarNombresMedicosEnComboBox() {
    if (cboNombreMedico == null) {
        System.err.println("Error: El JComboBox de nombres de médicos es nulo");
        return;
    }

    try {
        cboNombreMedico.removeAllItems();
        cboNombreMedico.addItem("<Seleccione>");
        
        List<Medico> medicos = medicoDAO.cargarTodos();
        
        for (Medico medico : medicos) {
            if (medico != null && medico.getNombres() != null && medico.getApellidos() != null) {
                // Formato: "Nombre Apellido"
                String nombreCompleto = medico.getNombres() + " " + medico.getApellidos();
                cboNombreMedico.addItem(nombreCompleto);
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar nombres de médicos: " + e.getMessage());
        e.printStackTrace();
    }
}public void setCboNombreMedicos(JComboBox cboNombreMedico) {
    this.cboNombreMedico = cboNombreMedico;
    cargarNombresMedicosEnComboBox();
    
    // Listener para manejar la selección
    cboNombreMedico.addActionListener(e -> {
        if (cboNombreMedico.getSelectedIndex() > 0) {
            String nombreCompleto = cboNombreMedico.getSelectedItem().toString();
            List<Medico> medicos = medicoDAO.cargarTodos();
            
            for (Medico medico : medicos) {
                String nombreMedico = medico.getNombres() + " " + medico.getApellidos();
                if (nombreMedico.equals(nombreCompleto)) {
                    // Actualizar especialidad si es necesario
                    if (cboEspecialdadMedico != null) {
                        cboEspecialdadMedico.setSelectedItem(medico.getEspecialidad());
                    }
                    medicoSeleccionado = medico; // Guardar referencia
                    break;
                }
            }
        }
    });
}

public void cargarEspecialidadesMedicosEnComboBox() {
    if (cboEspecialdadMedico == null) {
        System.err.println("Error: El JComboBox de especialidades es nulo");
        return;
    }

    try {
        cboEspecialdadMedico.removeAllItems();
        cboEspecialdadMedico.addItem("<Seleccione>");
        
        List<Medico> medicos = medicoDAO.cargarTodos();
        
        for (Medico medico : medicos) {
            if (medico != null && medico.getEspecialidad() != null) {
                cboEspecialdadMedico.addItem(medico.getEspecialidad());
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar especialidades: " + e.getMessage());
        e.printStackTrace();
    }
}
  public void setCboNMedico(JComboBox cboNombreMedico) {
    this.cboNombreMedico = cboNombreMedico;
     cargarNombresMedicosEnComboBox();
    
 
    cboNombreMedico.addActionListener(e -> {
        if (cboNombreMedico.getSelectedIndex() > 0) {
            String nombreSeleccionado = cboNombreMedico.getSelectedItem().toString();
            List<Medico> medicos = medicoDAO.cargarTodos();
            
            for (Medico medico : medicos) {
                if (medico.getNombres().equals(nombreSeleccionado)) {
                    cboApellidoMedico.setSelectedItem(medico.getApellidos());
                    cboEspecialidad.setSelectedItem(medico.getEspecialidad());
                    medicoSeleccionado = medico; 
                    break;
                }
            }
        }
    });
}
public void inicializarInterfazPaciente(String documentoPaciente) {
        cargarDatosPaciente(documentoPaciente);
    }


// Método para cargar datos
public void cargarDatosPaciente(String documento) {
    try {
        this.pacienteSeleccionado = pacienteDAO.buscarPorDocumento(documento);
        
        if (pacienteSeleccionado != null) {
            if (lblDocumento != null) lblDocumento.setText(pacienteSeleccionado.getNumeroDocumento());
            if (lblNombre != null) lblNombre.setText(pacienteSeleccionado.getNombres());
            if (lblApellido != null) lblApellido.setText(pacienteSeleccionado.getApellidos());
            if (lblEmail != null) lblEmail.setText(pacienteSeleccionado.getEmail());
            if (lblEps != null) lblEps.setText(pacienteSeleccionado.getEps());
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Error al cargar datos del paciente: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

// Getter para el paciente
public Paciente getPacienteSeleccionado() {
    return pacienteSeleccionado;
}
private void limpiarCamposPaciente() {
    if (lblDocumento != null) lblDocumento.setText("");
    if (lblNombre != null) lblNombre.setText("");
    if (lblApellido != null) lblApellido.setText("");
    if (lblEmail != null) lblEmail.setText("");
    if (lblEps != null) lblEps.setText("");
    this.pacienteSeleccionado = null;
}

}
