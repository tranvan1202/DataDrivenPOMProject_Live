package com.ddp.pageobjects;

import com.ddp.actiondriver.Action;
import com.ddp.base.BaseClass;
import com.ddp.utility.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PricePromiseClaimPage extends BaseClass {
    Action action= new Action();
    public void setEmptyValuesToAttributeValues(ITestContext ctx) {
        //Gắn variable value vào test value
        // Reset attribute values
        ctx.setAttribute("resultTCMethod","");
        ctx.setAttribute("resultTCUrl","");
        ctx.setAttribute("resultTCLocator","");
        ctx.setAttribute("resultTCExpected","");
        ctx.setAttribute("resultTCActualText","");
        ctx.setAttribute("resultTCActualTextLength","");
    }
    public void setTestValuesToAttributeValues(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected) {
        //Gắn variable value vào test value
        ctx.setAttribute("resultTCMethod",tcMethod);
        ctx.setAttribute("resultTCUrl",tcUrl);
        ctx.setAttribute("resultTCLocator",tcLocator);
        ctx.setAttribute("resultTCExpected",tcExpected);
    }
    public void setActualValuesToAttributeValues(ITestContext ctx, String inputtedActualText, String inputtedActualTextLength) {
        ctx.setAttribute("resultTCActualText", inputtedActualText);
        ctx.setAttribute("resultTCActualTextLength", inputtedActualTextLength);
    }

    public String getEmailErrorMessageByDriver(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) throws InterruptedException {
        setTestValuesToAttributeValues(ctx,tcMethod, tcUrl,tcLocator,tcExpected);
        CommonPageGG_New.printOutTheDpValues(tcMethod, tcUrl,tcLocator,tcExpected);
        String tcActualText = "";
        String tcActualTextLength;
        driver.get(tcUrl);
        //Đợi Element text
        Thread.sleep(1000);
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);

        // Lấy Canonical link để compare
        String sLinkCan = CommonPageGG_New.getCanonicalLinkByDriver(driver);

        //Kiểm tra xem page có bị redirected không?
        if(resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lưu Elements List khi ng dùng input Xpath
            List<WebElement> inputtedElement = driver.findElements(By.xpath(tcLocator));
            Thread.sleep(500);
            if(!inputtedElement.isEmpty()) {
                WebElement element = driver.findElement(By.xpath(tcLocator));
                action.moveToElement(getDriver(),element);
                // Implicit wait
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                // Interact with the email field to trigger error
                WebElement emailField = driver.findElement(By.xpath("//div[@class='control']/input[@type='email']"));
                emailField.sendKeys("Test");
                emailField.clear();  // Clear the field to trigger validation
                emailField.sendKeys(" "); // Trigger focus out by sending a space and deleting it
                emailField.sendKeys("\b");
                // Find and store the email error message
                WebElement emailErrorElement = driver.findElement(By.xpath("//div[@class='control']/input[@type='email']/following-sibling::p"));
                if (emailErrorElement.isDisplayed()) {
                    tcActualText = emailErrorElement.getText();
                }
                // Print out the error messages
                System.out.println("Email Error Message: " + tcActualText);
                tcActualTextLength = String.valueOf(tcActualText.length());
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }

    public String getOrderNumberErrorMessageByDriver(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) throws InterruptedException {
        setTestValuesToAttributeValues(ctx,tcMethod, tcUrl,tcLocator,tcExpected);
        CommonPageGG_New.printOutTheDpValues(tcMethod, tcUrl,tcLocator,tcExpected);
        String tcActualText = "";
        String tcActualTextLength;
        driver.get(tcUrl);
        //Đợi Element text
        Thread.sleep(1000);
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);

        // Lấy Canonical link để compare
        String sLinkCan = CommonPageGG_New.getCanonicalLinkByDriver(driver);

        //Kiểm tra xem page có bị redirected không?
        if(resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lưu Elements List khi ng dùng input Xpath
            List<WebElement> inputtedElement = driver.findElements(By.xpath(tcLocator));
            Thread.sleep(500);
            if(!inputtedElement.isEmpty()) {
                WebElement element = driver.findElement(By.xpath(tcLocator));
                action.moveToElement(getDriver(),element);
                // Implicit wait
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                // Interact with the order number field to trigger error
                WebElement orderNumberField = driver.findElement(By.xpath("//div[@class='control']/input[@type='orderNumber']"));
                orderNumberField.sendKeys("Test");
                orderNumberField.clear();  // Clear the field to trigger validation
                orderNumberField.sendKeys(" "); // Trigger focus out by sending a space and deleting it
                orderNumberField.sendKeys("\b");

                // Find and store the order number error message
                WebElement orderNumberErrorElement = driver.findElement(By.xpath("//div[@class='control']/input[@type='orderNumber']/following-sibling::p"));
                if (orderNumberErrorElement.isDisplayed()) {
                    tcActualText = orderNumberErrorElement.getText();
                }
                // Print out the error messages
                System.out.println("Order Number Error Message: " + tcActualText);
                tcActualTextLength = String.valueOf(tcActualText.length());
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);

                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                //Assert.assertEquals("",tcExpected ,"element not found\n");
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }

    public String getMatchPriceErrorMessageByDriver(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) throws InterruptedException {
        setTestValuesToAttributeValues(ctx,tcMethod, tcUrl,tcLocator,tcExpected);
        CommonPageGG_New.printOutTheDpValues(tcMethod, tcUrl,tcLocator,tcExpected);
        String tcActualText = "";
        String tcActualTextLength;
        driver.get(tcUrl);
        //Đợi Element text
        Thread.sleep(1000);
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);

        // Lấy Canonical link để compare
        String sLinkCan = CommonPageGG_New.getCanonicalLinkByDriver(driver);

        //Kiểm tra xem page có bị redirected không?
        if(resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lưu Elements List khi ng dùng input Xpath
            List<WebElement> inputtedElement = driver.findElements(By.xpath(tcLocator));
            Thread.sleep(500);
            if(!inputtedElement.isEmpty()) {
                WebElement element = driver.findElement(By.xpath(tcLocator));
                action.moveToElement(getDriver(),element);

                // Implicit wait
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                // Interact with the SKU field to trigger error
                WebElement skuField = driver.findElement(By.xpath("//div[@class='control']/input[@name='matchPrice']"));
                skuField.sendKeys("Test");
                skuField.clear();  // Clear the field to trigger validation
                skuField.sendKeys(" "); // Trigger focus out by sending a space and deleting it
                skuField.sendKeys("\b");
                // Find and store the SKU error message
                WebElement skuErrorElement = driver.findElement(By.xpath("//div[@class='control']/input[@name='matchPrice']/following-sibling::p"));
                if (skuErrorElement.isDisplayed()) {
                    tcActualText = skuErrorElement.getText();
                }
                // Print out the error messages
                System.out.println("Match Price Error Message: " + tcActualText);
                tcActualTextLength = String.valueOf(tcActualText.length());
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                //Assert.assertEquals("",tcExpected ,"element not found\n");
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }

    public String getMatchPriceURLErrorMessageByDriver(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) throws InterruptedException {
        setTestValuesToAttributeValues(ctx,tcMethod, tcUrl,tcLocator,tcExpected);
        CommonPageGG_New.printOutTheDpValues(tcMethod, tcUrl,tcLocator,tcExpected);
        String tcActualText = "";
        String tcActualTextLength;
        driver.get(tcUrl);
        //Đợi Element text
        Thread.sleep(1000);
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);

        // Lấy Canonical link để compare
        String sLinkCan = CommonPageGG_New.getCanonicalLinkByDriver(driver);

        //Kiểm tra xem page có bị redirected không?
        if(resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lưu Elements List khi ng dùng input Xpath
            List<WebElement> inputtedElement = driver.findElements(By.xpath(tcLocator));
            Thread.sleep(500);
            if(!inputtedElement.isEmpty()) {
                WebElement element = driver.findElement(By.xpath(tcLocator));
                action.moveToElement(getDriver(),element);
                // Implicit wait
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                // Interact with the price match URL field to trigger error
                WebElement priceMatchURLField = driver.findElement(By.xpath("//div[@class='field']/input[@type='priceMatchURL']"));
                priceMatchURLField.sendKeys("Test");
                priceMatchURLField.clear();  // Clear the field to trigger validation
                priceMatchURLField.sendKeys(" "); // Trigger focus out by sending a space and deleting it
                priceMatchURLField.sendKeys("\b");
                // Find and store the price match URL error message
                WebElement priceMatchURLErrorElement = driver.findElement(By.xpath("//div[@class='field']/input[@type='priceMatchURL']/following-sibling::p"));
                if (priceMatchURLErrorElement.isDisplayed()) {
                    tcActualText = priceMatchURLErrorElement.getText();
                }
                // Print out the error messages
                System.out.println("Price Match URL Error Message: " + tcActualText);
                tcActualTextLength = String.valueOf(tcActualText.length());
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                //Assert.assertEquals("",tcExpected ,"element not found\n");
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }
    public String getPlaceHolderTextByDriver(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) throws InterruptedException {
        setTestValuesToAttributeValues(ctx,tcMethod, tcUrl,tcLocator,tcExpected);
        CommonPageGG_New.printOutTheDpValues(tcMethod, tcUrl,tcLocator,tcExpected);
        String tcActualText = "";
        String tcActualTextLength;
        driver.get(tcUrl);
        //Đợi Element text
        Thread.sleep(1000);
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);

        // Lấy Canonical link để compare
        String sLinkCan = CommonPageGG_New.getCanonicalLinkByDriver(driver);

        //Kiểm tra xem page có bị redirected không?
        if(resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lưu Elements List khi ng dùng input Xpath
            List<WebElement> inputtedElement = driver.findElements(By.xpath(tcLocator));
            Thread.sleep(500);
            if(!inputtedElement.isEmpty()) {
                WebElement element = driver.findElement(By.xpath(tcLocator));
                action.moveToElement(getDriver(),element);
                // Implicit wait
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                // Element is present
                for (WebElement webInputtedElementText : inputtedElement) {
                    tcActualText= webInputtedElementText.getAttribute("placeholder");
                    // Print out the error messages
                    System.out.println(inputtedElement + " Placeholder text: " + tcActualText);
                    tcActualTextLength = String.valueOf(tcActualText.length());
                    setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                }
                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                //Assert.assertEquals("",tcExpected ,"element not found\n");
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }
    public String getURLUploadImageAP(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) throws InterruptedException {
        setTestValuesToAttributeValues(ctx,tcMethod, tcUrl,tcLocator,tcExpected);
        CommonPageGG_New.printOutTheDpValues(tcMethod, tcUrl,tcLocator,tcExpected);
        String tcActualText = "";
        String tcActualTextLength;
        driver.get(tcUrl);
        //Đợi Element text
        Thread.sleep(1000);
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);

        // Lấy Canonical link để compare
        String sLinkCan = CommonPageGG_New.getCanonicalLinkByDriver(driver);

        //Kiểm tra xem page có bị redirected không?
        if(resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lưu Elements List khi ng dùng input Xpath
            List<WebElement> inputtedElement = driver.findElements(By.xpath(tcLocator));
            Thread.sleep(500);
            if(!inputtedElement.isEmpty()) {
                WebElement element = driver.findElement(By.xpath(tcLocator));
                action.moveToElement(getDriver(),element);
                // Implicit wait
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                // Element is present
                for (WebElement webInputtedElementText : inputtedElement) {
                    tcActualText= webInputtedElementText.getAttribute("placeholder");
                    // Print out the error messages
                    System.out.println(inputtedElement + " Placeholder text: " + tcActualText);
                    tcActualTextLength = String.valueOf(tcActualText.length());
                    setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                }
                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
                //Assert.assertEquals("",tcExpected ,"element not found\n");
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            setActualValuesToAttributeValues(ctx,tcActualText,tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }
}
