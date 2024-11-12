package com.ddp.utility;

import java.io.IOException;
import java.util.ArrayList;

public class StringUtils {
    public static String convertP6QAToWWW(String url) {
        if (url.contains("p6-qa")) {
            url = url.replace("p6-qa","www");
            return url;
        }
        return url;
    }
    public static String convertLongToTime(long durationInMillis) {
        long millis = durationInMillis % 1000;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
        return time;
    }
    public static String addSplashIntoTheEndOfUrl(String inputUrl){
        inputUrl = inputUrl.trim();
        if (!inputUrl.endsWith("/")) {
            inputUrl = inputUrl.concat("/").trim();
        }
        return inputUrl;
    }
    public static String removeSplashFromEndOfUrl(String inputUrl) {
        inputUrl = inputUrl.trim();  // Trim any surrounding whitespace
        if (inputUrl.endsWith("/")) {
            inputUrl = inputUrl.substring(0, inputUrl.length() - 1);  // Remove the last character ("/")
        }
        return inputUrl;
    }

    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
    public static String getStringLength(String tcActualText) throws IOException {
        if(tcActualText != null && !tcActualText.isEmpty()) {
            return String.valueOf(tcActualText.length());
        }
        return "0";
    }

    public static String processTextSpaceAndHTMLTags(String text) {
        // Loại bỏ thẻ HTML
        String noHtmlText = text.replaceAll("<[^>]*>", "");

        // Trim để loại bỏ khoảng trắng thừa ở đầu/cuối và giữ lại khoảng trắng giữa các từ
        return noHtmlText.trim();
    }
}
