package com.ddp.testcases;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.ddp.base.BaseClass;
import com.ddp.dataprovider.DataProviderFactory;
import com.ddp.utility.ExcelUtils;
import com.ddp.pageobjects.ExcelHomePage;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.ddp.actiondriver.ValidateUIHelpers.getSiteCodeFromUrl;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class HomePageExcelTest extends BaseClass{
    private WebDriver driver;
    private Set<ExcelHomePage> logs;
    private ExcelHomePage homePageExcelData;
    private DataProviderFactory dataProviderFactory;
    private BaseClass baseSetup = this;
    public static String resultActualResult= "";
    public static String resultSiteCode ="";
    public static String trimmedKeyword="";
    @BeforeClass
    public void setUp() {
        // Đã khởi tạo browser hết rồi kể cả wait, phóng to màn hình,...
        driver = getDriver();
        logs = new LinkedHashSet<>();
    }
    @Test(priority = 1, dataProvider = "dpExcelSheet", dataProviderClass = DataProviderFactory.class)
    public void verifyExistenceOfTheKeywordElement(String dpTestAction,String dpUrl,String dpKeyword,String dpExpectedResult) throws Exception {
        homePageExcelData = new ExcelHomePage(driver);

        homePageExcelData.setAction(dpTestAction);
        homePageExcelData.setUrl(dpUrl+dpKeyword.trim());
        homePageExcelData.setKeyword(dpKeyword);
        homePageExcelData.setExpected(dpExpectedResult);
        homePageExcelData.setLogTime(new Date());

        // Biến load params
        //For qua các row data trong Excel
        resultActualResult = homePageExcelData.isShowingBestMatch(driver,dpUrl, dpKeyword);
        trimmedKeyword = dpKeyword.replaceAll(" ", "");
        resultSiteCode = getSiteCodeFromUrl(driver,dpUrl);
        homePageExcelData.setActual(resultActualResult);
        assertThat(resultActualResult, (containsString(dpExpectedResult)));
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        String imgFileNameMO =  resultSiteCode.toUpperCase() + "_" + trimmedKeyword.trim() + "_MO.png";
        String imgFileNamePC =  resultSiteCode.toUpperCase() + "_" + trimmedKeyword.trim() + "_PC.png".trim();
        String pathNameMOSiteCode = ExcelUtils.IMAGES_SRC + resultSiteCode.toUpperCase() + "/" + imgFileNameMO;
        String pathNamePCSiteCode = ExcelUtils.IMAGES_SRC + resultSiteCode.toUpperCase() + "/" + imgFileNamePC;
        homePageExcelData.setTestMethod(result.getName());
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                homePageExcelData.setStatus("SUCCESS");
                ExcelUtils.takeScreenshot(driver,pathNamePCSiteCode);

                driver.manage().window().setSize(new Dimension(390, 1200));
                //to perform Scroll on application using Selenium
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0,100)", "");
                Thread.sleep(2000);
                driver.manage().window().maximize();
                js.executeScript("window.scrollBy(0,-100)", "");
                //chụp màn hình
                ExcelUtils.takeScreenshot(driver,pathNameMOSiteCode);

                //ghi dữ liệu hình ảnh vào workbook
                homePageExcelData.setImagePC(pathNamePCSiteCode);
                homePageExcelData.setImageMO(pathNameMOSiteCode);
                //Đóng tab hiện tại
                driver.close();
                //Chuyển sang tab đã giữ
                driver.switchTo().window(ExcelHomePage.winHandleBefore);

                break;
            case ITestResult.FAILURE:
                homePageExcelData.setStatus("FAILURE");
                homePageExcelData.setException(result.getThrowable().getMessage());
                ExcelUtils.takeScreenshot(driver,pathNamePCSiteCode);

                driver.manage().window().setSize(new Dimension(390, 1200));
                //to perform Scroll on application using Selenium
                js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0,100)", "");
                Thread.sleep(2000);
                driver.manage().window().maximize();
                js.executeScript("window.scrollBy(0,-100)", "");
                //chụp màn hình
                ExcelUtils.takeScreenshot(driver,pathNameMOSiteCode);

                //ghi dữ liệu hình ảnh vào workbook
                homePageExcelData.setImagePC(pathNamePCSiteCode);
                homePageExcelData.setImageMO(pathNameMOSiteCode);

                //Đóng tab hiện tại
                driver.close();
                //Chuyển sang tab đã giữ
                driver.switchTo().window(ExcelHomePage.winHandleBefore);

                break;
            case ITestResult.SKIP:
                homePageExcelData.setStatus("SKIP");
            default:
                break;
        }
        logs.add(homePageExcelData);
        //driver.close();
    }

    @AfterClass
    public void destroy() throws IOException{
        homePageExcelData.writeLog(dataProviderFactory.SRC, "RESULT_TEST", logs);
    }
}
