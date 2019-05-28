import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.By.xpath;

public class MainPage extends Page {

    public MainPage (WebDriver driver){
        super(driver);
    }

    public MainPage open() {
        driver.get("http://localhost/litecart/en/");
        return this;
    }

    private By mostPopularSection = xpath("//div[@id = 'box-most-popular']");
    private By productItem = xpath(".//li[contains (@class ,'product')]");

    public ProductPage clickToProductItemMostPopular () {
        WebElement mostPopular = driver.findElement(mostPopularSection);
        List<WebElement> products = mostPopular.findElements(productItem);
        products.get(0).click();
        return new ProductPage(driver);
    }
}
