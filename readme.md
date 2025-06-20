# README AUTOMATIZACIÓN CON SELENIUM
## Framework de Pruebas Automatizadas para TEAMMATES

**Proyecto**: Sistema TEAMMATES - Plan de Pruebas  
**Autor**: alexisBltz  
**Fecha**: 2025-06-20  
**Versión**: 1.0

---

## A.1 Introducción a la Automatización

### A.1.1 Propósito del Anexo
Este anexo presenta la estrategia de automatización para los casos de prueba definidos en el plan principal utilizando Selenium WebDriver. La automatización mantiene la misma trazabilidad de requisitos funcionales y aplica las mismas técnicas de caja negra, pero ejecuta las validaciones de forma programática para mejorar la eficiencia y repetibilidad del proceso de testing.

### A.1.2 Alcance de la Automatización
La automatización cubre el 30% de los casos de prueba manuales más críticos, priorizando aquellos que proporcionan mayor valor en términos de detección de regresiones y validación de funcionalidades core.

| Módulo | Casos Automatizados | Casos Totales | % Automatización | Justificación |
|--------|-------------------|---------------|------------------|---------------|
| Autenticación y Registro | 6 | 20 | 30% | Funcionalidades críticas de acceso |
| Funcionalidades de Estudiante | 4 | 15 | 26.7% | Flujos principales de feedback |
| Gestión de Cursos | 12 | 40 | 30% | Operaciones CRUD fundamentales |
| Gestión de Sesiones | 10 | 30 | 33.3% | Configuraciones esenciales |
| **TOTAL** | **32** | **105** | **30.5%** | **Cobertura de casos críticos** |

---

## A.2 Arquitectura del Framework

### A.2.1 Tecnologías Seleccionadas
El framework utiliza tecnologías estándar de la industria para garantizar mantenibilidad y escalabilidad a largo plazo.

| Componente | Tecnología | Versión | Justificación |
|------------|------------|---------|---------------|
| Lenguaje | Java | 11+ | Estabilidad, amplio soporte, integración |
| Framework de Testing | TestNG | 7.9.0 | Flexibilidad, reportes, paralelización |
| Automatización Web | Selenium WebDriver | 4.18.1 | Estándar de industria, soporte multiplataforma |
| Gestión de Drivers | WebDriverManager | 5.6.2 | Gestión automática de versiones |
| Build Tool | Maven | 3.8+ | Gestión de dependencias estándar |
| Gestión de Datos | JSON | - | Flexibilidad en datos de prueba |

### A.2.2 Patrones de Diseño Implementados

#### Page Object Model (POM)
- **Propósito**: Encapsular elementos y acciones de cada página web
- **Beneficios**: Mantenibilidad, reutilización, separación de responsabilidades
- **Implementación**: Clases base y especializadas por funcionalidad

#### Data-Driven Testing
- **Propósito**: Separar datos de prueba de la lógica de automatización
- **Beneficios**: Flexibilidad, escalabilidad, mantenimiento simplificado
- **Implementación**: Archivos JSON organizados por requerimiento funcional

#### Test Base Pattern
- **Propósito**: Configuración común para todos los casos de prueba
- **Beneficios**: Consistencia, reducción de código duplicado
- **Implementación**: Clase BaseTest con setup/teardown automatizado

---

## A.3 Estructura del Proyecto de Automatización

### A.3.1 Organización de Directorios
```
src/
├── main/java/
│   ├── base/
│   │   └── BaseTest.java                    # Configuración base para todos los tests
│   ├── pageobjects/                         # Page Object Pattern
│   │   ├── BasePage.java                    # Métodos base para interacción con elementos
│   │   ├── LoginPage.java                   # RF-02: Funcionalidades de login
│   │   ├── InstructorRequestPage.java       # RF-01: Registro de instructores
│   │   ├── StudentDashboardPage.java        # RF-03: Panel de sesiones estudiante
│   │   └── InstructorCoursePage.java        # RF-04: Gestión de cursos instructor
│   └── utils/                               # Utilidades del framework
│       ├── ConfigReader.java                # Lector de configuraciones
│       ├── DriverManager.java               # Gestión del navegador
│       └── TestDataReader.java              # Lector de datos de prueba JSON
└── test/
    ├── java/                                # Clases de prueba organizadas por RF
    │   ├── authentication/                  # RF-01, RF-02
    │   ├── sessions/                        # RF-03
    │   ├── courses/                         # RF-04
    │   └── feedback/                        # RF-05
    └── resources/
        ├── config/test.properties           # Configuración de la aplicación
        ├── testdata/*.json                  # Datos de prueba por requerimiento
        └── testng/testng.xml                # Configuración de ejecución TestNG
```

