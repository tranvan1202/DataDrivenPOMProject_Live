package com.ddp.testcases;

import com.google.common.collect.Lists;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ProductPriceAssertionTest {

    public static void main(String[] args) throws InterruptedException {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Set up WebDriver
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            driver.navigate().to("https://wds.samsung.com/wds/sso/login/forwardLogin.do");
            driver.manage().addCookie(new Cookie("JSESSIONID", "0FA7758481C0E19B8A6798C03708C048"));
            sleep(3);
            driver.navigate().to("https://wds.samsung.com/wds/sso/login/ssoLoginSuccess.do");

            //Click nút QA
            WebElement submitQA = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/div[1]/div/dl[4]/dd[1]"));
            if (submitQA.isDisplayed()) {
                submitQA.click();
            }
            sleep(2000);
            ArrayList<String> browserTabs = Lists.newArrayList(driver.getWindowHandles());
            driver.switchTo().window(browserTabs.get(1));

            // Now open the static page in the same tab (to preserve context)
            driver.get("https://p6-qa.samsung.com/ph/offer/online/2024/galaxy-grand-holiday-sale/");

            // Store the handle for the static page tab (Samsung offer page)
            String staticPageTab = driver.getWindowHandle();

            // Find all product cards
            List<WebElement> productCards = driver.findElements(By.xpath("//div[contains(@class,'feature-column-carousel__feature bg-white')]"));

            // Iterate through each product card
            for (WebElement productCard : productCards) {
                try {
                    // Get the product price from the card
                    WebElement cardPriceElement = productCard.findElement(By.xpath(".//div[contains(@class,'feature-column-carousel__sub-title')]/h3"));
                    String cardPrice = cardPriceElement.getText().trim();

                    // Get the CTA button URL
                    WebElement ctaButton = productCard.findElement(By.xpath(".//a[contains(@aria-label,'Buy now')][normalize-space()='Buy now']"));
                    String productDetailUrl = ctaButton.getAttribute("href");

                    // Open the PDP in a new tab
                    ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", productDetailUrl);

                    // Switch to the newly opened tab (PDP)
                    Set<String> allTabs = driver.getWindowHandles();
                    for (String tab : allTabs) {
                        if (!tab.equals(staticPageTab)) {  // Check against the static page tab handle
                            driver.switchTo().window(tab);
                            break;
                        }
                    }

                    // Wait for the PDP to load and fetch the price
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    WebElement detailPagePriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[@class='cost-box__price-now']")));
                    String detailPagePrice = detailPagePriceElement.getText().trim();

                    // Print the prices for comparison
                    System.out.println("Card Price: " + cardPrice + " | PDP Price: " + detailPagePrice);

                    // Close the PDP tab and switch back to the static page tab
                    driver.close();
                    driver.switchTo().window(staticPageTab);

                } catch (NoSuchElementException e) {
                    System.out.println("Element not found for a product card. Skipping to the next.");
                }
            }

        } finally {
            // Clean up and quit the browser
            driver.quit();
        }
    }
}






