package pages.android.app1;

import helper.TestLogger;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import pages.base.BaseMobileApp1Page;

public class NavBar extends BaseMobileApp1Page {
    protected NavBar(AndroidDriver driver) {
        super(driver);
    }

    public final By tabHome = By.xpath("//*[@content-desc='Tab 1 of 6' or @content-desc='Tab 1 of 7']");
    public final By tabReservation = By.xpath("//*[@content-desc='Tab 2 of 7']");
    public final By tabTrxHistory = By.xpath("//*[@content-desc='Tab 2 of 6'] or @content-desc='Tab 3 of 7']");
    public final By tabDailyReport = By.xpath("//*[@content-desc='Tab 3 of 6' or @content-desc='Tab 4 of 7']");
    public final By tabShift = By.xpath("//*[@content-desc='Tab 4 of 6' or @content-desc='Tab 5 of 7']");
    public final By tabProduct = By.xpath("//*[@content-desc='Tab 5 of 6' or @content-desc='Tab 6 of 7']");
    public final By tabPrinter = By.xpath("//*[@content-desc='Tab 6 of 6' or @content-desc='Tab 7 of 7']");
    public final By tabRefresh = By.xpath("//*[starts-with(@content-desc,'Tab 6 of 6') or starts-with(@content-desc,'Tab 7 of 7')]/following-sibling::*[1]");
    public final By tabProfile = By.xpath("//*[starts-with(@content-desc,'Tab 6 of 6') or starts-with(@content-desc,'Tab 7 of 7')]/following-sibling::*[2]");
    public final By tabExit = By.xpath("//*[starts-with(@content-desc,'Tab 6 of 6') or starts-with(@content-desc,'Tab 7 of 7')]/following-sibling::*[3]");
    public final By dialogExitApp = By.xpath("//android.view.View[@content-desc='Are you sure you want to exit?']");
    // ==== method

    public void clickTabExit() {
        TestLogger.startAction();
        action.click(tabExit);
    }

    public void clickTabRefresh() {
        TestLogger.startAction();
        action.click(tabRefresh);
    }

    public void clickTabProfile() {
        TestLogger.startAction();
        action.click(tabProfile);
    }

    public void clickTabShift() {
        TestLogger.startAction();
        action.click(tabShift);
    }

    public void clickTabHome() {
        TestLogger.startAction();
        action.click(tabHome);
    }

    public void clickTabProduct() {
        TestLogger.startAction();
        action.click(tabProduct);
    }

    public void clickTabDailyReport() {
        TestLogger.startAction();
        action.click(tabDailyReport);
    }

}
