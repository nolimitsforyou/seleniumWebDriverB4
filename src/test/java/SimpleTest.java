import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SimpleTest {

    private WebDriver driver;

    @Test
    public void test() {
        driver =  new ChromeDriver();
        driver.get("https://www.airbnb.ru/");
    }
}
