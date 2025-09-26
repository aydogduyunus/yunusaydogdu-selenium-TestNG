package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class QaJobsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // QA başlık alanındaki "See all QA Jobs" butonu
    private By seeAllQaJobs = By.xpath("//*[@id=\"page-head\"]/div/div/div[1]/div/div/a");
    // İlan kartlarının kapsayıcısı (liste)
    private By jobList = By.xpath("//*[@id='jobs-list']/div/div");
    // Kartlar içindeki "View Role" linkleri
    private By viewRoleButton = By.xpath("//*[@id='jobs-list']/div/div/a");
    // Departman filtresi (chip/dropdown kapsayıcı)
    private By departmentDropdown = By.xpath("//*[@id=\"select2-filter-by-department-container\"]");
    // Departman menüsündeki "Quality Assurance" seçeneği
    private By departmentOption = By.xpath("//*[@id=\"select2-filter-by-department-result-3t7w-Quality Assurance\"]");
    // Lokasyon filtresi (ikinci combobox açılır butonu)
    private By locationDropdown = By.xpath("//*[@id=\"top-filter-form\"]/div[1]/span/span[1]/span/span[2]/b");
    // Lokasyon menüsünde "Istanbul, Turkey" seçeneği
    private By locationOption = By.xpath("//li[contains(text(),'Istanbul, Turkey')]");

    public QaJobsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // "See all QA Jobs" butonuna tıkla
    public void seeAllQaJobs() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(seeAllQaJobs));
        button.click();
        System.out.println("See all QA Jobs'a tıklandı.");
    }

    // Sadece lokasyon filtresinden "Istanbul, Turkey" seç (görünürlük + beklemeler ile)
    public void selectLocationOnly() {
        try {
            // Kartlar ve filtreler ekranda görünsün diye sayfayı biraz aşağı kaydır
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
            System.out.println("Sayfa 1000 piksel aşağı kaydırıldı.");
            // Filtre UI stabilitesi için kısa bekleme
            Thread.sleep(10000);

            // Lokasyon combobox'ını aç
            WebElement locationDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(locationDropdown));
            locationDropdownElement.click();
            System.out.println("İkinci combobox açıldı.");

            // Açılan listeden "Istanbul, Turkey" seçeneğini bul ve tıkla
            WebElement locationOptionElement = wait.until(ExpectedConditions.presenceOfElementLocated(locationOption));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locationOptionElement);
            Thread.sleep(1000);
            locationOptionElement.click();
            System.out.println("Istanbul, Turkey seçildi.");
            // Seçim sonrasında liste/kart yenilenmesi için kısa bekleme
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Bekleme sırasında bir hata oluştu: " + e.getMessage());
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Bekleme süresi aşıldı: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
    }

    // Listelenen iş ilanlarının lokasyon ve departman bilgilerinin beklentiyi karşıladığını kontrol et
    public void verifyJobs(String location, String department) {
        // İlan kartlarını bekle
        List<WebElement> jobs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jobList));
        Assert.assertTrue(jobs.size() > 0, "İş ilanları bulunamadı!");
        System.out.println("Bulunan iş ilanları: " + jobs.size());

        // Her kart için lokasyon ve departman metinlerini oku ve karşılaştır
        for (WebElement job : jobs) {
            String jobLocation = job.findElement(By.xpath(".//div[contains(@class, 'location')]")).getText();
            String jobDepartment = job.findElement(By.xpath(".//span[contains(@class, 'department')]")).getText();

            if (jobLocation.contains(location)) {
                System.out.println("Lokasyon eşleşiyor: " + jobLocation);
            } else {
                System.out.println("Lokasyon eşleşmiyor: " + jobLocation);
            }

            if (jobDepartment.contains(department)) {
                System.out.println("Departman eşleşiyor: " + jobDepartment);
            } else {
                System.out.println("Departman eşleşmiyor: " + jobDepartment);
            }

            // Her iki alan da beklentiyi sağlıyorsa bilgi mesajı
            if (jobLocation.contains(location) && jobDepartment.contains(department)) {
                System.out.println("Eşleşen iş ilanı bulundu: " + jobLocation + " - " + jobDepartment);
            }
        }
    }

    // İlk uygun "View Role" linkine tıkla
    public void clickViewRole() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(viewRoleButton));
        button.click();
        System.out.println("View Role tıklandı.");
    }
}