package base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;
import utils.DriverManager;

public class BaseTest {
    protected static final String CONFIG_FILE = "config/test.properties";
    
    @BeforeClass
    public void setUpClass() {
        System.out.println("=== Iniciando Suite de Tests ===");
        ConfigReader.loadConfig(CONFIG_FILE);
        DriverManager.initializeDriver();
    }
    
    @AfterClass  
    public void tearDownClass() {
        System.out.println("=== Finalizando Suite de Tests ===");
        DriverManager.quitDriver();
    }
    
    @BeforeMethod
    public void setUp() {
        System.out.println("Navegando a la aplicación...");
        DriverManager.navigateToApp();
    }
    
    @AfterMethod
    public void tearDown() {
        //para las fotitos
        System.out.println("Test completado.");
    }
}