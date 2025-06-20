package feedback;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import base.BaseTest;
import pageobjects.LoginPage;
import pageobjects.InstructorCoursePage;
import utils.TestDataReader;

/**
 * RF-05: Gestión de Sesiones de Feedback
 * Casos de prueba específicos CP-05-XX
 */
public class FeedbackSessionManagementTests extends BaseTest {
      @Test(description = "CP-05-01 - Crear sesión de feedback con datos válidos")
    public void testCreateFeedbackSessionWithValidData() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_01_CreateSession");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String sessionName = TestDataReader.getString(testData, "sessionName");
        String instructions = TestDataReader.getString(testData, "instructions");
        String expectedMessage = TestDataReader.getString(testData, "expectedMessage");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Nombre de sesión: " + sessionName);
        System.out.println("- Instrucciones: " + instructions);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login como instructor
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        
        // Navegar a cursos y crear sesión de feedback
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Crear sesión de feedback
        boolean sessionCreated = coursePage.createFeedbackSession(sessionName, instructions);
        assertTrue(sessionCreated, "La sesión de feedback debería crearse exitosamente");
        
        // Verificar mensaje de confirmación con el mensaje esperado
        boolean confirmationDisplayed = coursePage.isFeedbackSessionCreated();
        assertTrue(confirmationDisplayed, expectedMessage);
        assertTrue(confirmationDisplayed, "Debería mostrar mensaje de confirmación de creación");
        
