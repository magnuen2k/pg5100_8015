package no.kristiania.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SeleniumLocalIT extends SeleniumTestBase {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        driver = SeleniumDriverHandler.getChromeDriver();

        assumeTrue(driver != null, "Cannot find driver");
    }

    @AfterAll
    public static void tearDown() {
        if( driver != null) {
            driver.close();
        }
    }

    @Override
    protected WebDriver getDriver() {
        return driver;
    }

    @Override
    protected String getServerHost() {
        return "localhost";
    }

    @Override
    protected int getServerPort() {
        return port;
    }
}