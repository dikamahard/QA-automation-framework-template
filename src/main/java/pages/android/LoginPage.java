package pages.android;

import helper.TestLogger;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import pages.base.BaseMobilePage;

public class LoginPage extends BaseMobilePage {

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    public final By etPin = By.xpath("//*[@content-desc='Merchant POS Login']/following-sibling::android.view.View[2]");
    public final By bannerPosLogin = By.xpath("//android.view.View[@content-desc='Merchant POS Login']");
    public final By btnUnregisterDevice = By.xpath("//*[@content-desc='Unregister Device']");
    public final By dialogUnregisterDevice = By.xpath("//*[@content-desc='Are you sure you want to unregister this device?']");
    public final By dialogErrorInvalidPin = By.xpath("//*[@content-desc='75102 - Invalid PIN']");
    public final By dialogErrorDeviceLocked = By.xpath("//*[@content-desc='Your device is locked, please wait for 300 seconds.']");
    public final By dialogErrorAccountInactive = By.xpath("//*[@content-desc='75102 - Account inactive']");
    public final By dialogErrorDeviceInactive = By.xpath("//*[@content-desc='75102 - Device inactive']");

    // ===== method

    public void inputPin(String pin) {
        TestLogger.startAction("inputPin", pin);

        delay(500);
        action.tapPinDot(etPin);
        action.typeSomething(pin);
    }

    public void clickBtnUnregisterDevice() {
        TestLogger.startAction("clickBtnUnregisterDevice");

        action.click(btnUnregisterDevice);
    }
}
