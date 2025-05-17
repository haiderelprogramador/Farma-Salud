/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import dao.OrdenMedicaDAO;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import model.Enfermedad;
import model.Medicamento;
import model.OrdenMedica;
import java.time.LocalDate;


/**
 *
 * @author HP
 */
public class ControllerOrdenMedica {
    private DefaultTableModel tablaModelenfermedades;
    private DefaultTableModel tablaModelMedicamento;
    private OrdenMedicaDAO ordenmedicaDAO= new OrdenMedicaDAO();
    private int idOrden;
    private JTable jTable1;
    private JTable jTable2;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField5;
    private JTextArea jTextArea1;
    private JTextField jTextField3;
    private JTextField jTextField4;
    private Enfermedad enfermedadSeleccionada;
    
    public void setjTable1(JTable jTable1){
       this.jTable1=jTable1;
       this.tablaModelenfermedades=(DefaultTableModel) jTable1.getModel();
    }
    
    public void setjTable2(JTable jTable2){
      this.jTable2=jTable2;
      this.tablaModelMedicamento=(DefaultTableModel) jTable2.getModel();
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public void setjTextField5(JTextField jTextField5) {
        this.jTextField5 = jTextField5;
    }

   

    public void setjTextArea1(JTextArea jTextArea1) {
        this.jTextArea1 = jTextArea1;
    }

    public void setjTextField3(JTextField jTextField3) {
        this.jTextField3 = jTextField3;
    }

    public void setjTextField4(JTextField jTextField4) {
        this.jTextField4 = jTextField4;
    }

    public void setjTextField2(JTextField jTextField2) {
        this.jTextField2 = jTextField2;
    }

    public void setjTextField1(JTextField jTextField1) {
        this.jTextField1 = jTextField1;
    }
    
    
    public void setEnfermedadSeleccionada(String id, String nombre, String tipo, List<String> sintomas, List<String> causas) {
    this.enfermedadSeleccionada = new Enfermedad(
        Integer.parseInt(id), // Convertir String a int si es necesario
        nombre,
        tipo,
        sintomas,
        causas
    );
}
    

   public void guardarOrdenMedica() {
    try {
        String idOrden = ordenmedicaDAO.generarCodigoUnico();
        String diagnostico = jTextField5.getText().trim();
        String fechaTexto = jTextField4.getText().trim();
        String medicamentos = jTextArea1.getText().trim();
        String dosis = jTextField3.getText().trim();

        if (diagnostico.isEmpty() || fechaTexto.isEmpty() || medicamentos.isEmpty() || dosis.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Todos los campos deben estar completos",
                "Error de validación",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate fecha = LocalDate.parse(fechaTexto); // CONVERSIÓN SEGURA

        OrdenMedica nuevaOrden = new OrdenMedica(
            idOrden,
            diagnostico,
            dosis,
            fecha,
            medicamentos
        );

        if (ordenmedicaDAO.guardarOrdenMedica(nuevaOrden)) {
            JOptionPane.showMessageDialog(null,
                "Orden guardada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(null,
                "No se pudo guardar la orden",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al guardar orden: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

   
   
   
   public void limpiarFormulario(){
       jTextField2.setText("");
       jTextField1.setText("");
     jTextField5.setText("");
     jTextField4.setText("");
     jTextArea1.setText("");
     jTextField3.setText("");
   }

}
