package farmasalud.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;import java.util.ArrayList;


class InterfazDiagnostico extends JFrame {
    private JSONObject paciente;
    private JSONArray enfermedades;
    private List<JSONObject> enfermedadesDiagnosticadas = new ArrayList<>();

    public InterfazDiagnostico(JSONObject paciente, JSONArray enfermedades) {
        this.paciente = paciente;
        this.enfermedades = enfermedades;
        
        setTitle("Diagnóstico - " + paciente.get("nombres") + " " + paciente.get("apellidos"));
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initUI();
    }
    
    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior con datos del paciente
        JPanel pacientePanel = new JPanel(new GridLayout(0, 2, 10, 5));
        pacientePanel.setBorder(BorderFactory.createTitledBorder("Datos del Paciente"));
        
        pacientePanel.add(new JLabel("Nombre:"));
        pacientePanel.add(new JLabel(paciente.get("nombres") + " " + paciente.get("apellidos")));
        
        pacientePanel.add(new JLabel("Documento:"));
        pacientePanel.add(new JLabel(paciente.get("tipoDocumento") + " " + paciente.get("numeroDocumento")));
        
        pacientePanel.add(new JLabel("Tipo de Sangre:"));
        pacientePanel.add(new JLabel((String) paciente.get("tipoSangre")));
        
        pacientePanel.add(new JLabel("Sexo:"));
        pacientePanel.add(new JLabel((String) paciente.get("sexo")));
        
        pacientePanel.add(new JLabel("EPS:"));
        pacientePanel.add(new JLabel((String) paciente.get("eps")));
        
        // Panel central con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña de diagnóstico
        tabbedPane.addTab("Diagnóstico", crearPanelDiagnostico());
        
        // Panel inferior con botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton finalizarButton = new JButton("Finalizar Atención");
        finalizarButton.addActionListener(e -> finalizarAtencion());
        
        buttonPanel.add(finalizarButton);
        
        mainPanel.add(pacientePanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel crearPanelDiagnostico() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(25);
        JButton searchButton = new JButton("Buscar");
        
        // Tabla de enfermedades
        String[] columnNames = {"ID", "Nombre", "Tipo", "Síntomas Principales"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Object obj : enfermedades) {
            JSONObject enfermedad = (JSONObject) obj;
            
            JSONArray sintomas = (JSONArray) enfermedad.get("sintomas");
            StringBuilder sintomasStr = new StringBuilder();
            int maxSintomas = Math.min(3, sintomas.size());
            for (int j = 0; j < maxSintomas; j++) {
                if (j > 0) sintomasStr.append(", ");
                sintomasStr.append(sintomas.get(j));
            }
            if (sintomas.size() > 3) sintomasStr.append(", ...");
            
            model.addRow(new Object[]{
                enfermedad.get("id_enfermedad"),
                enfermedad.get("nombre"),
                enfermedad.get("tipo"),
                sintomasStr.toString()
            });
        }
        
        JTable tablaEnfermedades = new JTable(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablaEnfermedades.setRowSorter(sorter);
        
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
            }
        });
        
        searchPanel.add(new JLabel("Buscar enfermedad:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Panel de diagnóstico
        JPanel diagnosticoPanel = new JPanel(new BorderLayout());
        JTextArea diagnosticoArea = new JTextArea(5, 40);
        diagnosticoArea.setLineWrap(true);
        diagnosticoArea.setWrapStyleWord(true);
        diagnosticoArea.setEditable(false);
        
        JScrollPane diagnosticoScroll = new JScrollPane(diagnosticoArea);
        diagnosticoScroll.setBorder(BorderFactory.createTitledBorder("Diagnóstico:"));
        
        JButton agregarDiagnosticoBtn = new JButton("Agregar Diagnóstico");
        agregarDiagnosticoBtn.addActionListener(e -> {
            int selectedRow = tablaEnfermedades.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = tablaEnfermedades.convertRowIndexToModel(selectedRow);
                JSONObject enfermedad = (JSONObject) enfermedades.get(modelRow);
                
                enfermedadesDiagnosticadas.add(enfermedad);
                
                String diagnosticoActual = diagnosticoArea.getText();
                if (!diagnosticoActual.isEmpty()) {
                    diagnosticoActual += "\n";
                }
                diagnosticoActual += enfermedad.get("nombre") + ": " + 
                    String.join(", ", (JSONArray) enfermedad.get("sintomas"));
                
                diagnosticoArea.setText(diagnosticoActual);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Seleccione una enfermedad para diagnosticar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Botón para ver detalles
        JButton detallesBtn = new JButton("Ver Detalles");
        detallesBtn.addActionListener(e -> {
            int selectedRow = tablaEnfermedades.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = tablaEnfermedades.convertRowIndexToModel(selectedRow);
                mostrarDetalleEnfermedad((JSONObject) enfermedades.get(modelRow));
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Seleccione una enfermedad para ver detalles", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(detallesBtn);
        
        // Panel principal
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(tablaEnfermedades), BorderLayout.CENTER);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel agregarPanel = new JPanel(new BorderLayout());
        agregarPanel.add(diagnosticoScroll, BorderLayout.CENTER);
        agregarPanel.add(agregarDiagnosticoBtn, BorderLayout.SOUTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
            tablePanel, agregarPanel);
        splitPane.setResizeWeight(0.7);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void mostrarDetalleEnfermedad(JSONObject enfermedad) {
        JDialog dialog = new JDialog(this, "Detalles de Enfermedad", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        
        infoPanel.add(new JLabel("Nombre: " + enfermedad.get("nombre")));
        infoPanel.add(new JLabel("Tipo: " + enfermedad.get("tipo")));
        infoPanel.add(new JLabel(" ")); // Espacio
        
        // Síntomas
        infoPanel.add(new JLabel("Síntomas:"));
        JTextArea sintomasArea = new JTextArea();
        sintomasArea.setEditable(false);
        for (Object sintoma : (JSONArray) enfermedad.get("sintomas")) {
            sintomasArea.append("• " + sintoma + "\n");
        }
        infoPanel.add(new JScrollPane(sintomasArea));
        
        // Causas
        infoPanel.add(new JLabel("Causas:"));
        JTextArea causasArea = new JTextArea();
        causasArea.setEditable(false);
        for (Object causa : (JSONArray) enfermedad.get("causas")) {
            causasArea.append("• " + causa + "\n");
        }
        infoPanel.add(new JScrollPane(causasArea));
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void finalizarAtencion() {
        // Aquí puedes guardar el diagnóstico en tu base de datos o sistema
        if (enfermedadesDiagnosticadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se ha agregado ningún diagnóstico", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Atención finalizada. Diagnóstico guardado.", 
            "Proceso Completado", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }
}