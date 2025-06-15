package authentication;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import base.BaseTest;
import pageobjects.InstructorRequestPage;
import utils.TestDataReader;

public class RegistrationTests extends BaseTest {
    
    @Test(description = "CP-01-02 - Registro con correo no institucional")
    public void testInvalidEmailRegistration() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("registration_data.json", "CP_01_02_InvalidEmail");
        
        String testId = TestDataReader.getString(testData, "testId");
        String fullName = TestDataReader.getString(testData, "fullName");
        String institution = TestDataReader.getString(testData, "institution");
        String country = TestDataReader.getString(testData, "country");
        String email = TestDataReader.getString(testData, "email");
        String expectedMessage = TestDataReader.getString(testData, "expectedMessage");
        boolean shouldSucceed = TestDataReader.getBoolean(testData, "shouldSucceed");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Email: " + email);
        System.out.println("Resultado esperado: " + expectedMessage);
        
        // Ejecutar test
        InstructorRequestPage requestPage = new InstructorRequestPage();
        requestPage.navigateToRequestForm();
        requestPage.fillAndSubmitRequest(fullName, institution, country, email);
        
        // Verificar resultado
        if (shouldSucceed) {
            assertTrue(requestPage.isSuccessMessageDisplayed(), 
                "Debería mostrar mensaje de éxito");
            String actualMessage = requestPage.getSuccessMessage();
            assertTrue(actualMessage.contains(expectedMessage), 
                "Mensaje esperado: " + expectedMessage + ", Actual: " + actualMessage);
        } else {
            assertTrue(requestPage.isErrorMessageDisplayed(), 
                "Debería mostrar mensaje de error");
            String actualMessage = requestPage.getErrorMessage();
            assertTrue(actualMessage.contains(expectedMessage) || actualMessage.contains("error"), 
                "Mensaje esperado contener: " + expectedMessage + ", Actual: " + actualMessage);
        }
        
        System.out.println("Test " + testId + " completado");
    }
    
    @Test(description = "CP-01-03 - Registro fallido por campo obligatorio vacío")
    public void testEmptyFieldRegistration() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("registration_data.json", "CP_01_03_EmptyField");
        
        String testId = TestDataReader.getString(testData, "testId");
        String fullName = TestDataReader.getString(testData, "fullName"); // Vacío
        String institution = TestDataReader.getString(testData, "institution");
        String country = TestDataReader.getString(testData, "country");
        String email = TestDataReader.getString(testData, "email");
        String expectedMessage = TestDataReader.getString(testData, "expectedMessage");
        boolean shouldSucceed = TestDataReader.getBoolean(testData, "shouldSucceed");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Name (vacío): '" + fullName + "'");
        System.out.println("Resultado esperado: " + expectedMessage);
        
        // Ejecutar test
        InstructorRequestPage requestPage = new InstructorRequestPage();
        requestPage.navigateToRequestForm();
        requestPage.fillAndSubmitRequest(fullName, institution, country, email);
        
        // Verificar resultado
        if (shouldSucceed) {
            assertTrue(requestPage.isSuccessMessageDisplayed(), 
                "Debería mostrar mensaje de éxito");
        } else {
            assertTrue(requestPage.isErrorMessageDisplayed(), 
                "Debería mostrar mensaje de error para campo vacío");
            String actualMessage = requestPage.getErrorMessage();
            assertTrue(actualMessage.contains("required") || actualMessage.contains("problem") || actualMessage.contains("Please enter"), 
                "Mensaje debería indicar campo requerido. Actual: " + actualMessage);
        }
        
        System.out.println("Test " + testId + " completado");
    }
    
    @Test(description = "Test de conectividad - Verificar que la página Request carga")
    public void testRequestPageLoads() {
        System.out.println("=== Test de conectividad ===");
        
        InstructorRequestPage requestPage = new InstructorRequestPage();
        requestPage.navigateToRequestForm();
        
        // Verificar que los elementos principales están presentes
        assertTrue(requestPage.isNameFieldPresent(), 
            "Campo Name debería estar presente");
        assertTrue(requestPage.isEmailFieldPresent(), 
            "Campo Email debería estar presente");
        assertTrue(requestPage.isInstitutionFieldPresent(), 
            "Campo Institution debería estar presente");
        
        System.out.println("Página Request carga correctamente");
    }
}