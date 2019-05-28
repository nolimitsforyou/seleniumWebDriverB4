import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Application {

    private WebDriver driver;

    private MainPage mainPage;
    private ProductPage productPage;
    public Cart cart;

    public Application() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cart = new Cart(driver);

    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void addProductToCart() {
        mainPage.clickToProductItemMostPopular();
        if (productPage.selectorIsAvailable() == true) {
            productPage.selectFirstSize();
            productPage.clickAddToCart();
            mainPage.open();
        }
        else productPage.clickAddToCart();
        mainPage.open();
    }

    public void removeAllProductsFromCart() {
        cart.clickToChekOut();
        while (cart.getCountForRemove() != 0) {
            cart.clickToRemoveButton();
        }
    }


}
