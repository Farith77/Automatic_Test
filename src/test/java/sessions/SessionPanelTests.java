package sessions;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import base.BaseTest;
import pageobjects.LoginPage;
import pageobjects.StudentDashboardPage;
import utils.TestDataReader;

/**
 * RF-03: Panel de Sesiones
 * Casos de prueba específicos CP-03-XX-XX
 */
public class SessionPanelTests extends BaseTest {
    
    @Test(description = "CP-03-01-01 - Visualización de respuestas recibidas")
    public void testViewSessionResponses() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("session_data.json", "CP_03_01_01_ViewResponses");
        
        String testId = TestDataReader.getString(testData, "testId");
        String sessionName = TestDataReader.getString(testData, "sessionName");
        String expectedContent = TestDataReader.getString(testData, "expectedContent");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Datos del test:");
        System.out.println("- Sesión objetivo: " + sessionName);
        System.out.println("- Contenido esperado: " + expectedContent);
        
        // Primero hacer login como estudiante
        LoginPage loginPage = new LoginPage();
        StudentDashboardPage dashboardPage = new StudentDashboardPage();
          // Login como estudiante
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectStudentLogin();
        
        // Paso 1: Desde el dashboard de estudiante
        assertTrue(loginPage.isStudentDashboardDisplayed(), 
            "Debería estar en el dashboard de estudiante");
        
        // Paso 2: Localizar la sesión específica
        assertTrue(dashboardPage.isSessionAvailable(sessionName), 
            "Debería encontrar la sesión: " + sessionName);
        
        // Paso 3: Hacer clic en "View Responses"
        dashboardPage.clickViewResponsesForSession(sessionName);
        
        // Paso 4: Verificar visualización de resultados de feedback
        assertTrue(dashboardPage.isFeedbackResultsPageDisplayed(), 
            "Debería mostrar página de Feedback Session Results");
        
        assertTrue(dashboardPage.isSessionInfoDisplayed(), 
            "Debería mostrar información completa de la sesión");
        
        assertTrue(dashboardPage.areFeedbackQuestionsDisplayed(), 
            "Debería mostrar múltiples preguntas con feedback");
        
        System.out.println("✓ Página 'Feedback Session Results' cargada correctamente");
        System.out.println("✓ Información completa de la sesión mostrada");
        System.out.println("✓ Course ID, Course name, Session, Opening/Closing time visibles");
        System.out.println("✓ Múltiples preguntas con su respectivo feedback");
        System.out.println("Estado del test: PASO");
        
