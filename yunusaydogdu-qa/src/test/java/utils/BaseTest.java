package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.PageLoadStrategy;
import org.testng.Assert;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments(
                "--start-maximized",
                "--disable-notifications",
                "--disable-infobars",
                "--disable-gpu",
                "--no-sandbox",
                "--remote-allow-origins=*"
        );
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Küçük yardımcı
    protected void assertUrl(String expected) {
        String actual = driver.getCurrentUrl();
        Assert.assertTrue(actual.startsWith(expected),
                "URL beklenenle başlamıyor! expected startsWith: " + expected + " | actual: " + actual);
    }
}