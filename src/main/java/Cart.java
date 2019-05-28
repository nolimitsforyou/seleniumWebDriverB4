import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Cart extends Page {

    public Cart (WebDriver driver)  {
        super(driver);
    }

    public Cart open() {
        driver.get("http://localhost/litecart/en/checkout");
        return this;
    }

    private By counterProductOnCart = By.xpath("//div[@id = 'cart']//a/span[@class = 'quantity']");
    private By buttonRemove = By.xpath("//button[@name = 'remove_cart_item']");
    private By chekOutCartTable = By.xpath("//div[@id = 'checkout-cart-wrapper' and @style = 'opacity: 1;']");
    private By cart = By.xpath("//div[@id = 'cart']");
    private By checkOutLink = By.xpath("./a[@class = 'link']");

    public int getProductsCount() {
        WebElement counter = driver.findElement(counterProductOnCart);
        return Integer.valueOf(counter.getText());
    }

    public int getCountForRemove() {
        int removeButtons = driver.findElements(buttonRemove).size();
        return removeButtons;
    }

    public void clickToChekOut() {
        WebElement productsCart = driver.findElement(cart);
        productsCart.findElement(checkOutLink).click();

    }

    public void clickToRemoveButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(buttonRemove));
        driver.findElement(buttonRemove).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(chekOutCartTable));
    }

}
