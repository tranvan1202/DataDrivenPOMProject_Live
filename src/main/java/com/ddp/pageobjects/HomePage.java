package com.ddp.pageobjects;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.ddp.base.BaseClass;
import com.ddp.actiondriver.ValidateUIHelpers;

import java.util.ArrayList;

import static com.ddp.actiondriver.ValidateUIHelpers.getSiteCodeFromUrl;

public class HomePage {
    public static WebDriver driver;
    public static String winHandleBefore;
    private ValidateUIHelpers validateUIHelpers;
    //  Element at Home page
    private By takeOverVideo2024 = By.id("takeover202401");
    private By takeOverVideo2024CloseBtn = By.id("takeover202401__close-btn");
    private By acceptCookiesPopupBtn = By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[2]/a");
    private By switchToAllTab = By.xpath("//*[@id=\"result-list-container\"]/div[1]/div/ul/li[1]/a");
    private By iFrameOfText = By.xpath("//iframe[@title='Proactive Prompt']");
    private By iFramePopupText = By.xpath("//div[@class='frame-content']");
    private By iFrameClosePopupBtn = By.xpath("//button[@class='center-x-y flex-column-container center-x-y e1di9suy2 css-1s0vtz8 e1nedcse0']");
    private By bestMatchSelectionSectionTitle = By.xpath("(//div[@class='best-match__contents']/h3)[2]");
    private By bestMatchSelectionSection = By.xpath("(//a[@class='best-match__wrap'])[2]");
    public static String siteCode ="";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        validateUIHelpers = new ValidateUIHelpers(this.driver);
    }
    public String isShowingBestMatch(WebDriver driver, String url, String searchKeyword) throws InterruptedException {
        winHandleBefore = driver.getWindowHandle();
        driver.manage().window().maximize();
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("window.open()");

        ArrayList<String> browserTabs = Lists.newArrayList(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        String urlSearch = url + searchKeyword;
        driver.get(urlSearch);
        String bestMatchProductTitle = "";
        String redirectedUrl = "";
        //Nếu URL lấy đc từ Data Test Sheet khớp với URL hiện tại của driver
        if (validateUIHelpers.verifyUrl(url)) {
            System.out.printf("%s,%s,%s,%s,%s,%s\n","Sub","PC","MO","Search Keyword","Product Title","Product URL");
            siteCode = getSiteCodeFromUrl(driver,url);
            //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            if (validateUIHelpers.verifyElementExist(takeOverVideo2024)) {
                validateUIHelpers.clickElement(takeOverVideo2024CloseBtn);
            }
            if (validateUIHelpers.verifyElementExist(acceptCookiesPopupBtn)) {
                validateUIHelpers.clickElement(acceptCookiesPopupBtn);
            }
            if (validateUIHelpers.verifyElementExist(switchToAllTab)) {
                //WebElement clickAll = driver.findElement(switchToAllTab);
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("arguments[0].click()", driver.findElement(switchToAllTab));
            }
            //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            if (validateUIHelpers.verifyElementExist(iFrameOfText)) {
                driver.switchTo().defaultContent();
                driver.switchTo().frame(driver.findElement(iFrameOfText));
                while (validateUIHelpers.verifyElementExist(iFramePopupText)) {
                    validateUIHelpers.clickElement(iFrameClosePopupBtn);
                    //WebElement clickOnClosePopupTextButton = driver.findElement(iFrameClosePopupBtn);
                    //clickOnClosePopupTextButton.click();
                }
            }
            if (validateUIHelpers.verifyElementExist(bestMatchSelectionSection)) {
                redirectedUrl = driver.findElement(bestMatchSelectionSection).getAttribute("href");
                bestMatchProductTitle = driver.findElement(bestMatchSelectionSectionTitle).getText();
                System.out.printf("%s,%s,%s,%s,%s,%s\n",siteCode.toUpperCase(),"Yes","Yes",searchKeyword,bestMatchProductTitle,redirectedUrl);
                //Thread.sleep(1000);
                return bestMatchProductTitle;
            } else {
                bestMatchProductTitle = "";
                redirectedUrl = driver.findElement(bestMatchSelectionSection).getAttribute("href");
                System.out.printf("%s,%s,%s,%s,%s,%s\n",siteCode.toUpperCase(),"Yes","Yes",searchKeyword,bestMatchProductTitle,redirectedUrl);
                //Thread.sleep(1000);
                return bestMatchProductTitle;
            }
        }
        System.out.println("URL đã bị redirected");
        return bestMatchProductTitle;
    }


}
