package com.ddp.pageobjects;

import com.ddp.actiondriver.Action;
import com.ddp.base.BaseClass;
import org.openqa.selenium.Cookie;


public class LoginPage extends BaseClass {
    Action action= new Action();
    public LoginIndexPage passSessionIntoLogin() throws InterruptedException {
        //action.launchUrl(getDriver(),prop.getProperty("loginIndexUrl"));
        action.launchUrl(getDriver(),"https://wds.samsung.com/wds/sso/login/forwardLogin.do");
        getDriver().manage().addCookie(new Cookie("JSESSIONID", prop.getProperty("session")));
        action.launchUrl(getDriver(),"https://wds.samsung.com/wds/sso/login/ssoLoginSuccess.do");
        return new LoginIndexPage();
    }
    public String getLoginPageTitle() {
        String loginPage=getDriver().getTitle();
        return loginPage;
    }
}
