import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.By.xpath;

public class ProductPage extends Page {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    private By productCard = xpath("//div[@id = 'box-product']");
    private By selectorSize = By.xpath("//select[@name = 'options[Size]']");
    private By buttonAddProduct = By.xpath(".//button[@name = 'add_cart_product']");

    private By cart = By.xpath("//div[@id = 'cart']");
    private By cartAfterAddedProduct = By.xpath("//div[@id = 'cart' and @style]");

    public void selectFirstSize() {
        WebElement product = driver.findElement(productCard);
        Select size = new Select(product.findElement(selectorSize));
        size.selectByIndex(1);
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cart));
        driver.findElement(buttonAddProduct).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartAfterAddedProduct));
    }

    public boolean selectorIsAvailable() {
        try {
            WebElement sizeSelector = driver.findElement(selectorSize);
            sizeSelector.isDisplayed();
            return true;
        } catch (NoSuchElementException notElement) {
            return false;
        }
    }
}
