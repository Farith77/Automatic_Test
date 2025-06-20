# 🚀 TEAMMATES - Automatización de Pruebas End-to-End

[![Java](https://img.shields.io/badge/Java-11+-orange?style=flat-square&logo=oracle)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=flat-square&logo=apache-maven)](https://maven.apache.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green?style=flat-square&logo=selenium)](https://selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.9.0-red?style=flat-square)](https://testng.org/)

## 📋 Descripción del Proyecto

Este proyecto contiene pruebas automatizadas end-to-end para la aplicación web TEAMMATES, organizadas por requerimientos funcionales específicos. Las pruebas están implementadas usando **Selenium WebDriver**, **TestNG** y **Maven**.

## 🏗️ Arquitectura del Proyecto

```
src/
├── main/java/
│   ├── base/BaseTest.java                    # Configuración base para todos los tests
│   ├── pageobjects/                          # Page Object Pattern
│   │   ├── BasePage.java                     # Métodos base para interacción con elementos
│   │   ├── LoginPage.java                    # RF-02: Funcionalidades de login
│   │   ├── InstructorRequestPage.java        # RF-01: Registro de instructores
│   │   ├── StudentDashboardPage.java         # RF-03: Panel de sesiones estudiante
│   │   └── InstructorCoursePage.java         # RF-04: Gestión de cursos instructor
│   └── utils/                                # Utilidades del framework
│       ├── ConfigReader.java                 # Lector de configuraciones
│       ├── DriverManager.java                # Gestión del navegador
│       └── TestDataReader.java               # Lector de datos de prueba JSON
└── test/
    ├── java/                                 # Clases de prueba organizadas por RF
    │   ├── authentication/                   # RF-01, RF-02
    │   ├── sessions/                         # RF-03
    │   └── courses/                          # RF-04
    └── resources/
        ├── config/test.properties            # Configuración de la aplicación
        ├── testdata/*.json                   # Datos de prueba por requerimiento
        └── testng/testng.xml                 # Configuración de ejecución TestNG
```

## 🎯 Requerimientos Funcionales Automatizados

### **RF-01: Registro de Instructores**
- **Objetivo**: Verificar el proceso de registro de nuevos instructores en TEAMMATES
- **Casos implementados**: 4 casos de prueba

### **RF-02: Login de Usuarios**
- **Objetivo**: Verificar el proceso de autenticación y autorización de usuarios
- **Casos implementados**: 2 casos de prueba

### **RF-03: Panel de Sesiones** 
- **Objetivo**: Verificar funcionalidades del panel de sesiones de feedback para estudiantes
- **Casos implementados**: 4 casos de prueba

### **RF-04: Gestión de Cursos**
- **Objetivo**: Verificar funcionalidades de gestión de cursos para instructores
- **Casos implementados**: 8 casos de prueba

---

## 🔧 Configuración Inicial

### Prerrequisitos
- Java 11 o superior
- Maven 3.6+
- Chrome Browser (última versión)

### Instalación
```bash
# Clonar el repositorio
git clone <repository-url>
cd Automatic_Test

# Compilar el proyecto
mvn clean compile

# Verificar que todo esté configurado correctamente
mvn test-compile
```

### Configuración de Credenciales
Editar `src/test/resources/config/test.properties`:
```properties
# Credenciales de prueba
test.instructor.email=almamanima@unsa.edu.pe   
test.instructor.password=Randiyflaco9517534
test.student.email=almamanima@unsa.edu.pe 
test.student.password=Randiyflaco9517534

# URL de la aplicación
app.url=https://8-0-0-dot-teammates-grasshoppers-testing.uw.r.appspot.com/web/front/home
```

---

## 📝 Casos de Prueba Implementados

## **RF-01: Registro de Instructores**
> **Archivo**: `src/test/java/authentication/InstructorRegistrationTests.java`  
> **Datos**: `src/test/resources/testdata/registration_data.json`

### CP-01-01: Registro exitoso con correo válido
**Descripción**: Verificar registro exitoso de instructor con email institucional válido  
**Flujo**: Home → Become an Instructor → Llenar formulario válido → Submit → Verificar confirmación

```bash
mvn test -Dtest=InstructorRegistrationTests#testValidEmailRegistration
```

### CP-01-02: Registro fallido con correo no autorizado  
**Descripción**: Verificar rechazo de registro con email no institucional (gmail, yahoo, etc.)  
**Flujo**: Home → Become an Instructor → Email no autorizado → Submit → Verificar rechazo

```bash
mvn test -Dtest=InstructorRegistrationTests#testInvalidEmailRegistration
```

### CP-01-03: Registro fallido por campo obligatorio vacío
**Descripción**: Verificar validación de campos requeridos en formulario de registro  
**Flujo**: Home → Become an Instructor → Dejar campo vacío → Submit → Verificar error

```bash
mvn test -Dtest=InstructorRegistrationTests#testEmptyRequiredFieldRegistration
```

### CP-01-04: Registro fallido por nombre demasiado largo
**Descripción**: Verificar validación de longitud máxima en campo nombre  
**Flujo**: Home → Become an Instructor → Nombre muy largo → Submit → Verificar error de longitud

```bash
mvn test -Dtest=InstructorRegistrationTests#testNameTooLongRegistration
```

**Ejecutar todos los casos de RF-01:**
```bash
mvn test -Dtest=InstructorRegistrationTests
```

---

## **RF-02: Login de Usuarios**
> **Archivo**: `src/test/java/authentication/LoginTests.java`  
> **Datos**: `src/test/resources/testdata/login_data.json`

### CP-02-01: Inicio de sesión exitoso con cuenta registrada
**Descripción**: Verificar login exitoso de instructor con cuenta autorizada  
**Flujo**: Home → Login dropdown → Instructor Login → Google OAuth → Verificar acceso dashboard

```bash
mvn test -Dtest=LoginTests#testSuccessfulInstructorLogin
```

### CP-02-02: Denegación de acceso con cuenta no registrada
**Descripción**: Verificar rechazo de acceso para cuentas no autorizadas en TEAMMATES  
**Flujo**: Home → Login dropdown → Instructor Login → Cuenta no registrada → Verificar denegación

```bash
mvn test -Dtest=LoginTests#testUnauthorizedAccountLogin
```

**Ejecutar todos los casos de RF-02:**
```bash
mvn test -Dtest=LoginTests
```

---

## **RF-03: Panel de Sesiones**
> **Archivo**: `src/test/java/sessions/SessionPanelTests.java`  
> **Datos**: `src/test/resources/testdata/session_data.json`

### CP-03-01-01: Visualización de respuestas recibidas
**Descripción**: Verificar que un estudiante puede visualizar las respuestas de feedback recibidas  
**Flujo**: Dashboard estudiante → Localizar sesión → View Responses → Verificar resultados

```bash
mvn test -Dtest=SessionPanelTests#testViewSessionResponses
```

### CP-03-01-02: Sesión sin respuestas visibles al estudiante  
**Descripción**: Verificar manejo de sesiones con configuración de visibilidad restringida  
**Flujo**: Dashboard estudiante → Sesión con restricciones → View Responses → Verificar mensajes de visibilidad

```bash
mvn test -Dtest=SessionPanelTests#testSessionWithoutVisibleResponses
```

### CP-03-02-01: Envío exitoso de feedback con porcentajes válidos
**Descripción**: Verificar envío correcto de feedback con distribución válida de puntos (suma = 400)  
**Flujo**: Edit Submission → Asignar puntos válidos → Submit → Verificar confirmación

```bash
mvn test -Dtest=SessionPanelTests#testSuccessfulFeedbackSubmission
```

### CP-03-02-02: Envío fallido por suma incorrecta de porcentajes
**Descripción**: Verificar rechazo de feedback con distribución inválida de puntos (suma ≠ 400)  
**Flujo**: Edit Submission → Asignar puntos inválidos → Submit → Verificar error

```bash
mvn test -Dtest=SessionPanelTests#testFailedFeedbackSubmissionInvalidSum
```

**Ejecutar todos los casos de RF-03:**
```bash
mvn test -Dtest=SessionPanelTests
```

---

## **RF-04: Gestión de Cursos**
> **Archivo**: `src/test/java/courses/CourseManagementTests.java`  
> **Datos**: `src/test/resources/testdata/course_data.json`

### CP-04-01: Visualización exitosa del panel de cursos
**Descripción**: Verificar que un instructor puede acceder y visualizar las tres tablas de cursos  
**Flujo**: Login instructor → Navegar a Courses → Verificar tablas (Active, Archived, Deleted)

```bash
mvn test -Dtest=CourseManagementTests#testCoursesPanelDisplay
```

### CP-04-02: Crear curso con datos válidos
**Descripción**: Verificar creación exitosa de curso con todos los datos requeridos  
**Flujo**: Add New Course → Llenar formulario → Submit → Verificar notificación y tabla

```bash
mvn test -Dtest=CourseManagementTests#testCreateCourseWithValidData
```

### CP-04-03: Crear curso con ID vacío
**Descripción**: Verificar validación de campo ID obligatorio  
**Flujo**: Add New Course → Dejar ID vacío → Intentar submit → Verificar rechazo

```bash
mvn test -Dtest=CourseManagementTests#testCreateCourseWithEmptyId
```

### CP-04-04: Crear curso con nombre vacío
**Descripción**: Verificar validación de campo nombre obligatorio  
**Flujo**: Add New Course → Dejar nombre vacío → Intentar submit → Verificar rechazo

```bash
mvn test -Dtest=CourseManagementTests#testCreateCourseWithEmptyName
```

### CP-04-05: Crear curso con ID duplicado
**Descripción**: Verificar validación de unicidad del ID de curso  
**Flujo**: Add New Course → ID existente → Submit → Verificar error duplicado

```bash
mvn test -Dtest=CourseManagementTests#testCreateCourseWithDuplicateId
```

### CP-04-01-01: Agregar estudiantes con datos válidos a curso activo
**Descripción**: Verificar adición exitosa de estudiantes con datos completos y válidos  
**Flujo**: Seleccionar curso → Agregar estudiante → Llenar datos → Guardar → Verificar confirmación

```bash
mvn test -Dtest=CourseManagementTests#testAddStudentsWithValidData
```

### CP-04-01-02: Agregar estudiante con email duplicado a curso activo
**Descripción**: Verificar rechazo de estudiantes con email ya existente en el curso  
**Flujo**: Seleccionar curso → Email duplicado → Intentar guardar → Verificar error

```bash
mvn test -Dtest=CourseManagementTests#testAddStudentWithDuplicateEmail
```

### CP-04-01-03: Agregar estudiante con formato incorrecto en correo a curso activo
**Descripción**: Verificar validación de formato de email  
**Flujo**: Seleccionar curso → Email inválido → Intentar guardar → Verificar error formato

```bash
mvn test -Dtest=CourseManagementTests#testAddStudentWithInvalidEmailFormat
```

### CP-04-01-04: Validación de campo obligatorio Team vacío
**Descripción**: Verificar validación de campo Team requerido  
**Flujo**: Seleccionar curso → Dejar Team vacío → Intentar guardar → Verificar error

```bash
mvn test -Dtest=CourseManagementTests#testAddStudentWithEmptyTeam
```

**Ejecutar todos los casos de RF-04:**
```bash
mvn test -Dtest=CourseManagementTests
```

---

## 🚀 Comandos de Ejecución

### **Ejecutar por Categorías**

```bash
# 🎯 Ejecutar TODOS los tests configurados
mvn test

# � Ejecutar solo tests de autenticación (RF-01, RF-02)
mvn test -Dtest=InstructorRegistrationTests,LoginTests

# 📝 Ejecutar solo tests de registro de instructores (RF-01)
mvn test -Dtest=InstructorRegistrationTests

# 🔑 Ejecutar solo tests de login (RF-02)
mvn test -Dtest=LoginTests

# �📊 Ejecutar solo tests de sesiones (RF-03)
mvn test -Dtest=SessionPanelTests

# 🏫 Ejecutar solo tests de cursos (RF-04)  
mvn test -Dtest=CourseManagementTests

# 📋 Ejecutar usando configuración TestNG XML
mvn test -DsuiteXmlFile=src/test/resources/testng/testng.xml
```

### **Ejecutar Casos Específicos por Funcionalidad**

```bash
# 📝 Solo tests de registro exitoso y fallido
mvn test -Dtest=InstructorRegistrationTests#testValidEmailRegistration,InstructorRegistrationTests#testInvalidEmailRegistration

# 🔑 Solo tests de validación de campos en registro
mvn test -Dtest=InstructorRegistrationTests#testEmptyRequiredFieldRegistration,InstructorRegistrationTests#testNameTooLongRegistration

# 🔐 Solo tests de login (ambos escenarios)
mvn test -Dtest=LoginTests#testSuccessfulInstructorLogin,LoginTests#testUnauthorizedAccountLogin

# 📝 Solo tests de creación de cursos
mvn test -Dtest=CourseManagementTests#testCreateCourseWithValidData,CourseManagementTests#testCreateCourseWithEmptyId,CourseManagementTests#testCreateCourseWithEmptyName,CourseManagementTests#testCreateCourseWithDuplicateId

# 👥 Solo tests de gestión de estudiantes  
mvn test -Dtest=CourseManagementTests#testAddStudentsWithValidData,CourseManagementTests#testAddStudentWithDuplicateEmail,CourseManagementTests#testAddStudentWithInvalidEmailFormat,CourseManagementTests#testAddStudentWithEmptyTeam

# 💬 Solo tests de feedback
mvn test -Dtest=SessionPanelTests#testSuccessfulFeedbackSubmission,SessionPanelTests#testFailedFeedbackSubmissionInvalidSum

# 👀 Solo tests de visualización  
mvn test -Dtest=SessionPanelTests#testViewSessionResponses,SessionPanelTests#testSessionWithoutVisibleResponses
```

### **Comandos de Desarrollo y Debug**

```bash
# 🔧 Compilar sin ejecutar tests
mvn clean compile

# 🔍 Ejecutar con información detallada
mvn test -Dtest=CourseManagementTests -X

# 📊 Generar reportes detallados
mvn test && echo "Ver reportes en: target/surefire-reports/"

# 🎭 Ejecutar en modo headless (sin ventana del navegador)
# Editar test.properties: headless=true
mvn test
```

---

## 📊 Estructura de Datos de Prueba

### **Datos de Registro** (`registration_data.json`)
```json
{
  "CP_01_01_ValidEmail": {
    "testId": "CP-01-01",
    "fullName": "Andre Hilacondo Begazo",
    "institution": "Universidad Nacional de San Agustín de Arequipa",
    "country": "Perú",
    "email": "juan@unsa.edu.pe",
    "expectedMessage": "Solicitud recibida. Se enviará confirmación en 24 horas"
  },
  "CP_01_02_InvalidEmail": {
    "testId": "CP-01-02",
    "email": "jose@gmail.com",
    "expectedMessage": "Correo no autorizado para registro"
  }
}
```

### **Datos de Login** (`login_data.json`)
```json
{
  "CP_02_01_ValidInstructor": {
    "testId": "CP-02-01",
    "userType": "Instructor",
    "email": "ahilacondo@unsa.edu.pe",
    "expectedResult": "Acceso exitoso al panel principal"
  },
  "CP_02_02_UnauthorizedAccount": {
    "testId": "CP-02-02", 
    "email": "ehilacondob@unsa.edu.pe",
    "expectedMessage": "Cuenta no autorizada"
  }
}
```

### **Datos de Sesiones** (`session_data.json`)
```json
{
  "CP_03_01_01_ViewResponses": {
    "testId": "CP-03-01-01",
    "sessionName": "First team feedback session (percentage-based)",
    "expectedContent": "Feedback Session Results with course information"
  },
  "CP_03_02_01_ValidPercentages": {
    "testId": "CP-03-02-01", 
    "expectedTotal": 400,
    "studentPoints": {
      "Andre (Student)": 100,
      "Charlie Davis (Student)": 100,
      "Francis Gabriel (Student)": 100,
      "Gene Hudson (Student)": 100
    }
  }
}
```

### **Datos de Cursos** (`course_data.json`)
```json
{
  "CP_04_02_CreateCourse": {
    "testId": "CP-04-02",
    "courseName": "Automatización de Pruebas",
    "courseId": "AUTO-TEST-2025", 
    "institute": "Universidad Nacional de San Agustín de Arequipa"
  },
  "CP_04_03_EmptyId": {
    "testId": "CP-04-03",
    "courseName": "Curso Test",
    "courseId": "",
    "expectedError": "Course ID is required"
  }
}
```

---

## 🔧 Configuración Avanzada

### **Personalizar Configuración**
Editar `src/test/resources/config/test.properties`:

```properties
# Cambiar navegador
browser=chrome          # chrome, firefox, edge

# Modo headless (sin interfaz gráfica)
headless=true           # true, false

# Timeouts personalizados
browser.timeout=45      # segundos
implicit.wait=15        # segundos
explicit.wait=30        # segundos

# Debug y reportes
debug.mode=true         # más información en logs
screenshot.on.failure=true  # capturas en fallos
```

### **Personalizar Datos de Prueba**
Los archivos JSON en `src/test/resources/testdata/` pueden ser modificados para probar diferentes escenarios:

- `registration_data.json` - Datos para tests de registro de instructores (RF-01)
- `login_data.json` - Datos para tests de autenticación (RF-02)
- `session_data.json` - Datos para tests de sesiones (RF-03)
- `course_data.json` - Datos para tests de cursos (RF-04)

---

## 📈 Interpretación de Resultados

### **Resultados Exitosos**
```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### **Fallos de Prueba**
```
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
[ERROR] CourseManagementTests.testCreateCourseWithValidData:116 
        Debería mostrar notificación de éxito expected [true] but found [false]
```

### **Ubicación de Reportes**
- **Reportes HTML**: `target/surefire-reports/index.html`
- **Logs detallados**: Consola durante ejecución
- **Screenshots**: `test-output/` (si está habilitado)

---

## 🔍 Troubleshooting

### **Problemas Comunes**

1. **Error de compilación**
   ```bash
   mvn clean compile
   ```

2. **Browser no inicia**
   - Verificar que Chrome esté actualizado
   - Cambiar `headless=false` en test.properties

3. **Credenciales inválidas**
   - Verificar test.properties
   - Confirmar que las cuentas estén activas en TEAMMATES

4. **Tests lentos**
   - Reducir timeouts en test.properties
   - Usar `headless=true` para mayor velocidad

5. **Elementos no encontrados**
   - Verificar que la aplicación esté disponible
   - Revisar logs para selectores fallidos

---

## 📞 Soporte

Para reportar problemas o solicitar nuevas funcionalidades:

1. Revisar logs en consola para errores específicos
2. Verificar configuración en `test.properties`
3. Confirmar que la aplicación TEAMMATES esté accesible
4. Revisar datos de prueba en archivos JSON

---

## 📋 Checklist de Ejecución

Antes de ejecutar los tests:

- [ ] Java 11+ instalado
- [ ] Maven configurado
- [ ] Chrome browser actualizado  
- [ ] Credenciales válidas en test.properties
- [ ] URL de TEAMMATES accesible
- [ ] Proyecto compilado (`mvn clean compile`)

## 🎯 Resumen Completo de Casos de Prueba

### **RF-01: Registro de Instructores** (4 casos)
- CP-01-01: Registro exitoso con correo válido ✅
- CP-01-02: Registro fallido con correo no autorizado ✅  
- CP-01-03: Registro fallido por campo obligatorio vacío ✅
- CP-01-04: Registro fallido por nombre demasiado largo ✅

### **RF-02: Login de Usuarios** (2 casos)
- CP-02-01: Inicio de sesión exitoso con cuenta registrada ✅
- CP-02-02: Denegación de acceso con cuenta no registrada ✅

### **RF-03: Panel de Sesiones** (4 casos)
- CP-03-01-01: Visualización de respuestas recibidas ✅
- CP-03-01-02: Sesión sin respuestas visibles al estudiante ✅
- CP-03-02-01: Envío exitoso de feedback con porcentajes válidos ✅
- CP-03-02-02: Envío fallido por suma incorrecta de porcentajes ✅

### **RF-04: Gestión de Cursos** (8 casos)
- CP-04-01: Visualización exitosa del panel de cursos ✅
- CP-04-02: Crear curso con datos válidos ✅
- CP-04-03: Crear curso con ID vacío ✅
- CP-04-04: Crear curso con nombre vacío ✅
- CP-04-05: Crear curso con ID duplicado ✅
- CP-04-01-01: Agregar estudiantes con datos válidos ✅
- CP-04-01-02: Agregar estudiante con email duplicado ✅
- CP-04-01-03: Agregar estudiante con formato incorrecto en correo ✅
- CP-04-01-04: Validación de campo obligatorio Team vacío ✅

**Total: 18 casos de prueba automatizados** 🎉

### **Comandos Rápidos de Validación**

```bash
# Verificar RF-01 (Registro)
mvn test -Dtest=InstructorRegistrationTests

# Verificar RF-02 (Login)  
mvn test -Dtest=LoginTests

# Verificar RF-03 (Sesiones)
mvn test -Dtest=SessionPanelTests

# Verificar RF-04 (Cursos)
mvn test -Dtest=CourseManagementTests

# Ejecutar TODOS los tests
mvn test
```

¡Listo para ejecutar! 🚀
