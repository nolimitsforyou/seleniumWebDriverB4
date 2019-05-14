import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class ProductCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private By counterProductOnCart = By.xpath("//div[@id = 'cart']//a/span[@class = 'quantity']");
    private By cartAfterAddedProduct = By.xpath("//div[@id = 'cart' and @style]");
    private By product = By.xpath("//li[contains (@class ,'product')]");
    private By productBox = By.xpath("//div[@id = 'box-product']");
    private By selectorSize = By.xpath(".//select[@name = 'options[Size]']");
    private By buttonAddProduct = By.xpath(".//button[@name = 'add_cart_product']");

    private boolean isElementPresent (WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        }
        catch (NoSuchElementException notElement) {
            return false;
        }
    }

    private int getProductsCount() {
        return Integer.valueOf(driver.findElement(counterProductOnCart).getText());
    }

    private void checkProductWasAdded(int countAfter) {
        WebElement cartAdded = wait.until(presenceOfElementLocated(cartAfterAddedProduct));
        if (getProductsCount() == countAfter)
    }

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> products = driver.findElements(product);
        products.get(0).click();
        WebElement productCard = driver.findElement(productBox);
        if(isElementPresent(driver, selectorSize) == true) {
            Select size = new Select(productCard.findElement(selectorSize));
            size.selectByIndex(1);
            productCard.findElement(buttonAddProduct).click();
        } else {
            productCard.findElement(buttonAddProduct).click();
        }

    }

    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}
