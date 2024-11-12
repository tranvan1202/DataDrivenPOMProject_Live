package com.ddp.utility;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GSheetUtils {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens/path";
    private static final String existingSpreadSheetID = "1ua7soFSoBeEeaZGu9khbHHmXdTIUkfdTljeOdgJSKT4";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    static Credential credential;
    static Sheets.Spreadsheets spreadsheets;

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GSheetUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void getCredentials() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        InputStream in = GSheetUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " +  CREDENTIALS_FILE_PATH);
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

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1k94LriFFLA7W5tPQqCS6Dk4fvXOljkvjOplvois67O0";
        final String range = "Class Data!A2:E";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s, %s\n", row.get(0), row.get(4));
            }
        }
        //getting the spreadsheet instance
        getSpreadsheetInstance();

        //createNewSpreadSheet();
        //createNewSheet(existingSpreadSheetID,"YoutubeTest1");
        writeDataGoogleSheets("YoutubeTest1", new ArrayList<Object>(Arrays.asList("Source2","Status Code2", "Test Status2")), existingSpreadSheetID);
        // writeSheet(new ArrayList<Object>(Arrays.asList("","","")),"",);
    }

    public static void createNewSpreadSheet() throws GeneralSecurityException,IOException {
        Spreadsheet createdResponse = null;
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT,JSON_FACTORY,getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME).build();

            SpreadsheetProperties spreadsheetProperties = new SpreadsheetProperties();
            spreadsheetProperties.setTitle("Test Title");
            SheetProperties sheetProperties = new SheetProperties();
            sheetProperties.setTitle("Test Suite 1");
            Sheet sheet = new Sheet().setProperties(sheetProperties);

            Spreadsheet spreadsheet = new Spreadsheet().setProperties(spreadsheetProperties)
                    .setSheets(Collections.singletonList(sheet));
            createdResponse = service.spreadsheets().create(spreadsheet).execute();

            //Prints the new spreadsheet id
            System.out.println("Spreadsheet URL: " + createdResponse.getSpreadsheetUrl());

            //Add data script
            List<List<Object>> data = new ArrayList<List<Object>>();
            List<Object> list2 = new ArrayList<Object>();

            list2.add("Good");
            list2.add("Morning");
            list2.add("=1+1");

            data.add(list2);

            /* ValueRange valueRange = new ValueRange().setValues(data);
            service.spreadsheets().values().update(createdResponse.getSpreadsheetId(),
            "A1", valueRange).setValueInputOption("RAW").execute();
            */

            ArrayList<Object> data1 = new ArrayList<Object>(
                    Arrays.asList("Source", "Status Code", "Test Status","=2+2"));
            writeSheet(data1,"A1",createdResponse.getSpreadsheetId());

        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error approriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                System.out.printf("Spreadsheet not found with id '%s'.\n", createdResponse.getSpreadsheetId());
            } else {
                throw e;
            }
        }
    }

    public static void createNewSheet(String existingSpreadSheetID, String newsheetTitle)
            throws IOException, GeneralSecurityException {
        // Create a new AddSheetRequest
        AddSheetRequest addSheetRequest = new AddSheetRequest();
        SheetProperties sheetProperties = new SheetProperties();
        sheetProperties.setIndex(0);

        //Add the sheetName to the sheetProperties
        addSheetRequest.setProperties(sheetProperties);
        addSheetRequest.setProperties(sheetProperties.setTitle(newsheetTitle));

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

    public static void getSpreadsheetInstance() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        spreadsheets = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(), getCredentials(HTTP_TRANSPORT))
                .setApplicationName("Google Sheet Java Integrate").build().spreadsheets();
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
    public static Object getTestData(String spreadsheetId, String range) throws IOException, GeneralSecurityException{
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();

        return values;
    }

}