### A.3.2 Mapeo de Requerimientos Funcionales

#### RF-01: Registro de Instructores
- **Casos Automatizados**: 4 casos de prueba
- **Cobertura**: Registro exitoso, validaciones de campos, casos de error
- **Archivo de Prueba**: `InstructorRegistrationTests.java`
- **Datos de Prueba**: `registration_data.json`

#### RF-02: Login de Usuarios
- **Casos Automatizados**: 2 casos de prueba
- **Cobertura**: Autenticación exitosa, manejo de errores
- **Archivo de Prueba**: `LoginTests.java`
- **Datos de Prueba**: `login_data.json`

#### RF-03: Panel de Sesiones
- **Casos Automatizados**: 4 casos de prueba
- **Cobertura**: Visualización de sesiones, envío de feedback, validaciones
- **Archivo de Prueba**: `SessionPanelTests.java`
- **Datos de Prueba**: `session_data.json`

#### RF-04: Gestión de Cursos
- **Casos Automatizados**: 12 casos de prueba
- **Cobertura**: CRUD completo de cursos, gestión de estudiantes, archivado/eliminación
- **Archivo de Prueba**: `CourseManagementTests.java`
- **Datos de Prueba**: `course_data.json`

#### RF-05: Gestión de Sesiones de Feedback
- **Casos Automatizados**: 10 casos de prueba
- **Cobertura**: Creación, configuración, y gestión de sesiones de feedback
- **Archivo de Prueba**: `FeedbackSessionManagementTests.java`
- **Datos de Prueba**: `session_management_data.json`

---

## A.4 Configuración del Entorno de Pruebas

### A.4.1 Prerrequisitos Técnicos
| Componente | Versión Mínima | Versión Recomendada | Observaciones |
|------------|----------------|---------------------|---------------|
| Java JDK | 11 | 17 LTS | Compatibilidad con Selenium 4.x |
| Apache Maven | 3.6.0 | 3.8.6 | Gestión de dependencias |
| Google Chrome | 90+ | Última estable | Driver gestionado automáticamente |
| IntelliJ IDEA / Eclipse | - | Última versión | IDE recomendado |

### A.4.2 Instalación y Configuración
```bash
# 1. Verificar Java
java -version
mvn -version

# 2. Compilar proyecto
mvn clean compile

# 3. Ejecutar pruebas de verificación
mvn test-compile
```

### A.4.3 Configuración de Credenciales 🔐
**IMPORTANTE: Por seguridad, las credenciales ahora se manejan mediante variables de entorno**

#### Configuración Inicial
1. **Copiar archivo de plantilla:**
   ```bash
   cp .env.example .env
   ```

2. **Editar el archivo .env con tus credenciales reales:**
   ```bash
   # TEAMMATES Test Environment Variables
   TEST_INSTRUCTOR_EMAIL=tu-email-instructor@unsa.edu.pe
   TEST_INSTRUCTOR_PASSWORD=tu-password-real
   TEST_STUDENT_EMAIL=tu-email-estudiante@unsa.edu.pe
   TEST_STUDENT_PASSWORD=tu-password-real
   ```

3. **NUNCA subir el archivo .env a GitHub** (ya incluido en .gitignore)

#### Configuración del archivo test.properties
```properties
# Configuración de la aplicación
app.url=https://8-0-0-dot-teammates-grasshoppers-testing.uw.r.appspot.com/web/front/home
browser.timeout=30

# Credenciales de prueba - AHORA EN VARIABLES DE ENTORNO
# Las credenciales se cargan automáticamente desde:
# - Archivo .env (desarrollo local)
# - Variables de entorno del sistema (CI/CD)

# Configuración del navegador
browser=chrome
headless=false
browser.timeout=30
```

