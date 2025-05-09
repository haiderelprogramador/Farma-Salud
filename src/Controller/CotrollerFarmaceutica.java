package Controller;

import dao.FarmaceuticaDAO;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Farmaceutica;

public class CotrollerFarmaceutica {
  
    private DefaultTableModel tableModelFarmaceutica;
    private FarmaceuticaDAO farmaceuticaDAO = new FarmaceuticaDAO();
    private String documentoOriginal;
    
    // Componentes de la interfaz
    private JTable tablaFarmaceuticas;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDocumento;
    private JTextField txtEmail;
    private JTextField txtFechaNacimiento;
    private JTextField txtTelefono;
    private JTextField txtCodigoEmpleado;
    private JTextField txtFechaContratacion;
    private JComboBox<String> cbSexo;
    private JComboBox<String> cbTurno;
    private JTextField txtContraseña;
    
    // Setters para los componentes
    public void setTablaFarmaceuticas(JTable tablaFarmaceuticas) {
        this.tablaFarmaceuticas = tablaFarmaceuticas;
        this.tableModelFarmaceutica = (DefaultTableModel) tablaFarmaceuticas.getModel();
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
    
    public void setTxtFechaNacimiento(JTextField txtFechaNacimiento) {
        this.txtFechaNacimiento = txtFechaNacimiento;
    }
    
    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }
    
    public void setTxtCodigoEmpleado(JTextField txtCodigoEmpleado) {
        this.txtCodigoEmpleado = txtCodigoEmpleado;
    }
    
    public void setTxtFechaContratacion(JTextField txtFechaContratacion) {
        this.txtFechaContratacion = txtFechaContratacion;
    }
    
    public void setCbSexo(JComboBox<String> cbSexo) {
        this.cbSexo = cbSexo;
    }
    
    public void setCbTurno(JComboBox<String> cbTurno) {
        this.cbTurno = cbTurno;
    }
    
    public void setTxtContraseña(JTextField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }
    
