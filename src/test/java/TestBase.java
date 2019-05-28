import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestBase {

    public Application app;

    @BeforeTest
    public void start() {
        app = new Application();
    }

    @AfterTest
    public void stop() {
        app.quit();
    }
}