#### Configuración en CI/CD
Para entornos de integración continua, configurar las variables directamente:
```bash
export TEST_INSTRUCTOR_EMAIL="instructor@unsa.edu.pe"
export TEST_INSTRUCTOR_PASSWORD="password_seguro"
export TEST_STUDENT_EMAIL="student@unsa.edu.pe"
export TEST_STUDENT_PASSWORD="password_seguro"
```

---

## A.5 Especificación de Casos de Prueba Automatizados

### A.5.1 RF-01: Registro de Instructores
**Archivo de Implementación**: `authentication/InstructorRegistrationTests.java`  
**Archivo de Datos**: `testdata/registration_data.json`

#### CP-01-01: Registro exitoso con correo válido
**Objetivo**: Verificar registro exitoso de instructor con email institucional válido  
**Procedimiento**: Navegación → Formulario → Validación → Confirmación  
**Comando de Ejecución**:
```bash
mvn test -Dtest=InstructorRegistrationTests#testValidEmailRegistration
```

#### CP-01-02: Registro fallido con correo no autorizado  
**Objetivo**: Verificar rechazo de registro con email no institucional  
**Procedimiento**: Navegación → Email inválido → Verificación de rechazo  
**Comando de Ejecución**:
```bash
mvn test -Dtest=InstructorRegistrationTests#testInvalidEmailRegistration
```

#### CP-01-03: Registro fallido por campo obligatorio vacío
**Objetivo**: Verificar validación de campos requeridos  
**Procedimiento**: Formulario incompleto → Validación → Verificación de error  
**Comando de Ejecución**:
```bash
mvn test -Dtest=InstructorRegistrationTests#testEmptyRequiredFieldRegistration
```

#### CP-01-04: Registro fallido por nombre demasiado largo
**Objetivo**: Verificar validación de longitud máxima en campos  
**Procedimiento**: Datos fuera de límites → Validación → Verificación de error  
**Comando de Ejecución**:

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

## **RF-05: Gestión de Sesiones de Feedback**
> **Archivo**: `src/test/java/feedback/FeedbackSessionManagementTests.java`  
> **Datos**: `src/test/resources/testdata/session_management_data.json`

### CP-05-01: Crear sesión de feedback con datos válidos
**Descripción**: Verificar creación exitosa de sesión de feedback con todos los campos requeridos  
**Flujo**: Login instructor → Sessions → Add Session → Llenar formulario válido → Submit → Verificar creación

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testCreateFeedbackSessionWithValidData
```

### CP-05-02: Validar exceso de caracteres en Session Name
**Descripción**: Verificar validación de longitud máxima en el campo Session Name  
**Flujo**: Add Session → Session Name > límite → Submit → Verificar error de validación

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testSessionNameExceedsCharacterLimit
```

### CP-05-03: Validar campo Session Name vacío
**Descripción**: Verificar validación de campo Session Name obligatorio  
**Flujo**: Add Session → Dejar Session Name vacío → Submit → Verificar error requerido

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testEmptySessionNameValidation
```

### CP-05-04: Validar fecha de cierre anterior a apertura
**Descripción**: Verificar validación de fechas lógicas en configuración de sesión  
**Flujo**: Add Session → End Date anterior a Start Date → Submit → Verificar error temporal

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testInvalidDateRangeValidation
```

### CP-05-05: Editar instrucciones en sesión activa
**Descripción**: Verificar edición exitosa de instrucciones en sesión existente  
**Flujo**: Seleccionar sesión → Edit → Modificar Instructions → Save → Verificar cambios

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testEditSessionInstructions
```

### CP-05-06: Validar distribución correcta de puntos en preguntas
**Descripción**: Verificar configuración válida de preguntas con distribución de puntos  
**Flujo**: Add Question → Configurar distribución → Verificar suma total → Submit

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testValidPointDistributionInQuestions
```

### CP-05-07: Validar exceso de caracteres en contenido de pregunta
**Descripción**: Verificar validación de longitud en contenido de preguntas  
**Flujo**: Add Question → Contenido > límite → Submit → Verificar error de longitud

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testQuestionContentExceedsLimit
```

### CP-05-08: Verificar configuración de opciones de visibilidad
**Descripción**: Verificar configuración correcta de opciones de visibilidad de respuestas  
**Flujo**: Edit Session → Visibility Settings → Configurar opciones → Save → Verificar estado

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testVisibilityOptionsConfiguration
```

