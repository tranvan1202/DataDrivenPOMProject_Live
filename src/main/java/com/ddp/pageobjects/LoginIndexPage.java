package com.ddp.pageobjects;

import com.ddp.actiondriver.Action;
import com.ddp.base.BaseClass;
import com.google.common.collect.Lists;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class LoginIndexPage extends BaseClass {
    Action action= new Action();
    @FindBy(xpath = "/html/body/div/div[2]/div/div[1]/div[1]/div/dl[4]/dd[1]")
    private WebElement submitQALink;
    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/dl[1]/dd[1]")
    private WebElement submitAuthorLink;

    // Khởi tạo class khi được gọi và truyền driver vào để các thành phần trong
    // class này đọc
    // Khởi tạo class khi được gọi và truyền driver vào để các thành phần trong
    // Và khởi tạo initElements
    public LoginIndexPage() {
        PageFactory.initElements(getDriver(), this);
    }
    public LoginQAPage clickSubmitButton(String env) throws InterruptedException {
        getDriver().manage().addCookie(new Cookie("JSESSIONID", BaseClass.prop.getProperty("session")));
        sleep(1000);
        getDriver().navigate().to("https://wds.samsung.com/wds/sso/login/ssoLoginSuccess.do");
        if(env.equals("qa")){
            action.fluentWait(getDriver(), submitQALink, 10);
        } else {
            action.fluentWait(getDriver(), submitAuthorLink, 10);
        }
        sleep(1000);
        ArrayList<String> browserTabs = Lists.newArrayList(getDriver().getWindowHandles());
        getDriver().switchTo().window(browserTabs.get(1));
        return new LoginQAPage();
    }
    public String getLoginIndexPageTitle() {
        String pageTitle = getDriver().getTitle();
        return pageTitle;
    }
}
