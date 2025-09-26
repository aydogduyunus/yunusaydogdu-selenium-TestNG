package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.BaseTest;

import java.time.Duration;

public class InsiderTestCase extends BaseTest {
    private WebDriverWait wait;

    @BeforeMethod
    public void setUpTest() {
        setUp();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void testInsiderCareersFlow() {
        driver.get("https://useinsider.com/");

        // Home Page
        HomePage homePage = new HomePage(driver, wait);
        homePage.verifyHomePage();
        homePage.clickCompanyMenu();
        homePage.clickCareers();

        // Careers Page
        CareersPage careersPage = new CareersPage(driver, wait);
        assertUrl("https://useinsider.com/careers/");
        careersPage.clickSeeAllTeams();
        careersPage.verifyLocations();
        careersPage.verifyLifeAtInsider();
        careersPage.clickQa();

        // Qa Page
        QaJobsPage  qaJobsPage = new QaJobsPage (driver, wait);
        qaJobsPage.seeAllQaJobs();

        // === (EKLENEN BLOK) Departman chip’i "All" → "Quality Assurance" olana kadar bekle ===
        org.openqa.selenium.By deptChip = org.openqa.selenium.By.id("select2-filter-by-department-container");
        org.openqa.selenium.By jobsCards = org.openqa.selenium.By.xpath("//*[@id='jobs-list']/div/div");

        // Biraz aşağı kaydır ki chip ve kartlar görünüme gelsin
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100)");
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}

        // Chip görünür olsun
        org.openqa.selenium.WebElement chipEl = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(deptChip));

        // Chip metninin "Quality Assurance" olmasını bekle (otomatik dönüşüm)
        try {
            boolean qaSet = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(12))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions
                            .textToBePresentInElementLocated(deptChip, "Quality Assurance"));
            if (qaSet) System.out.println("Departman otomatik olarak 'Quality Assurance' oldu.");
        } catch (org.openqa.selenium.TimeoutException te) {
            System.out.println("Departman otomatik dönmedi, manuel seçim yapılıyor...");
            // Dropdown'u aç ve 'Quality Assurance' seç
            chipEl.click();
            org.openqa.selenium.WebElement qaOption = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(
                    org.openqa.selenium.By.xpath("//li[contains(@id,'select2-filter-by-department-result')][normalize-space()='Quality Assurance']")));
            qaOption.click();
            // Chip metni QA oldu mu?
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                    .textToBePresentInElementLocated(deptChip, "Quality Assurance"));
        }

        // Liste yenilenmesini stabilize et
        java.util.List<org.openqa.selenium.WebElement> before =
                driver.findElements(jobsCards);
        if (!before.isEmpty()) {
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(6))
                        .until(org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf(before.get(0)));
            } catch (Exception ignored) {}
        }

        // Kartlar gerçekten yüklendi mi?
        java.util.List<org.openqa.selenium.WebElement> cards =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                        .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy(jobsCards));
        System.out.println("Kart sayısı: " + cards.size());
        // === (EKLENEN BLOK SONU) ===

        // QaJobsPage
        QaJobsPage jobsPage = new QaJobsPage (driver, wait);
        jobsPage.selectLocationOnly(); // Filtreleri uygula (combobox işlemleri)
        jobsPage.verifyJobs("Istanbul", "Quality Assurance");

        // --- Hover + düz tık: ilk kartın "View Role" butonu ---
        WebElement firstCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='jobs-list']/div/div[1]"))
        );
        new org.openqa.selenium.interactions.Actions(driver)
                .moveToElement(firstCard)
                .pause(java.time.Duration.ofMillis(300))
                .perform();

        WebElement viewRoleBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='jobs-list']/div/div[1]//a[contains(.,'View Role')]"))
        );
        viewRoleBtn.click();
        System.out.println("\"View Role\" tıklandı.");
// ------------------------------------------------------
    }

    @AfterMethod
    public void tearDownTest() {
        tearDown();
    }
}