import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class UserRegistrationTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private By loginBox = By.xpath("//div[@id='box-account-login']");
    private By emailLogin = By.xpath(".//input[@name = 'email']");
    private By passwlLogin = By.xpath(".//input[@name = 'password']");
    private By loginButton = By.xpath(".//button[@name = 'login']");
    private By regLink = By.xpath(".//tr[5]/td/a");

    private By createAccountForm = By.xpath("//div[@id = 'create-account']");
    private By fieldFirstName = By.xpath(".//input[@name = 'firstname']");
    private By fieldLasttName = By.xpath(".//input[@name = 'lastname']");
    private By fieldAdress1 = By.xpath(".//input[@name = 'address1']");
    private By fieldPostcode = By.xpath(".//input[@name = 'postcode']");
    private By fieldCity = By.xpath(".//input[@name = 'city']");
    private By fieldEmail = By.xpath(".//input[@name = 'email']");
    private By fieldPhone = By.xpath(".//input[@name = 'phone']");
    private By fieldDesiredPassw = By.xpath(".//input[@name = 'password']");
    private By fieldConfirmPassw = By.xpath(".//input[@name = 'confirmed_password']");
    private By countrySelector = By.xpath(".//select[@name = 'country_code']");
    private By buttonCreateAcc = By.xpath(".//button[@name = 'create_account']");

    private By accountBox = By.xpath("//div[@id = 'box-account']");
    private By logOutButton = By.xpath(".//li[4]/a");




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
        WebElement login = driver.findElement(loginBox);
        login.findElement(regLink).click();
        //регистрация нового пользователя
        String emailUser = System.currentTimeMillis() / 1000L + "@testselenium.com";
        WebElement createAccForm = driver.findElement(createAccountForm);
        createAccForm.findElement(fieldFirstName).sendKeys("Василий");
        createAccForm.findElement(fieldLasttName).sendKeys("Иванович");
        createAccForm.findElement(fieldAdress1).sendKeys("ул.Старая");
        createAccForm.findElement(fieldPostcode).sendKeys("12345");
        createAccForm.findElement(fieldCity).sendKeys("Нью Йорк");
        createAccForm.findElement(fieldEmail).sendKeys(emailUser);
        createAccForm.findElement(fieldPhone).sendKeys("+79990001122");
        createAccForm.findElement(fieldDesiredPassw).sendKeys("123456");
        createAccForm.findElement(fieldConfirmPassw).sendKeys("123456");
        Select countriesList = new Select(driver.findElement(countrySelector));
        countriesList.selectByVisibleText("United States");
        createAccForm.findElement(buttonCreateAcc).click();
        // выход из аккаунта
        WebElement account = driver.findElement(accountBox);
        account.findElement(logOutButton).click();
        // вход в аккаунт
        login = driver.findElement(loginBox);
        login.findElement(emailLogin).sendKeys(emailUser);
        login.findElement(passwlLogin).sendKeys("123456");
        login.findElement(loginButton).click();
        // выход из аккаунта
        account = driver.findElement(accountBox);
        account.findElement(logOutButton).click();

    }

    @AfterTest
    public void stop() {

        if (driver != null) {
            driver.quit();

        }
    }
}
