package com.ddp.testcases;

import com.ddp.base.BaseClass;
import com.ddp.dataprovider.DataProviderFactory;
import com.ddp.pageobjects.CommonPage;
import com.ddp.pageobjects.LoginIndexPage;
import com.ddp.pageobjects.LoginPage;
import com.ddp.pageobjects.LoginQAPage;
import com.ddp.utility.Log;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertTrue;

public class CommonPageTest extends BaseClass {
    private CommonPage commonPage;
    @Parameters("browser")
    @BeforeMethod(groups = {"Smoke","Sanity","Regression"})
    public void setup(String browser) {
        launchApp(browser);
    }

    @AfterMethod(groups = {"Smoke","Sanity","Regression"})
    public void tearDown() {
        getDriver().quit();
    }

    @Test(priority = 1, dataProvider = "urlList", dataProviderClass = DataProviderFactory.class)
    public void isInputtedLinkBroken(String param) {
        commonPage = new CommonPage();
        assertTrue(commonPage.isLinkBrokenOnWWW(param));
    }

}
