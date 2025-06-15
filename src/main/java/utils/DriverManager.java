package utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager { 
    private static WebDriver driver;
    
    public static void initializeDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup(); 
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
                
            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getTimeout()));
        driver.manage().window().maximize();
    }
    
    public static WebDriver getDriver() {
        if (driver == null) {
            throw new RuntimeException("Driver not initialized. Call initializeDriver() first.");
        }
        return driver;
    }
    
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    public static void navigateToApp() {
        getDriver().get(ConfigReader.getAppUrl());
    }
}