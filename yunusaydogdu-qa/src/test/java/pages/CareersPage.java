package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class CareersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // "Locations" slider'ındaki her bir lokasyon öğesi
    private By locations = By.xpath("//*[@id='location-slider']/div/ul/li");
    // Sayfadaki "Life at Insider" başlığı (bölüm görünürlüğü için)
    private By lifeAtInsider = By.xpath("//h2[contains(@class, 'elementor-heading-title') and text()='Life at Insider']");
    // "See all teams" butonu (takım listesine gitmek için)
    private By seeAllTeamsButton = By.xpath("//*[@id=\"career-find-our-calling\"]/div/div/a");
    // "Quality Assurance" kartı başlığı (QA takım detayına geçmek için)
    private By clickQa = By.xpath("//*[@id=\"career-find-our-calling\"]/div/div/div[2]/div[12]/div[2]/a/h3");

    public CareersPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // "See all teams" butonuna güvenli tıklama (scroll + fallback JS click)
    public void clickSeeAllTeams() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='career-find-our-calling']/div/div/a")));
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Butonu görünür alana getir
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            // Normal tıklama dene
            element.click();
        } catch (ElementClickInterceptedException e) {
            // Herhangi bir overlay engeli varsa JS ile zorla tıklama
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    // "Locations" alanında en az bir öğe bulunduğunu doğrula
    public void verifyLocations() {
        List<WebElement> locationElements = driver.findElements(locations);
        Assert.assertTrue(locationElements.size() > 0, "Locations bulunamadı!");
        System.out.println("Locations doğrulandı: " + locationElements.size() + " adet.");
    }

    // "Life at Insider" bölüm başlığının görünürlüğünü doğrula
    public void verifyLifeAtInsider() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lifeAtInsider));
        Assert.assertTrue(element.isDisplayed(), "Life at Insider bölümü bulunamadı!");
        System.out.println("Life at Insider doğrulandı.");
    }

    // "Quality Assurance" takım kartına tıkla (scroll + fallback JS click)
    public void clickQa() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(clickQa));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Kartı görünür alana getir
        js.executeScript("arguments[0].scrollIntoView(true);", button);
        try {
            // Normal tıklama
            button.click();
        } catch (ElementClickInterceptedException e) {
            // Engellenirse JS ile tıkla
            js.executeScript("arguments[0].click();", button);
        }
        System.out.println("Quality Assurance tıklandı.");
    }
}