package com.ddp.pageobjects;

import com.ddp.actiondriver.Action;
import com.ddp.base.BaseClass;
import com.ddp.utility.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;

import java.net.HttpURLConnection;
import java.net.URL;

public class CommonPageGG_New extends BaseClass {
    Action action = new Action();
    //public final String stringHtmlHeadByJsoup = "html > head";
    //public final String stringXpathCanonicalLink = "link[rel='canonical']";
    //public final By byXpathHtmlHead = By.xpath("//html//head");
    private List<String> jsErrors;
    private List<String> brokenImages;
    private List<String> brokenLinks;
    private String environment;

    public static String getCanonicalLinkByDriver(WebDriver driver) throws InterruptedException {
        // Đặt thời gian chờ
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Chờ đến khi thẻ <link rel="canonical"> xuất hiện
        WebElement canonicalLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//link[@rel='canonical']")));
        // Lấy giá trị của thuộc tính href
        return canonicalLink.getAttribute("href");
    }

    public String getCanonicalLinkByJsoup(Document doc) throws IOException {
        Element eMeta = doc.select("html > head").first();
        return eMeta.select("link[rel='canonical']").attr("href");
    }

    public static void printOutTheDpValues(String tcMethod, String tcUrl, String tcLocator, String tcExpected) {
        System.out.printf("%s\n%s\n%s\n%s\n", "Inputted zTCMethod: " + tcMethod
                , "Inputted TCUrl: " + tcUrl,
                "Inputted TCLocator: " + tcLocator,
                "Inputted TCExpected: " + tcExpected);
    }

    public void setEmptyValuesToAttributeValues(ITestContext ctx) {
        //Gắn variable value vào test value
        // Reset attribute values
        ctx.setAttribute("resultTCMethod", "");
        ctx.setAttribute("resultTCUrl", "");
        ctx.setAttribute("resultTCLocator", "");
        ctx.setAttribute("resultTCExpected", "");
        ctx.setAttribute("resultTCActualText", "");
        ctx.setAttribute("resultTCActualTextLength", "");
    }

    public void setTestValuesToAttributeValues(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected) {
        //Gắn variable value vào test value
        ctx.setAttribute("resultTCMethod", tcMethod);
        ctx.setAttribute("resultTCUrl", tcUrl);
        ctx.setAttribute("resultTCLocator", tcLocator);
        ctx.setAttribute("resultTCExpected", tcExpected);
    }

    public void setActualValuesToAttributeValues(ITestContext ctx, String inputtedActualText, String inputtedActualTextLength) {
        ctx.setAttribute("resultTCActualText", inputtedActualText);
        ctx.setAttribute("resultTCActualTextLength", inputtedActualTextLength);
    }

    public String getTcActualTextByJsoup(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected) throws IOException {
        setTestValuesToAttributeValues(ctx, tcMethod, tcUrl, tcLocator, tcExpected);
        printOutTheDpValues(tcMethod, tcUrl, tcLocator, tcExpected);
        String tcActualText;
        String tcActualTextLength;
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);
        //Connect vào URL
        Document doc = Jsoup.connect(tcUrl).get();
        //Lấy Can link để compare
        String sLinkCan = getCanonicalLinkByJsoup(doc);