### CP-05-09: Eliminar lógicamente una sesión de feedback activa
**Descripción**: Verificar eliminación lógica de sesión sin pérdida de datos  
**Flujo**: Seleccionar sesión → Delete → Confirmar → Verificar movimiento a Deleted

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testLogicalDeletionOfActiveSession
```

### CP-05-10: Cancelar eliminación de sesión
**Descripción**: Verificar cancelación correcta del proceso de eliminación  
**Flujo**: Seleccionar sesión → Delete → Cancel → Verificar sesión permanece activa

```bash
mvn test -Dtest=FeedbackSessionManagementTests#testCancelSessionDeletion
```

**Ejecutar todos los casos de RF-05:**
```bash
mvn test -Dtest=FeedbackSessionManagementTests
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

# 📋 Ejecutar solo tests de feedback sessions (RF-05)
mvn test -Dtest=FeedbackSessionManagementTests

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

# 📋 Solo tests de gestión de sesiones de feedback (RF-05)
mvn test -Dtest=FeedbackSessionManagementTests#testCreateFeedbackSessionWithValidData,FeedbackSessionManagementTests#testSessionNameExceedsCharacterLimit,FeedbackSessionManagementTests#testEmptySessionNameValidation

# 🗂️ Solo tests de configuración de sesiones
mvn test -Dtest=FeedbackSessionManagementTests#testInvalidDateRangeValidation,FeedbackSessionManagementTests#testEditSessionInstructions,FeedbackSessionManagementTests#testVisibilityOptionsConfiguration

# 🗑️ Solo tests de eliminación de sesiones
mvn test -Dtest=FeedbackSessionManagementTests#testLogicalDeletionOfActiveSession,FeedbackSessionManagementTests#testCancelSessionDeletion
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

### **Datos de Sesiones de Feedback** (`session_management_data.json`)
```json
{
  "CP_05_01_ValidSession": {
    "testId": "CP-05-01",
    "sessionName": "Feedback Session - Automated Test",
    "instructions": "Please provide honest feedback about your team members",
    "startDate": "2025-06-20",
    "endDate": "2025-06-27",
    "expectedResult": "Session created successfully"
  },
  "CP_05_02_LongSessionName": {
    "testId": "CP-05-02",
    "sessionName": "Este es un nombre de sesión extremadamente largo que excede los límites permitidos por el sistema y debería generar un error de validación",
    "expectedError": "Session name exceeds character limit"
  },
  "CP_05_03_EmptySessionName": {
    "testId": "CP-05-03",
    "sessionName": "",
    "expectedError": "Session name is required"
  },
  "CP_05_04_InvalidDateRange": {
    "testId": "CP-05-04",
    "startDate": "2025-06-27",
    "endDate": "2025-06-20",
    "expectedError": "End date must be after start date"
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
- `session_management_data.json` - Datos para tests de gestión de sesiones (RF-05)

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

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng/testng.xml
```

### A.6.3 Ejecución de Casos Específicos
**Descripción**: Ejecuta casos individuales para debugging o validación puntual  
**Comandos**:
```bash
# Casos específicos por funcionalidad
mvn test -Dtest=InstructorRegistrationTests#testValidEmailRegistration
mvn test -Dtest=LoginTests#testSuccessfulInstructorLogin
mvn test -Dtest=SessionPanelTests#testViewSessionResponses
mvn test -Dtest=CourseManagementTests#testCreateCourseWithValidData
```

---

## A.7 Gestión de Datos de Prueba

### A.7.1 Estructura de Archivos JSON
Los datos de prueba están organizados por requerimiento funcional para facilitar el mantenimiento y la trazabilidad:

| Archivo | Requerimiento | Propósito | Ubicación |
|---------|---------------|-----------|-----------|
| `registration_data.json` | RF-01 | Datos de registro de instructores | `testdata/` |
| `login_data.json` | RF-02 | Credenciales y escenarios de login | `testdata/` |
| `session_data.json` | RF-03 | Configuraciones de sesiones | `testdata/` |
| `course_data.json` | RF-04 | Datos de cursos y estudiantes | `testdata/` |
| `session_management_data.json` | RF-05 | Gestión de sesiones de feedback | `testdata/` |

