package com.ddp.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ddp.base.BaseClass;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.testng.ITestContext;

public class GSheetUtils_New {
    static Sheets.Spreadsheets spreadsheets;
    static Credential credential;
    public static String existingSpreadSheetID = BaseClass.prop.getProperty("ggSheetExistingId");
    public static String testDataSheetName = BaseClass.prop.getProperty("ggSheetTestDataSheetName");
    static final String credentialPath = "/credentials.json";
    static ValueRange response;
    static List<List<Object>> values ;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens/path";
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);

    public static void writeDataGoogleSheets(String sheetName, List<Object> data, String existingSpreadSheetID) throws IOException {
        int nextRow = getRows(sheetName, existingSpreadSheetID) + 1;
        writeSheet(data, "!A"+ nextRow, existingSpreadSheetID);
    }

    public static int getRows(String sheetName, String existingSpreadSheetID) throws IOException {
        List<List<Object>> values = spreadsheets.values().get(existingSpreadSheetID, sheetName)
                .execute().getValues();
        int numRows = values != null ? values.size() :0;
        System.out.printf("%d rows retrieved. in '" + sheetName + "'\n", numRows);
        return numRows;
    }

    public static int getRows() throws IOException {

        List<List<Object>> values = spreadsheets.values().get(existingSpreadSheetID, testDataSheetName)
                .execute().getValues();
        int numRows = values != null ? values.size() : 0;
        System.out.printf("%d rows retrieved. in '"+testDataSheetName+"'\n", numRows);
        return numRows;
    }

    public static void getSpreadSheetValues(String sheetName) throws IOException
    {
        values = spreadsheets.values().get(existingSpreadSheetID, sheetName)
                .execute().getValues();
    }

    public static void getSpreadSheetValues() throws IOException
    {
        values = spreadsheets.values().get(existingSpreadSheetID, testDataSheetName)
                .execute().getValues();
    }


    public static String getCellContent(int rownum,int colnum) throws IOException {
        return values.get(rownum).get(colnum).toString();
    }


    public static int getLastColumnNum() throws IOException,Exception
    {
        return values.get(0).size();
    }

    public static void authorize() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        InputStream in = GSheetUtils.class.getResourceAsStream(credentialPath);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " +  credentialPath);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,new InputStreamReader(in));

        //Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,JSON_FACTORY,
                clientSecrets,SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        credential = new AuthorizationCodeInstalledApp(flow,receiver).authorize("user");
    }

    public static void getSpreadsheetInstance() throws GeneralSecurityException, IOException {
        spreadsheets = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential).setApplicationName("My Project 79975").build()
                .spreadsheets();
    }

    public static void getSpreadsheetValuesFromRange(String sheetID, String range) throws IOException, InterruptedException {

        values = spreadsheets.values()
                .get(sheetID, range)
                .execute().getValues();
    }

    public static void createNewSheet(String existingSpreadSheetID, String newSheetTitle)
            throws IOException, GeneralSecurityException {
        // Create a new AddSheetRequest
        AddSheetRequest addSheetRequest = new AddSheetRequest();
        SheetProperties sheetProperties = new SheetProperties();
        sheetProperties.setIndex(0);

        //Add the sheetName to the sheetProperties
        addSheetRequest.setProperties(sheetProperties);
        addSheetRequest.setProperties(sheetProperties.setTitle(newSheetTitle));

        // Create batchUpdateSpreadsheetRequest
        BatchUpdateSpreadsheetRequest batchUpdateSpreadsheetRequest = new BatchUpdateSpreadsheetRequest();

        // Create requestList and set it on the batchUpdateSpreadsheetRequest
        List<Request> requestList = new ArrayList<Request>();
        batchUpdateSpreadsheetRequest.setRequests(requestList);

        //Create a new request with containing the addSheetRequest and add it to the
        //requestList
        Request request = new Request();
        request.setAddSheet(addSheetRequest);
        requestList.add(request);

        // Add the requestList to the batchUpdateSpreadsheetRequest
        batchUpdateSpreadsheetRequest.setRequests(requestList);

        //Call the sheets API to execute the batchUpdate
        spreadsheets.batchUpdate(existingSpreadSheetID,batchUpdateSpreadsheetRequest).execute();
    }
    public static void writeSheet(List<Object> inputData, String sheetAndRange, String existingSpreadSheetID)
            throws IOException {
        @SuppressWarnings("unchecked")
        List<List<Object>> values = Arrays.asList(inputData);
        ValueRange body = new ValueRange().setValues(values);
        UpdateValuesResponse result = spreadsheets.values().update(existingSpreadSheetID, sheetAndRange, body)
                .setValueInputOption("RAW").execute();
        System.out.printf("%d cells updated. \n", result.getUpdatedCells());
    }
}
