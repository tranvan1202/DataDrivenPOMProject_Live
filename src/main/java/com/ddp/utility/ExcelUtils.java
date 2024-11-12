package com.ddp.utility;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;

public class ExcelUtils {
    public static final String DATA_SRC = "resources\\test-data\\";
    public static final String IMAGES_SRC = "D:\\Auto\\Project\\PJTSS_CheckLinks\\resources\\images\\";

    public static XSSFWorkbook getWorkbook(String filePath) throws IOException {
        File src = new File(filePath);
        if(!src.exists()) {
            throw new IOException("Not found path: " + filePath);
        }
        FileInputStream fis = new FileInputStream(src);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        fis.close();
        return workbook;
    }

    public static XSSFSheet getSheet(XSSFWorkbook workbook, String sheetName) {
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if(sheet == null) {
            throw new NullPointerException("Sheet " + sheetName + " sheet not found, can't insert data!");
        }
        return sheet;
    }

    public static CellStyle getRowStyle(XSSFWorkbook workbook) {
        CellStyle rowStyle = workbook.createCellStyle();
        rowStyle.setAlignment(HorizontalAlignment.CENTER);
        rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        rowStyle.setWrapText(true);

        return rowStyle;
    }

    public static String getCellValue(XSSFSheet sheet, int row, int column) {
        String returnValue;
        XSSFCell cell = sheet.getRow(row).getCell(column); //Row: hàng, Cell: cột
        //Lấy hàng trước, lấy cell
        //Trong excel là 1, trong code bđ từ 0
        try {
            if(cell.getCellType() == CellType.STRING) {
                returnValue = cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                returnValue = String.format("%.0f", cell.getNumericCellValue());
            } else {
                returnValue = "";
            }
        } catch (Exception e) {
            returnValue = "";
        }
        return returnValue;
    }

    public static void takeScreenshot(WebDriver driver, String outputSrc) throws IOException {
        File screenshot = (((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE));
        FileUtils.copyFile(screenshot, new File (outputSrc));
    }

    public static Object[][] readSheetData(XSSFSheet sheet) {
        int rows = sheet.getPhysicalNumberOfRows(); //tổng cộng số row
        int columns = sheet.getRow(0).getLastCellNum(); //tổng số column

        Object[][] data = new Object[rows-1][columns]; //tạo mảng 2 chiều
        //-1 để chừa hàng cho title, code bắt đầu từ hàng thứ 2 trở đi

        for(int row = 1 ; row <rows; row++) {//số 1 này tương ứng vs hàng t2 trong Excel
            for(int col =0; col < columns; col++) {
                data[row-1][col] = ExcelUtils.getCellValue(sheet, row, col); //bên trong getCellValue
            }
        }
        return data;
    }

    public static void writeImage(String image, Row row, Cell cell, XSSFSheet sheet) throws IOException {
        InputStream is = new FileInputStream(image); //Lấy hình - image là đường dẫn

        byte[] bytes = IOUtils.toByteArray(is);// trung gian

        int pictureId = sheet.getWorkbook(). addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_PNG);
        //dùng addPicture để tạo đối tượng hình ảnh vào Workbook (chưa có hình thật)

        is.close();//đóng kết nối

        XSSFDrawing drawing = sheet.createDrawingPatriarch(); //Bắt buộc khởi tạo để đưa hình lên Excel
        ClientAnchor anchor = new XSSFClientAnchor(); //định vị

        anchor.setCol1(cell.getColumnIndex());
        anchor.setRow1((row.getRowNum()));
        anchor.setCol2(cell.getColumnIndex()+1);
        anchor.setRow2(row.getRowNum()+1);

        drawing.createPicture(anchor,pictureId); //PictureId để biết hình nào vào
    }

    public static void export(String outputSrc, XSSFWorkbook workbook) throws IOException {
        FileOutputStream out = new FileOutputStream(outputSrc);
        workbook.write(out);
        out.close();
    }
}
