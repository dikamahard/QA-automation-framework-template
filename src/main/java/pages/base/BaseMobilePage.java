package pages.base;

import helper.ActionMobile;
import helper.TestLogger;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseMobilePage {
    protected final AndroidDriver driver;
    protected final ActionMobile action;
    public WebDriverWait wait;

    protected BaseMobilePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.action = new ActionMobile(driver);
    }

    public WebDriverWait wait(int sec) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(sec));
    }

    public By btnOk = AppiumBy.accessibilityId("OK");
    public By btnYes = AppiumBy.accessibilityId("Yes");
    public By btnNo = AppiumBy.accessibilityId("No");
    public By btnConfirm = AppiumBy.accessibilityId("Confirm");
    public By btnBack = AppiumBy.accessibilityId("Back");
    public By btnAdd = AppiumBy.accessibilityId("Add");
    public By btnSave = AppiumBy.accessibilityId("Save");
    public By dialogErrorSessionExpired = By.xpath("//*[@content-desc='Session has ended']");
    public By loadingSpinner = AppiumBy.accessibilityId("Loading");
    public By checkBoxTermAgreement = By.xpath("//*[@content-desc = 'I Agree with the Terms & Conditions']/preceding-sibling::android.widget.CheckBox");
    public By permissionDialog = By.xpath(
            "//*[@resource-id='com.android.permissioncontroller:id/grant_dialog'] | " +
                    "//*[@resource-id='com.android.permissioncontroller:id/content_container']"
    );
    protected void click(WebElement el) {
        el.click();
    }

    public void checkTermAgreement() {
        action.click(checkBoxTermAgreement);
    }

    public void clickBtnOk() {
        TestLogger.startAction();

        action.click(btnOk);
    }

    public void clickBtnYes() {
        TestLogger.startAction();
        action.click(btnYes);
    }

    public void clickBtnSave() {
        TestLogger.startAction();
        action.click(btnSave);
    }

    public void clickBtnBack() {
        TestLogger.startAction();
        action.click(btnBack);
    }

    public void clickBtnConfirm() {
        TestLogger.startAction();
        action.click(btnConfirm);
    }

    public void clickBtnAdd() {
        TestLogger.startAction();
        action.click(btnAdd);
    }

    public void clickBtnNo() {
        action.click(btnNo);
    }

    public void delay(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException ignored) {
            //ignore
        }
    }

}
