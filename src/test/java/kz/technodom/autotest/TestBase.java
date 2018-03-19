package kz.technodom.autotest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class TestBase {
    WebDriver driver;
    WebDriverWait webDriverWait;

    protected static String getPropertiesValue(String locator) throws IOException {
        Properties properties = new Properties();
        try {
            InputStream fileInputStream = null;
            try {
                File file = new File("src\\test\\recourses\\testproperties.properties");
                properties.load(new FileReader(file));
                return properties.getProperty(locator);
            } finally {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeTest
    public void openBrowser() throws IOException {
        String spec = null;
        String property = System.getProperty("browser");
        if (property != null && property.length() > 0) {
            spec = property;
        } else {
            spec = "chrome";
        }

        if (spec.contains("chrome")) {
            System.setProperty(getPropertiesValue("SystemPropertyChrome"), getPropertiesValue("DriverLinkChrome"));
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            webDriverWait = new WebDriverWait(driver, 10);
        } else if (spec.contains("firefox")) {
            System.setProperty(getPropertiesValue("SystemPropertyFirefox"), getPropertiesValue("DriverLinkFirefox"));
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            webDriverWait = new WebDriverWait(driver, 10);
        } else {
            System.setProperty(getPropertiesValue("SystemPropertyChrome"), getPropertiesValue("DriverLinkChrome"));
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            webDriverWait = new WebDriverWait(driver, 10);
        }
    }

    protected void init() {

        String url = null;
        String property = System.getProperty("host.url");
        if (property != null && property.length() > 0) {
            url = property;
        } else {
            url = "https://www.technodom.kz";
        }
        driver.get(url);
    }

    protected void checkout() {
        driver.findElement(By.id("register.phone")).sendKeys("7770000000");
        driver.findElement(By.id("register.email")).sendKeys("test1234567899876543211@mail.ru");
        driver.findElement(By.id("paymentType3")).click();
        driver.findElement(By.name("address")).sendKeys("Тест, Алматы, Курмангазы, 178а");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='success-text']")).getText().contains("Уважаемый Клиент, Ваш заказ"), true);
    }

    protected void selectAndgoToCheckout() throws InterruptedException {
        List<WebElement> list = driver.findElements(By.xpath("//div[@data-category='Смартфоны']"));
        int random = ThreadLocalRandom.current().nextInt(0, list.size() - 3);
        String priceText = list.get(random).getAttribute("data-price");
        String result = priceText.substring(0, priceText.indexOf("."));
        int priceInCategory = Integer.parseInt(result);

        list.get(random).click();


        WebElement buyButton = driver.findElement(By.xpath("//a[@class='pay-button product-description__pay-button js-button-buy addToCartEvent']"));
        Assert.assertEquals(priceInCategory, Integer.parseInt(buyButton.getAttribute("data-price")));
        buyButton.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//a[@class='cus-btn cus-btn__small cus-btn__full']")).click();
    }

    protected void goToCategories(String locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath(locator)));
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}
