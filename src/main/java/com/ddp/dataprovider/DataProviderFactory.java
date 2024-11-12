package com.ddp.dataprovider;

import com.ddp.utility.ExcelUtils;
import com.ddp.utility.GSheetUtils_New;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import static com.ddp.utility.GSheetUtils.getTestData;
import java.lang.reflect.Method;


public class DataProviderFactory {
    public static final String SRC = ExcelUtils.DATA_SRC + "Test_Excel.xlsx";

    public static String paramsInputExistingSheetId(ITestContext context) {
        return context.getCurrentXmlTest().getParameter("paramExistingSpreadSheetID");
    }
    public static String paramsInputTestDataSheetRange(ITestContext context) {
        return context.getCurrentXmlTest().getParameter("paramTestDataSheetRange");
    }
    public static String paramsInputUrlColumn(ITestContext context) {
        return context.getCurrentXmlTest().getParameter("paramUrlColumnNumber");
    }
    public static String paramsInputTestMethodColumn(ITestContext context) {
        return context.getCurrentXmlTest().getParameter("paramTestMethodColumnNumber");
    }
    public static String paramTestTypeNameColumn(ITestContext context) {
        return context.getCurrentXmlTest().getParameter("paramTestTypeNameColumnNumber");
    }
    public static String paramsInputLocatorColumn(ITestContext context) {
        return context.getCurrentXmlTest().getParameter("paramInputLocatorColumnNumber");
    }
    public static String paramsInputExpectedResultColumn(ITestContext context) {
        return context.getCurrentXmlTest().getParameter("paramExpectedResultColumnNumber");
    }

    @DataProvider(name="urlList")
    public Object[][] splitURLLinks(ITestContext context) {
        String parameter = context.getCurrentXmlTest().getParameter("urlList");
        String[] names = parameter.split(",");
        Object[][] returnValues = new Object[names.length][1];
        int index = 0;
        for (Object[] each : returnValues) {
            each[0] = names[index++].trim();
        }
        return returnValues;
    }
    @DataProvider(name="dpTestDataSheet")
    public Iterator<Object[]> readTestDataSheet(ITestContext context) throws Exception {
        String existingSpreadSheetID = paramsInputExistingSheetId(context);
        String ggSpreadSheetRange = paramsInputTestDataSheetRange(context);

        String paramsInputUrlColumn = paramsInputUrlColumn(context);
        String paramsInputTestMethodColumn = paramsInputTestMethodColumn(context);
        String paramsInputLocatorColumn = paramsInputLocatorColumn(context);
        String paramsInputExpectedResultColumn = paramsInputExpectedResultColumn(context);
        String paramTestTypeNameColumn =  paramTestTypeNameColumn(context);

        List<Object[]> list = new ArrayList<Object[]>();
        List<List<Object>> ggDataList = (List<List<Object>>) getTestData(existingSpreadSheetID ,ggSpreadSheetRange) ;

        if (ggDataList == null || ggDataList.isEmpty()) {
            System.out.println("No data found.");
        } else{
            for (List row : ggDataList) {
                String qaURL=  (String) row.get(Integer.parseInt(paramsInputUrlColumn));
                String testMethod=  (String) row.get(Integer.parseInt(paramsInputTestMethodColumn));
                String testTypeName=  (String) row.get(Integer.parseInt(paramTestTypeNameColumn));
                String locator=  (String) row.get(Integer.parseInt(paramsInputLocatorColumn));
                String expectedResult=  (String) row.get(Integer.parseInt(paramsInputExpectedResultColumn));

                list.add(new Object[] { qaURL,testMethod, testTypeName,locator,expectedResult});
            }
        }
        return list.iterator();
    }
    @DataProvider(name="dpAltBodyText")
    public Iterator<Object[]> readAltBodySheet(ITestContext context) throws Exception {
        String existingSpreadSheetID = paramsInputExistingSheetId(context);
        String ggSpreadSheetRange = paramsInputTestDataSheetRange(context);

        String paramsInputUrlColumn = paramsInputUrlColumn(context);
        String paramsInputTestMethodColumn = paramsInputTestMethodColumn(context);

        List<Object[]> list = new ArrayList<Object[]>();
        List<List<Object>> ggDataList = (List<List<Object>>) getTestData(existingSpreadSheetID ,ggSpreadSheetRange) ;

        if (ggDataList == null || ggDataList.isEmpty()) {
            System.out.println("No data found.");
        } else{
            for (List row : ggDataList) {
                String qaURL=  (String) row.get(Integer.parseInt(paramsInputUrlColumn));
                String testMethod=  (String) row.get(Integer.parseInt(paramsInputTestMethodColumn));

                list.add(new Object[] { qaURL,testMethod});
            }
        }
        return list.iterator();
    }
    @DataProvider(name="dpExcelSheet")
    public Object[][] excelData() throws IOException {
        //Mở file excel để lấy data test
        XSSFWorkbook workbook = ExcelUtils.getWorkbook(SRC);
        //Thay đổi tên sheet phù hợp
        XSSFSheet sheet = workbook.getSheet("TEST_DATA_SHEET");
        // đọc dữ  liệu test bằng hàm tiện ích
        Object[][] data = ExcelUtils.readSheetData(sheet);
        return data;
    }

    @DataProvider(name="dataProviderForIterations",parallel=false)
    public static Object[][] supplyDataForIterations(Method m) throws Exception{
        return getDataForDataProvider(m.getName());
    }

    private static Object[][] getDataForDataProvider(String testCaseName) throws Exception {
        GSheetUtils_New.getSpreadSheetValues();
        int totalColumns=GSheetUtils_New.getLastColumnNum();
        ArrayList<Integer> rowsCount=getNumberofIterationsForATestCase(testCaseName);
        Object[][] b=new Object[rowsCount.size()][1];
        Hashtable<String,String> table =null;
        for(int i=1;i<=rowsCount.size();i++) {
            table=new Hashtable<String,String>();
            for(int j=0;j<totalColumns;j++){
                table.put(GSheetUtils_New.getCellContent(0, j), GSheetUtils_New.getCellContent(rowsCount.get(i-1), j));
                b[i-1][0]=table;
            }
        }
        return b;
    }

    private static ArrayList<Integer> getNumberofIterationsForATestCase(String testCaseName) throws IOException {
        ArrayList<Integer> a=new ArrayList<Integer>();
        int totalRows = GSheetUtils_New.getRows();
        for(int i=1;i < totalRows;i++) {
            String cellContent = GSheetUtils_New.getCellContent(i, 0);
            if(testCaseName.equalsIgnoreCase(cellContent)) {
                String IsExecutable = GSheetUtils_New.getCellContent(i, 1);
                if(IsExecutable.equalsIgnoreCase("Yes")) {
                    a.add(i);
                }
            }
        }
        return a;
    }
}
