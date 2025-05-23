package Utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.OrdenMedica;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneradorOrdenMedicaPDF {
    
    // Paleta de colores moderna y elegante
    private static final BaseColor PRIMARY_COLOR = new BaseColor(45, 55, 72);      // Azul oscuro elegante
    private static final BaseColor SECONDARY_COLOR = new BaseColor(129, 140, 248); // Azul moderno
    private static final BaseColor ACCENT_COLOR = new BaseColor(236, 72, 153);     // Rosa elegante
    private static final BaseColor SUCCESS_COLOR = new BaseColor(34, 197, 94);     // Verde suave
    private static final BaseColor LIGHT_GRAY = new BaseColor(248, 250, 252);     // Gris muy claro
    private static final BaseColor MEDIUM_GRAY = new BaseColor(148, 163, 184);    // Gris medio
    private static final BaseColor DARK_GRAY = new BaseColor(71, 85, 105);        // Gris oscuro
    
    // Fuentes elegantes y modernas
    private static final Font MAIN_TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, PRIMARY_COLOR);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, SECONDARY_COLOR);
    private static final Font SECTION_TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, PRIMARY_COLOR);
    private static final Font LABEL_FONT = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, DARK_GRAY);
    private static final Font VALUE_FONT = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
    private static final Font FOOTER_FONT = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, MEDIUM_GRAY);
    private static final Font SIGNATURE_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, PRIMARY_COLOR);
    private static final Font NOTE_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, MEDIUM_GRAY);
    
    public static void generarPDF(OrdenMedica orden, String filePath) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 40, 40, 60, 50);
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        
        // Configurar evento para encabezado y pie de página hermosos
        writer.setPageEvent(new PdfPageEventHelper() {
            public void onEndPage(PdfWriter writer, Document document) {
                try {
                    addBeautifulHeader(writer, document);
                    addBeautifulFooter(writer, document);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        document.open();
        
        // Agregar contenido con diseño hermoso
        addStylizedTitle(document);
        addSpacer(document, 15);
        addPatientInfoSection(document, orden);
        addSpacer(document, 20);
        addMedicalOrderSection(document, orden);
        addSpacer(document, 30);
        addBeautifulSignatureSection(document);
        
        document.close();
    }
    
    private static void addBeautifulHeader(PdfWriter writer, Document document) throws DocumentException {
        PdfContentByte canvas = writer.getDirectContent();
        
        // Crear rectángulo de encabezado con gradiente
        Rectangle headerRect = new Rectangle(document.left(), document.top() + 5, 
                                           document.right(), document.top() + 35);
        headerRect.setBackgroundColor(PRIMARY_COLOR);
        canvas.rectangle(headerRect);
        canvas.setColorFill(PRIMARY_COLOR);
        canvas.fill();
        
        // Agregar texto del encabezado
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{3, 1});
        
        PdfPCell titleCell = new PdfPCell(new Phrase("🏥 FarmaSalud - Sistema de Gestión Médica", HEADER_FONT));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setBackgroundColor(PRIMARY_COLOR);
        titleCell.setPadding(8);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        PdfPCell dateCell = new PdfPCell(new Phrase("📅 " + dateFormat.format(new Date()), HEADER_FONT));
        dateCell.setBorder(Rectangle.NO_BORDER);
        dateCell.setBackgroundColor(PRIMARY_COLOR);
        dateCell.setPadding(8);
        dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        headerTable.addCell(titleCell);
        headerTable.addCell(dateCell);
        
        headerTable.writeSelectedRows(0, -1, document.left(), document.top() + 35, canvas);
    }
    
    private static void addBeautifulFooter(PdfWriter writer, Document document) throws DocumentException {
        PdfContentByte canvas = writer.getDirectContent();
        
        // Línea decorativa
        canvas.setColorStroke(SECONDARY_COLOR);
        canvas.setLineWidth(2);
        canvas.moveTo(document.left(), document.bottom() - 5);
        canvas.lineTo(document.right(), document.bottom() - 5);
        canvas.stroke();
        
        // Información del pie de página
        PdfPTable footerTable = new PdfPTable(3);
        footerTable.setWidthPercentage(100);
        footerTable.setWidths(new float[]{1, 1, 1});
        
        PdfPCell leftCell = new PdfPCell(new Phrase("🔐 Documento Confidencial", FOOTER_FONT));
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setPadding(5);
        leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        
        PdfPCell centerCell = new PdfPCell(new Phrase("Página " + writer.getPageNumber(), FOOTER_FONT));
        centerCell.setBorder(Rectangle.NO_BORDER);
        centerCell.setPadding(5);
        centerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        PdfPCell rightCell = new PdfPCell(new Phrase("🕐 " + timeFormat.format(new Date()), FOOTER_FONT));
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setPadding(5);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        footerTable.addCell(leftCell);
        footerTable.addCell(centerCell);
        footerTable.addCell(rightCell);
        
        footerTable.writeSelectedRows(0, -1, document.left(), document.bottom() - 25, canvas);
    }
    
    private static void addStylizedTitle(Document document) throws DocumentException {
        // Título principal con estilo
        Paragraph title = new Paragraph("💊 ORDEN MÉDICA", MAIN_TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(5);
        document.add(title);
        
        // Línea decorativa bajo el título
        PdfPTable decorativeLine = new PdfPTable(1);
        decorativeLine.setWidthPercentage(60);
        decorativeLine.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell lineCell = new PdfPCell();
        lineCell.setBorder(Rectangle.NO_BORDER);
        lineCell.setBackgroundColor(ACCENT_COLOR);
        lineCell.setFixedHeight(3);
        
        decorativeLine.addCell(lineCell);
        document.add(decorativeLine);
    }
    
    private static void addPatientInfoSection(Document document, OrdenMedica orden) throws DocumentException {
        // Título de sección con icono
        Paragraph sectionTitle = new Paragraph("👤 INFORMACIÓN DEL PACIENTE", SECTION_TITLE_FONT);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(15);
        document.add(sectionTitle);
        
        // Tabla con diseño moderno
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1.2f, 2f});
        
        // Agregar filas con estilo
        addStylizedTableRow(table, "👨‍⚕️ Nombre completo:", orden.getNombre() + " " + orden.getApellido(), true);
        addStylizedTableRow(table, "🩸 Tipo de sangre:", orden.getTipoSangre(), false);
        addStylizedTableRow(table, "⚥ Sexo:", orden.getSexo(), true);
        addStylizedTableRow(table, "🏥 EPS:", orden.getEps(), false);
        
        document.add(table);
    }
    
    private static void addMedicalOrderSection(Document document, OrdenMedica orden) throws DocumentException {
        // Título de sección
        Paragraph sectionTitle = new Paragraph("📋 DETALLES DE LA ORDEN MÉDICA", SECTION_TITLE_FONT);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(15);
        document.add(sectionTitle);
        
        // Tabla principal
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1.2f, 2f});
        
        addStylizedTableRow(table, "📅 Fecha:", orden.getFecha(), true);
        addStylizedTableRow(table, "🔍 Diagnóstico:", orden.getDiagnostico(), false);
        
        document.add(table);
        
        // Sección especial para medicamentos
        addMedicationSection(document, orden);
    }
    
    private static void addMedicationSection(Document document, OrdenMedica orden) throws DocumentException {
        addSpacer(document, 15);
        
        // Título para medicamentos
        Paragraph medTitle = new Paragraph("💊 PRESCRIPCIÓN MÉDICA", SECTION_TITLE_FONT);
        medTitle.setSpacingAfter(10);
        document.add(medTitle);
        
        // Crear tabla para medicamentos con diseño especial
        PdfPTable medTable = new PdfPTable(1);
        medTable.setWidthPercentage(100);
        
        // Celda para medicamentos
        PdfPCell medCell = new PdfPCell();
        medCell.setBackgroundColor(LIGHT_GRAY);
        medCell.setBorder(Rectangle.NO_BORDER);
        medCell.setPadding(15);
        medCell.setBorderWidth(1);
        medCell.setBorderColor(SECONDARY_COLOR);
        
        Paragraph medParagraph = new Paragraph();
        medParagraph.add(new Chunk("Medicamentos prescritos:\n", LABEL_FONT));
        medParagraph.add(new Chunk(orden.getAreamedicamentos().toString() + "\n\n", VALUE_FONT));
        medParagraph.add(new Chunk("Cantidad/Dosis:\n", LABEL_FONT));
        medParagraph.add(new Chunk(orden.getReceta(), VALUE_FONT));
        
        medCell.addElement(medParagraph);
        medTable.addCell(medCell);
        
        document.add(medTable);
    }
    
    private static void addBeautifulSignatureSection(Document document) throws DocumentException {
        // Sección de firma elegante
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setWidths(new float[]{1, 1});
        
        // Celda izquierda - Información adicional
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setPadding(20);
        
        Paragraph validityInfo = new Paragraph();
        validityInfo.add(new Chunk("⏰ Validez del documento:\n", LABEL_FONT));
        validityInfo.add(new Chunk("30 días a partir de la fecha de emisión\n\n", VALUE_FONT));
        validityInfo.add(new Chunk("✅ Estado: ", LABEL_FONT));
        validityInfo.add(new Chunk("ACTIVA", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, SUCCESS_COLOR)));
        
        leftCell.addElement(validityInfo);
        
        // Celda derecha - Firma
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setPadding(20);
        rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        Paragraph signature = new Paragraph();
        signature.add(new Chunk("\n\n\n", SIGNATURE_FONT));
        signature.add(new Chunk("________________________________\n", SIGNATURE_FONT));
        signature.add(new Chunk("👨‍⚕️ Firma del Médico Tratante\n", SIGNATURE_FONT));
        signature.add(new Chunk("Registro Médico: RM-2024-001", NOTE_FONT));
        signature.setAlignment(Element.ALIGN_CENTER);
        
        rightCell.addElement(signature);
        
        signatureTable.addCell(leftCell);
        signatureTable.addCell(rightCell);
        
        document.add(signatureTable);
        
        // Nota final elegante
        addSpacer(document, 20);
        PdfPTable noteTable = new PdfPTable(1);
        noteTable.setWidthPercentage(80);
        noteTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell noteCell = new PdfPCell();
        noteCell.setBackgroundColor(new BaseColor(254, 249, 195)); // Amarillo muy suave
        noteCell.setBorder(Rectangle.NO_BORDER);
        noteCell.setBorderWidth(1);
        noteCell.setBorderColor(new BaseColor(251, 191, 36)); // Borde amarillo
        noteCell.setPadding(10);
        noteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        Paragraph finalNote = new Paragraph(
            "⚠️ IMPORTANTE: Mantenga este documento en lugar seguro. " +
            "Es requerido para el retiro de medicamentos en farmacia.",
            NOTE_FONT
        );
        finalNote.setAlignment(Element.ALIGN_CENTER);
        
        noteCell.addElement(finalNote);
        noteTable.addCell(noteCell);
        
        document.add(noteTable);
    }
    
    private static void addStylizedTableRow(PdfPTable table, String label, String value, boolean alternate) {
        if (value != null && !value.isEmpty()) {
            // Celda de etiqueta
            PdfPCell labelCell = new PdfPCell(new Phrase(label, LABEL_FONT));
            labelCell.setBorder(Rectangle.NO_BORDER);
            labelCell.setPadding(12);
            labelCell.setBackgroundColor(alternate ? LIGHT_GRAY : BaseColor.WHITE);
            labelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            // Celda de valor
            PdfPCell valueCell = new PdfPCell(new Phrase(value, VALUE_FONT));
            valueCell.setBorder(Rectangle.NO_BORDER);
            valueCell.setPadding(12);
            valueCell.setBackgroundColor(alternate ? LIGHT_GRAY : BaseColor.WHITE);
            valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            table.addCell(labelCell);
            table.addCell(valueCell);
        }
    }
    
    private static void addSpacer(Document document, float height) throws DocumentException {
        Paragraph spacer = new Paragraph(" ");
        spacer.setSpacingAfter(height);
        document.add(spacer);
    }
}