package com.ddp.dataprovider;
import com.ddp.utility.GSheetUtils_New;
import org.testng.annotations.DataProvider;

import java.util.*;
import java.util.stream.Collectors;
public class DataProviderFactory_New {
//    @DataProvider(name = "pageDataProvider")
//    public static Iterator<Object[]> pageDataProvider() throws Exception {
//        // Fetch data from Google Sheet
//        List<Map<String, String>> rawData = GSheetUtils_New.getSpreadSheetValues();
//
//        // Filter data for executable test cases
//        List<Map<String, String>> executableData = rawData.stream()
//                .filter(row -> row.get("isExecutable").equalsIgnoreCase("Yes"))
//                .collect(Collectors.toList());
//
//        // Group data by URL
//        Map<String, List<ElementData>> groupedData = executableData.stream()
//                .collect(Collectors.groupingBy(row -> row.get("URL"),
//                        Collectors.mapping(row -> new ElementData(row.get("XpathLocator"), row.get("Expected text")),
//                                Collectors.toList())));
//
//        // Prepare DataProvider output
//        List<Object[]> dataProvider = new ArrayList<>();
//        for (Map.Entry<String, List<ElementData>> entry : groupedData.entrySet()) {
//            dataProvider.add(new Object[]{entry.getKey(), entry.getValue()});
//        }
//
//        return dataProvider.iterator();
//    }
//
//    // Helper class to store element data
//    public static class ElementData {
//        private String locator;
//        private String expectedText;
//
//        public ElementData(String locator, String expectedText) {
//            this.locator = locator;
//            this.expectedText = expectedText;
//        }
//
//        public String getLocator() {
//            return locator;
//        }
//
//        public String getExpectedText() {
//            return expectedText;
//        }
//    }
}
