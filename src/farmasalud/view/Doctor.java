/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package farmasalud.view;

import Controller.ControllerEnfermedades;
import Controller.ControllerMedicamento;
import Controller.ControllerOrdenMedica;
import Controller.ControllerPaciente;
import dao.PacienteDAO;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.JTextField;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Medicamento;
import model.OrdenMedica;

/**
 *
 * @author HP
 */
public class Doctor extends javax.swing.JFrame {
    
    private ControllerEnfermedades controllerEnfermedades = new ControllerEnfermedades();
    private ControllerMedicamento controllerMedicamento = new ControllerMedicamento(this);
    private ControllerOrdenMedica controllerOrdenMedica = new ControllerOrdenMedica();
    private Medicamento medicamentoSeleccionado; // Asegúrate de que esté declarado al inicio de tu clase
   private ControllerPaciente controllerPaciente = new ControllerPaciente();

    
    
    public Doctor() {
      
    initComponents();
        configurarControllerEnfermedades();
        configurarControllerMedicamento();
        configurarOrdenMedica();
    }
    
    private void controllerPacientes(){
    controllerPaciente.setTablaPacientes(jTable3);
    controllerPaciente.setTxtNombre(jTextField1);
    controllerPaciente.setTxtApellido(jTextField1);
    controllerPaciente.setTxtDocumento(jTextField1);
    controllerPaciente.setTxtEmail(jTextField1);
   // controllerPaciente.setDateChooserNacimiento(dateChooserNacimiento);
    controllerPaciente.setTxtCelular(jTextField1);
    //controllerPaciente.setCbSexo(cbSexo);
    //controllerPaciente.setCbTipoDocumento(cbTipoDocumento);
    //controllerPaciente.setCbTipoSangre(cbTipoSangre);
    controllerPaciente.setTxtAreaAntecedentes(jTextArea1);
    controllerPaciente.setTxtContraseña(jTextField1);
    
    controllerPaciente.initTablePaciente();
    controllerPaciente.cargarDatosEnTablaPaciente();
    
    
    }
    
    private void configurarOrdenMedica(){
        controllerOrdenMedica.setjTextField2(jTextField2);
  controllerOrdenMedica.setjTextField1(jTextField1);
    controllerOrdenMedica.setjTextField5(jTextField5);
    controllerOrdenMedica.setjTextField4(jTextField4);
    controllerOrdenMedica.setjTextField3(jTextField3);
    controllerOrdenMedica.setjTextArea1(jTextArea1);
    }
    
    private void configurarControllerMedicamento() {
     controllerMedicamento.setTabladeMedicamentos(jTable2);
     
     controllerMedicamento.setTxtCodMedicamento(jTextField1);
     controllerMedicamento.setTxtMedicamento(jTextField1);
     controllerMedicamento.setTxtDescripcion(jTextField1);
     controllerMedicamento.setTxtLaboratorio(jTextField1);
     controllerMedicamento.setTxtCantidad(jTextField1);
     controllerMedicamento.setTxtLote(jTextField1);
     controllerMedicamento.setTxtFechaVencimiento(jTextField1);
     //controllerMedicamento.setCbDisponible(cbDisponible);
     controllerMedicamento.setTxtPrecio(jTextField1);
     
          controllerMedicamento.setupTableModelMedicamentos();

        try {
            controllerMedicamento.cargarDatosMedicamentos();
        } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar medicamentos: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        
        
    }
  
