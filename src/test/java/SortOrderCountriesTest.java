import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SortOrderCountriesTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private By countriesForm = By.xpath("//form[@name = 'countries_form']");
    private By editGeoZonePage = By.xpath("//table[@id = 'table-zones']");
    private By countriesButton = By.xpath("//span[contains(text(), 'Countries')]");
    private By geoZonesButton = By.xpath("//span[contains(text(), 'Geo Zones')]");
    private By countZonesByZonesPage = By.xpath("//tr[@class = 'row']/td[4]");
    private By countryLink = By.xpath(".//tr[@class = 'row']//a[not(contains(@title ,'Edit'))]");
    private By editPen = By.xpath("./following-sibling::td");
    private By countOfGeoZones = By.xpath("//tr[@class = 'row']/td[6]");
    private By zonesTable = By.xpath("//table[@id = 'table-zones']");
    private By zones = By.xpath(".//td[3]");


    private void checkSortOrder(By mainElement, By childElement){
        ArrayList<String> originalList = new ArrayList<String>();
        List<WebElement> listCountries = driver.findElement(mainElement).findElements(childElement);
        for (WebElement ele : listCountries) {
            originalList.add(ele.getText());
        }
        originalList.removeAll(Arrays.asList(""));
        ArrayList<String> sortedList = new ArrayList<String>();
        for (String s : originalList) {
            sortedList.add(s);
        }
        sortedList.removeAll(Arrays.asList(""));
        Collections.sort(sortedList);
        Assert.assertTrue(sortedList.equals(originalList));
    }

    private void checkCountriesWithZones(By countZones, By zonesTable, By zones, By clickButton, By editPen) {
        List<WebElement> countries = driver.findElements(countZones);
        for (int i = 0; i < countries.size(); i++){
            countries = driver.findElements(countZones);
            int countZone = Integer.parseInt(countries.get(i).getText());
            if(countZone > 0) {
                countries.get(i).findElement(editPen).click();
                checkSortOrder(zonesTable, zones);
                buttonClick(clickButton);
            }
        }
    }

    private void buttonClick(By buttonName) {
        driver.findElement(buttonName).click();
    }

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

    }

    @Test
    public void test_1() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
//        а) проверить, что страны расположены в алфавитном порядке
        checkSortOrder(countriesForm, countryLink);
//        б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке
        checkCountriesWithZones(countOfGeoZones, zonesTable, zones, countriesButton, editPen);
//        на странице зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
        buttonClick(geoZonesButton);
        checkCountriesWithZones(countZonesByZonesPage, );
    }

    @AfterTest
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }
}


