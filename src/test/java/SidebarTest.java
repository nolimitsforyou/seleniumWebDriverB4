import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sun.awt.windows.WEmbeddedFrame;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SidebarTest {

    private WebDriver driver;


    @BeforeTest
    public void start() {
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
        List<WebElement> mainSections = driver.findElements(By.xpath("//li[@id = 'app-']"));
        for(int i = 0; i < mainSections.size(); i++) {
            mainSections.get(i).click();
        }
    }

    @AfterTest
    public void stop() {
        if(driver != null) {
            driver.quit();
        }
    }
}
