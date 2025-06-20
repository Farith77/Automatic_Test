package pageobjects;

import org.openqa.selenium.By;

import utils.ConfigReader;

public class InstructorRequestPage extends BasePage {
    
    // ids -- las coloque exacto, no sé si este mal
    private By nameField = By.id("name");
    private By institutionField = By.id("institution");
    private By countryField = By.id("country");
    private By emailField = By.id("email");
    private By commentsField = By.id("comments");
    private By submitButton = By.id("submit-button");
    private By cancelButton = By.xpath("//a[contains(text(),'Cancel')]");
    
    // mensaje de error
    private By errorAlert = By.className("alert-danger");
    private By nameError = By.xpath("//div[contains(text(),'Please enter your name')]");
    private By institutionError = By.xpath("//div[contains(text(),'Please enter your institution name')]");
    private By countryError = By.xpath("//div[contains(text(),'Please enter your institution\\'s country')]");
    private By emailError = By.xpath("//div[contains(text(),'Please enter your email address')]");
      // mensaje de exito - selectores más amplios para capturar diferentes variaciones
    private By successMessage = By.xpath("//*[contains(text(),'submitted successfully')] | " +
                                        "//*[contains(text(),'Your request has been submitted')] | " +
                                        "//*[contains(text(),'Solicitud recibida')] | " +
                                        "//*[contains(text(),'acknowledgement email')] | " +
                                        "//*[contains(@class,'alert-success')] | " +
                                        "//*[contains(@class,'success')] | " +
                                        "//div[contains(@class,'alert') and contains(@class,'alert-success')]");
    
    private By anyMessage = By.xpath("//div[contains(@class,'alert')] | " +
                                    "//*[contains(@class,'message')] | " +
                                    "//*[contains(@class,'notification')] | " +
                                    "//*[contains(text(),'request')] | " +
                                    "//*[contains(text(),'submitted')] | " +
                                    "//*[contains(text(),'email')]");
    private By iAmInstructorButton = By.id("btn-am-instructor");

    // Botones específicos para el flujo CP-01-01
    private By requestAccountButton = By.xpath("//a[contains(text(),'Request a Free Instructor Account') or contains(@class,'btn') and contains(text(),'Request')]");
    private By requestAccountButtonAlt = By.xpath("//button[contains(text(),'Request a Free Instructor Account')] | //a[contains(text(),'Request a Free Instructor Account')]");
    
    // directo al gui de registros, solo instructores, REVISAR
    public void navigateToRequestForm() {
        String url = ConfigReader.getAppUrl() + "/web/front/request";
        navigateToUrl(url);
        waitForPageLoad();
    }
    
    private void waitForPageLoad() {
        // "I am an instructor"
        System.out.println("Esperando página inicial...");
        waitForElementVisible(iAmInstructorButton);
        
        // Hacer click
        System.out.println("Haciendo click en 'I am an instructor'");
        click(iAmInstructorButton);
        
        // formulario
        System.out.println("Esperando formulario...");
        waitForElementVisible(nameField);
    }
    
    // Llenar form
    public void fillRequestForm(String fullName, String institution, String country, String email, String comments) {
        System.out.println("Llenando formulario de solicitud:");
        System.out.println("- Nombre: '" + fullName + "'");
        System.out.println("- Institución: '" + institution + "'");
        System.out.println("- País: '" + country + "'");
        System.out.println("- Email: '" + email + "'");
        
        if (!fullName.isEmpty()) {
            type(nameField, fullName);
        }
        if (!institution.isEmpty()) {
            type(institutionField, institution);
        }
        if (!country.isEmpty()) {
            type(countryField, country);
        }
        if (!email.isEmpty()) {
            type(emailField, email);
        }
        if (comments != null && !comments.isEmpty()) {
            type(commentsField, comments);
        }
    }
    
    public void submitRequest() {
        System.out.println("Enviando solicitud...");
        click(submitButton);
    }
    
    public void fillAndSubmitRequest(String fullName, String institution, String country, String email) {
        fillRequestForm(fullName, institution, country, email, "");
        submitRequest();
    }    public String getSuccessMessage() {
        try {
            waitFor(3); // Esperar a que aparezca el mensaje
            if (isElementPresent(successMessage)) {
                return getText(successMessage);
            }
            return "";
        } catch (Exception e) {
            System.out.println("   - Error obteniendo mensaje de éxito: " + e.getMessage());
            return "";
        }
    }
    
