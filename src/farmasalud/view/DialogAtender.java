/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package farmasalud.view;

import Controller.ControllerCargarMedicosCitas;
import Controller.ControllerPaciente;
import DAOImpl.OrdenMedicaDAOImpl;
import Listener.PacienteListener;
import dao.OrdenMedicaDAO;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.Cita;
import model.Medico;
import model.Paciente;

/**
 *
 * @author usuario
 */
public class DialogAtender extends javax.swing.JDialog implements PacienteListener {
private Cita citaSeleccionada;
private Paciente paciente;
private Cita cita;
private DefaultTableModel tableModel;
private int filaSeleccionada;
private int columnaEstado = 7; 
private String documentoPaciente;

OrdenMedicaDAO OrdenMEdica = new OrdenMedicaDAOImpl();
    /**
     * Creates new form DialogAtender
     */
    public DialogAtender(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ControllerPaciente.getInstance().agregarPacienteListener(this);
    }

   public void actualizar(Paciente paciente) {
    if (paciente != null && this.documentoPaciente != null 
        && paciente.getNumeroDocumento().equals(documentoPaciente)) {

        setPesoYAltura(paciente.getPeso(), paciente.getAltura());
        JOptionPane.showMessageDialog(this, "Peso y altura actualizados del paciente.");
    }
}
public void setDocumentoPaciente(String documentoPaciente) {
    this.documentoPaciente = documentoPaciente;
}


    public void setFechaYHora(String fecha, String hora,String idCita,String estado,String motivo,String sede,String documento) {
    
    lblFechaCita.setText(fecha);  
    lblHoraCita.setText(hora);  
    lblEstado.setText(estado);
    lblMotivo.setText(motivo);
    lblSede.setText(sede);
    
}
    
    public void setCita(Cita cita) {
    this.cita = cita;
   
}
    
    public void setCitaSeleccionada(Cita cita) {
    this.citaSeleccionada = cita;
}

private boolean esFechaValida(String fecha, String formato) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false); // Para validar estrictamente la fecha
        sdf.parse(fecha);
        return true;
    } catch (Exception e) {
        return false;
    }
}
  public void setMedicamento(String medicamento) {
    if (txtMedicamento.getText().trim().isEmpty()) {
        txtMedicamento.setText(medicamento);
    } else {
        txtMedicamento.append("\n" + medicamento);
    }
}

private Medico medicoSeleccionado;

public void setMedicoSeleccionado(Medico medico) {
    this.medicoSeleccionado = medico;
}
public void setTableModel(DefaultTableModel tableModel) {
    this.tableModel = tableModel;
}

