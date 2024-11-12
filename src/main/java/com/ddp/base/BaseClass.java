package com.ddp.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.ddp.utility.GSheetUtils_New;
import org.apache.log4j.xml.DOMConfigurator;
import org.ietf.jgss.Oid;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import com.beust.jcommander.Parameter;
import com.ddp.actiondriver.Action;
import com.ddp.utility.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class BaseClass {
    public static Properties prop;

    // Declare ThreadLocal Driver
    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    //loadConfig method is to load the configuration
    @BeforeSuite(groups = { "Smoke", "Sanity", "Regression" })
    public void loadConfig() throws IOException, GeneralSecurityException {
        ExtentManager.setExtent();
        DOMConfigurator.configure("log4j.xml");

        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "\\configuration\\config.properties");
            prop.load(ip);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver() {
        // Get Driver from threadLocalMap
        return driver.get();
    }

    public void launchApp(String browserName) {

        if (browserName.equalsIgnoreCase("Chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            options.addArguments("--disable-notifications");
            options.addArguments("start-maximized");
            WebDriverManager.chromedriver().setup();
            // Set Browser to ThreadLocalMap
            driver.set(new ChromeDriver(options));
        } else if (browserName.equalsIgnoreCase("FireFox")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver());
        } else if (browserName.equalsIgnoreCase("IE")) {
            WebDriverManager.iedriver().setup();
            driver.set(new InternetExplorerDriver());
        }
        //Maximize the screen
        getDriver().manage().window().maximize();
        //Delete all the cookies
        getDriver().manage().deleteAllCookies();
        //Implicit TimeOuts
        getDriver().manage().timeouts().implicitlyWait
                (Integer.parseInt(prop.getProperty("implicitWait")),TimeUnit.SECONDS);
        //PageLoad TimeOuts
        getDriver().manage().timeouts().pageLoadTimeout
                (Integer.parseInt(prop.getProperty("pageLoadTimeOut")),TimeUnit.SECONDS);
        //Launching the URL
        //getDriver().get(prop.getProperty("loginIndexUrl"));
    }
    @AfterSuite(groups = { "Smoke", "Regression","Sanity" })
    public void afterSuite() {
        ExtentManager.endReport();
    }

    @AfterClass
    public void tearDown() throws Exception {
        Thread.sleep(2000);
        getDriver().quit();
    }
}
