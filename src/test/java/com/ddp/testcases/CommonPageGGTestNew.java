package com.ddp.testcases;

import com.ddp.base.BaseClass;
import com.ddp.dataprovider.DataProviderFactory;
import com.ddp.pageobjects.*;
import com.ddp.utility.*;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Hashtable;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Listeners(GSheetListener_New.class)
public class CommonPageGGTestNew extends BaseClass {
    private LoginPage loginPage;
    private LoginIndexPage loginIndexPage;
    private LoginQAPage loginQAPage;
    private SEOMetaPage seoMetaPage;
    private CommonPageGG_New commonPageGGNew;

    @Parameters({"browser"})
    @BeforeClass(groups = {"Smoke","Sanity","Regression"})
    public void setUp(String browser) {
        // Đã khởi tạo browser hết rồi kể cả wait, phóng to màn hình,...
        launchApp(browser);
    }
    //    ----------------------------------------------------------------------------------------------------------Test Cases P6-QA
    @Test(priority = 1, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void loginToP6QA(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        System.out.println(getDriver());
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        loginPage = new LoginPage();
        loginIndexPage = loginPage.passSessionIntoLogin();
        loginQAPage = loginIndexPage.clickSubmitButton("qa");
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
    }
    @Test(priority = 1, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void loginToP6Author(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        System.out.println(getDriver());
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        loginPage = new LoginPage();
        loginIndexPage = loginPage.passSessionIntoLogin();
        loginQAPage = loginIndexPage.clickSubmitButton("author");
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
    }

    @Test(priority = 2, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyNotContainTextByXpathOnP6QA(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());

        assertThat(tcActualText, not(containsString(tcExpected)));
    }
    @Test(priority = 3, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyContainsTextByXpathOnP6QA(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        assertThat(tcActualText, (containsString(tcExpected)));
    }
    @Test(priority = 4, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyEqualsTextByXpathOnP6QA(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        Assert.assertEquals(tcActualText, tcExpected);
    }
    @Test(priority = 5, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyNotEqualsTextByXpathOnP6QA(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());

        Assert.assertNotEquals(tcActualText, tcExpected);
    }
    @Test(priority = 6, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyCharacterLimitByXpathOnP6QA(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText= commonPageGGNew.getTcActualTextByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        assertThat(StringUtils.getStringLength(tcActualText), lessThanOrEqualTo(tcExpected));
    }
    @Test(priority = 7, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyExistOfMetaContentOnP6QA(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        SoftAssert softassert = new SoftAssert();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        String stringPageTitle = "";
        String stringCanonicalLink = "";
        String stringMetaContent = "";

        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        //Convert URL để compare
        String convertedUrlToWWW = StringUtils.convertP6QAToWWW(tcUrl);

        seoMetaPage = commonPageGGNew.setSEOContentToAttrValuesByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        //ArrayList<Elements> arraySEOElements = commonPageGGNew.getArraySEOElementsByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);
        //Verify Page Title
        for (Element el : seoMetaPage.getPageTitleElements()) {
            stringPageTitle = el.text();
            System.out.println(el + ", VALUE: " + stringPageTitle);
            softassert.assertTrue(org.apache.commons.lang3.StringUtils.isNotBlank(stringPageTitle),"Page title is empty\n");
        }
        //Verify Can Link
        for (Element el : seoMetaPage.getCanonicalElements()) {
            stringCanonicalLink = el.attr("href");
            System.out.println(el + ", VALUE: " + stringCanonicalLink);
            softassert.assertTrue(org.apache.commons.lang3.StringUtils.isNotBlank(stringCanonicalLink),"Canonical Link is empty\n");
            softassert.assertEquals(stringCanonicalLink,convertedUrlToWWW,"Canonical link is not matched with the URL");
        }
        //Verify content Meta
        for (Element el : seoMetaPage.getMetaElements()) {
            stringMetaContent = el.attr("content");
            System.out.println(el + ", VALUE: " + stringMetaContent);
            softassert.assertTrue(org.apache.commons.lang3.StringUtils.isNotBlank(stringMetaContent),"Content for " + el.attributes() + "is empty\n");
        }
        softassert.assertAll();

    }

    @Test(priority = 8, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyRedirectedLinkOnP6Author(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = loginQAPage.loginAndGoToCommonPageNew();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = dataDP.get("URL").trim() ;
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.navigateURLAndGetCanonicalLinkByDriver(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());
        Assert.assertEquals(tcActualText, tcExpected);
    }

//    ----------------------------------------------------------------------------------------------------------Test Cases WWW
    @Test(priority = 1, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyNotContainTextByXpathOnWWW_new(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = new CommonPageGG_New();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);

        assertThat(tcActualText, not(containsString(tcExpected)));
    }
    @Test(priority = 2, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyContainsTextByXpathOnWWW_new(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = new CommonPageGG_New();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);

        assertThat(tcActualText, (containsString(tcExpected)));
    }
    @Test(priority = 3, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyEqualsTextByXpathOnWWW_new(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = new CommonPageGG_New();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);

        Assert.assertEquals(tcActualText, tcExpected);
    }
    @Test(priority = 4, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyNotEqualsTextByXpathOnWWW_new(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = new CommonPageGG_New();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);

        Assert.assertNotEquals(tcActualText, tcExpected);
    }
    @Test(priority = 5, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyCharacterLimitByXpathOnWWW_new(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = new CommonPageGG_New();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getTcActualTextByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);

        assertThat(StringUtils.getStringLength(tcActualText), lessThanOrEqualTo(tcExpected));
    }

    @Test(priority = 6, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void getAltTextOnWWW(Hashtable<String , String> dataDP, ITestContext ctx) throws IOException {
        commonPageGGNew = new CommonPageGG_New();
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);

        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        String tcActualText = commonPageGGNew.getAltTextByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);

        assertThat(tcActualText, is(not(emptyString())));

    }
    @Test(priority = 7, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Smoke")
    public void verifyExistOfMetaContentOnWWW(Hashtable<String , String> dataDP, ITestContext ctx) throws Exception {
        commonPageGGNew = new CommonPageGG_New();
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);

        SoftAssert softassert = new SoftAssert();
        String stringPageTitle = "";
        String stringCanonicalLink = "";
        String stringMetaContent = "";

        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        //Convert URL để compare
        String convertedUrlToWWW = StringUtils.convertP6QAToWWW(tcUrl);

        seoMetaPage = commonPageGGNew.setSEOContentToAttrValuesByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);
        //ArrayList<Elements> arraySEOElements = commonPageGGNew.getArraySEOElementsByJsoup(ctx,tcMethod, tcUrl, tcLocator, tcExpected);
        for (Element el : seoMetaPage.getPageTitleElements()) {
            stringPageTitle = el.text();
            System.out.println(el + ", VALUE: " + stringPageTitle);
            softassert.assertTrue(org.apache.commons.lang3.StringUtils.isNotBlank(stringPageTitle),"Page title is empty\n");
        }
        for (Element el : seoMetaPage.getCanonicalElements()) {
            stringCanonicalLink = el.attr("href");
            System.out.println(el + ", VALUE: " + stringCanonicalLink);
            softassert.assertTrue(org.apache.commons.lang3.StringUtils.isNotBlank(stringCanonicalLink),"Canonical Link is empty\n");
            softassert.assertEquals(stringCanonicalLink,convertedUrlToWWW,"Canonical link is not matched with the URL");
        }
        for (Element el : seoMetaPage.getMetaElements()) {
            stringMetaContent = el.attr("content");
            System.out.println(el + ", VALUE: " + stringMetaContent);
            softassert.assertTrue(org.apache.commons.lang3.StringUtils.isNotBlank(stringMetaContent),"Content for " + el.attributes() + "is empty\n");
        }
        softassert.assertAll();
    }

    @Test(priority = 9, dataProvider = "dataProviderForIterations", dataProviderClass = DataProviderFactory.class, groups = "Sanity")
    public void sanityTestWWWPage (Hashtable<String , String> dataDP, ITestContext ctx) throws IOException {
        commonPageGGNew = new CommonPageGG_New();
        // Reset attribute values
        commonPageGGNew.setEmptyValuesToAttributeValues(ctx);
        //Gắn dp value vào variable
        String tcMethod = dataDP.get("Method");
        String tcUrl = StringUtils.addSplashIntoTheEndOfUrl(dataDP.get("URL"));
        String tcLocator = dataDP.get("XpathLocator");
        String tcExpected = dataDP.get("Expected");

        commonPageGGNew.checkBrokenImages(ctx,tcMethod, tcUrl, tcLocator, tcExpected, getDriver());

    }
}