    // Inicializar tabla
    public void initTableFarmaceutica() {
        tableModelFarmaceutica = new DefaultTableModel(
            new Object[]{"Documento", "Nombres", "Apellidos", "Fecha Nacimiento", "Sexo", 
                         "Email", "Teléfono", "Código Empleado", "Fecha Contratación", "Turno"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaFarmaceuticas.setModel(tableModelFarmaceutica);
    }
    
    // Cargar datos en la tabla
    public void cargarDatosEnTablaFarmaceutica() {
        try {
            tableModelFarmaceutica.setRowCount(0);
            List<Farmaceutica> farmaceuticas = farmaceuticaDAO.cargarTodos();
            
            if (farmaceuticas == null || farmaceuticas.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron farmacéuticos registrados", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            for (Farmaceutica farmaceutica : farmaceuticas) {
                Object[] row = {
                    farmaceutica.getNumeroDocumento(),
                    farmaceutica.getNombres(),
                    farmaceutica.getApellidos(),
                    farmaceutica.getFechaNacimiento(),
                    farmaceutica.getSexo(),
                    farmaceutica.getEmail(),
                    farmaceutica.getCelular(),
                    farmaceutica.getCodigoEmpleado(),
                    farmaceutica.getFechaContratacion(),
                    farmaceutica.getTurno()
                };
                tableModelFarmaceutica.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al cargar farmacéuticos: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Guardar farmacéutico desde formulario
    public void guardarFarmaceuticaDesdeFormulario() {
        try {
            // Obtener datos del formulario
            String documento = txtDocumento.getText().trim();
            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String fechaNacStr = txtFechaNacimiento.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String codigoEmpleado = txtCodigoEmpleado.getText().trim();
            String fechaContratacionStr = txtFechaContratacion.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String turno = cbTurno.getSelectedItem().toString();
            String contraseña = txtContraseña.getText().trim();
            
            // Validar campos obligatorios
            if (documento.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || 
                email.isEmpty() || telefono.isEmpty() || contraseña.isEmpty() || 
                codigoEmpleado.isEmpty() || fechaNacStr.isEmpty() || 
                fechaContratacionStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Todos los campos son obligatorios", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Convertir fechas
            LocalDate fechaNacimiento;
            LocalDate fechaContratacion;
            try {
                fechaNacimiento = LocalDate.parse(fechaNacStr);
                fechaContratacion = LocalDate.parse(fechaContratacionStr);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null,
                    "Formato de fecha inválido. Usa YYYY-MM-DD",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si ya existe
            if (existeFarmaceutica(documento, codigoEmpleado)) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe un farmacéutico con este documento o código de empleado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear nuevo farmacéutico
            Farmaceutica nuevoFarmaceutica = new Farmaceutica(
                documento,
                nombres,
                apellidos,
                fechaNacimiento,
                sexo,
                "", // EPS (vacío si no se usa)
                email,
                telefono,
                contraseña,
                codigoEmpleado,
                fechaContratacion,
                turno
            );
            
            // Guardar en la base de datos
            if (farmaceuticaDAO.guardarFarmaceutica(nuevoFarmaceutica)) {
                JOptionPane.showMessageDialog(null, 
                    "Farmacéutico guardado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaFarmaceutica();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo guardar el farmacéutico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al guardar farmacéutico: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Verificar si existe un farmacéutico
    private boolean existeFarmaceutica(String documento, String codigoEmpleado) {
        List<Farmaceutica> farmaceuticas = farmaceuticaDAO.cargarTodos();
        if (farmaceuticas != null) {
            return farmaceuticas.stream()
                .anyMatch(f -> (f.getNumeroDocumento() != null && f.getNumeroDocumento().equals(documento)) ||
                              (f.getCodigoEmpleado() != null && f.getCodigoEmpleado().equals(codigoEmpleado)));
        }
        return false;
    }
    
    // Limpiar formulario
    public void limpiarFormulario() {
        txtDocumento.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtFechaNacimiento.setText("");
        txtTelefono.setText("");
        txtContraseña.setText("");
        txtCodigoEmpleado.setText("");
        txtFechaContratacion.setText("");
        cbSexo.setSelectedIndex(0);
        cbTurno.setSelectedIndex(0);
    }
    
    // Eliminar farmacéutico seleccionado
    public void eliminarFarmaceuticaSeleccionado() {
        int filaSeleccionada = tablaFarmaceuticas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, 
                "Seleccione un farmacéutico de la tabla", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String documento = tableModelFarmaceutica.getValueAt(filaSeleccionada, 0).toString();
        
        int confirmacion = JOptionPane.showConfirmDialog(
            null, 
            "¿Eliminar al farmacéutico con documento " + documento + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (farmaceuticaDAO.eliminarFarmaceutica(documento)) {
                JOptionPane.showMessageDialog(null, 
                    "Farmacéutico eliminado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaFarmaceutica();
            } else {
                JOptionPane.showMessageDialog(null, 
                    "No se pudo eliminar al farmacéutico", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Actualizar farmacéutico
    public void actualizarFarmaceutica() {
        try {
            int filaSeleccionada = tablaFarmaceuticas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione un farmacéutico de la tabla", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String documentoOriginal = tableModelFarmaceutica.getValueAt(filaSeleccionada, 0).toString();
            String documento = txtDocumento.getText().trim();
            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String fechaNacStr = txtFechaNacimiento.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String codigoEmpleado = txtCodigoEmpleado.getText().trim();
            String fechaContratacionStr = txtFechaContratacion.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String turno = cbTurno.getSelectedItem().toString();
            String contraseña = txtContraseña.getText().trim();

            // Validar campos obligatorios
            if (documento.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || 
                email.isEmpty() || telefono.isEmpty() || contraseña.isEmpty() || 
                codigoEmpleado.isEmpty() || fechaNacStr.isEmpty() || 
                fechaContratacionStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convertir fechas
            LocalDate fechaNacimiento;
            LocalDate fechaContratacion;
            try {
                fechaNacimiento = LocalDate.parse(fechaNacStr);
                fechaContratacion = LocalDate.parse(fechaContratacionStr);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null,
                    "Formato de fecha inválido. Usa YYYY-MM-DD",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si cambió el documento o código de empleado
            if (!documentoOriginal.equals(documento)) {
                if (existeFarmaceutica(documento, codigoEmpleado)) {
                    JOptionPane.showMessageDialog(null,
                        "Ya existe un farmacéutico con este documento o código de empleado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Crear farmacéutico actualizado
            Farmaceutica farmaceuticaActualizado = new Farmaceutica(
                documento,
                nombres,
                apellidos,
                fechaNacimiento,
                sexo,
                "", // EPS (vacío si no se usa)
                email,
                telefono,
                contraseña,
                codigoEmpleado,
                fechaContratacion,
                turno
            );

            // Actualizar en la base de datos
            if (farmaceuticaDAO.actualizarFarmaceutica(documentoOriginal, farmaceuticaActualizado)) {
                JOptionPane.showMessageDialog(null,
                    "Farmacéutico actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaFarmaceutica();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar el farmacéutico",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al actualizar farmacéutico: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Cargar datos del farmacéutico seleccionado en el formulario
    public void cargarDatosFarmaceuticaEnFormulario() {
        int filaSeleccionada = tablaFarmaceuticas.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                txtDocumento.setText(tableModelFarmaceutica.getValueAt(filaSeleccionada, 0).toString());
                txtNombre.setText(tableModelFarmaceutica.getValueAt(filaSeleccionada, 1).toString());
                txtApellido.setText(tableModelFarmaceutica.getValueAt(filaSeleccionada, 2).toString());
                txtFechaNacimiento.setText(tableModelFarmaceutica.getValueAt(filaSeleccionada, 3).toString());
                cbSexo.setSelectedItem(tableModelFarmaceutica.getValueAt(filaSeleccionada, 4).toString());
                txtEmail.setText(tableModelFarmaceutica.getValueAt(filaSeleccionada, 5).toString());
                txtTelefono.setText(tableModelFarmaceutica.getValueAt(filaSeleccionada, 6).toString());
                txtCodigoEmpleado.setText(tableModelFarmaceutica.getValueAt(filaSeleccionada, 7).toString());
                txtFechaContratacion.setText(tableModelFarmaceutica.getValueAt(filaSeleccionada, 8).toString());
                cbTurno.setSelectedItem(tableModelFarmaceutica.getValueAt(filaSeleccionada, 9).toString());
                
                this.documentoOriginal = txtDocumento.getText();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Error al cargar datos del farmacéutico: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    
    
    
}


