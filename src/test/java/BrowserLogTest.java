import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.util.List;

public class BrowserLogTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private By catalog = By.xpath("//span[text() = 'Catalog']");
    private By productItem = By.xpath("//a[contains (@href , 'product_id') and not(contains (@title ,'Edit'))]");

    private void checkLogs() {
        for (LogEntry logEntry : driver.manage().logs().get("browser").getAll()) {
            System.out.println(logEntry);
            Assert.assertTrue(logEntry.equals(""));
        }
    }

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(catalog).click();
        List<WebElement> products = driver.findElements(productItem);
        for(int i = 0; i < products.size(); i++) {
            products.get(i).click();
            driver.navigate().back();
            products = driver.findElements(productItem);
        checkLogs();
        }
    }

    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}
