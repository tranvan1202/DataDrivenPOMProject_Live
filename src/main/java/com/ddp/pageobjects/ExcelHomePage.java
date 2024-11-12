package com.ddp.pageobjects;


import com.ddp.base.BaseClass;
import com.ddp.utility.ExcelLog;
import com.ddp.utility.ExcelTestDataObject;
import com.ddp.utility.ExcelUtils;
import com.ddp.actiondriver.ValidateUIHelpers;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static com.ddp.actiondriver.ValidateUIHelpers.getSiteCodeFromUrl;

public class ExcelHomePage extends ExcelTestDataObject implements ExcelLog<ExcelHomePage> {
    public static WebDriver driver;
    public static String winHandleBefore;
    private ValidateUIHelpers validateUIHelpers;
    //  Element at Home page
    private By takeOverVideo2024 = By.id("takeover202401");
    private By takeOverVideo2024CloseBtn = By.id("takeover202401__close-btn");
    private By acceptCookiesPopupBtn = By.xpath("//*[@id=\"header\"]/div[2]/div/div/div[2]/a");
    private By switchToAllTab = By.xpath("//*[@id=\"result-list-container\"]/div[1]/div/ul/li[1]/a");
    private By iFrameOfText = By.xpath("//iframe[@title='Proactive Prompt']");
    private By iFramePopupText = By.xpath("//div[@class='frame-content']");
    private By iFrameClosePopupBtn = By.xpath("//button[@class='center-x-y flex-column-container center-x-y e1di9suy2 css-1s0vtz8 e1nedcse0']");
    private By bestMatchSelectionSectionTitle = By.xpath("(//div[@class='best-match__contents']/h3)[2]");
    private By bestMatchSelectionSection = By.xpath("(//a[@class='best-match__wrap'])[2]");
    public static String siteCode ="";
    private String keyword;
    private String url;
    public ExcelHomePage(WebDriver driver) {
        this.driver = driver;
        validateUIHelpers = new ValidateUIHelpers(this.driver);
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public String isShowingBestMatch(WebDriver driver, String url, String searchKeyword) throws InterruptedException {
        winHandleBefore = driver.getWindowHandle();
        driver.manage().window().maximize();
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("window.open()");

        ArrayList<String> browserTabs = Lists.newArrayList(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        String urlSearch = url + searchKeyword;
        driver.get(urlSearch);
        String bestMatchProductTitle = "";
        String redirectedUrl = "";
        //Nếu URL lấy đc từ Data Test Sheet khớp với URL hiện tại của driver
        if (validateUIHelpers.verifyUrl(url)) {
            System.out.printf("%s,%s,%s,%s,%s,%s\n","Sub","PC","MO","Search Keyword","Product Title","Product URL");
            siteCode = getSiteCodeFromUrl(driver,url);
            //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            if (validateUIHelpers.verifyElementExist(takeOverVideo2024)) {
                validateUIHelpers.clickElement(takeOverVideo2024CloseBtn);
            }
            if (validateUIHelpers.verifyElementExist(acceptCookiesPopupBtn)) {
                validateUIHelpers.clickElement(acceptCookiesPopupBtn);
            }
            if (validateUIHelpers.verifyElementExist(switchToAllTab)) {
                //WebElement clickAll = driver.findElement(switchToAllTab);
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("arguments[0].click()", driver.findElement(switchToAllTab));
            }
            //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            if (validateUIHelpers.verifyElementExist(iFrameOfText)) {
                driver.switchTo().defaultContent();
                driver.switchTo().frame(driver.findElement(iFrameOfText));
                while (validateUIHelpers.verifyElementExist(iFramePopupText)) {
                    validateUIHelpers.clickElement(iFrameClosePopupBtn);
                    //WebElement clickOnClosePopupTextButton = driver.findElement(iFrameClosePopupBtn);
                    //clickOnClosePopupTextButton.click();
                }
            }
            if (validateUIHelpers.verifyElementExist(bestMatchSelectionSection)) {
                redirectedUrl = driver.findElement(bestMatchSelectionSection).getAttribute("href");
                bestMatchProductTitle = driver.findElement(bestMatchSelectionSectionTitle).getText();
                System.out.printf("%s,%s,%s,%s,%s,%s\n",siteCode.toUpperCase(),"Yes","Yes",searchKeyword,bestMatchProductTitle,redirectedUrl);
                //Thread.sleep(1000);
                return bestMatchProductTitle;
            } else {
                bestMatchProductTitle = "";
                redirectedUrl = "";
                System.out.printf("%s,%s,%s,%s,%s,%s\n",siteCode.toUpperCase(),"Yes","Yes",searchKeyword,bestMatchProductTitle,redirectedUrl);
                //Thread.sleep(1000);
                return bestMatchProductTitle;
            }
        }
        System.out.println("URL đã bị redirected");
        return bestMatchProductTitle;
    }

    @Override
    public void writeLog(String src, String sheetName, Set<ExcelHomePage> logs) throws IOException {
        //Hàm tiện ích tạo đối tượng workbook từ đường dẫn file chỉ định
        XSSFWorkbook workbook = ExcelUtils.getWorkbook(src);

        //hàm tiện ích gọi sheet cần sử dụng (đã được tạo thủ công sẵn) từ tên chỉ định
        //Lưu ý tên chỉ định phải khớp vs tên sheet trong file
        XSSFSheet sheet = ExcelUtils.getSheet(workbook, sheetName);

        // xử lý ghi tiếp dữ liệu từ hàng cuối cùng của dữ liệu hiện tại
        int startRow = 0, lastRow = sheet.getPhysicalNumberOfRows();
        if (lastRow < startRow) {
            lastRow = startRow;
        }
        //Hàm tiện ích tạo đối tương rowStyle đã đc thiết lập sẵn các giá trị
        CellStyle rowStyle = ExcelUtils.getRowStyle(workbook);

        //Duyệt qua bộ dữ liệu
        for(ExcelHomePage log:logs) {
            // Tạo row mới dựa vào index của row cuối cùng, đồng thời tăng lastRow lên 1 cho vòng lặp kế tiếp
            Row row = sheet.createRow(lastRow);
            //Thiết lập chiều cao của row, nên để default = 60 cho tiện hiển thị ảnh thumbnail
            row.setHeightInPoints(60);
            row.setRowStyle(rowStyle);

            log.writeDataRow(log, row, sheet);
            lastRow++;
        }
        // hàm tiện ích xuất ra file sử dụng g dẫn và workbook chỉ định
        ExcelUtils.export(src, workbook);
    }

    @Override
    public void writeDataRow(ExcelHomePage log, Row row, XSSFSheet sheet) throws IOException {
        CellStyle globalStyle = row.getRowStyle();

        Cell cell;

        cell = row.createCell(0);
        cell.setCellValue(log.getUrl());
        cell.setCellStyle(globalStyle);

        cell = row.createCell(1);
        cell.setCellValue(log.getKeyword());
        cell.setCellStyle(globalStyle);

        // gọi hàm writeTestData từ lớp cha
        // Số 2 ở đây là startIndex để bắt đầu, dữ liệu sẽ đc thêm từ index 2
        writeTestData(2, row,sheet);

    }

}
