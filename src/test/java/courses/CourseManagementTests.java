package courses;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import base.BaseTest;
import pageobjects.LoginPage;
import pageobjects.InstructorCoursePage;
import utils.TestDataReader;

/**
 * RF-04: Gestión de Cursos
 * Casos de prueba específicos CP-04-XX
 */
public class CourseManagementTests extends BaseTest {
    
    @Test(description = "CP-04-01 - Visualización exitosa del panel de cursos")
    public void testCoursesPanelDisplay() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_01_CoursesPanel");
        
        String testId = TestDataReader.getString(testData, "testId");
        String userType = TestDataReader.getString(testData, "userType");
        String email = TestDataReader.getString(testData, "email");
        String expectedTables = TestDataReader.getString(testData, "expectedTables");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Datos del test:");
        System.out.println("- Tipo de usuario: " + userType);
        System.out.println("- Email: " + email);
        System.out.println("- Tablas esperadas: " + expectedTables);
        
        // Ejecutar test siguiendo los pasos específicos
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Paso 1: Acceder a la página principal de TEAMMATES
        loginPage.navigateToHomePage();
        
        // Paso 2: Hacer clic en "Login" → "Instructor Login"
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
          // Paso 3: Autenticarse como instructor
        // (El login se realiza automáticamente en selectInstructorLogin)
        
        assertTrue(loginPage.isInstructorDashboardDisplayed(), 
            "Debería estar autenticado como instructor");
        
        // Paso 4: Navegar a la sección "Courses"
        coursePage.navigateToCoursesSection();
        
        // Paso 5: Verificar visualización de las tres tablas
        assertTrue(coursePage.isActiveCoursesTableDisplayed(), 
            "Debería mostrar tabla de cursos activos");
        
        assertTrue(coursePage.isArchivedCoursesTableDisplayed(), 
            "Debería mostrar tabla de cursos archivados");
        
        assertTrue(coursePage.isDeletedCoursesTableDisplayed(), 
            "Debería mostrar tabla de cursos eliminados");
        
        System.out.println("✓ Las tres tablas se visualizan correctamente:");
        System.out.println("✓ Active courses");
        System.out.println("✓ Archived courses");
        System.out.println("✓ Deleted courses");
        System.out.println("Estado del test: PASO");
        
        System.out.println("Test completado - " + testId);
    }
    
    @Test(description = "CP-04-02 - Crear curso con datos válidos")
    public void testCreateCourseWithValidData() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_02_CreateCourse");
        
        String testId = TestDataReader.getString(testData, "testId");
        String courseName = TestDataReader.getString(testData, "courseName");
        String courseId = TestDataReader.getString(testData, "courseId");
        String institute = TestDataReader.getString(testData, "institute");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Datos del test:");
        System.out.println("- Nombre del curso: " + courseName);
        System.out.println("- ID del curso: " + courseId);
        System.out.println("- Instituto: " + institute);
        System.out.println("- Resultado esperado: " + expectedResult);
        
        // Login como instructor
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
          // Paso 1: Loguearse como instructor
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        // (El login se realiza automáticamente en selectInstructorLogin)
        
        // Paso 2: Ingresar a cursos
        coursePage.navigateToCoursesSection();
        
        // Paso 3: Presionar el botón "Añadir nuevo curso"
        coursePage.clickAddNewCourseButton();
        
        assertTrue(coursePage.isCreateCourseFormDisplayed(), 
            "Debería mostrar formulario de creación de curso");
        
        // Paso 4: Llenar los campos
        coursePage.fillCourseForm(courseName, courseId, institute);
        
        // Paso 5: Guardar
        coursePage.submitCourseForm();
        
        // Verificar resultado
        assertTrue(coursePage.isCourseCreationSuccessNotificationDisplayed(), 
            "Debería mostrar notificación de éxito");
        
        assertTrue(coursePage.isCourseVisibleInTable(courseName, courseId), 
            "El curso debería ser visible en la tabla de cursos");
        
        String successMessage = coursePage.getCourseCreationSuccessMessage();
        assertTrue(successMessage.contains("created") || successMessage.contains("successfully"), 
            "Debería mostrar mensaje de curso creado exitosamente");
        
        System.out.println("✓ Se visualizó notificación de que el curso fue creado");
        System.out.println("✓ Se puede visualizar el curso en la tabla de cursos");
        System.out.println("✓ Curso: " + courseName + " (" + courseId + ")");
        System.out.println("Estado del test: PASO");
        
        System.out.println("Test completado - " + testId);
    }
}