        System.out.println("✓ Sesión de feedback creada exitosamente");
        System.out.println("✓ Mensaje de confirmación mostrado");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-02 - Validar exceso de caracteres en Session Name")
    public void testSessionNameTooLong() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_02_SessionNameTooLong");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String sessionName = TestDataReader.getString(testData, "sessionName");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Longitud del nombre: " + sessionName.length() + " caracteres");
        System.out.println("- Resultado esperado: " + expectedResult);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Verificar que el frontend limita los caracteres
        boolean characterLimitEnforced = coursePage.testSessionNameCharacterLimit(sessionName);
        assertTrue(characterLimitEnforced, "El frontend debería inhabilitar caracteres adicionales");
        
        System.out.println("✓ El frontend inhabilita que se coloquen más caracteres");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-03 - Validar campo Session Name vacío")
    public void testEmptySessionName() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_03_EmptySessionName");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Resultado esperado: " + expectedResult);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Verificar que el botón se deshabilita con nombre vacío
        boolean buttonDisabled = coursePage.isCreateSessionButtonDisabledWithEmptyName();
        assertTrue(buttonDisabled, "El botón Create Feedback Session debería estar deshabilitado");
        
        System.out.println("✓ El frontend inhabilita el botón de Create Feedback Session");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-04 - Validar fecha de cierre anterior a apertura")
    public void testInvalidDates() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_04_InvalidDates");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String sessionName = TestDataReader.getString(testData, "sessionName");
        String openingTime = TestDataReader.getString(testData, "submissionOpeningTime");
        String closingTime = TestDataReader.getString(testData, "submissionClosingTime");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Sesión: " + sessionName);
        System.out.println("- Fecha apertura: " + openingTime);
        System.out.println("- Fecha cierre: " + closingTime);
        System.out.println("- Resultado esperado: " + expectedResult);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Verificar validación de fechas
        boolean dateValidationWorks = coursePage.validateInvalidDateRange(openingTime, closingTime);
        assertTrue(dateValidationWorks, "El frontend debería prevenir fechas inválidas");
        
        System.out.println("✓ El frontend inhabilita que se pueda seleccionar una fecha anterior");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-05 - Editar instrucciones en sesión activa")
    public void testEditSessionInstructions() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_05_EditInstructions");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String newInstructions = TestDataReader.getString(testData, "newInstructions");
        String expectedMessage = TestDataReader.getString(testData, "expectedMessage");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Nuevas instrucciones: " + newInstructions);
        System.out.println("- Mensaje esperado: " + expectedMessage);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Editar instrucciones de sesión existente
        boolean instructionsUpdated = coursePage.editSessionInstructions(newInstructions);
        assertTrue(instructionsUpdated, "Las instrucciones deberían actualizarse exitosamente");
        
        // Verificar mensaje de confirmación
        assertTrue(coursePage.getCurrentPageText().contains("feedback session has been updated"),
                  "Debería mostrar mensaje de actualización");
        
        System.out.println("✓ Instrucciones de sesión actualizadas");
        System.out.println("✓ Mensaje de confirmación mostrado");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-06 - Validar distribución correcta de puntos en preguntas")
    public void testValidPointDistribution() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_06_ValidPointDistribution");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String question = TestDataReader.getString(testData, "question");
        int totalPoints = TestDataReader.getInt(testData, "totalPoints");
        String expectedMessage = TestDataReader.getString(testData, "expectedMessage");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Pregunta: " + question);
        System.out.println("- Puntos totales: " + totalPoints);
        System.out.println("- Mensaje esperado: " + expectedMessage);
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Pregunta: " + question);
        System.out.println("- Puntos totales: " + totalPoints);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Crear pregunta con distribución de puntos
        boolean questionCreated = coursePage.createQuestionWithPointDistribution(question, totalPoints);
        assertTrue(questionCreated, "La pregunta con distribución de puntos debería crearse");
        
        // Verificar mensaje de confirmación
        assertTrue(coursePage.getCurrentPageText().contains("question have been updated") ||
                  coursePage.getCurrentPageText().contains("changes") && coursePage.getCurrentPageText().contains("updated"),
                  "Debería mostrar mensaje de actualización de pregunta");
        
        System.out.println("✓ Pregunta con distribución de puntos creada");
        System.out.println("✓ Validación de puntos correcta");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-07 - Validar exceso de caracteres en contenido de pregunta")
    public void testQuestionContentTooLong() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_07_QuestionTooLong");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String questionContent = TestDataReader.getString(testData, "questionContent");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Longitud de pregunta: " + questionContent.length() + " caracteres");
        System.out.println("- Resultado esperado: " + expectedResult);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Verificar límite de caracteres en pregunta
        boolean characterLimitDisplayed = coursePage.testQuestionCharacterLimit(questionContent);
        assertTrue(characterLimitDisplayed, "Debería mostrar contador de caracteres restantes");
        
        System.out.println("✓ Se muestra '0 characters left' al alcanzar el límite");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-08 - Verificar la configuración de opciones de visibilidad")
    public void testVisibilitySettings() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_08_VisibilitySettings");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String expectedMessage = TestDataReader.getString(testData, "expectedMessage");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Mensaje esperado: " + expectedMessage);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Configurar opciones de visibilidad
        boolean visibilityConfigured = coursePage.configureVisibilitySettings();
        assertTrue(visibilityConfigured, "Las opciones de visibilidad deberían configurarse");
        
        // Verificar que se pueden seleccionar las opciones
        assertTrue(coursePage.areVisibilityOptionsAvailable(),
                  "Todas las opciones de visibilidad deberían estar disponibles");
        
        System.out.println("✓ Opciones de visibilidad configuradas correctamente");
        System.out.println("✓ Cambios guardados exitosamente");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-09 - Eliminar lógicamente una sesión de feedback activa")
    public void testDeleteActiveSession() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_09_DeleteSession");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String action = TestDataReader.getString(testData, "action");
        String confirmation = TestDataReader.getString(testData, "confirmation");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Acción: " + action);
        System.out.println("- Confirmación: " + confirmation);
        System.out.println("- Resultado esperado: " + expectedResult);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Eliminar sesión y confirmar
        boolean sessionDeleted = coursePage.deleteSessionWithConfirmation(true);
        assertTrue(sessionDeleted, "La sesión debería eliminarse lógicamente");
        
        // Verificar cambio de estado
        assertTrue(coursePage.isSessionInDeletedState(),
                  "La sesión debería cambiar a estado eliminado");
        
        System.out.println("✓ Sesión cambia de estado activa a eliminada");
        System.out.println("Estado del test: PASO");
    }
      @Test(description = "CP-05-10 - Cancelar eliminación de sesión")
    public void testCancelSessionDeletion() {
        JsonObject testData = TestDataReader.getTestData("session_management_data.json", "CP_05_10_CancelDelete");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String action = TestDataReader.getString(testData, "action");
        String confirmation = TestDataReader.getString(testData, "confirmation");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Acción: " + action);
        System.out.println("- Confirmación: " + confirmation);
        System.out.println("- Resultado esperado: " + expectedResult);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        
        // Intentar eliminar sesión pero cancelar
        boolean sessionPreserved = coursePage.deleteSessionWithConfirmation(false);
        assertTrue(sessionPreserved, "La sesión debería mantenerse activa");
        
        // Verificar que mantiene estado activo
        assertTrue(coursePage.isSessionInActiveState(),
                  "La sesión debería mantener estado activa");
        
        // Verificar que modal se cierra
        assertFalse(coursePage.isDeleteConfirmationModalVisible(),
                   "El modal de confirmación debería cerrarse");
        
        System.out.println("✓ Sesión mantiene estado activa, modal se cierra");
        System.out.println("Estado del test: PASO");
    }
}
