package courses;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
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
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_01_CoursesPanelDisplay");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        
        // Ejecutar test siguiendo los pasos específicos
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Paso 1: Acceder a la página principal de TEAMMATES
        loginPage.navigateToHomePage();
        
        // Paso 2: Hacer clic en "Login" → "Instructor Login"
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        
        // Paso 3: Autenticarse como instructor
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
    }
    
    @Test(description = "CP-04-02 - Crear curso con datos válidos")
    public void testCreateCourseWithValidData() {
        // Obtener datos de prueba del JSON
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_02_CreateCourse");
        
        String testId = TestDataReader.getString(testData, "testId");
        String courseName = TestDataReader.getString(testData, "courseName");
        String courseId = TestDataReader.getString(testData, "courseId");
        String institute = TestDataReader.getString(testData, "institute");
        String expectedMessage = TestDataReader.getString(testData, "expectedMessage");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Datos del test:");
        System.out.println("- Nombre del curso: " + courseName);
        System.out.println("- ID del curso: " + courseId);
        System.out.println("- Instituto: " + institute);
        System.out.println("- Resultado esperado: " + expectedMessage);
        
        // Login como instructor
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Paso 1: Loguearse como instructor
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        
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
    }

    @Test(description = "CP-04-03 - Crear curso con ID vacío")
    public void testCreateCourseWithEmptyId() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_03_EmptyId");
        
        String testId = TestDataReader.getString(testData, "testId");
        String courseName = TestDataReader.getString(testData, "courseName");
        String courseId = TestDataReader.getString(testData, "courseId");
        String institute = TestDataReader.getString(testData, "institute");
        String expectedError = TestDataReader.getString(testData, "expectedError");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: Crear curso con ID vacío");
        System.out.println("Resultado esperado: " + expectedError);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.clickAddNewCourseButton();
        
        // Llenar formulario con ID vacío
        coursePage.fillCourseForm(courseName, courseId, institute);
        
        // Verificar que el botón no se habilita con ID vacío
        assertFalse(coursePage.isAddCourseButtonEnabled(), 
            "El botón 'Add Course' no debería estar habilitado con ID vacío");
        
        System.out.println("✓ El botón 'Add Course' permanece deshabilitado con ID vacío");
        System.out.println("Estado del test: PASO");
    }

    @Test(description = "CP-04-04 - Crear curso con nombre vacío")
    public void testCreateCourseWithEmptyName() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_04_EmptyName");
        
        String testId = TestDataReader.getString(testData, "testId");
        String courseName = TestDataReader.getString(testData, "courseName");
        String courseId = TestDataReader.getString(testData, "courseId");
        String institute = TestDataReader.getString(testData, "institute");
        String expectedError = TestDataReader.getString(testData, "expectedError");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: Crear curso con nombre vacío");
        System.out.println("Resultado esperado: " + expectedError);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.clickAddNewCourseButton();
        
        // Llenar formulario con nombre vacío
        coursePage.fillCourseForm(courseName, courseId, institute);
        
        // Verificar que el botón no se habilita con nombre vacío
        assertFalse(coursePage.isAddCourseButtonEnabled(), 
            "El botón 'Add Course' no debería estar habilitado con nombre vacío");
        
        System.out.println("✓ El botón 'Add Course' permanece deshabilitado con nombre vacío");
        System.out.println("Estado del test: PASO");
    }

    @Test(description = "CP-04-05 - Crear curso con ID duplicado")
    public void testCreateCourseWithDuplicateId() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_05_DuplicateId");
        
        String testId = TestDataReader.getString(testData, "testId");
        String courseName = TestDataReader.getString(testData, "courseName");
        String courseId = TestDataReader.getString(testData, "courseId");
        String institute = TestDataReader.getString(testData, "institute");
        String expectedError = TestDataReader.getString(testData, "expectedError");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: Crear curso con ID duplicado");
        System.out.println("Resultado esperado: " + expectedError);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.clickAddNewCourseButton();
        
        // Intentar crear curso con ID duplicado
        coursePage.fillCourseForm(courseName, courseId, institute);
        coursePage.submitCourseForm();
        
        // Verificar mensaje de error por ID duplicado
        assertTrue(coursePage.isDuplicateIdErrorDisplayed(), 
            "Debería mostrar error por ID duplicado");
        
        String errorMessage = coursePage.getDuplicateIdErrorMessage();
        assertTrue(errorMessage.contains("duplicate") || errorMessage.contains("already exists"), 
            "Debería mostrar mensaje indicando que el ID ya existe");
        
        System.out.println("✓ Se muestra notificación indicando que no se puede repetir el ID del curso");
        System.out.println("Estado del test: PASO");
    }

    @Test(description = "CP-04-01-01 - Agregar estudiantes con datos válidos a curso activo")
    public void testAddValidStudentsToActiveCourse() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_01_01_ValidStudents");
          String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        JsonArray students = testData.getAsJsonArray("students");
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
        
        // Seleccionar un curso activo para agregar estudiantes
        coursePage.selectFirstActiveCourse();
        coursePage.navigateToStudentsTab();
        
        // Agregar estudiantes
        for (int i = 0; i < students.size(); i++) {
            JsonObject student = students.get(i).getAsJsonObject();
            String name = TestDataReader.getString(student, "name");
            String email = TestDataReader.getString(student, "email");
            String team = TestDataReader.getString(student, "team");
            String section = TestDataReader.getString(student, "section");
            
            coursePage.addStudent(name, email, team, section);
        }
        
        // Verificar confirmación
        assertTrue(coursePage.isStudentAddedSuccessfully(), 
            "Debería mostrar mensaje de confirmación");
        
        System.out.println("✓ Estudiantes agregados exitosamente");
        System.out.println("Estado del test: PASO");
    }    @Test(description = "CP-04-01-02 - Agregar estudiante con email duplicado")
    public void testAddStudentWithDuplicateEmail() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_01_02_DuplicateEmail");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        JsonArray students = testData.getAsJsonArray("students");
        String expectedError = TestDataReader.getString(testData, "expectedError");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Error esperado: " + expectedError);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        coursePage.navigateToStudentsTab();
        
        // Intentar agregar estudiante con email duplicado
        JsonObject student = students.get(0).getAsJsonObject();
        String name = TestDataReader.getString(student, "name");
        String email = TestDataReader.getString(student, "email");
        String team = TestDataReader.getString(student, "team");
        String section = TestDataReader.getString(student, "section");
        
        coursePage.addStudent(name, email, team, section);
        
        // Verificar error por email duplicado
        assertTrue(coursePage.isDuplicateEmailErrorDisplayed(), 
            "Debería mostrar error por email duplicado");
        
        System.out.println("✓ Se muestra error por emails duplicados");
        System.out.println("Estado del test: PASO");
    }

    @Test(description = "CP-04-01-03 - Agregar estudiante con formato incorrecto en correo")    public void testAddStudentWithInvalidEmail() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_01_03_InvalidEmail");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        JsonArray students = testData.getAsJsonArray("students");
        String expectedError = TestDataReader.getString(testData, "expectedError");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Error esperado: " + expectedError);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        coursePage.navigateToStudentsTab();
        
        // Intentar agregar estudiante con email inválido
        JsonObject student = students.get(0).getAsJsonObject();
        String name = TestDataReader.getString(student, "name");
        String email = TestDataReader.getString(student, "email");
        String team = TestDataReader.getString(student, "team");
        String section = TestDataReader.getString(student, "section");
        
        coursePage.addStudent(name, email, team, section);
        
        // Verificar error por formato de email inválido
        assertTrue(coursePage.isInvalidEmailErrorDisplayed(), 
            "Debería mostrar error por formato de email inválido");
        
        System.out.println("✓ Se muestra mensaje indicando el error de formato de email");
        System.out.println("Estado del test: PASO");
    }

    @Test(description = "CP-04-01-04 - Validación de campo obligatorio Team vacío")    public void testAddStudentWithEmptyTeam() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_01_04_EmptyTeam");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        JsonArray students = testData.getAsJsonArray("students");
        String expectedError = TestDataReader.getString(testData, "expectedError");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Error esperado: " + expectedError);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        loginPage.navigateToHomePage();
        loginPage.clickLoginDropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        coursePage.selectFirstActiveCourse();
        coursePage.navigateToStudentsTab();
        
        // Intentar agregar estudiante con team vacío
        JsonObject student = students.get(0).getAsJsonObject();
        String name = TestDataReader.getString(student, "name");
        String email = TestDataReader.getString(student, "email");
        String team = TestDataReader.getString(student, "team");
        String section = TestDataReader.getString(student, "section");
        
        coursePage.addStudent(name, email, team, section);
        
        // Verificar error por campo team vacío
        assertTrue(coursePage.isEmptyTeamErrorDisplayed(), 
            "Debería mostrar error por campo Team vacío");
        
        System.out.println("✓ Se muestra mensaje indicando el error de campo Team obligatorio");
        System.out.println("Estado del test: PASO");
    }    @Test(description = "CP-04-04-02 - Curso ya archivado no puede archivarse")
    public void testCannotArchiveAlreadyArchivedCourse() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_04_02_ArchiveArchived");
        
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
        
        // Verificar que cursos archivados no tienen opción de archivar
        assertTrue(coursePage.isArchivedCoursesTableDisplayed(), 
            "Debería mostrar tabla de cursos archivados");
        
        assertFalse(coursePage.isArchiveOptionAvailableForArchivedCourses(), 
            "La opción Archivar no debería estar disponible para cursos ya archivados");
        
        System.out.println("✓ La opción Archivar no está disponible para cursos archivados");
        System.out.println("Estado del test: PASO");
    }
    @Test(description = "CP-04-05-01 - Eliminación lógica exitosa de curso activo")
    public void testDeleteActiveCourse() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_05_01_DeleteActive");
        
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
        
        // Obtener información del primer curso activo antes de eliminarlo
        String courseToDelete = coursePage.getFirstActiveCourseInfo();
        
        // Eliminar curso activo
        coursePage.deleteFirstActiveCourse();
        coursePage.confirmDeleteAction();
        
        // Verificar que el curso desapareció de activos
        assertFalse(coursePage.isCourseInActiveTable(courseToDelete), 
            "El curso no debería estar en la tabla de cursos activos");
        
        // Verificar que el curso apareció en eliminados
        assertTrue(coursePage.isCourseInDeletedTable(courseToDelete), 
            "El curso debería aparecer en la tabla de cursos eliminados");
        
        System.out.println("✓ Curso desaparece de Cursos activos y aparece en Cursos eliminados");
        System.out.println("Estado del test: PASO");
    }    @Test(description = "CP-04-06-01 - Desarchivado exitoso de un curso archivado")
    public void testUnarchiveCourse() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_06_01_UnarchiveCourse");
        
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
        
        // Obtener información del primer curso archivado
        String courseToUnarchive = coursePage.getFirstArchivedCourseInfo();
        
        // Desarchivar curso
        coursePage.unarchiveFirstArchivedCourse();
        coursePage.confirmUnarchiveAction();
        
        // Verificar que el curso desapareció de archivados
        assertFalse(coursePage.isCourseInArchivedTable(courseToUnarchive), 
            "El curso no debería estar en la tabla de cursos archivados");
        
        // Verificar que el curso apareció en activos
        assertTrue(coursePage.isCourseInActiveTable(courseToUnarchive), 
            "El curso debería aparecer en la tabla de cursos activos");
        
        System.out.println("✓ Curso desaparece de Archivados y aparece en Cursos activos");
        System.out.println("Estado del test: PASO");
    }    public void testRestoreDeletedCourse() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_07_01_RestoreCourse");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Resultado esperado: " + expectedResult);
          LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegaciónropdown();
        loginPage.selectInstructorLogin();
        coursePage.navigateToCoursesSection();
        
        // Obtener información del primer curso eliminado
        String courseToRestore = coursePage.getFirstDeletedCourseInfo();
        
        // Restaurar curso eliminado
        coursePage.restoreFirstDeletedCourse();
        coursePage.confirmRestoreAction();
        
        // Verificar que el curso desapareció de eliminados
        assertFalse(coursePage.isCourseInDeletedTable(courseToRestore), 
            "El curso no debería estar en la tabla de cursos eliminados");
        
        // Verificar que el curso apareció en su estado anterior (activos)
        assertTrue(coursePage.isCourseInActiveTable(courseToRestore), 
            "El curso debería aparecer en la tabla de cursos activos");
        
        System.out.println("✓ Curso desaparece de papelera y reaparece en estado anterior");
        System.out.println("Estado del test: PASO");
    }    public void testPermanentlyDeleteCourse() {
        JsonObject testData = TestDataReader.getTestData("course_data.json", "CP_04_07_02_PermanentDelete");
        
        String testId = TestDataReader.getString(testData, "testId");
        String description = TestDataReader.getString(testData, "description");
        String expectedResult = TestDataReader.getString(testData, "expectedResult");
        
        System.out.println("=== Ejecutando " + testId + " ===");
          System.out.println("=== Ejecutando " + testId + " ===");
        System.out.println("Descripción: " + description);
        System.out.println("- Resultado esperado: " + expectedResult);
        
        LoginPage loginPage = new LoginPage();
        InstructorCoursePage coursePage = new InstructorCoursePage();
        
        // Login y navegación
        String courseToPermanentlyDelete = coursePage.getFirstDeletedCourseInfo();
        
        // Eliminar permanentemente
        coursePage.permanentlyDeleteFirstDeletedCourse();
        coursePage.confirmPermanentDeleteAction();
        
        // Verificar que el curso desapareció completamente
        assertFalse(coursePage.isCourseInDeletedTable(courseToPermanentlyDelete), 
            "El curso no debería estar en la tabla de cursos eliminados");
        
        assertFalse(coursePage.isCourseInActiveTable(courseToPermanentlyDelete), 
            "El curso no debería estar en la tabla de cursos activos");
        
        assertFalse(coursePage.isCourseInArchivedTable(courseToPermanentlyDelete), 
            "El curso no debería estar en la tabla de cursos archivados");
        
        System.out.println("✓ Curso desaparece de todo el sistema");
        System.out.println("Estado del test: PASO");
    }
}
