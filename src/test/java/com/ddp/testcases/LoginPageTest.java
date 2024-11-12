package com.ddp.testcases;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.ddp.base.BaseClass;
import com.ddp.pageobjects.LoginPage;
import com.ddp.utility.Log;

public class LoginPageTest extends BaseClass {
    private LoginPage loginPage;
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
    public void verifyLoginPageTitle() {
        loginPage = new LoginPage();
        Log.startTestCase("verifyLoginPageTitle");
        String actTitle= loginPage.getLoginPageTitle();
        Assert.assertEquals(actTitle, "WMC");
        Log.endTestCase("verifyLoginPageTitle");
    }

}