     private void configurarControllerEnfermedades() {
    controllerEnfermedades.setTablaEnfermedades(jTable1); // Asigna la tabla de enfermedades
    controllerEnfermedades.setTxtIdEnfermedad(jTextField1); // ID Enfermedad
    controllerEnfermedades.setTxtNombre(jTextField1); // Nombre
    controllerEnfermedades.setTxtTipo(jTextField1); // Tipo
    controllerEnfermedades.setTxtSintomas(jTextArea1);// Síntomas (necesitarías cambiar a JTextArea en el controller)
    controllerEnfermedades.setTxtCausas(jTextArea1); // Causas (necesitarías cambiar a JTextArea en el controller)
    
    controllerEnfermedades.initTableEnfermedades();
    controllerEnfermedades.cargarDatosEnTabla();
    
     jTable1.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            String id = jTable1.getValueAt(fila, 0).toString();
            String nombre = jTable1.getValueAt(fila, 1).toString();
            String tipo = jTable1.getValueAt(fila, 2).toString();
            String sintomasStr = jTable1.getValueAt(fila, 3).toString(); // Columna de síntomas
            String causasStr = jTable1.getValueAt(fila, 4).toString();  // Columna de causas
            
            // Convertir a List<String> si es necesario
            List<String> sintomas = Arrays.asList(sintomasStr.split(","));
            List<String> causas = Arrays.asList(causasStr.split(","));
            
            controllerOrdenMedica.setEnfermedadSeleccionada(id, nombre, tipo, sintomas, causas);
        }
    }
});
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
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblIconRecepcion = new javax.swing.JLabel();
        Diagnostico = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        Agenda = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setMinimumSize(new java.awt.Dimension(1340, 770));
        jPanel1.setPreferredSize(new java.awt.Dimension(1340, 770));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(28, 43, 110));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Doctor");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, 42));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 69, 228, 10));

        lblIconRecepcion.setBackground(new java.awt.Color(255, 255, 255));
        lblIconRecepcion.setText("      ");
        lblIconRecepcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.add(lblIconRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 97, 102, 117));

        Diagnostico.setBackground(new java.awt.Color(28, 43, 110));
        Diagnostico.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                DiagnosticoAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        Diagnostico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DiagnosticoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DiagnosticoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DiagnosticoMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DiagnosticoMouseReleased(evt);
            }
        });
        Diagnostico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosticoKeyPressed(evt);
            }
        });
        Diagnostico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Diagnostico");
        Diagnostico.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 100, 50));

        jPanel2.add(Diagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 240, 70));

        Agenda.setBackground(new java.awt.Color(28, 43, 110));
        Agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AgendaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AgendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AgendaMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Agenda Medica");

        javax.swing.GroupLayout AgendaLayout = new javax.swing.GroupLayout(Agenda);
        Agenda.setLayout(AgendaLayout);
        AgendaLayout.setHorizontalGroup(
            AgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AgendaLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(52, 52, 52))
        );
        AgendaLayout.setVerticalGroup(
            AgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgendaLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(Agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 387, -1, 70));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 770));

        jPanel3.setBackground(new java.awt.Color(10, 92, 184));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 17, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Farma Salud");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 17, 149, 54));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("\"Tu bienestar, nuestra prioridad.\"  ");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 28, -1, -1));
        jPanel3.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 79, 65));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 0, 1670, 100));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Enfermedad:");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, 440, 110));

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        jPanel6.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 230, -1));

        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, -1, 20));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Tratamiento:");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Diagnostico:");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Medicamento:");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 170, -1, 20));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 460, 80));

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });
        jPanel6.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, 250, -1));

        jButton3.setText("Buscar ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 170, 80, 20));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        jPanel6.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 330, 370, 70));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Medicamentos asignados:");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Dosis:");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, -1, -1));
        jPanel6.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, 360, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Fecha:");
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));
        jPanel6.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 120, -1));

        jButton1.setBackground(new java.awt.Color(10, 92, 184));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Terminar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 530, -1, 40));
        jPanel6.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 450, 40));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable3);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 420, 200));

        jLabel14.setText("Pacientes Asignados:");
        jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        jTabbedPane1.addTab("tab1", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1660, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 635, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel7);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 100, 1660, 670));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DiagnosticoAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_DiagnosticoAncestorMoved
        // TODO add your handling code here:

    }//GEN-LAST:event_DiagnosticoAncestorMoved

    private void DiagnosticoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiagnosticoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosticoMouseReleased

    private void DiagnosticoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosticoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosticoKeyPressed

    private void DiagnosticoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiagnosticoMouseClicked
        // TODO add your handling code here:
                   jTabbedPane1.setSelectedIndex(0); 

    }//GEN-LAST:event_DiagnosticoMouseClicked

    private void DiagnosticoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiagnosticoMouseEntered
        // TODO add your handling code here:
                Diagnostico.setBackground(new Color(10, 92, 184)); 

    }//GEN-LAST:event_DiagnosticoMouseEntered

    private void DiagnosticoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiagnosticoMouseExited
        // TODO add your handling code here:
                Diagnostico.setBackground(new Color(28,43,110)); 

    }//GEN-LAST:event_DiagnosticoMouseExited

    private void AgendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendaMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1); 
    }//GEN-LAST:event_AgendaMouseClicked

    private void AgendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendaMouseEntered
        // TODO add your handling code here:
                Agenda.setBackground(new Color(10, 92, 184)); 

       
    }//GEN-LAST:event_AgendaMouseEntered

    private void AgendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendaMouseExited
        // TODO add your handling code here:
                                Agenda.setBackground(new Color(28,43,110)); 

    }//GEN-LAST:event_AgendaMouseExited

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         String criterio = jTextField1.getText().trim();
        controllerEnfermedades.buscarEnfermedades(criterio);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String criterio = jTextField1.getText().trim();
        controllerEnfermedades.buscarEnfermedades(criterio);
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String criterio = jTextField2.getText().trim();
        controllerMedicamento.buscarMedicamentos(criterio);
    }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         String criterio = jButton3.getText().trim();
        controllerMedicamento.buscarMedicamentos(criterio);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        controllerOrdenMedica.guardarOrdenMedica();
    }//GEN-LAST:event_jButton1MouseClicked

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
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Doctor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Agenda;
    private javax.swing.JPanel Diagnostico;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel lblIconRecepcion;
    // End of variables declaration//GEN-END:variables
}
