import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SortOrderCountriesTest {

    private WebDriver driver;

    private By countriesForm = By.xpath("//form[@name = 'countries_form']");
    private By countryLink = By.xpath(".//tr[@class = 'row']//a[not(contains(@title ,'Edit'))]");

    private void checkSortOrder(By mainElement, By childElement){
        ArrayList<String> originalList = new ArrayList<String>();
        List<WebElement> listCountries = driver.findElement(mainElement).findElements(childElement);
        for (WebElement ele : listCountries) {
            originalList.add(ele.getText());
        }
        ArrayList<String> sortedList = new ArrayList<String>();
        for (String s : originalList) {
            sortedList.add(s);
        }
        Collections.sort(sortedList);
        Assert.assertTrue(sortedList.equals(originalList));
    }

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test_1() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        checkSortOrder(countriesForm, countryLink);
    }


    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}


