package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    
    public static void loadConfig(String filePath) {
        try {
            properties = new Properties();
            System.out.println("Intentando cargar: " + filePath);
            
            // Intentar cargar desde classpath
            InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(filePath);
            
            if (inputStream == null) {
                System.out.println("Archivo no encontrado en classpath: " + filePath);
                // Intentar cargar valores por defecto
                loadDefaultProperties();
                return;
            }
            
            properties.load(inputStream);
            inputStream.close();
            System.out.println("Configuración cargada exitosamente");
            
        } catch (IOException e) {
            System.out.println("Error cargando configuración: " + e.getMessage());
            loadDefaultProperties();
        }
    }
    
    private static void loadDefaultProperties() {
        System.out.println("Cargando configuración por defecto...");
        properties = new Properties();
        properties.setProperty("app.url", "http://localhost:8080");
        properties.setProperty("browser", "chrome");
        properties.setProperty("browser.timeout", "30");
        System.out.println("Configuración por defecto cargada");
    }
    
    public static String getProperty(String key) {
        if (properties == null) {
            throw new RuntimeException("Config not loaded. Call loadConfig() first.");
        }
        String value = properties.getProperty(key);
        System.out.println("Propiedad " + key + " = " + value);
        return value;
    }
    
    public static String getAppUrl() {
        return getProperty("app.url");
    }
    
    public static String getBrowser() {
        return getProperty("browser");
    }
    
    public static int getTimeout() {
        return Integer.parseInt(getProperty("browser.timeout"));
    }
    
    public static String getInstructorEmail() {
        return getProperty("test.instructor.email");
    }
    
    public static String getInstructorPassword() {
        return getProperty("test.instructor.password");
    }
    
    public static String getStudentEmail() {
        return getProperty("test.student.email");
    }
    
    public static String getStudentPassword() {
        return getProperty("test.student.password");
    }
}