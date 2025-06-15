# TEAMMATES automation test

[![Java](https://img.shields.io/badge/Java-11+-orange?style=flat-square&logo=oracle)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=flat-square&logo=apache-maven)](https://maven.apache.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green?style=flat-square&logo=selenium)](https://selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.9.0-red?style=flat-square)](https://testng.org/)

## Arquitectura del Framework

```
src/
├── main/java/
│   ├── pageobjects/              # Page Object classes
│   │   ├── BasePage.java         # Métodos comunes reutilizables
│   │   └── InstructorRequestPage.java  # Página de registro de instructores
│   ├── utils/                    # Utilities classes
│   │   ├── ConfigReader.java     # Lector de configuración
│   │   ├── TestDataReader.java   # Lector de datos JSON
│   │   └── DriverManager.java    # Gestor de WebDriver
│   └── base/
│       └── BaseTest.java         # Setup/Teardown común
└── test/
    ├── java/
    │   └── authentication/       # Test classes
    │       └── RegistrationTests.java
    └── resources/
        ├── config/               # Configuration files
        │   └── test.properties
        ├── testdata/             # JSON test data
        │   └── registration_data.json
        └── testng/               # TestNG configuration
            └── testng.xml
```

## Prerequisitos

- **Java 11+** 
- **Maven 3.6+** 
- **Chrome/Firefox/Edge** instalado 
- **TEAMMATES** aplicación corriendo en `localhost:8080` 

## Instalación y Ejecución

### Setup Inicial

**Clonar el repositorio:**
```bash
git clone https://github.com/Farith77/Automatic_Test.git
```

**Navegar al directorio:**
```bash
cd Automatic_Test
```

### Ejecutar Tests

**Ejecutar todos los tests:**
```bash
mvn test
```

**Limpiar y ejecutar tests:**
```bash
mvn clean test
```

**Ejecutar test específico:**
```bash
mvn test -Dtest=RegistrationTests#testInvalidEmailRegistration
```

**Ver reportes generados:**
```bash
target/surefire-reports/
```

## Configuración

### Cambiar Navegador

Editar el archivo de configuración:
```bash
src/test/resources/config/test.properties
```

**Contenido del archivo:**
```properties
# Opciones disponibles: chrome | firefox | edge
browser=chrome

# URL de la aplicación TEAMMATES
app.url=http://localhost:8080

# Timeout para elementos (segundos)
browser.timeout=30
```

### Modificar Datos de Prueba

Editar los datos de test:
```bash
src/test/resources/testdata/registration_data.json
```

**Ejemplo de estructura JSON:**
```json
{
  "CP_01_02_InvalidEmail": {
    "testId": "CP-01-02",
    "email": "nuevo@gmail.com",
    "fullName": "Nuevo Usuario",
    "institution": "Universidad Test",
    "country": "Perú",
    "expectedMessage": "submitted successfully",
    "shouldSucceed": true
  }
}
```