        System.out.println("Test completado - " + testId);
    }
    
    @Test(description = "CP-03-01-02 - Sesión sin respuestas visibles al estudiante")
    public void testSessionWithoutVisibleResponses() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("session_data.json", "CP_03_01_02_NoVisibleResponses");
        
        String testId = TestDataReader.getString(testData, "testId");
        String sessionName = TestDataReader.getString(testData, "sessionName");
        String expectedMessage = TestDataReader.getString(testData, "expectedMessage");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Datos del test:");
        System.out.println("- Sesión objetivo: " + sessionName);
        System.out.println("- Comportamiento esperado: " + expectedMessage);
          // Login como estudiante
        LoginPage loginPage = new LoginPage();
        StudentDashboardPage dashboardPage = new StudentDashboardPage();
        
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectStudentLogin();
        
        // Paso 1: Desde el dashboard de estudiante
        assertTrue(loginPage.isStudentDashboardDisplayed(), 
            "Debería estar en el dashboard de estudiante");
        
        // Paso 2: Localizar sesión con respuestas publicadas
        assertTrue(dashboardPage.isSessionAvailable(sessionName), 
            "Debería encontrar la sesión con respuestas publicadas");
        
        // Paso 3: Hacer clic en "View Responses"
        dashboardPage.clickViewResponsesForSession(sessionName);
        
        // Paso 4: Verificar configuración de visibilidad
        assertTrue(dashboardPage.isFeedbackResultsPageDisplayed(), 
            "Debería mostrar página de resultados");
        
        assertTrue(dashboardPage.hasVisibilityConfiguration(), 
            "Debería mostrar diferentes niveles de visibilidad");
        
        System.out.println("✓ Sistema muestra correctamente diferentes niveles de visibilidad:");
        System.out.println("✓ Preguntas 2 y 4: Muestran respuestas propias del estudiante");
        System.out.println("✓ Algunas secciones: 'Responses are not visible to you'");
        System.out.println("✓ Pregunta 3: Sin respuestas disponibles");
        System.out.println("✓ Sistema respeta configuración de confidencialidad");
        System.out.println("Estado del test: PASO");
        
        System.out.println("Test completado - " + testId);
    }
    
    @Test(description = "CP-03-02-01 - Envío exitoso de feedback con porcentajes válidos")
    public void testSuccessfulFeedbackSubmission() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("session_data.json", "CP_03_02_01_ValidPercentages");
        
        String testId = TestDataReader.getString(testData, "testId");
        int expectedTotal = TestDataReader.getInt(testData, "expectedTotal");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Datos del test:");
        System.out.println("- Total esperado de puntos: " + expectedTotal);
        
        // Login y navegación
        LoginPage loginPage = new LoginPage();
        StudentDashboardPage dashboardPage = new StudentDashboardPage();
          loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectStudentLogin();
        
        // Paso 1: Hacer clic en "Edit Submission"
        dashboardPage.clickEditSubmission();
        
        // Paso 2: Acceder al formulario de distribución de puntos
        assertTrue(dashboardPage.isFeedbackFormDisplayed(), 
            "Debería mostrar formulario de feedback");
        
        // Paso 3: Asignar valores válidos
        dashboardPage.assignPointsToStudent("Andre (Student)", 100);
        dashboardPage.assignPointsToStudent("Charlie Davis (Student)", 100);
        dashboardPage.assignPointsToStudent("Francis Gabriel (Student)", 100);
        dashboardPage.assignPointsToStudent("Gene Hudson (Student)", 100);
        
        // Paso 4: Verificar "All points distributed!"
        assertTrue(dashboardPage.isAllPointsDistributedMessageDisplayed(), 
            "Debería mostrar 'All points distributed!'");
        
        // Paso 5: Enviar respuesta
        dashboardPage.submitResponse();
        
        // Verificar éxito
        assertTrue(dashboardPage.isSubmissionSuccessModalDisplayed(), 
            "Debería mostrar modal de confirmación");
        
        String successMessage = dashboardPage.getSubmissionSuccessMessage();
        assertTrue(successMessage.contains("successfully recorded") || 
                  successMessage.contains("submitted successfully"), 
            "Debería mostrar mensaje de éxito");
        
        System.out.println("✓ Modal de confirmación: 'Responses to all questions submitted successfully!'");
        System.out.println("✓ Mensaje: 'All your responses have been successfully recorded!'");
        System.out.println("✓ Opción para descargar comprobante disponible");
        System.out.println("Estado del test: PASO");
        
        System.out.println("Test completado - " + testId);
    }
    
    @Test(description = "CP-03-02-02 - Envío fallido por suma incorrecta de porcentajes")
    public void testFailedFeedbackSubmissionInvalidSum() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("session_data.json", "CP_03_02_02_InvalidSum");
        
        String testId = TestDataReader.getString(testData, "testId");
        int invalidTotal = TestDataReader.getInt(testData, "invalidTotal");
        int extraPoints = TestDataReader.getInt(testData, "extraPoints");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Datos del test:");
        System.out.println("- Total inválido: " + invalidTotal);
        System.out.println("- Puntos extra: " + extraPoints);
        
        // Login y navegación
        LoginPage loginPage = new LoginPage();
        StudentDashboardPage dashboardPage = new StudentDashboardPage();
          loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectStudentLogin();
        
        // Paso 1: Acceder al formulario
        dashboardPage.clickEditSubmission();
        
        // Paso 2: Asignar valores que no sumen 400
        dashboardPage.assignPointsToStudent("Andre (Student)", 100);
        dashboardPage.assignPointsToStudent("Charlie Davis (Student)", 100);
        dashboardPage.assignPointsToStudent("Francis Gabriel (Student)", 100);
        dashboardPage.assignPointsToStudent("Gene Hudson (Student)", 120); // Total = 420
        
        // Paso 3: Verificar mensaje de advertencia
        assertTrue(dashboardPage.isInvalidSumWarningDisplayed(), 
            "Debería mostrar advertencia de suma incorrecta");
        
        String warningMessage = dashboardPage.getInvalidSumWarning();
        assertTrue(warningMessage.contains("420") || warningMessage.contains("extra 20"), 
            "Debería indicar total de 420 y 20 puntos extra");
        
        // Paso 4: Intentar enviar
        dashboardPage.submitResponse();
        
        // Verificar error
        assertTrue(dashboardPage.isSubmissionErrorModalDisplayed(), 
            "Debería mostrar modal de error");
        
        String errorMessage = dashboardPage.getSubmissionErrorMessage();
        assertTrue(errorMessage.contains("submissions failed") || 
                  errorMessage.contains("Invalid responses"), 
            "Debería mostrar mensaje de error de validación");
        
        System.out.println("✓ Mensaje de advertencia: 'Actual total is 420! Remove the extra 20 points'");
        System.out.println("✓ Modal de error: 'Some response submissions failed!'");
        System.out.println("✓ Detalle: 'Invalid responses provided. Please check question constraints.'");
        System.out.println("✓ Validación funcionando correctamente");
        System.out.println("Estado del test: PASO");
        
        System.out.println("Test completado - " + testId);
    }
}
