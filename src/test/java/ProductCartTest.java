import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
    private By cart = By.xpath("//div[@id = 'cart']");
    private By cartAfterAddedProduct = By.xpath("//div[@id = 'cart' and @style]");
    private By checkOutLink = By.xpath("./a[@class = 'link']");
    private By product = By.xpath("//li[contains (@class ,'product')]");
    private By productBox = By.xpath("//div[@id = 'box-product']");
    private By selectorSize = By.xpath(".//select[@name = 'options[Size]']");
    private By buttonAddProduct = By.xpath(".//button[@name = 'add_cart_product']");
    private By pageHomeLink = By.xpath("//div[@class = 'content']//li/a[@href = '/litecart/']");

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

    private void checkProductWasAdded(int beforeAdd, int afterAdd) {
        Assert.assertTrue(beforeAdd != afterAdd);
    }

    private void addProductToCart(WebDriver driver) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cart));
        driver.findElement(buttonAddProduct).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartAfterAddedProduct));
    }

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/en/");
        for (int i = 0; i < 3; i++) {
            List<WebElement> products = driver.findElements(product);
            products.get(0).click();
            WebElement productCard = driver.findElement(productBox);
            if(isElementPresent(driver, selectorSize) == true) {
                Select size = new Select(productCard.findElement(selectorSize));
                int countBeforeAdd = getProductsCount();
                size.selectByIndex(1);
                addProductToCart(driver);
                int countAfterAdd = getProductsCount();
                checkProductWasAdded(countBeforeAdd, countAfterAdd);
                driver.findElement(pageHomeLink).click();
            } else {
                int countBeforeAdd = getProductsCount();
                addProductToCart(driver);
                int countAfterAdd = getProductsCount();
                checkProductWasAdded(countBeforeAdd, countAfterAdd);
                driver.findElement(pageHomeLink).click();
            }
        }
        // открываем корзину, удаляем оттуда весь товар
        WebElement productsCart = driver.findElement(cart);
        productsCart.findElement(checkOutLink).click();

    }

    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}
