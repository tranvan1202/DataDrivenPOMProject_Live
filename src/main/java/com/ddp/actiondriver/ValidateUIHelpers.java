package com.ddp.actiondriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ValidateUIHelpers {

    private WebDriver driver;
    private WebDriverWait wait;

    public ValidateUIHelpers(WebDriver _driver){
        driver = _driver;
        wait = new WebDriverWait(driver,Duration.ofSeconds(30));
    }

    public void setText(By element, String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(element).sendKeys(value);
    }

    public void clickElement(By element){
        //wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(element).click();
    }

    public boolean verifyUrl(String url)
    {
        System.out.println("Current URL:"+ driver.getCurrentUrl());
        System.out.println("Input URL:"+ url);

        return driver.getCurrentUrl().contains(url); //True/False
    }
    public boolean verifyElementText(By element, String textValue){
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver.findElement(element).getText().equals(textValue); //True/False
    }
    public boolean verifyElementContainText(By element, String textValue){
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver.findElement(element).getText().contains(textValue); //True/False
    }
    public boolean verifyElementExist(By element){
        //Tạo list lưu tất cả đối tượng WebElement
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> listElement = driver.findElements(element);

        int total = listElement.size();

        if(total > 0){
            return true;
        }

        return false;
    }

    // Wait

    public void waitForPageLoaded(){
        // wait for jQuery to loaded
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(jQueryLoad);
            wait.until(jsLoad);
        } catch (Throwable error) {
            Assert.fail("Quá thời gian load trang.");
        }

    }

    public static String getSiteCodeFromUrl(WebDriver driver, String url) {
        String siteCode = (driver.getCurrentUrl().substring(url.toString().indexOf(".com/") + 5)).substring(0, 2);
        return siteCode;
    }
}