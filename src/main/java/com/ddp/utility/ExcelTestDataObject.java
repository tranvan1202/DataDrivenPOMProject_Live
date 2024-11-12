package com.ddp.utility;


import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.IOException;
import java.util.Date;

public class ExcelTestDataObject {
    private String action;
    private String testMethod;
    private Date logTime;
    private String expected;
    private String actual;
    private String status;
    private String exception = null;
    private String imagePC = null;
    private String imageMO = null;

    public String getImageMO() {return imageMO;}

    public void setImageMO(String imageMO) {this.imageMO = imageMO;}

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getImagePC() {
        return imagePC;
    }

    public void setImagePC(String imagePC) {
        this.imagePC = imagePC;
    }
    public void writeTestData(int startIndex, Row row, XSSFSheet sheet) throws IOException {
        CreationHelper creationHelper = sheet.getWorkbook().getCreationHelper();

        CellStyle globalStyle = row.getRowStyle();

        Cell cell; //tạo đối tượng Cell để ghi data

        cell = row.createCell(startIndex);
        cell.setCellValue(getAction());
        cell.setCellStyle(globalStyle);

        cell = row.createCell(startIndex + 1);
        cell.setCellValue(getLogTime()); //Kiểu Date - Trả về con số nên phải Format lại kiểu ngày-tháng-năm
        CellStyle dateTimeStyle = globalStyle;
        dateTimeStyle.setDataFormat(creationHelper.createDataFormat().getFormat("hh:mm:ss dd-mm-yyyy"));
        cell.setCellStyle(dateTimeStyle);

        cell = row.createCell(startIndex + 2);
        cell.setCellValue(getTestMethod());
        cell.setCellStyle(globalStyle);

        cell = row.createCell(startIndex + 2);
        cell.setCellValue(getExpected());
        cell.setCellStyle(globalStyle);

        cell = row.createCell(startIndex + 3);
        cell.setCellValue(getActual());
        cell.setCellStyle(globalStyle);

        cell = row.createCell(startIndex + 4);
        cell.setCellValue(getStatus());
        cell.setCellStyle(globalStyle);

        if(getException() != null) {
            cell = row.createCell(startIndex + 5);
            cell.setCellValue(getException());
            cell.setCellStyle(globalStyle);
        }

        if(getImagePC() != null) {
            cell = row.createCell(startIndex + 6);
            cell.setCellStyle(globalStyle);
            ExcelUtils.writeImage(getImagePC(), row, cell, sheet);

            cell = row.createCell(startIndex + 7);
            cell.setCellValue("Link screenshot (PC)");
            cell.setCellStyle(globalStyle);

            //Tạo Hyperlink
            XSSFHyperlink hyperlink = (XSSFHyperlink) creationHelper.createHyperlink(HyperlinkType.URL);
            hyperlink.setAddress(getImagePC().replace("\\","/"));//setAddress vào hyperlink
            cell.setHyperlink(hyperlink);
        }

        if(getImageMO() != null) {
            cell = row.createCell(startIndex + 8);
            cell.setCellStyle(globalStyle);
            ExcelUtils.writeImage(getImageMO(), row, cell, sheet);

            cell = row.createCell(startIndex + 9);
            cell.setCellValue("Link screenshot (MO)");
            cell.setCellStyle(globalStyle);

            //Tạo Hyperlink
            XSSFHyperlink hyperlink = (XSSFHyperlink) creationHelper.createHyperlink(HyperlinkType.URL);
            hyperlink.setAddress(getImageMO().replace("\\","/"));//setAddress vào hyperlink
            cell.setHyperlink(hyperlink);
        }

      }

}