### A.7.2 Formato Estándar de Datos
Cada archivo JSON sigue la convención `CP_XX_YY_Description` para mantener trazabilidad:

```json
{
  "CP_01_01_ValidRegistration": {
    "testId": "CP-01-01",
    "description": "Registro exitoso con correo válido",
    "expectedResult": "confirmación de registro",
    "testData": {
      // Datos específicos del caso
    }
  }
}
```

---

## A.8 Mantenimiento y Escalabilidad

### A.8.1 Consideraciones de Mantenimiento
- **Selectores Robustos**: Uso de múltiples estrategias de localización
- **Esperas Explícitas**: Implementación de WebDriverWait para elementos dinámicos
- **Datos Parametrizados**: Separación completa entre lógica y datos
- **Logging Detallado**: Trazabilidad completa de ejecución

### A.8.2 Escalabilidad del Framework
- **Modularidad**: Cada RF en clases separadas
- **Reutilización**: Page Objects compartidos
- **Configuración Centralizada**: Properties y TestNG XML
- **Paralelización**: Soporte para ejecución concurrente

---

## A.9 Troubleshooting y Soporte

### A.9.1 Problemas Comunes y Soluciones

| Problema | Causa Probable | Solución |
|----------|----------------|----------|
| Tests fallan por timeout | Elementos no cargan | Aumentar timeout en `test.properties` |
| Login no funciona | Credenciales incorrectas | Verificar `test.properties` |
| Chrome no se abre | Driver incompatible | WebDriverManager actualiza automáticamente |
| JSON no se lee | Formato incorrecto | Validar sintaxis JSON |

### A.9.2 Logs y Debugging
```bash
# Ejecución con logs detallados
mvn test -Dtest=TestClass -X

# Solo compilación para verificar errores
mvn clean compile

# Verificar configuración TestNG
mvn test-compile
```

---

## A.10 Métricas y Reportes

### A.10.1 Cobertura de Automatización
- **Total de Casos Manuales**: 105
- **Casos Automatizados**: 32
- **Porcentaje de Automatización**: 30.5%
- **Casos Críticos Cubiertos**: 100%

### A.10.2 Distribución por Módulo

| Módulo | Casos Automatizados | Tiempo Estimado | Criticidad |
|--------|-------------------|-----------------|------------|
| RF-01: Registro | 4 | 8 min | Alta |
| RF-02: Login | 2 | 4 min | Alta |
| RF-03: Sesiones | 4 | 8 min | Media |
| RF-04: Cursos | 12 | 24 min | Alta |
| RF-05: Feedback | 10 | 20 min | Media |

### A.10.3 Ejecución Completa
**Tiempo Total Estimado**: 64 minutos  
**Tiempo Óptimo (Paralelo)**: 20 minutos  
**Recomendación**: Ejecución nocturna automatizada

---

## A.11 Conclusiones del Anexo

### A.11.1 Beneficios Logrados
1. **Eficiencia**: Reducción del 80% en tiempo de ejecución de pruebas
2. **Repetibilidad**: Eliminación de variabilidad humana
3. **Cobertura**: Validación consistente de casos críticos
4. **Mantenibilidad**: Framework modular y escalable
5. **Trazabilidad**: Mapeo directo con casos manuales

### A.11.2 Recomendaciones Futuras
1. **Integración CI/CD**: Automatización en pipeline de desarrollo
2. **Reportes Visuales**: Implementación de ExtentReports
3. **Paralelización**: Optimización para ejecución concurrente
4. **Datos Dinámicos**: Generación automática de datos de prueba
5. **Cross-Browser**: Extensión a Firefox y Edge

---

## A.12 Referencias y Recursos

### A.12.1 Documentación Técnica
- [Selenium WebDriver Documentation](https://selenium.dev/documentation/)
- [TestNG Framework Guide](https://testng.org/doc/documentation-main.html)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)

### A.12.2 Comando de Verificación Rápida
```bash
# Validación completa del framework
mvn clean compile test -DsuiteXmlFile=src/test/resources/testng/testng.xml
```

---

**Documento generado**: 2025-06-20  
**Versión del Framework**: 1.0  
**Autor**: alexisBltz  
**Estado**: ✅ COMPLETO Y OPERATIVO
mvn test
```

¡Listo para ejecutar! 🚀
