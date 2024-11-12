package com.ddp.testcases;

import com.ddp.base.BaseClass;
import com.ddp.dataprovider.DataProviderFactory;
import com.ddp.pageobjects.*;
import com.ddp.utility.GSheetListener_New;
import com.ddp.utility.StringUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@Listeners(GSheetListener_New.class)
public class PricePromiseClaimPageTest extends BaseClass {
    private LoginPage loginPage;
    private LoginIndexPage loginIndexPage;
    private LoginQAPage loginQAPage;
    private PricePromiseClaimPage pricePromiseClaimPage;
    @Parameters({"browser"})
    @BeforeClass(groups = {"Smoke","Sanity","Regression"})
    public void setUp(String browser) {
        // Đã khởi tạo browser hết rồi kể cả wait, phóng to màn hình,...
        launchApp(browser);
    }
    @Test(priority = 1, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void loginToP6QA(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        System.out.println(getDriver());
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        loginPage = new LoginPage();
        loginIndexPage = loginPage.passSessionIntoLogin();
        loginQAPage = loginIndexPage.clickSubmitQALink();
        pricePromiseClaimPage = loginQAPage.loginAndGoToPPCPage();
        // Reset attribute values
        pricePromiseClaimPage.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
    }

    @Test(priority = 2, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyEmailErrorMessageText(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        pricePromiseClaimPage = loginQAPage.loginAndGoToPPCPage();
        // Reset attribute values
        pricePromiseClaimPage.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String emailErrorMessage = pricePromiseClaimPage.getEmailErrorMessageByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        Assert.assertEquals(emailErrorMessage, tcExpected);
    }

    @Test(priority = 3, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyOrderNumberErrorMessageText(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        pricePromiseClaimPage = loginQAPage.loginAndGoToPPCPage();
        // Reset attribute values
        pricePromiseClaimPage.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String orderNumberErrorMessageByDriver = pricePromiseClaimPage.getOrderNumberErrorMessageByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        Assert.assertEquals(orderNumberErrorMessageByDriver, tcExpected);
    }

    @Test(priority = 4, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyMatchPriceErrorMessageText(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        pricePromiseClaimPage = loginQAPage.loginAndGoToPPCPage();
        // Reset attribute values
        pricePromiseClaimPage.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String matchPriceErrorMessageByDriver = pricePromiseClaimPage.getMatchPriceErrorMessageByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        Assert.assertEquals(matchPriceErrorMessageByDriver, tcExpected);
    }

    @Test(priority = 5, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyMatchPriceURLErrorMessageText(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        pricePromiseClaimPage = loginQAPage.loginAndGoToPPCPage();
        // Reset attribute values
        pricePromiseClaimPage.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String matchPriceURLErrorMessageByDriver = pricePromiseClaimPage.getMatchPriceURLErrorMessageByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        Assert.assertEquals(matchPriceURLErrorMessageByDriver, tcExpected);
    }
    @Test(priority = 6, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyPlaceHolderText(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        pricePromiseClaimPage = loginQAPage.loginAndGoToPPCPage();
        // Reset attribute values
        pricePromiseClaimPage.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String placeHolderTextByDriver = pricePromiseClaimPage.getPlaceHolderTextByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        Assert.assertEquals(placeHolderTextByDriver, tcExpected);
    }
}
