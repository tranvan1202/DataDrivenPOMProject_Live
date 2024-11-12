/**
 * 
 */
package com.ddp.testcases;

import com.ddp.base.BaseClass;
import com.ddp.pageobjects.LoginIndexPage;
import com.ddp.pageobjects.LoginPage;
import com.ddp.utility.Log;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginIndexPageTest extends BaseClass {
	private LoginPage loginPage;
	private LoginIndexPage loginIndexPage;
	
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
	public void verifyLoginIndexPageTitle() throws InterruptedException {
		loginPage = new LoginPage();
		loginIndexPage = loginPage.passSessionIntoLogin();

		Log.startTestCase("verifyLoginIndexPageTitle");
		String actTitle= loginIndexPage.getLoginIndexPageTitle();
		Assert.assertEquals(actTitle, "WMC");
		Log.endTestCase("verifyLoginIndexPageTitle");
	}

}
