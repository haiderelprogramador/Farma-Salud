
package Controller;

import com.toedter.calendar.JDateChooser;
import dao.CitasDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;
import dao.SalasDAO;
import dao.SedeDAO;
import farmasalud.view.CitaListener;
import farmasalud.view.CitaListener;
import farmasalud.view.ConsultarCita;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    private JTextField txtIdCita;
    private JLabel lblDocumento;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblEmail;
    private JComboBox cboTipoCita;
    private JLabel lblEspecialidad;
    private JComboBox cboEstado;
    private JComboBox cboMotivoCita;
    private JLabel lblEps;
    private Medico medicoSeleccionado;
    private Paciente pacienteActual;
    private Sede sedeSeleccionada;
    private JComboBox cboConsultorio;
    private  Salas salaSeleccinada;
    private JComboBox cboSede;
    private JComboBox cboMedico;
    private JLabel lblEspecialidadMedico;
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

   // Instancia Singleton
    private static ControllerCitasPaciente instance;
    
    // Listeners
    private List<CitaListener> listeners = new ArrayList<>();

    // Constructor privado
    private ControllerCitasPaciente() {
        // Inicialización de DAOs
        this.citasDAO = new CitasDAO();
        this.pacienteDAO = new PacienteDAO();
        this.medicoDAO = new MedicoDAO();
        this.sedesDAO = new SedeDAO();
        this.salasDAO = new SalasDAO();
    }

    // Método para obtener la instancia Singleton
    public static synchronized ControllerCitasPaciente getInstance() {
        if (instance == null) {
            instance = new ControllerCitasPaciente();
        }
        return instance;
    }

    // Métodos para gestionar listeners
    public void addCitaListener(CitaListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeCitaListener(CitaListener listener) {
        listeners.remove(listener);
    }

    private void notificarCitaAgregada(Cita cita) {
        for (CitaListener listener : listeners) {
            listener.citaAgregada(cita);
        }
    }

    public void setPacienteActual(Paciente pacienteActual){
        this.pacienteActual=pacienteActual;
    }
    public void setLblEspecialidadMedico(JLabel lblEspecialidadMedico) {
        this.lblEspecialidadMedico = lblEspecialidadMedico;
         configurarComboMedico();
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

    public void setTxtIdCita(JTextField txtIdCita) {
        this.txtIdCita = txtIdCita;
    }
   public void setJDateFechaCita(JDateChooser JDateFechaCita) {
    this.JDateFechaCita = JDateFechaCita;
    if (this.JDateFechaCita != null) {
        // Configura la fecha mínima como hoy
        this.JDateFechaCita.setMinSelectableDate(new Date());
        // Establece un formato de fecha claro
        this.JDateFechaCita.setDateFormatString("yyyy-MM-dd");
        // Opcional: Establece la fecha actual por defecto
        this.JDateFechaCita.setDate(new Date());
    }
}
    public void LblEspecialidad(JLabel lblEspecialidadMedico) {
        this.lblEspecialidadMedico = lblEspecialidadMedico;
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

   public void setCboMedico(JComboBox cboMedico) {
    this.cboMedico = cboMedico;
    cargarMedicosEnComboBox();           // <- debe cargarse aquí
    configurarComboMedico();             // <- y luego configurar el listener
}
     public boolean cargarYPersistirPaciente(String documento) {
        try {
            this.pacienteActual = pacienteDAO.buscarPorDocumento(documento);
            if (this.pacienteActual == null) {
                System.err.println("Paciente no encontrado en BD: " + documento);
                return false;
            }
            
            if (lblDocumento != null) lblDocumento.setText(pacienteActual.getNumeroDocumento());
            if (lblNombre != null) lblNombre.setText(pacienteActual.getNombres());
            if (lblApellido != null) lblApellido.setText(pacienteActual.getApellidos());
            if (lblEmail != null) lblEmail.setText(pacienteActual.getEmail());
            if (lblEps != null) lblEps.setText(pacienteActual.getEps());
            
            return true;
        } catch (Exception e) {
            System.err.println("Error al cargar paciente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

   
public void guardarCitaDesdeFormulario() {
    if (pacienteActual == null || pacienteActual.getNumeroDocumento() == null) {
        JOptionPane.showMessageDialog(null, 
            "Error: No se ha cargado correctamente el paciente", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
// Modifica esta parte del método guardarCitaDesdeFormulario:
if (cboMedico.getSelectedItem() == null || "<Seleccione>".equals(cboMedico.getSelectedItem().toString())) {
    JOptionPane.showMessageDialog(null, "Debe seleccionar un médico", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

System.out.println("Médico seleccionado: " + cboMedico.getSelectedItem());

String nombreCompleto = cboMedico.getSelectedItem().toString();
medicoSeleccionado = obtenerMedicoPorNombreCompleto(nombreCompleto);


    try {
        String idCita = txtIdCita.getText().trim();
        Date fechaDate = JDateFechaCita.getDate();
        if (fechaDate == null) {
    JOptionPane.showMessageDialog(null, 
        "Por favor seleccione una fecha válida", 
        "Fecha requerida", 
        JOptionPane.ERROR_MESSAGE);
    JDateFechaCita.requestFocus(); // Enfoca el campo de fecha
    return;
}
        String horaCita = cboHoraCita.getSelectedItem().toString();
        String motivo = cboMotivoCita.getSelectedItem().toString();
        String tipoCita = cboTipoCita.getSelectedItem().toString();
        EstadoCita estado = EstadoCita.valueOf(cboEstado.getSelectedItem().toString());
 
if (cboTipoCita.getSelectedIndex() <= 0 || 
    cboMotivoCita.getSelectedIndex() <= 0 || 
    cboHoraCita.getSelectedIndex() <= 0) {
    JOptionPane.showMessageDialog(null, "Todos los campos deben tener una selección válida", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

       if (idCita.isEmpty() || fechaDate == null || horaCita.isEmpty() || motivo.isEmpty() || 
    tipoCita.isEmpty() || tipoCita.equalsIgnoreCase("<Seleccione>")) {
    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}


        String nombreSede = cboSede.getSelectedItem().toString();
        if (nombreSede.equals("<Seleccione>")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una sede válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        sedeSelecccionada = sedesDAO.buscarPorNombre(nombreSede);
        if (sedeSelecccionada == null) {
            JOptionPane.showMessageDialog(null, "La sede seleccionada no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        

        String nombreConsultorio = cboConsultorio.getSelectedItem().toString();
        if (nombreConsultorio.equals("<Seleccione>")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un consultorio válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        salaSeleccinada = salasDAO.buscarPorNombre(nombreConsultorio);
        if (salaSeleccinada == null) {
            JOptionPane.showMessageDialog(null, "El consultorio seleccionado no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate fechaCita = fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (existeCitaEnMismaHora(fechaCita, horaCita)) {
            JOptionPane.showMessageDialog(null,
                "Ya existe una cita programada para esta hora. Por favor seleccione otra hora.",
                "Hora no disponible",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean existe = citasDAO.cargarTodos().stream()
            .anyMatch(p -> p.getIdCita() != null && p.getIdCita().equals(idCita));
        if (existe) {
            JOptionPane.showMessageDialog(null,
                "Ya existe una cita con este código",
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
            pacienteActual,
            medicoSeleccionado,
            sedeSelecccionada
        );

        nuevaCita.setDocumentoPaciente(pacienteActual.getNumeroDocumento());

        citasDAO.guardarCita(nuevaCita);
        JOptionPane.showMessageDialog(null, "Cita guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        notificarCitaAgregada(nuevaCita);
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
                  "NombreMedico", "ApellidoMedico", "Especialidad", "Sede" }, 0) {
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
        Medico medico = cita.getMedico(); 
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
            if (lblEspecialidadMedico != null) lblEspecialidadMedico.setText(medico.getEspecialidad());
            if (cboMedico != null) cboMedico.setSelectedItem(medico.getNombres());
            if (lblEspecialidadMedico != null) lblEspecialidadMedico.setText(medico.getEspecialidad());
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
 public boolean existeOtraCitaEnMismaHora(LocalDate fecha, String hora, String nombreCompletoMedico, String idCitaExcluir) {
    List<Cita> citas = citasDAO.cargarTodos();

    for (Cita cita : citas) {
        if (!cita.getIdCita().equals(idCitaExcluir)) {
            if (cita.getFechaCita().equals(fecha) &&
                cita.getHora().equals(hora) &&
                cita.getMedico() != null &&
                (cita.getMedico().getNombres() + " " + cita.getMedico().getApellidos()).equalsIgnoreCase(nombreCompletoMedico) &&
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
        return;
    }

    tableModelCitas.setRowCount(0); // Limpiar

    List<Cita> citas = citasDAO.cargarTodos();

    for (Cita cita : citas) {
        if (documentoPaciente.equals(cita.getDocumentoPaciente())) {
            Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
            Medico medico = cita.getMedico();
            String nombreSala = (cita.getSala() != null) ? cita.getSala().getNombreSala() : "No asignado";
            String nombreSede = (cita.getSede() != null) ? cita.getSede().getNombreSede() : "No asignada";

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
                    nombreSala,
                    cita.getEstado().toString(),
                    medico.getNombres(),
                    medico.getApellidos(),
                    medico.getEspecialidad(),
                    nombreSede
                };
                tableModelCitas.addRow(row);
            }
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


public void inicializarInterfazPaciente(String documentoPaciente) {
    
        cargarDatosPaciente(documentoPaciente);
    }


public void cargarDatosPaciente(String documento) {
  
    try {
        this.pacienteActual = pacienteDAO.buscarPorDocumento(documento);
        
        if (pacienteActual == null) {
            System.err.println("[ERROR] No se encontró paciente con documento: " + documento);
            throw new RuntimeException("Paciente no encontrado");
        }
        
        
        if (lblDocumento != null) lblDocumento.setText(pacienteActual.getNumeroDocumento());
        if (lblNombre != null) lblNombre.setText(pacienteActual.getNombres());
        if (lblApellido != null) lblApellido.setText(pacienteActual.getApellidos());
        if (lblEmail != null) lblEmail.setText(pacienteActual.getEmail());
        if (lblEps != null) lblEps.setText(pacienteActual.getEps());
        
    } catch (Exception e) {
        System.err.println("[ERROR] Error al cargar paciente: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al cargar paciente", e);
    }
}
public Paciente getPacienteSeleccionado() {
    return pacienteActual;
}
public void configurarComboMedico() {
    if (cboMedico == null) return;

    // Evitar múltiples listeners
    for (ActionListener al : cboMedico.getActionListeners()) {
        cboMedico.removeActionListener(al);
    }

    cboMedico.addActionListener(e -> {
        String seleccion = (String) cboMedico.getSelectedItem();

        if (seleccion != null && !seleccion.equals("<Seleccione>")) {
            medicoSeleccionado = obtenerMedicoPorNombreCompleto(seleccion);

            if (medicoSeleccionado != null && lblEspecialidadMedico != null) {
                lblEspecialidadMedico.setText(medicoSeleccionado.getEspecialidad());
            }
        } else {
            medicoSeleccionado = null;

            if (lblEspecialidadMedico != null) {
                lblEspecialidadMedico.setText("");
            }
        }
    });
}

public void cargarMedicosEnComboBox() {
    if (cboMedico == null) return;

    try {
        cboMedico.removeAllItems();
        cboMedico.addItem("<Seleccione>");

        List<Medico> medicos = medicoDAO.cargarTodos();

        for (Medico medico : medicos) {
            if (medico != null && medico.getNombres() != null && medico.getApellidos() != null) {
                String nombreCompleto = (medico.getNombres() + " " + medico.getApellidos()).trim().replaceAll("\\s+", " ");
                cboMedico.addItem(nombreCompleto);
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar médicos en ComboBox: " + e.getMessage());
        e.printStackTrace();
    }
}
public void filtrarMedicosPorEspecialidad(String especialidad) {
    if (cboMedico == null) return;
    
    cboMedico.removeAllItems();
    cboMedico.addItem("<Seleccione>");
    
    if (especialidad == null || especialidad.equals("<Seleccione>")) {
        cargarMedicosEnComboBox();
        return;
    }
    
    List<Medico> medicos = medicoDAO.cargarTodos();
    for (Medico medico : medicos) {
        if (medico.getEspecialidad() != null && 
            medico.getEspecialidad().equalsIgnoreCase(especialidad)) {
            String nombreCompleto = medico.getNombres() + " " + medico.getApellidos();
            cboMedico.addItem(nombreCompleto);
        }
    }
}
public Medico obtenerMedicoPorNombreCompleto(String nombreCompleto) {
    if (nombreCompleto == null || nombreCompleto.isEmpty() || "<Seleccione>".equals(nombreCompleto)) {
        return null;
    }
    
    // Normalización del nombre
    nombreCompleto = nombreCompleto.trim().replaceAll("\\s+", " ");
    
    List<Medico> medicos = medicoDAO.cargarTodos();
    for (Medico medico : medicos) {
        if (medico != null && medico.getNombres() != null && medico.getApellidos() != null) {
            String nombreCompletoMedico = (medico.getNombres() + " " + medico.getApellidos())
                .trim().replaceAll("\\s+", " ");
                
            if (nombreCompletoMedico.equalsIgnoreCase(nombreCompleto)) {
                return medico;
            }
        }
    }
    System.err.println("No se encontró médico con nombre: " + nombreCompleto);
    return null;
}
 public static void resetInstance() {
        instance = null;
    }

}
