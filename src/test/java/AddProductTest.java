import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AddProductTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private By leftSidebar = By.xpath("//td[@id = 'sidebar']");
    private By catalog = By.xpath(".//span[text() = 'Catalog']");
    private By pageContent = By.xpath("//td[@id= 'content']");
    private By buttonAddNewProduct = By.xpath(".//a[@class = 'button'][2]");

    private By tabInformation = By.xpath(".//div[@class = 'tabs']/ul/li[2]");
    private By tabPrice = By.xpath(".//div[@class = 'tabs']/ul/li[4]");

    private By nameProduct = By.xpath(".//input[contains(@name ,'name')]");
    private By codeProduct = By.xpath(".//input[@name = 'code']");
    private By productGr = By.xpath(".//strong[ contains (text() ,'Product Groups')]/following-sibling::div//tr[3]//input");
    private By fieldQuantity = By.xpath(".//input[@name = 'quantity']");
    private By uploadImages = By.xpath(".//input[@name = 'new_images[]']");
    private By fieldDateValidFrom = By.xpath(".//input[@name = 'date_valid_from']");
    private By fieldDateValidTo = By.xpath(".//input[@name = 'date_valid_to']");
    private By buttonSave = By.xpath(".//button[@name = 'save']");

    private By selectManufacturer = By.xpath(".//select[@name = 'manufacturer_id']");
    private By fieldKeywords = By.xpath(".//input[@name = 'keywords']");
    private By fieldShortDescription = By.xpath(".//input[contains(@name ,'short_description')]");
    private By fieldDescription = By.xpath(".//div[@class = 'trumbowyg-editor']");
    private By fieldHeadTitle = By.xpath(".//input[contains(@name ,'head_title')]");
    private By fieldMetaDescription = By.xpath(".//input[contains(@name ,'meta_description')]");

    private By fieldPurchasePrice = By.xpath(".//input[@name = 'purchase_price']");
    private By selectCurrency = By.xpath(".//select[@name = 'purchase_price_currency_code']");
    private By usdPrice = By.xpath(".//input[@name = 'prices[USD]']");
    private By eurPrice = By.xpath(".//input[@name = 'prices[EUR]']");
//    private By usdPriceTax = By.xpath(".//input[@name = 'gross_prices[USD]']");
//    private By eurPriceTax = By.xpath(".//input[@name = 'gross_prices[EUR]']");




    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        WebElement sidebar = driver.findElement(leftSidebar);
        sidebar.findElement(catalog).click();
        WebElement contentPage = driver.findElement(pageContent);
        contentPage.findElement(buttonAddNewProduct).click();
        // заполнение вклдаки General
        contentPage = driver.findElement(pageContent);
        contentPage.findElement(nameProduct).sendKeys("Test Product Name");
        contentPage.findElement(codeProduct).sendKeys("000001");
        contentPage.findElement(productGr).click();
        contentPage.findElement(fieldQuantity).clear();
        contentPage.findElement(fieldQuantity).sendKeys("100");
        String picture = new File("src/resource/pig_pepe.png").getAbsolutePath();
        contentPage.findElement(uploadImages).sendKeys(picture);
        contentPage.findElement(fieldDateValidFrom).sendKeys("01052019");
        contentPage.findElement(fieldDateValidTo).sendKeys("30062019");
        contentPage.findElement(tabInformation).click();
        // заполнение вкладки Information
        contentPage = driver.findElement(pageContent);
        Select Manufacturer = new Select(contentPage.findElement(selectManufacturer));
        Manufacturer.selectByValue("1");
        contentPage.findElement(fieldKeywords).sendKeys("Test Keywords");
        contentPage.findElement(fieldShortDescription).sendKeys("Test short description");
        contentPage.findElement(fieldDescription).sendKeys("test text 123456");
        contentPage.findElement(fieldHeadTitle).sendKeys("test field text");
        contentPage.findElement(fieldMetaDescription).sendKeys("test text meta");
        contentPage.findElement(tabPrice).click();
        // заполнение вкладки Prices
        contentPage = driver.findElement(pageContent);
        contentPage.findElement(fieldPurchasePrice).clear();
        contentPage.findElement(fieldPurchasePrice).sendKeys("3.99");
        Select currency = new Select(contentPage.findElement(selectCurrency));
        currency.selectByValue("USD");
        contentPage.findElement(usdPrice).clear();
        contentPage.findElement(usdPrice).sendKeys("10");
        contentPage.findElement(eurPrice).clear();
        contentPage.findElement(eurPrice).sendKeys("12");
        contentPage.findElement(buttonSave).click();
        //проверяем что товар появился в каталоге
        contentPage = driver.findElement(pageContent);
        Assert.assertTrue(contentPage.findElement(By.linkText("Test Product Name")).isDisplayed());
    }

    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}
