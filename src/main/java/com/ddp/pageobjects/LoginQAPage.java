package com.ddp.pageobjects;

import com.ddp.actiondriver.Action;
import com.ddp.base.BaseClass;
import org.openqa.selenium.support.PageFactory;

public class LoginQAPage extends BaseClass {
    Action action= new Action();
    public LoginQAPage() {
        PageFactory.initElements(getDriver(), this);
    }
    public String getLoginQAPageTitle() {
        String loginQAPageTitle=getDriver().getTitle();
        return loginQAPageTitle;
    }
    public String getCurrentURL() throws Throwable {
        String loginQAPageUrl=action.getCurrentURL(getDriver());
        return loginQAPageUrl;
    }

    public CommonPage loginAndGoToCommonPage() throws InterruptedException{
        return new CommonPage();
    }
    public CommonPageGG_New loginAndGoToCommonPageNew() throws InterruptedException{
        return new CommonPageGG_New();
    }
    public PricePromiseClaimPage loginAndGoToPPCPage() throws InterruptedException{
        return new PricePromiseClaimPage();
    }
}
