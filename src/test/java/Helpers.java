import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Helpers {

    public static final String MAIN_PAGE = "http://localhost/litecart/en/";

    public static void loginAsAdmin(WebDriver driver) {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    public static void buttonClick(WebDriver driver, By buttonName) {
        driver.findElement(buttonName).click();
    }
}
