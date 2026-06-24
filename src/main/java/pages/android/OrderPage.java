package pages.android;

import helper.TestLogger;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class OrderPage extends NavBar {
    public OrderPage(AndroidDriver driver) {
        super(driver);
    }

    public final By tvPageTitle = By.xpath("//*[.//*[@content-desc='Create New Order']]/preceding-sibling::*[@content-desc='Active Orders']");

    public final By tvEmployee(String name) {
        return By.xpath("//android.view.View[@content-desc='"+name+" ']");
    }
    public final By btnTakeAway = By.xpath("//android.widget.Button[@content-desc='Take Away']");
    public final By dialogCreateNewOrder = By.xpath("//*[@content-desc='Create New Order']");
    public final By dropSaleChannel = By.xpath("//android.widget.Button[contains(@content-desc,'Sale Channel')]");
    public final By dropList(String list) {
        return By.xpath("//android.widget.Button[@content-desc='"+list+"']");
    }
    public final By etCustomerName = By.xpath("//android.widget.Button[contains(@content-desc,'Sale Channel')]/following-sibling::android.widget.EditText");
    public final By dialogErrorStartShift = By.xpath("//android.view.View[@content-desc='403 - Please start shift before order']");

    // === method

    public void clickBtnTakeAway() {
        TestLogger.startAction();
        action.click(btnTakeAway);
    }

}
