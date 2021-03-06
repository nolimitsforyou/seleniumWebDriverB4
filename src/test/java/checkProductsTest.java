import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private void checkRgbColors(WebElement sectionName, By priceType) {
        String regex = "\\((\\d{1,}), (\\d{1,}), (\\d{1,})";
        String colorOfPrice = sectionName.findElement(priceType).getCssValue("color");
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(colorOfPrice);
        ArrayList<String> colorList = new ArrayList<String>();
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                colorList.add(matcher.group(i));
            }
            String red = colorList.get(0);
            String green = colorList.get(1);
            String blue = colorList.get(2);
            Assert.assertEquals(red, green, blue);
        }
    }

    private void checkRedColor(WebElement sectionName, By priceType) {
        String regex = "\\((\\d{1,}), (\\d{1,}), (\\d{1,})";
        String colorOfPrice = sectionName.findElement(priceType).getCssValue("color");
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(colorOfPrice);
        ArrayList<String> colorList = new ArrayList<String>();
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                colorList.add(matcher.group(i));
            }
            String green = colorList.get(1);
            String blue = colorList.get(2);
            Assert.assertEquals(green, blue, "0");
        }
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
        WebElement sectionСampaigns = driver.findElement(campaigns);
        String nameOfProductComp = sectionСampaigns.findElement(productName).getText();
        String regPriceComp = sectionСampaigns.findElement(regularPrice).getText();
        String discountComp = sectionСampaigns.findElement(discountPrise).getText();
        /** обычная цена зачёркнутая (Главная страница) */
        String linePriceRegularComp = sectionСampaigns.findElement(regularPrice).getCssValue("text-decoration-line");
        String expectedLineRegComp = "line-through";
        Assert.assertEquals(linePriceRegularComp,expectedLineRegComp);
        /** акционная цена жирная (Главная страница) */
        String fontPriceDiscountComp = sectionСampaigns.findElement(discountPrise).getAttribute("class");
        String expectedFontComp = "campaign-price";
        Assert.assertEquals(fontPriceDiscountComp,expectedFontComp);
        /**  обычная цена серая (Главная страница) */
        checkRgbColors(sectionСampaigns, regularPrice);
        /** акционная цена красная (Главная страница) */
        checkRedColor(sectionСampaigns, discountPrise);
        /** акционная цена крупнее, чем обычная (Главная страница) */
        Double sizeDiscountComp = Double.valueOf(sectionСampaigns.findElement(discountPrise).getCssValue("font-size").substring(0,2 ));
        Double sizeRegularComp = Double.valueOf(sectionСampaigns.findElement(regularPrice).getCssValue("font-size").substring(0,2 ));
        Assert.assertTrue(sizeDiscountComp > sizeRegularComp);

        /** ПЕРЕХОД НА СТРАНИЦУ ТОВАРА */
        sectionСampaigns.findElement(product).click();
        WebElement productBox = driver.findElement(boxProduct);
        String nameBox = productBox.findElement(boxProductName).getText();
        String regPriceBox = productBox.findElement(regularPrice).getText();
        String discountBox = productBox.findElement(discountPrise).getText();
        /** обычная цена зачёркнутая (Страница товара) */
        String linePriceRegularBox = productBox.findElement(regularPrice).getCssValue("text-decoration-line");
        String expectedLineRegBox = "line-through";
        Assert.assertEquals(linePriceRegularBox,expectedLineRegBox);
        /** акционная цена жирная (Страница товара) */
        String fontPriceDiscountBox = productBox.findElement(discountPrise).getAttribute("class");
        String expectedFontBox = "campaign-price";
        Assert.assertEquals(fontPriceDiscountBox,expectedFontBox);
        /**  обычная цена серая (Страница товара) */
        checkRgbColors(productBox, regularPrice);
        /** акционная цена красная (Страница товара) */
        checkRedColor(productBox, discountPrise);
        /** акционная цена крупнее, чем обычная (Главная страница) */
        Double sizeDiscountBox = Double.valueOf(productBox.findElement(discountPrise).getCssValue("font-size").substring(0,2 ));
        Double sizeRegularBox = Double.valueOf(productBox.findElement(regularPrice).getCssValue("font-size").substring(0,2 ));
        Assert.assertTrue(sizeDiscountBox > sizeRegularBox);

        /** ПРОВЕРКИ ТОВАРА С ОБОИХ СТРАНИЦ
         * а) на главной странице и на странице товара совпадает текст названия товара */
        Assert.assertEquals(nameOfProductComp, nameBox);
        /** б) на главной странице и на странице товара совпадают цены (обычная и акционная) */
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
