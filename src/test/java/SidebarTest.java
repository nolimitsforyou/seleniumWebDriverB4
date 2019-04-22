import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SidebarTest {

    private WebDriver driver;
    private By mainSections = By.xpath("//li[@id = 'app-']");
    private By subSections = By.xpath("//li[@id = 'app-']/ul/li/a");
    private By headLine = By.xpath("//td[@id = 'content']/h1");

    private void checkHeadline() {
        WebElement headL = driver.findElement(headLine);
        Assert.assertTrue(headL.isDisplayed());
    }

    @BeforeTest
    public void setUp() {
        driver =  new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        List<WebElement> mainS = driver.findElements(mainSections);
        for (int i = 0; i < mainS.size(); i++) {
            mainS = driver.findElements(mainSections);
            mainS.get(i).click();
            checkHeadline();
            if (driver.findElement(subSections).isDisplayed()) {
                List<WebElement> subS = driver.findElements(subSections);
                for (int j = 0; j < subS.size(); j++) {
                    subS = driver.findElements(subSections);
                    subS.get(j).click();
                    checkHeadline();
                }
            }
        }
    }

    @AfterTest
    public void stop() {
        if(driver != null) {
            driver.quit();
        }
    }
}
