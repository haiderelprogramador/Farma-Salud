package Controller;

import DAOImpl.PacienteDAOImpl;
import com.toedter.calendar.JDateChooser;
import dao.PacienteDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Paciente;


/**
 *
 * @author Maria liz
 */


public class ControllerPaciente {

  
    private DefaultTableModel tableModelPaciente;
    private String documentoOriginal;
    private  static ControllerPaciente instance;
    private JTable tablaPacientes;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDocumento;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JTextField txtContraseña;
    private JDateChooser dateChooserNacimiento;
    private final PacienteDAO pacienteDAO;
    private JComboBox<String> cbSexo;
    private JComboBox<String> cbEps;
    private JComboBox<String> cbTipoDocumento;
    private JComboBox<String> cbTipoSangre;
    private JTextArea txtAreaAntecedentes;
    
    public static ControllerPaciente getInstance() {
        if (instance == null) {
            instance = new ControllerPaciente();
        }
        return instance;
    }
       public  ControllerPaciente() {
        this.pacienteDAO = new PacienteDAOImpl();
    }
    public void setTablaPacientes(JTable tablaPacientes) {
        this.tablaPacientes = tablaPacientes;
        this.tableModelPaciente = (DefaultTableModel) tablaPacientes.getModel();
    }
    
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }
    
    public void setTxtApellido(JTextField txtApellido) {
        this.txtApellido = txtApellido;
    }
    
    public void setTxtDocumento(JTextField txtDocumento) {
        this.txtDocumento = txtDocumento;
    }
    
    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }
    
    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }
    
    public void setTxtContraseña(JTextField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }
    
    public void setDateChooserNacimiento(JDateChooser dateChooserNacimiento) {
        this.dateChooserNacimiento = dateChooserNacimiento;
    }
    
    public void setCbSexo(JComboBox<String> cbSexo) {
        this.cbSexo = cbSexo;
    }
    
    public void setCbEps(JComboBox<String> cbEps) {
        this.cbEps = cbEps;
    }
    
    public void setCbTipoDocumento(JComboBox<String> cbTipoDocumento) {
        this.cbTipoDocumento = cbTipoDocumento;
    }
    
    public void setCbTipoSangre(JComboBox<String> cbTipoSangre) {
        this.cbTipoSangre = cbTipoSangre;
    }
    
    public void setTxtAreaAntecedentes(JTextArea txtAreaAntecedentes) {
        this.txtAreaAntecedentes = txtAreaAntecedentes;
    }
    
    // Inicialización de la tabla
    public void initTablePaciente() {
        tableModelPaciente = new DefaultTableModel(
            new Object[]{"Documento", "Nombres", "Apellidos", "Fecha Nac.", "Sexo", 
                        "EPS", "Email", "Teléfono", "Tipo Doc.", "Tipo Sangre", "Antecedentes"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPacientes.setModel(tableModelPaciente);
    }
    
    public void cargarDatosEnTablaPaciente() {
        try {
            tableModelPaciente.setRowCount(0);
            List<Paciente> pacientes = pacienteDAO.cargarTodos();
            
            if (pacientes == null || pacientes.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron pacientes registrados", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            for (Paciente paciente : pacientes) {
                Object[] row = {
                    paciente.getNumeroDocumento(),
                    paciente.getNombres(),
                    paciente.getApellidos(),
                    paciente.getFechaNacimiento(),
                    paciente.getSexo(),
                    paciente.getEps(),
                    paciente.getEmail(),
                    paciente.getCelular(),
                    paciente.getTipoDocumento(),
                    paciente.getTipoSangre(),
                    paciente.getAntecedentes(),
                    paciente.getContraseña()
                };
                tableModelPaciente.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al cargar pacientes: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void guardarPacienteDesdeFormulario() {
        try {
            // Obtener datos del formulario
            String documento = txtDocumento.getText().trim();
            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String celular = txtCelular.getText().trim();
            String contraseña = txtContraseña.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String eps = cbEps.getSelectedItem().toString();
            String tipoDocumento = cbTipoDocumento.getSelectedItem().toString();
            String tipoSangre = cbTipoSangre.getSelectedItem().toString();
            String antecedentes = txtAreaAntecedentes.getText().trim();
            
            // Validar campos obligatorios
            if (documento.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || 
                email.isEmpty() || celular.isEmpty()  || 
                dateChooserNacimiento.getDate() == null) {
                JOptionPane.showMessageDialog(null, 
                    "Todos los campos son obligatorios", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Convertir fecha
            LocalDate fechaNacimiento = dateChooserNacimiento.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            // Verificar si ya existe el paciente
            if (existePaciente(documento)) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe un paciente con este documento",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear nuevo paciente
            Paciente nuevoPaciente = new Paciente(
                documento,
                nombres,
                apellidos,
                fechaNacimiento,
                sexo,
                eps,
                email,
                celular,
                contraseña,
                tipoDocumento,
                tipoSangre,
                antecedentes
            );
            
            // Guardar en la base de datos
            if (pacienteDAO.guardarPaciente(nuevoPaciente)) {
                JOptionPane.showMessageDialog(null, 
                    "Paciente guardado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaPaciente();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo guardar el paciente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al guardar paciente: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private boolean existePaciente(String documento) {
        List<Paciente> pacientes = pacienteDAO.cargarTodos();
        if (pacientes != null) {
            return pacientes.stream()
                .anyMatch(p -> p.getNumeroDocumento() != null && 
                              p.getNumeroDocumento().equals(documento));
        }
        return false;
    }
    
    public void limpiarFormulario() {
        txtDocumento.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        txtContraseña.setText("");
        dateChooserNacimiento.setDate(null);
        cbSexo.setSelectedIndex(0);
        cbEps.setSelectedIndex(0);
        cbTipoDocumento.setSelectedIndex(0);
        cbTipoSangre.setSelectedIndex(0);
        txtAreaAntecedentes.setText("");
    }
    
    public void eliminarPacienteSeleccionado() {
        int filaSeleccionada = tablaPacientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, 
                "Seleccione un paciente de la tabla", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String documento = tableModelPaciente.getValueAt(filaSeleccionada, 0).toString();
        
        int confirmacion = JOptionPane.showConfirmDialog(
            null, 
            "¿Eliminar al paciente con documento " + documento + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (pacienteDAO.eliminarPaciente(documento)) {
                JOptionPane.showMessageDialog(null, 
                    "Paciente eliminado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaPaciente();
            } else {
                JOptionPane.showMessageDialog(null, 
                    "No se pudo eliminar al paciente", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void actualizarPaciente() {
        try {
            int filaSeleccionada = tablaPacientes.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione un paciente de la tabla", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String documentoOriginal = tableModelPaciente.getValueAt(filaSeleccionada, 0).toString();
            String documento = txtDocumento.getText().trim();
            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String celular = txtCelular.getText().trim();
            String contraseña = txtContraseña.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String eps = cbEps.getSelectedItem().toString();
            String tipoDocumento = cbTipoDocumento.getSelectedItem().toString();
            String tipoSangre = cbTipoSangre.getSelectedItem().toString();
            String antecedentes = txtAreaAntecedentes.getText().trim();

            // Validar campos obligatorios
            if (documento.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || 
                email.isEmpty() || celular.isEmpty()  || 
                dateChooserNacimiento.getDate() == null) {
                JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convertir fecha
            LocalDate fechaNacimiento = dateChooserNacimiento.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Verificar si cambió el documento y si ya existe
            if (!documentoOriginal.equals(documento) && existePaciente(documento)) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe un paciente con este documento",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear paciente actualizado
            Paciente pacienteActualizado = new Paciente(
                documento,
                nombres,
                apellidos,
                fechaNacimiento,
                sexo,
                eps,
                email,
                celular,
                contraseña,
                tipoDocumento,
                tipoSangre,
                antecedentes
            );

            if (pacienteDAO.actualizarPaciente(documentoOriginal, pacienteActualizado)) {
                JOptionPane.showMessageDialog(null,
                    "Paciente actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaPaciente();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar el paciente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al actualizar paciente: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void cargarDatosPacienteEnFormulario() {
        int filaSeleccionada = tablaPacientes.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                txtDocumento.setText(tableModelPaciente.getValueAt(filaSeleccionada, 0).toString());
                txtNombre.setText(tableModelPaciente.getValueAt(filaSeleccionada, 1).toString());
                txtApellido.setText(tableModelPaciente.getValueAt(filaSeleccionada, 2).toString());
                
               
                Object fechaValue = tableModelPaciente.getValueAt(filaSeleccionada, 3);
                if (fechaValue instanceof LocalDate) {
                    LocalDate fechaNac = (LocalDate) fechaValue;
                    dateChooserNacimiento.setDate(Date.from(fechaNac.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                }
                
                cbSexo.setSelectedItem(tableModelPaciente.getValueAt(filaSeleccionada, 4).toString());
                cbEps.setSelectedItem(tableModelPaciente.getValueAt(filaSeleccionada, 5).toString());
                txtEmail.setText(tableModelPaciente.getValueAt(filaSeleccionada, 6).toString());
                txtCelular.setText(tableModelPaciente.getValueAt(filaSeleccionada, 7).toString());
                cbTipoDocumento.setSelectedItem(tableModelPaciente.getValueAt(filaSeleccionada, 8).toString());
                cbTipoSangre.setSelectedItem(tableModelPaciente.getValueAt(filaSeleccionada, 9).toString());
                txtAreaAntecedentes.setText(tableModelPaciente.getValueAt(filaSeleccionada, 10).toString());
                txtContraseña.setText(tableModelPaciente.getValueAt(filaSeleccionada, 11).toString());
                
                this.documentoOriginal = txtDocumento.getText();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Error al cargar datos del paciente: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}



