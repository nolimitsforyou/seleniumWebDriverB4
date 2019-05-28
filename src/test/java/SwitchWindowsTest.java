import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class SwitchWindowsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private By countriesButton = By.xpath("//span[contains(text(), 'Countries')]");
    private By contentPage = By.xpath("//td[@id = 'content']");
    private By buttonAddNewCountry = By.xpath("./div/a");
    private By externalLink = By.xpath(".//a/i[@class = 'fa fa-external-link']");

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        ;
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String originalWindow = driver.getWindowHandle();
        final Set<String> oldWindowsSet = driver.getWindowHandles();
        driver.findElement(countriesButton).click();
        WebElement pageContent = driver.findElement(contentPage);
        pageContent.findElement(buttonAddNewCountry).click();
        pageContent = driver.findElement(contentPage);
        List <WebElement> externalLinks = pageContent.findElements(externalLink);
        for (int i = 0; i < externalLinks.size(); i++) {
            externalLinks.get(i).click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            Set<String> newWindowsSet = driver.getWindowHandles();
            newWindowsSet.removeAll(oldWindowsSet);
            String newWindowHandle = newWindowsSet.iterator().next();
            driver.switchTo().window(newWindowHandle);
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}