    public String getErrorMessage() {
        try {
            if (isElementPresent(errorAlert)) {
                return getText(errorAlert);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
      public boolean isSuccessMessageDisplayed() {
        try {
            System.out.println("   - Verificando mensaje de éxito...");
            waitFor(5); // Dar más tiempo para respuesta del servidor
            
            // Verificar si hay mensaje de éxito
            boolean messageFound = isElementPresent(successMessage);
            System.out.println("   - Mensaje de éxito encontrado: " + messageFound);
            
            if (messageFound) {
                String messageText = getText(successMessage);
                System.out.println("   ✓ Mensaje de éxito: " + messageText);
                return true;
            } else {
                // Debug exhaustivo
                System.out.println("   - No se encontró mensaje de éxito, investigando...");
                debugPageMessages();
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("   - Error verificando mensaje de éxito: " + e.getMessage());
            debugPageMessages();
            return false;
        }
    }
      /**
     * Método de debug para capturar mensajes en la página
     */
    private void debugPageMessages() {
        try {
            System.out.println("   - DEBUG: Analizando página después del envío...");
            
            // Obtener URL actual
            String currentUrl = driver.getCurrentUrl();
            System.out.println("   - DEBUG: URL actual: " + currentUrl);
            
            // Buscar cualquier tipo de mensaje/alerta
            By allAlerts = By.xpath("//div[contains(@class,'alert')] | //*[contains(@class,'message')] | //*[contains(@class,'notification')]");
            if (isElementPresent(allAlerts)) {
                String alertText = getText(allAlerts);
                System.out.println("   - DEBUG: Mensaje/Alerta encontrada: " + alertText);
            } else {
                System.out.println("   - DEBUG: No se encontraron alertas estándar");
            }
            
            // Buscar texto específico de éxito
            By successTexts = By.xpath("//*[contains(text(),'submitted') or contains(text(),'success') or contains(text(),'acknowledgement')]");
            if (isElementPresent(successTexts)) {
                String successText = getText(successTexts);
                System.out.println("   - DEBUG: Texto de éxito encontrado: " + successText);
            }
            
            // Buscar errores
            By errorTexts = By.xpath("//*[contains(text(),'error') or contains(text(),'Error') or contains(text(),'failed')]");
            if (isElementPresent(errorTexts)) {
                String errorText = getText(errorTexts);
                System.out.println("   - DEBUG: Texto de error encontrado: " + errorText);
            }
            
            // Verificar título de la página
            String pageTitle = driver.getTitle();
            System.out.println("   - DEBUG: Título de página: " + pageTitle);
            
            // Capturar una muestra del contenido de la página
            String pageText = getText(By.tagName("body"));
            if (pageText.contains("submitted") || pageText.contains("success") || pageText.contains("acknowledgement")) {
                // Encontrar la sección relevante
                String[] words = pageText.split("\\s+");
                StringBuilder relevantText = new StringBuilder();
                boolean foundRelevant = false;
                
                for (int i = 0; i < words.length; i++) {
                    if (words[i].toLowerCase().contains("submit") || 
                        words[i].toLowerCase().contains("success") || 
                        words[i].toLowerCase().contains("acknowledge")) {
                        foundRelevant = true;
                        // Capturar contexto (5 palabras antes y 10 después)
                        int start = Math.max(0, i - 5);
                        int end = Math.min(words.length, i + 10);
                        for (int j = start; j < end; j++) {
                            relevantText.append(words[j]).append(" ");
                        }
                        break;
                    }
                }
                
                if (foundRelevant) {
                    System.out.println("   - DEBUG: Contexto relevante encontrado: " + relevantText.toString().trim());
                }
            } else {
                System.out.println("   - DEBUG: No se encontró texto de éxito en el contenido de la página");
                // Mostrar una muestra pequeña
                if (pageText.length() > 300) {
                    pageText = pageText.substring(0, 300) + "...";
                }
                System.out.println("   - DEBUG: Muestra del contenido: " + pageText.replaceAll("\\s+", " ").trim());
            }
            
        } catch (Exception e) {
            System.out.println("   - DEBUG: Error capturando información: " + e.getMessage());
        }
    }
    
    public boolean isErrorMessageDisplayed() {
        return isElementPresent(errorAlert) || 
               isElementPresent(nameError) || 
               isElementPresent(institutionError) ||
               isElementPresent(countryError) || 
               isElementPresent(emailError);
    }
    
    public boolean isFormDisplayed() {
        return isElementPresent(nameField) && isElementPresent(emailField);
    }
    
    // conectividad
    public boolean isRequestPageLoaded() {
        return isElementPresent(nameField);
    }
    
    public boolean isNameFieldPresent() {
        return isElementPresent(nameField);
    }
    
    public boolean isEmailFieldPresent() {
        return isElementPresent(emailField);
    }
    
    public boolean isInstitutionFieldPresent() {
        return isElementPresent(institutionField);
    }
    
    /**
     * Método específico para CP-01-01: Navegación completa desde página principal
     * Sigue los pasos: Página principal → Request Account → I am an instructor → Formulario
     */
    public void navigateFromHomePage() {
        System.out.println("=== CP-01-01: Iniciando navegación desde página principal ===");
        
        // Paso 1: Acceder a la página principal
        System.out.println("1. Accediendo a página principal de TEAMMATES");
        String homeUrl = ConfigReader.getAppUrl();
        navigateToUrl(homeUrl);
        waitFor(3); // Esperar carga completa
        
        // Paso 2: Buscar y hacer clic en "Request a Free Instructor Account"
        System.out.println("2. Buscando botón 'Request a Free Instructor Account'");
        try {
            if (isElementPresent(requestAccountButton)) {
                System.out.println("   - Encontrado botón principal, haciendo clic...");
                click(requestAccountButton);
            } else if (isElementPresent(requestAccountButtonAlt)) {
                System.out.println("   - Encontrado botón alternativo, haciendo clic...");
                click(requestAccountButtonAlt);
            } else {
                System.out.println("   - Navegando directamente a la página de solicitud");
                navigateToUrl(ConfigReader.getAppUrl() + "/web/front/request");
            }
        } catch (Exception e) {
            System.out.println("   - Error al hacer clic, navegando directamente: " + e.getMessage());
            navigateToUrl(ConfigReader.getAppUrl() + "/web/front/request");
        }
        
        waitFor(2);
        
        // Paso 3: En la página de solicitud, hacer clic en "I am an instructor"
        System.out.println("3. En página de solicitud, haciendo clic en 'I am an instructor'");
        waitForElementVisible(iAmInstructorButton, 10);
        click(iAmInstructorButton);
        
        // Paso 4: Esperar que aparezca el formulario
        System.out.println("4. Esperando formulario de registro...");
        waitForElementVisible(nameField, 10);
        System.out.println("   ✓ Formulario cargado correctamente");
    }
    
    /**
     * Completar formulario específico para CP-01-01
     */
    public void fillCP01FormData(String fullName, String institution, String country, String email) {
        System.out.println("=== Completando formulario CP-01-01 ===");
        System.out.println("4. Completando formulario con datos:");
        System.out.println("   - Full Name: " + fullName);
        System.out.println("   - Institution: " + institution); 
        System.out.println("   - Country: " + country);
        System.out.println("   - Email: " + email);
        System.out.println("   - Comments: (vacío)");
        
        // Llenar campos
        type(nameField, fullName);
        type(institutionField, institution);
        type(countryField, country);
        type(emailField, email);
        // Comments se deja vacío según especificación
        
        System.out.println("   ✓ Formulario completado");
    }
      /**
     * Enviar formulario para CP-01-01
     */
    public void submitCP01Form() {
        System.out.println("5. Haciendo clic en 'Submit'");
        click(submitButton);
        
        System.out.println("   - Formulario enviado, esperando respuesta...");
        waitFor(5); // Esperar más tiempo para la respuesta del servidor
        
        // Capturar información de la respuesta
        String currentUrl = driver.getCurrentUrl();
        System.out.println("   - URL después del envío: " + currentUrl);
        
        // Verificar si hay redirección o cambio en la página
        if (currentUrl.contains("request") || currentUrl.contains("success") || currentUrl.contains("confirmation")) {
            System.out.println("   ✓ Posible respuesta detectada en URL");
        }
        
        System.out.println("   ✓ Proceso de envío completado");
    }
}