public void setFilaSeleccionada(int fila) {
    this.filaSeleccionada = fila;
}



  private void buscarPaciente() {
        String documento = txtDocumento.getText().trim();

        if (documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el documento del paciente.");
            return;
        }

        ControllerCargarMedicosCitas controller = new ControllerCargarMedicosCitas();
        model.Paciente paciente = controller.buscarPacientePorDocumento(documento);

        if (paciente != null) {
            lblNombre.setText(paciente.getNombres() != null ? paciente.getNombres() : "");
            lblApellido.setText(paciente.getApellidos() != null ? paciente.getApellidos() : "");

            Object fecha = paciente.getFechaNacimiento();
            String fechaFormateada = "";

            if (fecha == null) {
                System.out.println("Fecha de nacimiento es null");
                fechaFormateada = "No disponible";
            } else if (fecha instanceof Date) {
                fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format((Date) fecha);
            } else if (fecha instanceof String fechaStr) {
                System.out.println("Fecha como String: " + fechaStr);
                try {
                    // Intentar yyyy-MM-dd
                    Date parsed = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
                    fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(parsed);
                } catch (Exception e1) {
                    try {
                        // Intentar dd/MM/yyyy
                        Date parsed = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                        fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(parsed);
                    } catch (Exception e2) {
                        System.out.println("Formato de fecha no reconocido: " + fechaStr);
                        fechaFormateada = "Formato inválido";
                    }
                }

            } else if (fecha instanceof LocalDate localDate) {
                fechaFormateada = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                System.out.println("Tipo inesperado de fecha: " + fecha.getClass());
                fechaFormateada = "No disponible";
            }

            lblFechaNacimiento.setText(fechaFormateada);

            lblSexo.setText(paciente.getSexo() != null ? paciente.getSexo() : "");
            lblEps.setText(paciente.getEps() != null ? paciente.getEps() : "");
            lblEmail.setText(paciente.getEmail() != null ? paciente.getEmail() : "");
            lblCelular.setText(paciente.getCelular() != null ? paciente.getCelular() : "");
            lblTipoSangre.setText(paciente.getTipoSangre() != null ? paciente.getTipoSangre() : "");

            txtAntecedentes.setText(paciente.getAntecedentes() != null ? paciente.getAntecedentes() : "");
            txtAntecedentes.setEditable(false);

            txtPeso.setText(String.valueOf(paciente.getPeso()));
            lblAltura.setText(String.valueOf(paciente.getAltura()));

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el paciente con el documento ingresado.",
                    "Paciente no encontrado",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposPaciente();
        }
    }
  public void setPesoYAltura(double peso, double altura) {
    txtPeso.setText(String.valueOf(peso));
    lblAltura.setText(String.valueOf(altura));
}


    private void limpiarCamposPaciente() {
        lblNombre.setText("");
        lblApellido.setText("");
        lblFechaNacimiento.setText("");
        lblSexo.setText("");
        lblEps.setText("");
        lblEmail.setText("");
        lblCelular.setText("");
        lblAltura.setText("");
        txtPeso.setText("");
        txtAntecedentes.setText("");
        lblFechaCita.setText("");
        lblHoraCita.setText("");
    }
    public void setDiagnostico(String diagnostico) {
    areaDiagnostico.setText(diagnostico);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        lblAltura = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        lblSede = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        lblTipoSangre = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        lblFechaCita = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAntecedentes = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        lblEps = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        lblHoraCita = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        lblMotivo = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        txtPeso = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaDiagnostico = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMedicamento = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreceta = new javax.swing.JTextArea();
        btnSeleccionarEnfermedad = new javax.swing.JButton();
        btnSeleccionarMedicamento = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(940, 670));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(28, 43, 110));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Documento ");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 9, 119, -1));

        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });
        jPanel3.add(txtDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 6, 167, 37));

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 13, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 1000, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Nombre");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, 30));
        jPanel4.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 150, 40));

        jSeparator1.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator1.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 150, 10));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Apellido");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));
        jPanel4.add(lblApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 130, 40));

        jSeparator2.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 150, 10));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Email");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 70, 30));
        jPanel4.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 150, 40));

        jSeparator3.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator3.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 150, 10));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Altura");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, 20));
        jPanel4.add(lblAltura, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 150, 40));

        jSeparator4.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 150, 10));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Peso");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        jSeparator5.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 243, 150, 10));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Sede");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));
        jPanel4.add(lblSede, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 150, 40));

        jSeparator6.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 150, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Fecha Nacimiento");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));
        jPanel4.add(lblFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 160, 40));

        jSeparator7.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 160, 10));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Grupo Sangre ");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 130, 40));
        jPanel4.add(lblTipoSangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 160, 40));

        jSeparator8.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 160, 10));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Fecha Cita");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 110, 30));
        jPanel4.add(lblFechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 160, 40));

        jSeparator9.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 160, 160, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Antecedentes ");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, -1, -1));

        txtAntecedentes.setColumns(20);
        txtAntecedentes.setRows(5);
        jScrollPane1.setViewportView(txtAntecedentes);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 186, 180, 80));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Telefono");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 40, -1, -1));
        jPanel4.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 20, 150, 40));

        jSeparator10.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, 150, 10));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Sexo ");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, 70, 40));
        jPanel4.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 70, 150, 40));

        jSeparator11.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 150, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("EPS");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, -1, -1));
        jPanel4.add(lblEps, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 120, 150, 40));

        jSeparator12.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 160, 150, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Hora Cita");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, -1, -1));
        jPanel4.add(lblHoraCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, 150, 40));

        jSeparator13.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 210, 150, 10));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Estado");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 230, -1, -1));
        jPanel4.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 220, 150, 40));

        jSeparator14.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 260, 150, 10));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Motivo Cita");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 270, -1, -1));
        jPanel4.add(lblMotivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 280, 140, 30));

        jSeparator15.setForeground(new java.awt.Color(28, 43, 110));
        jPanel4.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 310, 150, 10));

        txtPeso.setText("jTextField2");
        jPanel4.add(txtPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 140, 40));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 1000, 340));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Diagnostico");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, 119, -1));

        areaDiagnostico.setColumns(20);
        areaDiagnostico.setRows(5);
        jScrollPane2.setViewportView(areaDiagnostico);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 570, 290, 130));

        txtMedicamento.setColumns(20);
        txtMedicamento.setRows(5);
        jScrollPane3.setViewportView(txtMedicamento);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 550, -1, 70));

        txtAreceta.setColumns(20);
        txtAreceta.setRows(5);
        jScrollPane4.setViewportView(txtAreceta);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 636, 210, 80));

        btnSeleccionarEnfermedad.setText("Seleccionar Enfermedad");
        btnSeleccionarEnfermedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarEnfermedadActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionarEnfermedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, 170, 60));

        btnSeleccionarMedicamento.setText("Seleccionar Medicamento");
        btnSeleccionarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarMedicamentoActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionarMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 630, 170, 60));

        btnGuardar.setText("Guardar");
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 700, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed

        String documento = txtDocumento.getText().trim();
    
    // Validar que no esté vacío
    if (documento.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "El documento no puede estar vacío.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.requestFocus();
        return;
    }
    
    // Validar que solo contenga números
    if (!documento.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, 
            "Solo se permiten números en el documento.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.setText("");
        txtDocumento.requestFocus();
        return;
    }
    
    // Validar longitud (7 a 10 dígitos)
    if (documento.length() < 7 || documento.length() > 10) {
        JOptionPane.showMessageDialog(this, 
            "El documento debe tener entre 7 y 10 dígitos.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.requestFocus();
        return;
    }
    }//GEN-LAST:event_txtDocumentoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   String documento = txtDocumento.getText().trim();
    
    if (documento.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Ingrese un número de documento.", 
            "Campo vacío", 
            JOptionPane.WARNING_MESSAGE);
        txtDocumento.requestFocus(); // Enfocar el campo
        return;
    }

    // 2. Validar que solo contenga números (si es necesario)
    if (!documento.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, 
            "El documento solo puede contener números.", 
            "Error de formato", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.setText(""); // Limpiar el campo
        txtDocumento.requestFocus();
        return;
    }

    // 3. Validar longitud (ejemplo: DNI debe tener 8 dígitos)
    if (documento.length() < 7 || documento.length() > 10) {
        JOptionPane.showMessageDialog(this, 
            "El documento debe tener entre 7 y 10 dígitos.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.requestFocus();
        return;
    }

    // 4. Ejecutar la búsqueda del paciente
    try {
        buscarPaciente(); // Llama a tu método de búsqueda
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error al buscar paciente: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }       

                                          

   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSeleccionarEnfermedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarEnfermedadActionPerformed
   java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
    Enfermedades dialog = new Enfermedades((java.awt.Frame) parentWindow, true);
    dialog.setDialogAtender(this); 
    dialog.setLocationRelativeTo(parentWindow);
    dialog.inicializarListener(); 
    dialog.setVisible(true);
    }//GEN-LAST:event_btnSeleccionarEnfermedadActionPerformed

    private void btnSeleccionarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarMedicamentoActionPerformed
   java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
    DialogMedicamentos dialog = new DialogMedicamentos((java.awt.Frame) parentWindow, true);
    dialog.setDialogMedicamento(this); 
    dialog.setLocationRelativeTo(parentWindow);
    dialog.inicializarListener(); 
    dialog.setVisible(true);
    }//GEN-LAST:event_btnSeleccionarMedicamentoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogAtender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogAtender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogAtender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogAtender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogAtender dialog = new DialogAtender(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaDiagnostico;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSeleccionarEnfermedad;
    private javax.swing.JButton btnSeleccionarMedicamento;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblAltura;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEps;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaCita;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblHoraCita;
    private javax.swing.JLabel lblMotivo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblSede;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTipoSangre;
    private javax.swing.JTextArea txtAntecedentes;
    private javax.swing.JTextArea txtAreceta;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextArea txtMedicamento;
    private javax.swing.JTextField txtPeso;
    // End of variables declaration//GEN-END:variables
}
