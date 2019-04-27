import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class checkProductsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private By campaigns = By.xpath("//div[@id = 'box-campaigns']");
    private By product = By.xpath(".//li[contains (@class ,'product')]");
    private By productName = By.xpath(".//div[@class = 'name']");
    private By regularPrice = By.xpath(".//s[@class = 'regular-price']");
    private By discountPrise = By.xpath(".//strong[@class = 'campaign-price']");
    private By boxProduct = By.xpath("//div[@id = 'box-product']");
    private By boxProductName = By.xpath("//div[@id = 'box-product']//h1");


    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

    }

    @Test
    public void test() {
        driver.get(Helpers.MAIN_PAGE);
        WebElement sectionСampaigns = driver.findElement(campaigns);
        String nameOfProductComp = sectionСampaigns.findElement(productName).getText();
        String regPriceComp = sectionСampaigns.findElement(regularPrice).getText();
        String discountComp = sectionСampaigns.findElement(discountPrise).getText();
        sectionСampaigns.findElement(product).click();
        WebElement productBox = driver.findElement(boxProduct);
        String nameBox = productBox.findElement(boxProductName).getText();
        String regPriceBox = productBox.findElement(regularPrice).getText();
        String discountBox = productBox.findElement(discountPrise).getText();
//        а) на главной странице и на странице товара совпадает текст названия товара
        Assert.assertEquals(nameOfProductComp, nameBox);
//        б) на главной странице и на странице товара совпадают цены (обычная и акционная)
        Assert.assertEquals(regPriceComp, regPriceBox);
        Assert.assertEquals(discountComp, discountBox);

    }

    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}
