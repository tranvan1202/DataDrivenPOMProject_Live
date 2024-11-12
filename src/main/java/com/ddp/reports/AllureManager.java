package com.ddp.reports;

import com.ddp.base.BaseClass;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AllureManager {
//    allure serve -h localhost
//    Mở maven -> Execute Maven Goal -> mvn clean để clean history
    //    allure generate --single-file target/allure-results --clean


    //Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    //Image attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshotPNG() {
        return ((TakesScreenshot) BaseClass.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}