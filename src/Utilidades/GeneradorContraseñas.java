package Utilidades;

import java.security.SecureRandom;

public class GeneradorContraseñas {
    private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String SIMBOLOS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final SecureRandom random = new SecureRandom();
    
    public String generarContrasena(int longitud) {
        StringBuilder caracteresPermitidos = new StringBuilder();
        caracteresPermitidos.append(MAYUSCULAS).append(MINUSCULAS).append(NUMEROS).append(SIMBOLOS);
        
        StringBuilder contrasena = new StringBuilder(longitud);
        
        // Asegurar al menos un caracter de cada tipo
        contrasena.append(MAYUSCULAS.charAt(random.nextInt(MAYUSCULAS.length())));
        contrasena.append(MINUSCULAS.charAt(random.nextInt(MINUSCULAS.length())));
        contrasena.append(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        contrasena.append(SIMBOLOS.charAt(random.nextInt(SIMBOLOS.length())));
        
        // Completar el resto
        for (int i = 4; i < longitud; i++) {
            contrasena.append(caracteresPermitidos.charAt(
                random.nextInt(caracteresPermitidos.length())));
        }
        
        // Mezclar los caracteres
        return desordenarContrasena(contrasena.toString());
    }
    
    private String desordenarContrasena(String contrasena) {
        char[] caracteres = contrasena.toCharArray();
        for (int i = 0; i < caracteres.length; i++) {
            int posicionAleatoria = random.nextInt(caracteres.length);
            char temp = caracteres[i];
            caracteres[i] = caracteres[posicionAleatoria];
            caracteres[posicionAleatoria] = temp;
        }
        return new String(caracteres);
    }
}