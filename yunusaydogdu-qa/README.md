# Insider Careers Test Automation

Bu proje, **Insider Careers Page** akışını otomatikleştirmek için geliştirilmiştir.  
**Java + Selenium WebDriver + TestNG + Page Object Model (POM)** yaklaşımı kullanılmıştır.

---

## 🛠 Kullanılan Teknolojiler
- Java 11
- Selenium WebDriver 4.21.0
- TestNG
- Maven
- Page Object Model (POM)

---

## 📋 Test Senaryosu
Otomasyon testi aşağıdaki adımları kapsar:

1. Insider ana sayfasına git.
2. **Company** menüsünü aç ve **Careers** linkine tıkla.
3. Careers sayfasında:
    - **Locations** bölümünün göründüğünü doğrula.
    - **Life at Insider** bölümünün göründüğünü doğrula.
    - **Quality Assurance** takım kartına tıkla.
4. QA Jobs sayfasında:
    - **See all QA Jobs** butonuna tıkla.
    - Departman filtresinin otomatik olarak **Quality Assurance** olduğunu doğrula.
    - Listelenen iş ilanlarının **lokasyon** (`Istanbul, Turkiye`) ve **departman** (`Quality Assurance`) bilgilerini kontrol et.
    - İlk ilan kartında **View Role** butonuna tıkla.

---

## ✅ Doğrulamalar
- **HomePage:** Logo’nun görünür olması.
- **CareersPage:** Locations ve Life at Insider bölümlerinin doğrulanması.
- **QaJobsPage:**
    - Kart sayısının > 0 olması.
    - Her kartın **lokasyon** ve **departman** değerlerinin doğru olması.
    - İlk kartın “View Role” butonuna tıklanması.

---

## ⚠️ Notlar
- `Thread.sleep()` bazı yerlerde **UI stabilitesi** için bırakıldı. Gerçek projelerde explicit wait tercih edilmelidir.
- `QaJobsPage` içinde `selectLocationOnly` metodu var, ancak test akışında kullanılmamaktadır. İleride ihtiyaç olursa kullanılabilir.
- Locator’lar Insider’ın güncel DOM yapısına göre yazıldı. Website güncellenirse test kırılabilir.

---

## ▶️ Çalıştırma
1. Projeyi klonla:
   ```bash
   git clone <https://github.com/aydogduyunus/yunusaydogdu-selenium-TestNG.git>