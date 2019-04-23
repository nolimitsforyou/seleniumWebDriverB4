import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class StickerTest {

    private WebDriver driver;

    private By product = By.xpath("//li[contains (@class ,'product')]");
    private By sticker = By.xpath(".//div[contains (@class ,'sticker')]");


    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> products = driver.findElements(product);
        for(WebElement element : products){
            Assert.assertEquals( element.findElements(sticker).size(), 1);
        }
    }

        @AfterTest
        public void stop() {
            if (driver != null) {
                driver.quit();
            }
        }
    }


