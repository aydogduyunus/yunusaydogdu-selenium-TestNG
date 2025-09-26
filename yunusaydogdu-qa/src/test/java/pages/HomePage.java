package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Ana navigasyondaki Insider logosu (sayfanın yüklendiğini doğrulamak için)
    private By logo = By.xpath("//*[@id='navigation']/div[2]/a[1]/img");
    // Üst menüdeki "Company" sekmesi (dropdown açmak için)
    private By companyMenu = By.xpath("//a[@id='navbarDropdownMenuLink' and contains(text(),'Company')]");
    // Company menüsündeki "Careers" linki
    private By careersLink = By.xpath("//*[@id='navbarNavDropdown']/ul[1]/li[6]/div/div[2]/a[2]");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Ana sayfanın temel görünürlük kontrolleri (logo görünür mü?)
    public void verifyHomePage() {
        WebElement logoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(logo));
        Assert.assertTrue(logoElement.isDisplayed(), "Home Page yüklenemedi!");
        System.out.println("Ana sayfa yüklendi.");
    }

    // Üst menüdeki Company sekmesini aç
    public void clickCompanyMenu() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(companyMenu));
        menu.click();
        System.out.println("Company menüsü tıklandı.");
    }

    // Company menüsünden Careers sayfasına geçiş
    public void clickCareers() {
        WebElement careers = wait.until(ExpectedConditions.elementToBeClickable(careersLink));
        careers.click();
        System.out.println("Careers bağlantısı tıklandı.");
    }
}