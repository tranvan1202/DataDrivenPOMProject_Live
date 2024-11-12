package com.ddp.utility;

import com.ddp.pageobjects.CommonPageGG_New;
import com.ddp.reports.AllureManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static com.ddp.utility.GSheetUtils_New.existingSpreadSheetID;

public class GSheetListener_New extends CommonPageGG_New implements ITestListener {
    private String resultSheetName = "";
    private long testProcessingMilli;
    private String resultProcessingTime;

    @Override
    public void onFinish(ITestContext arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart(ITestContext arg0)  {
        try {
            setupResultSheet();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub
        //Mở nếu muốn ghi Result vào GG Sheet
        testProcessingMilli = result.getEndMillis() - result.getStartMillis();
        resultProcessingTime = StringUtils.convertLongToTime(testProcessingMilli);
        try {
            GSheetUtils_New.writeDataGoogleSheets(resultSheetName, new ArrayList<Object>(Arrays.asList(
                    result.getTestContext().getAttribute("resultTCMethod"), result.getTestContext().getAttribute("resultTCUrl"),
                    result.getTestContext().getAttribute("resultTCLocator"), result.getTestContext().getAttribute("resultTCExpected"),
                    result.getTestContext().getAttribute("resultTCActualText"), result.getTestContext().getAttribute("resultTCActualTextLength"),
                    "Failed", result.getThrowable().getMessage(), resultProcessingTime)), existingSpreadSheetID);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Allure Report
//        AllureManager.saveTextLog(result.getName() + " is failed.");
//        AllureManager.saveTextLog(String.valueOf(result.getTestContext().getAttribute("tcFullInfoAltTextOfEachImg")));
//        AllureManager.saveScreenshotPNG();

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
        // Reset attribute values

    }

    @Override
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
        //testStartTime = LocalDateTime.now();
        //testCaseStartTime = testStartTime.format(myTimeFormat);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
        //Mở nếu muốn ghi Result vào GG Sheet
        testProcessingMilli = result.getEndMillis() - result.getStartMillis();
        resultProcessingTime = StringUtils.convertLongToTime(testProcessingMilli);
        try {
            GSheetUtils_New.writeDataGoogleSheets(resultSheetName, new ArrayList<Object>(Arrays.asList(
                    result.getTestContext().getAttribute("resultTCMethod"), result.getTestContext().getAttribute("resultTCUrl"),
                    result.getTestContext().getAttribute("resultTCLocator"), result.getTestContext().getAttribute("resultTCExpected"),
                    result.getTestContext().getAttribute("resultTCActualText"), result.getTestContext().getAttribute("resultTCActualTextLength"),
                    "Passed","No issue", resultProcessingTime)), existingSpreadSheetID);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        //Allure Report
//        AllureManager.saveTextLog(result.getName() + " is passed.");
//        AllureManager.saveTextLog(String.valueOf(result.getTestContext().getAttribute("tcFullInfoAltTextOfEachImg")));
//        AllureManager.saveScreenshotPNG();
    }
    public void setupResultSheet() throws GeneralSecurityException, IOException {
        LocalDateTime sheetStartTime = LocalDateTime.now();
        DateTimeFormatter myDateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");

        GSheetUtils_New.authorize();
        GSheetUtils_New.getSpreadsheetInstance();

        String formatDate = sheetStartTime.format(myDateTimeFormat);

        resultSheetName = "TestResult_" + formatDate;
        System.out.println("SheetName-" + formatDate);

        try {
            GSheetUtils_New.createNewSheet(existingSpreadSheetID, resultSheetName);
            GSheetUtils_New.writeDataGoogleSheets(resultSheetName,
                    new ArrayList<Object>(Arrays.asList("TestMethod","URL","XpathLocator","Expected"
                            ,"Actual Text", "Actual Text Length","Result","Reason", "Processing Time")),
                    existingSpreadSheetID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
