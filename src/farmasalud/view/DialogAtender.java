/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package farmasalud.view;

import Controller.ControllerCargarMedicosCitas;
import jakarta.mail.internet.ParseException;

import javax.swing.SwingUtilities;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;


/**
 *
 * @author Maria liz
 */
public class DialogAtender extends javax.swing.JDialog {

    /**
     * Creates new form DialogAtender
     */
    public DialogAtender(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
   

    }
    public void setFechaYHora(String fecha, String hora) {
    
    lblFechaCita.setText(fecha);  
    lblHoraCita.setText(hora);    
}



// Dentro de tu clase

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

            // ---------- FECHA DE NACIMIENTO ----------
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

            // ---------- OTROS CAMPOS ----------
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
    }
    public void setDiagnostico(String diagnostico) {
    areaDiagnostico.setText(diagnostico);
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaDiagnostico = new javax.swing.JTextArea();
        btnTratamiento = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblApellido = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        lblTipoSangre = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        lblEps = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        lblFechaCita = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblHoraCita = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAntecedentes = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        lblAltura = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnSeleccionarEnfermedad = new javax.swing.JButton();
        lblDiagnostico = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(28, 43, 110));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 930, 110));

        areaDiagnostico.setColumns(20);
        areaDiagnostico.setRows(5);
        jScrollPane1.setViewportView(areaDiagnostico);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 360, 140));

        btnTratamiento.setText("Tratamiento");
        btnTratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTratamientoActionPerformed(evt);
            }
        });
        jPanel1.add(btnTratamiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 583, -1, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Nombre ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 80, -1));
        jPanel2.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 120, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Apellido");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator1.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 42, 120, 10));
        jPanel2.add(lblApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 110, 40));

        jSeparator2.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator2.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 120, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Email ");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));
        jPanel2.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 110, 40));

        jSeparator3.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 120, 10));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Altura ");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jSeparator4.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator4.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 120, 10));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Fecha Nacimiento ");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));
        jPanel2.add(lblFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 140, 30));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, -1));

        jSeparator6.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator6.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 140, 10));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Grupo sangre ");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 140, -1));
        jPanel2.add(lblTipoSangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 140, 30));

        jSeparator7.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator7.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 140, 10));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Eps");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Peso ");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        txtPeso.setBorder(null);
        txtPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesoActionPerformed(evt);
            }
        });
        jPanel2.add(txtPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, 30));

        jSeparator8.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator8.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 130, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Sexo");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 60, -1, -1));
        jPanel2.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 130, 40));

        jSeparator9.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator9.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 130, 10));
        jPanel2.add(lblEps, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 130, 40));

        jSeparator10.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator10.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, 130, 10));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("Antendentes");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, -1, -1));

        jSeparator11.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator11.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 140, 10));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("Fecha Cita ");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 90, 30));
        jPanel2.add(lblFechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 140, 40));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("HoraCita ");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 150, -1, -1));
        jPanel2.add(lblHoraCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 140, 110, 30));

        jSeparator12.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator12.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 170, 110, 10));

        txtAntecedentes.setEditable(false);
        txtAntecedentes.setColumns(20);
        txtAntecedentes.setRows(5);
        jScrollPane2.setViewportView(txtAntecedentes);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 140, 170, 70));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Telefono");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, -1, -1));
        jPanel2.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 0, 120, 40));

        jSeparator13.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator13.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 120, 10));

        lblAltura.setBorder(null);
        jPanel2.add(lblAltura, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 120, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 860, 230));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Documento ");

        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addGap(54, 54, 54)
                .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(btnBuscar)
                .addContainerGap(378, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnBuscar))
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 860, 40));

        btnSeleccionarEnfermedad.setText("Seleccionar Enfermedad ");
        btnSeleccionarEnfermedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarEnfermedadActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionarEnfermedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 550, -1, 40));

        lblDiagnostico.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDiagnostico.setText("Diagnostico");
        jPanel1.add(lblDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarEnfermedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarEnfermedadActionPerformed
java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
    Enfermedades dialog = new Enfermedades((java.awt.Frame) parentWindow, true);
    dialog.setDialogAtender(this); 
    dialog.setLocationRelativeTo(parentWindow);
    dialog.inicializarListener(); 
    dialog.setVisible(true);
   
    }//GEN-LAST:event_btnSeleccionarEnfermedadActionPerformed

    private void btnTratamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTratamientoActionPerformed
   java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
    DialogTratamiento dialog = new DialogTratamiento((java.awt.Frame) parentWindow, true);
    dialog.setLocationRelativeTo(parentWindow); // Centrar el diálogo
    dialog.setVisible(true);   // TODO add your handling code here:
    }//GEN-LAST:event_btnTratamientoActionPerformed

    private void txtPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
buscarPaciente();        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentoActionPerformed

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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnSeleccionarEnfermedad;
    private javax.swing.JButton btnTratamiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField lblAltura;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblDiagnostico;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEps;
    private javax.swing.JLabel lblFechaCita;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblHoraCita;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTipoSangre;
    private javax.swing.JTextArea txtAntecedentes;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtPeso;
    // End of variables declaration//GEN-END:variables
}
