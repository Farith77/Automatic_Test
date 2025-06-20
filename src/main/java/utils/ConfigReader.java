package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

public class ConfigReader {
    private static Properties properties;
    private static Dotenv dotenv;
    
    static {
        // Cargar variables de entorno desde .env
        try {
            dotenv = Dotenv.configure()
                    .directory(".")
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            System.out.println("Archivo .env no encontrado, usando variables del sistema");
            dotenv = null;
        }
    }
    
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
        // Priorizar variable de entorno sobre archivo de configuración
        String envValue = getEnvironmentVariable("TEST_INSTRUCTOR_EMAIL");
        if (envValue != null) {
            return envValue;
        }
        return getProperty("test.instructor.email");
    }
    
    public static String getInstructorPassword() {
        // Priorizar variable de entorno sobre archivo de configuración
        String envValue = getEnvironmentVariable("TEST_INSTRUCTOR_PASSWORD");
        if (envValue != null) {
            return envValue;
        }
        return getProperty("test.instructor.password");
    }
    
    public static String getStudentEmail() {
        // Priorizar variable de entorno sobre archivo de configuración
        String envValue = getEnvironmentVariable("TEST_STUDENT_EMAIL");
        if (envValue != null) {
            return envValue;
        }
        return getProperty("test.student.email");
    }
    
    public static String getStudentPassword() {
        // Priorizar variable de entorno sobre archivo de configuración
        String envValue = getEnvironmentVariable("TEST_STUDENT_PASSWORD");
        if (envValue != null) {
            return envValue;
        }
        return getProperty("test.student.password");
    }
    
    /**
     * Obtiene una variable de entorno, primero desde .env y luego del sistema
     */
    private static String getEnvironmentVariable(String key) {
        // Primero intentar desde archivo .env
        if (dotenv != null) {
            String value = dotenv.get(key);
            if (value != null) {
                return value;
            }
        }
        
        // Luego intentar desde variables del sistema
        return System.getenv(key);
    }
}