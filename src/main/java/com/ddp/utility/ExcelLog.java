package com.ddp.utility;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.IOException;
import java.util.Set;

public interface ExcelLog<T extends ExcelTestDataObject> {
    public void writeLog(String src, String sheetName, Set<T> logs) throws IOException;
    public void writeDataRow(T log, Row row, XSSFSheet sheet) throws IOException;

}