        //Kiểm tra xem page có bị redirected không?
        if (resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            Elements elements = doc.selectXpath(tcLocator);
            if (!elements.isEmpty()) {
                // Element is present
                tcActualText = StringUtils.processTextSpaceAndHTMLTags(elements.text());
                tcActualTextLength = String.valueOf(tcActualText.length());

                System.out.printf("%s\n%s\n%s\n", "Actual Element: " + elements,
                        "Actual text: " + tcActualText,
                        "Actual text length: " + tcActualTextLength);
                setActualValuesToAttributeValues(ctx, tcActualText, tcActualTextLength);
                //assertThat(tcActualText, not(containsString(tcExpected)));
                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                setActualValuesToAttributeValues(ctx, tcActualText, tcActualTextLength);
                //Assert.assertEquals("",tcExpected ,"element not found\n");
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            setActualValuesToAttributeValues(ctx, tcActualText, tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }

    public String getAltTextByJsoup(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected) throws IOException {
        setTestValuesToAttributeValues(ctx, tcMethod, tcUrl, tcLocator, tcExpected);
        printOutTheDpValues(tcMethod, tcUrl, tcLocator, tcExpected);
        String tcActualText;
        String tcActualTextLength;
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);
        //Connect vào URL
        Document doc = Jsoup.connect(tcUrl).get();
        //Lấy Can link để compare
        String sLinkCan = getCanonicalLinkByJsoup(doc);

        //Kiểm tra xem page có bị redirected không?
        if (resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            Elements bodyElement = doc.selectXpath(tcLocator);
            if (!bodyElement.isEmpty()) {
                // Element is present
                Elements img = bodyElement.select("img");
                ArrayList<String> originalArrAltTextAttrNames = new ArrayList<>();
                ArrayList<String> arrAltTextOfAllImages = new ArrayList<>();
                ArrayList<String> arrElementList = new ArrayList<>();
                // Loop through img tags to get all the Alt text attribute names
                for (Element el : img) {
                    for (Attribute attribute : el.attributes()) {
                        if ((attribute.getKey().contains("alt"))) {
                            originalArrAltTextAttrNames.add(attribute.getKey());
                        }
                    }
                }
                // Xóa duplicated values trong mảng Alt text attribute names
                ArrayList<String> arrAltTextAttrNames = StringUtils.removeDuplicates(originalArrAltTextAttrNames);
                System.out.println("New list attribute Alt text names:" + arrAltTextAttrNames);

                //Loop through img tags, to get Alt text Attribute Values (with selected Attribute Keys)
                for (Element el : img) {
                    System.out.println(el);
                    arrElementList.add(String.valueOf(el));
                    for (String attrKey : arrAltTextAttrNames) {
                        //If the value is not empty
                        if (!el.attr(attrKey).isEmpty()) {
                            System.out.println("=>" + el.attribute(attrKey));
                            arrAltTextOfAllImages.add("=>" + el.attribute(attrKey));
                        }
                    }
                }
                // Ghi Element list vào Allure Report
                String tcFullInfoAltTextOfEachImg = String.valueOf(arrElementList);
                //tcActualText= String.valueOf(arrAltTextOfAllImages);
                // Ghi Alt text vào GG Sheet
                tcActualText = String.valueOf(arrAltTextOfAllImages);
                tcActualTextLength = String.valueOf(tcActualText.length());

                ctx.setAttribute("resultTCActualText", tcActualText);
                ctx.setAttribute("resultTCActualTextLength", tcActualTextLength);

                ctx.setAttribute("tcFullInfoAltTextOfEachImg", tcFullInfoAltTextOfEachImg);

                //assertThat(tcActualText, is(not(emptyString())));
                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                ctx.setAttribute("resultTCActualText", tcActualText);
                ctx.setAttribute("resultTCActualTextLength", tcActualTextLength);
                //Assert.assertEquals("",tcExpected ,"element not found\n");
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            ctx.setAttribute("resultTCActualText", tcActualText);
            ctx.setAttribute("resultTCActualTextLength", tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }

    public String getTcActualTextByDriver(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) throws InterruptedException {
        setTestValuesToAttributeValues(ctx, tcMethod, tcUrl, tcLocator, tcExpected);
        printOutTheDpValues(tcMethod, tcUrl, tcLocator, tcExpected);
        String tcActualText = "";
        String tcActualTextLength;

        driver.get(tcUrl);
        //Đợi Element text
        Thread.sleep(1000);
        //Đợi 5s ko tìm thấy Element thì move tiếp
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);

        // Lấy Canonical link để compare
        String sLinkCan = getCanonicalLinkByDriver(driver);

        //Kiểm tra xem page có bị redirected không?
        if (resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lưu Elements List khi ng dùng input Xpath
            List<WebElement> inputtedElement = driver.findElements(By.xpath(tcLocator));
            Thread.sleep(500);
            if (!inputtedElement.isEmpty()) {
                //Mở này nếu muốn jump vào element
                //WebElement element = driver.findElement(By.xpath(tcLocator));
                //action.moveToElement(getDriver(),element);

                //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                // Element is present
                for (WebElement webInputtedElementText : inputtedElement) {
                    Document doc = Jsoup.parse(webInputtedElementText.getAttribute("outerHTML"));
                    tcActualText = StringUtils.processTextSpaceAndHTMLTags(doc.text());
                    tcActualTextLength = String.valueOf(tcActualText.length());
                    setActualValuesToAttributeValues(ctx, tcActualText, tcActualTextLength);
                }
                //assertThat(tcActualText, not(containsString(tcExpected)));
                return tcActualText;
            } else {
                // Element is not present
                tcActualText = "";
                tcActualTextLength = "0";
                setActualValuesToAttributeValues(ctx, tcActualText, tcActualTextLength);
                //Assert.assertEquals("",tcExpected ,"element not found\n");
                return tcActualText;
            }
        } else {
            // Bị redirected sang URL khác
            tcActualText = "";
            tcActualTextLength = "0";
            setActualValuesToAttributeValues(ctx, tcActualText, tcActualTextLength);
            //Assert.assertEquals("",resultQAUrlConvertedToVerify,"page has been redirected to: " + sLinkCan +"\n");
            return tcActualText;
        }
    }

    public Elements pageTitleElementsJsoup(Document doc) {
        return doc.selectXpath("//title");
    }

    public Elements canonicalElementsJsoup(Document doc) {
        return doc.selectXpath("//link[@rel='canonical']");
    }

    public Elements metaElementsJsoup(Document doc) {
        return doc.selectXpath("//title//following::meta");
    }

    public SEOMetaPage setSEOContentToAttrValuesByJsoup(ITestContext ctx, String tcMethod, String tcUrl,
                                                        String tcLocator, String tcExpected) throws IOException {
        setTestValuesToAttributeValues(ctx, tcMethod, tcUrl, tcLocator, tcExpected);
        printOutTheDpValues(tcMethod, tcUrl, tcLocator, tcExpected);
        String tcActualText;
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);
        //Connect vào URL
        Document doc = Jsoup.connect(tcUrl).get();
        //Lấy Can link để compare
        String sLinkCan = getCanonicalLinkByJsoup(doc);
        //Kiểm tra xem page có bị redirected không?
        if (resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lấy Elements Title
            Elements elementsTitle = pageTitleElementsJsoup(doc);
            //Lấy Elements Can link
            Elements elementsCanLink = canonicalElementsJsoup(doc);
            //Lấy Elements Meta
            Elements elementsMeta = metaElementsJsoup(doc);

            tcActualText = elementsTitle.toString() + "\n" + elementsCanLink.toString() + "\n" + elementsMeta.toString() + "\n";

            ctx.setAttribute("resultTCActualText", tcActualText);
            return new SEOMetaPage(elementsTitle, elementsCanLink, elementsMeta);
        } else {
            // Bị redirected sang URL khác
            tcActualText = "=> Error: URL has been redirected to " + sLinkCan;
            System.out.println(tcActualText);
            ctx.setAttribute("resultTCActualText", tcActualText);
            return new SEOMetaPage();
        }
    }

    public SEOMetaPage setSEOContentToAttrValuesByDriver(ITestContext ctx, String tcMethod, String tcUrl,
                                                         String tcLocator, String tcExpected, WebDriver driver) throws IOException {
        setTestValuesToAttributeValues(ctx, tcMethod, tcUrl, tcLocator, tcExpected);
        printOutTheDpValues(tcMethod, tcUrl, tcLocator, tcExpected);
        String tcActualText;
        // Convert URL P6-QA(nếu có) sang WWW để compare với Canonical link
        String resultQAUrlConvertedToVerify = StringUtils.convertP6QAToWWW(tcUrl);
        //Connect vào URL
        driver.get(tcUrl);
        //Lấy WebElement header
        WebElement header = driver.findElement(By.xpath("//html//head"));
        //Parse WebElement attribute header thành String
        String headerSourceCode = header.getAttribute("innerHTML");
        // Parse html thành Doc
        Document doc = Jsoup.parse(headerSourceCode);
        //Lấy Can link để compare
        String sLinkCan = getCanonicalLinkByJsoup(doc);
        //Kiểm tra xem page có bị redirected không?
        if (resultQAUrlConvertedToVerify.equals(sLinkCan)) {
            //Lấy Elements Title
            Elements elementsTitle = pageTitleElementsJsoup(doc);
            //Lấy Elements Can link
            Elements elementsCanLink = canonicalElementsJsoup(doc);
            //Lấy Elements Meta
            Elements elementsMeta = metaElementsJsoup(doc);

            tcActualText = elementsTitle.toString() + "\n" + elementsCanLink.toString() + "\n" + elementsMeta.toString() + "\n";

            ctx.setAttribute("resultTCActualText", tcActualText);
            return new SEOMetaPage(elementsTitle, elementsCanLink, elementsMeta);
        } else {
            // Bị redirected sang URL khác
            tcActualText = "=> Error: URL has been redirected to " + sLinkCan;
            System.out.println(tcActualText);
            ctx.setAttribute("resultTCActualText", tcActualText);
            return new SEOMetaPage();
        }
    }

    public String navigateURLAndGetCanonicalLinkByDriver(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) throws InterruptedException {
        setTestValuesToAttributeValues(ctx, tcMethod, tcUrl, tcLocator, tcExpected);
        printOutTheDpValues(tcMethod, tcUrl, tcLocator, tcExpected);
        String tcActualText = "";
        String tcActualTextLength;
        driver.get(tcUrl);
        String sLinkCan = getCanonicalLinkByDriver(driver);

        ctx.setAttribute("resultTCActualText", sLinkCan);
        return sLinkCan;
    }

    public void checkBrokenImages(ITestContext ctx, String tcMethod, String tcUrl, String tcLocator, String tcExpected, WebDriver driver) {
        setTestValuesToAttributeValues(ctx, tcMethod, tcUrl, tcLocator, tcExpected);
        printOutTheDpValues(tcMethod, tcUrl, tcLocator, tcExpected);
        String tcActualText = "";
        String tcActualTextLength;
        driver.get(tcUrl);

        List<String> brokenImages = new ArrayList<>();
        List<WebElement> images = driver.findElements(By.tagName("img"));

        for (WebElement img : images) {
            boolean hasSrc = false;
            String imageSrc = "";

            // Execute JavaScript to get all attributes of the image as a Map
            Map<String, Object> result = (Map<String, Object>) ((JavascriptExecutor) driver).executeScript(
                    "let items = {}; for (let index = 0; index < arguments[0].attributes.length; ++index) " +
                            "{ items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", img
            );

            // Convert result to Map<String, String>
            Map<String, String> attributes = new HashMap<>();
            for (Map.Entry<String, Object> entry : result.entrySet()) {
                attributes.put(entry.getKey(), entry.getValue().toString());
            }

            // Check for any attribute containing "src"
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                if (entry.getKey().toLowerCase().contains("src")) {
                    imageSrc = entry.getValue();
                    hasSrc = true;
                    break;
                }
            }

            // If no src attribute is found or it's empty
            if (!hasSrc || imageSrc.isEmpty()) {
                String imgHtml = img.getAttribute("outerHTML"); // Get the entire HTML of the img tag
                brokenImages.add("Image without src attribute: " + imgHtml); // Add the HTML to the error message
            } else {
                // Add protocol if missing (protocol-relative URLs)
                if (imageSrc.startsWith("//")) {
                    imageSrc = "https:" + imageSrc;
                }

                try {
                    URL imageUrl = new URL(imageSrc);
                    HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                    connection.setRequestMethod("HEAD");
                    int responseCode = connection.getResponseCode();

                    if (responseCode != 200) {
                        brokenImages.add("Broken image found: " + imageSrc + " - Response code: " + responseCode);
                    }
                } catch (MalformedURLException e) {
                    brokenImages.add("Invalid URL for image: " + imageSrc + " - " + e.getMessage());
                } catch (IOException e) {
                    brokenImages.add("Error while checking image: " + imageSrc + " - " + e.getMessage());
                }
            }
        }

        // Output broken images
        if (!brokenImages.isEmpty()) {
            System.out.println("Broken images found:");
            for (String brokenImage : brokenImages) {
                System.out.println(brokenImage);
            }
        } else {
            System.out.println("No broken images found.");
        }
    }
    public boolean checkJavaScriptErrors (WebDriver driver){
        // Inject JavaScript to capture console errors
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.onerror = function(errorMessage) { window.jsErrors = window.jsErrors || []; window.jsErrors.push(errorMessage); };");

        // Wait for the page to load and any JS to execute
        try {
            Thread.sleep(3000);  // Adjust this delay according to page load time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> jsErrors = (List<String>) jsExecutor.executeScript("return window.jsErrors");
        if (jsErrors.isEmpty()) {
            return true;
        }
        return false;
        // Assert no JavaScript errors
        //Assert.assertTrue(jsErrors.isEmpty(), "JavaScript errors found: " + jsErrors.toString());
    }
}


