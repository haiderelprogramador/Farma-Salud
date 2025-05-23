import java.util.Map;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.google.gson.Gson; // Necesitarás la librería Gson

public class ValidacionJson {
    private final Map<String, Object> datosOriginales;
    private final String checksum;
    private final String claveSeguridad;

    public ValidacionJson(Map<String, Object> datos, String claveSeguridad) {
        this.datosOriginales = new HashMap<>(datos);
        this.claveSeguridad = claveSeguridad;
        this.checksum = generarChecksum(datos);
    }

    private String generarChecksum(Map<String, Object> datos) {
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(datos);
            
            if (claveSeguridad != null) {
                jsonString += claveSeguridad;
            }

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(jsonString.getBytes(StandardCharsets.UTF_8));
            
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar checksum", e);
        }
    }

    public boolean validarIntegridad() {
        String checksumActual = generarChecksum(datosOriginales);
        return checksumActual.equals(checksum);
    }

    public Map<String, Object> getDatos() {
        if (!validarIntegridad()) {
            throw new SecurityException("Los datos han sido modificados manualmente. Integridad comprometida.");
        }
        return new HashMap<>(datosOriginales);
    }

    public String toJson() {
        if (!validarIntegridad()) {
            throw new SecurityException("No se puede serializar. Datos modificados manualmente.");
        }
        Gson gson = new Gson();
        return gson.toJson(datosOriginales);
    }
}