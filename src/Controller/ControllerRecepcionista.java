package Controller;

import com.toedter.calendar.JDateChooser;
import dao.RecepcionistaDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Recepcionista;

public class ControllerRecepcionista {
  
    private DefaultTableModel tableModelRecepcionista;
    private RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();
    private String documentoOriginal;
    
    // Componentes de la vista
    private JTable tablaRecepcionistas;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDocumento;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JTextField txtContraseña;
    private JTextField txtCodigoEmpleado;
    private JDateChooser dateChooserNacimiento;
    private JDateChooser dateChooserContratacion;
    private JComboBox<String> cbSexo;
    private JComboBox<String> cbHorario;
    private JComboBox<String> cbEps;

    // Setters para los componentes de la vista
    public void setTablaRecepcionistas(JTable tablaRecepcionistas) {
        this.tablaRecepcionistas = tablaRecepcionistas;
        this.tableModelRecepcionista = (DefaultTableModel) tablaRecepcionistas.getModel();
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
    
    public void setTxtCodigoEmpleado(JTextField txtCodigoEmpleado) {
        this.txtCodigoEmpleado = txtCodigoEmpleado;
    }
    
    public void setDateChooserNacimiento(JDateChooser dateChooserNacimiento) {
        this.dateChooserNacimiento = dateChooserNacimiento;
    }
    
    public void setDateChooserContratacion(JDateChooser dateChooserContratacion) {
        this.dateChooserContratacion = dateChooserContratacion;
    }
    
    public void setCbSexo(JComboBox<String> cbSexo) {
        this.cbSexo = cbSexo;
    }
    
    public void setCbHorario(JComboBox<String> cbHorario) {
        this.cbHorario = cbHorario;
    }
    
    public void setCbEps(JComboBox<String> cbEps) {
        this.cbEps = cbEps;
    }
    
    // Inicialización de la tabla
    public void initTableRecepcionista() {
        tableModelRecepcionista = new DefaultTableModel(
            new Object[]{"Documento", "Nombres", "Apellidos", "Fecha Nac.", "Sexo", 
                        "EPS", "Email", "Teléfono", "Código Empleado", "Fecha Contratación", "Horario"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaRecepcionistas.setModel(tableModelRecepcionista);
    }
    
    // Cargar datos en la tabla
    public void cargarDatosEnTablaRecepcionista() {
        try {
            tableModelRecepcionista.setRowCount(0);
            List<Recepcionista> recepcionistas = recepcionistaDAO.cargarTodos();
            
            if (recepcionistas == null || recepcionistas.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron recepcionistas registrados", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            for (Recepcionista recepcionista : recepcionistas) {
                Object[] row = {
                    recepcionista.getNumeroDocumento(),
                    recepcionista.getNombres(),
                    recepcionista.getApellidos(),
                    recepcionista.getFechaNacimiento(),
                    recepcionista.getSexo(),
                    recepcionista.getEps(),
                    recepcionista.getEmail(),
                    recepcionista.getCelular(),
                    recepcionista.getCodigoEmpleado(),
                    recepcionista.getFechaContratacion(),
                    recepcionista.getHorario()
                };
                tableModelRecepcionista.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al cargar recepcionistas: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Guardar recepcionista desde formulario
    public void guardarRecepcionistaDesdeFormulario() {
        try {
            // Obtener datos del formulario
            String documento = txtDocumento.getText().trim();
            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String celular = txtCelular.getText().trim();
            String contraseña = txtContraseña.getText().trim();
            String codigoEmpleado = txtCodigoEmpleado.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String horario = cbHorario.getSelectedItem().toString();
            String eps = cbEps.getSelectedItem().toString();
            
            // Validar campos obligatorios
            if (documento.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || 
                email.isEmpty() || celular.isEmpty() || contraseña.isEmpty() || 
                codigoEmpleado.isEmpty() || dateChooserNacimiento.getDate() == null || 
                dateChooserContratacion.getDate() == null) {
                JOptionPane.showMessageDialog(null, 
                    "Todos los campos son obligatorios", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Convertir fechas
            LocalDate fechaNacimiento = dateChooserNacimiento.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaContratacion = dateChooserContratacion.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            // Verificar si ya existe
            if (existeRecepcionista(documento, codigoEmpleado)) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe un recepcionista con este documento o código de empleado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear nuevo recepcionista
            Recepcionista nuevoRecepcionista = new Recepcionista(
                documento,
                nombres,
                apellidos,
                fechaNacimiento,
                sexo,
                eps,
                email,
                celular,
                contraseña,
                codigoEmpleado,
                fechaContratacion,
                horario
            );
            
            // Guardar en la base de datos
            if (recepcionistaDAO.guardarRecepcionista(nuevoRecepcionista)) {
                JOptionPane.showMessageDialog(null, 
                    "Recepcionista guardado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaRecepcionista();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo guardar el recepcionista",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al guardar recepcionista: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Verificar si existe un recepcionista
    private boolean existeRecepcionista(String documento, String codigoEmpleado) {
        List<Recepcionista> recepcionistas = recepcionistaDAO.cargarTodos();
        if (recepcionistas != null) {
            return recepcionistas.stream()
                .anyMatch(r -> (r.getNumeroDocumento() != null && r.getNumeroDocumento().equals(documento)) ||
                              (r.getCodigoEmpleado() != null && r.getCodigoEmpleado().equals(codigoEmpleado)));
        }
        return false;
    }
    
    // Limpiar formulario
    public void limpiarFormulario() {
        txtDocumento.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        txtContraseña.setText("");
        txtCodigoEmpleado.setText("");
        dateChooserNacimiento.setDate(null);
        dateChooserContratacion.setDate(null);
        cbSexo.setSelectedIndex(0);
        cbHorario.setSelectedIndex(0);
        cbEps.setSelectedIndex(0);
    }
    
    // Eliminar recepcionista seleccionado
    public void eliminarRecepcionistaSeleccionado() {
        int filaSeleccionada = tablaRecepcionistas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, 
                "Seleccione un recepcionista de la tabla", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String documento = tableModelRecepcionista.getValueAt(filaSeleccionada, 0).toString();
        
        int confirmacion = JOptionPane.showConfirmDialog(
            null, 
            "¿Eliminar al recepcionista con documento " + documento + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (recepcionistaDAO.eliminarRecepcionista(documento)) {
                JOptionPane.showMessageDialog(null, 
                    "Recepcionista eliminado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaRecepcionista();
            } else {
                JOptionPane.showMessageDialog(null, 
                    "No se pudo eliminar al recepcionista", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Actualizar recepcionista
    public void actualizarRecepcionista() {
        try {
            int filaSeleccionada = tablaRecepcionistas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione un recepcionista de la tabla", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String documentoOriginal = tableModelRecepcionista.getValueAt(filaSeleccionada, 0).toString();
            String documento = txtDocumento.getText().trim();
            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String celular = txtCelular.getText().trim();
            String contraseña = txtContraseña.getText().trim();
            String codigoEmpleado = txtCodigoEmpleado.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String horario = cbHorario.getSelectedItem().toString();
            String eps = cbEps.getSelectedItem().toString();

            // Validar campos obligatorios
            if (documento.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || 
                email.isEmpty() || celular.isEmpty() || contraseña.isEmpty() || 
                codigoEmpleado.isEmpty() || dateChooserNacimiento.getDate() == null || 
                dateChooserContratacion.getDate() == null) {
                JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convertir fechas
            LocalDate fechaNacimiento = dateChooserNacimiento.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaContratacion = dateChooserContratacion.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Verificar si cambió el documento o código de empleado
            if (!documentoOriginal.equals(documento)) {
                if (existeRecepcionista(documento, codigoEmpleado)) {
                    JOptionPane.showMessageDialog(null,
                        "Ya existe un recepcionista con este documento o código de empleado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Crear recepcionista actualizado
            Recepcionista recepcionistaActualizado = new Recepcionista(
                documento,
                nombres,
                apellidos,
                fechaNacimiento,
                sexo,
                eps,
                email,
                celular,
                contraseña,
                codigoEmpleado,
                fechaContratacion,
                horario
            );

            // Actualizar en la base de datos
            if (recepcionistaDAO.actualizarRecepcionista(documentoOriginal, recepcionistaActualizado)) {
                JOptionPane.showMessageDialog(null,
                    "Recepcionista actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaRecepcionista();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar el recepcionista",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al actualizar recepcionista: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Cargar datos del recepcionista seleccionado en el formulario
    public void cargarDatosRecepcionistaEnFormulario() {
        int filaSeleccionada = tablaRecepcionistas.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                txtDocumento.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 0).toString());
                txtNombre.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 1).toString());
                txtApellido.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 2).toString());
                
                // Convertir y establecer fechas
                Object fechaNacValue = tableModelRecepcionista.getValueAt(filaSeleccionada, 3);
                if (fechaNacValue instanceof LocalDate) {
                    LocalDate fechaNac = (LocalDate) fechaNacValue;
                    dateChooserNacimiento.setDate(Date.from(fechaNac.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                }
                
                cbSexo.setSelectedItem(tableModelRecepcionista.getValueAt(filaSeleccionada, 4).toString());
                cbEps.setSelectedItem(tableModelRecepcionista.getValueAt(filaSeleccionada, 5).toString());
                txtEmail.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 6).toString());
                txtCelular.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 7).toString());
                txtCodigoEmpleado.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 8).toString());
                
                Object fechaContValue = tableModelRecepcionista.getValueAt(filaSeleccionada, 9);
                if (fechaContValue instanceof LocalDate) {
                    LocalDate fechaCont = (LocalDate) fechaContValue;
                    dateChooserContratacion.setDate(Date.from(fechaCont.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                }
                
                cbHorario.setSelectedItem(tableModelRecepcionista.getValueAt(filaSeleccionada, 10).toString());
                
                this.documentoOriginal = txtDocumento.getText();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Error al cargar datos del recepcionista: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}