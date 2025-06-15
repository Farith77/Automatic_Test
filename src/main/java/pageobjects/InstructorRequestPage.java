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
    
    // mensaje de exito
    private By successMessage = By.xpath("//*[contains(text(),'submitted successfully')]");
    private By iAmInstructorButton = By.id("btn-am-instructor");

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
    }

    public String getSuccessMessage() {
        try {
            return getText(successMessage);
        } catch (Exception e) {
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
        return isElementPresent(successMessage);
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
}