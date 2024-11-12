package com.ddp.testcases;

import com.ddp.base.BaseClass;
import com.ddp.pageobjects.LoginIndexPage;
import com.ddp.pageobjects.LoginPage;
import com.ddp.pageobjects.LoginQAPage;
import com.ddp.utility.Log;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginQAPageTest extends BaseClass {
    private LoginPage loginPage;
    private LoginIndexPage loginIndexPage;
    private LoginQAPage loginQAPage;

    @Parameters("browser")
    @BeforeMethod(groups = {"Smoke","Sanity","Regression"})
    public void setup(String browser) {
        launchApp(browser);
    }

    @AfterMethod(groups = {"Smoke","Sanity","Regression"})
    public void tearDown() {
        getDriver().quit();
    }
    @Test(groups = "Smoke")
    public void verifyLoginQAPageTitle() throws InterruptedException {
        loginPage = new LoginPage();
        loginIndexPage = loginPage.passSessionIntoLogin();
        loginQAPage = loginIndexPage.clickSubmitQALink();

        Log.startTestCase("verifyLoginQAPageTitle");
        String actTitle= loginQAPage.getLoginQAPageTitle();
        Assert.assertEquals(actTitle, "Home Electronics | Home Appliances | Mobile | Computing |");
        Log.endTestCase("verifyLoginQAPageTitle");
    }
}
