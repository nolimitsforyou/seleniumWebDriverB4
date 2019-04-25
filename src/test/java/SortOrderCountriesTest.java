import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
    private By countriesButton = By.xpath("//span[contains(text(), 'Countries')]");
    private By countryLink = By.xpath(".//tr[@class = 'row']//a[not(contains(@title ,'Edit'))]");
    private By editPen = By.xpath("./following-sibling::td");
    private By countOfGeoZones = By.xpath(".//tr[@class = 'row']/td[6]");
    private By zonesTable = By.xpath("//table[@id = 'table-zones']");
    private By zones = By.xpath(".//td[3]");


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
        //а) проверить, что страны расположены в алфавитном порядке
       // checkSortOrder(countriesForm, countryLink);
        //б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке
        List<WebElement> countriesWithZones = driver.findElement(countriesForm).findElements(countOfGeoZones);
        for(WebElement ele : countriesWithZones){
            int countGeo = Integer.parseInt(ele.getText());
            if(countGeo > 0) {
                ele.findElement(editPen).click();
                //checkSortOrder(zonesTable, zones);
                driver.findElement(countriesButton).click();
            }
        }
    }


    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}


