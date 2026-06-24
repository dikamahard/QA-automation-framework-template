package pages.android;

import helper.TestLogger;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class OrderProductPage extends NavBar {
    public OrderProductPage(AndroidDriver driver) {
        super(driver);
    }

    public final By btnGotoPayment = By.xpath("//android.widget.Button[@content-desc='Go to Payment']");
    public final By btnCancel = By.xpath("//android.widget.Button[@content-desc='Cancel']");
    public final By btnRemove = By.xpath("//android.widget.Button[@content-desc='Remove']");
    public final By tvEmptyCart = By.xpath("//*[@content-desc='Must add items to the cart before proceeding to checkout.']");
    public final By searchBar = By.xpath("//android.widget.EditText[./android.widget.Button]");
    public final By dialogAddOrder = By.xpath("//*[contains(@content-desc, 'Add Order') and .//*[@content-desc='Add']  and .//*[@content-desc='Back']]");
    public final By tvOrderQty = By.xpath("//*[contains(@content-desc, 'Add Order\nTotal\nRp.')]//android.widget.ImageView/following-sibling::*");
    public final By btnAddOrderQty = By.xpath(
            "//*[contains(@content-desc, 'Total') and contains(@content-desc, 'Rp.') and " +
                    "(contains(@content-desc, 'Add Order') or contains(@content-desc, 'Customize Order'))]" +
                    "//android.widget.ImageView/following-sibling::*/android.widget.Button[2]"
    );
    public final By ivProductAddOrder = By.xpath("//*[contains(@content-desc, 'Add Order\nTotal\nRp.')]//android.widget.ImageView");
    public final By ivProductCustomizeOrder = By.xpath("//*[contains(@content-desc, 'Customize Order\nTotal\nRp.')]//android.widget.ImageView");

    public final By tvNoProduct = By.xpath("//*[@content-desc=\"You don't have any products.\"]");
    public final By etNotes = By.xpath("//*[@content-desc='Notes: ']/following-sibling::android.widget.EditText");
    public final By checkBoxCompliment = By.xpath("//android.widget.CheckBox[@content-desc='Compliment']");
    public final By dialogErrorOrderCancel = By.xpath("//android.view.View[@content-desc='75104 - Order has been cancelled']");
    public final By ivProduct(String prodName, String prodVariant) {
        return By.xpath("//*[contains(@content-desc, '"+prodName+"') and contains(@content-desc, '"+prodVariant+"')]");
    }
    public final By tvCartProductList(String product){
        return By.xpath("//*[contains(@content-desc, '"+product+"')]");
    }
    public final By btnFilterCategory(String prodCategory, String prodSubCategory) {
        String label = prodCategory + " | " + prodSubCategory;
        return By.xpath("//android.widget.Button[@content-desc='" + label + "']");
    }
    public final By listProductOption(String optName, boolean requiredStatus, boolean multiStatus) {
        String reqStat = "optional";
        String mulStat = "single";
        if (requiredStatus) {
            reqStat = "mandatory";
        }
        if (multiStatus) {
            mulStat = "multiple";
        }


        return By.xpath("//*[contains(@content-desc, '"+optName+"') and contains(@content-desc, '"+reqStat+"') and contains(@content-desc, '"+mulStat+"')]");
    }

    public final By checkBoxProductOption(String optName, boolean requiredStatus, boolean multiStatus, String label) {
        String reqStat = "optional";
        String mulStat = "single";
        if (requiredStatus) {
            reqStat = "mandatory";
        }
        if (multiStatus) {
            mulStat = "multiple";
        }

        return By.xpath("//*[contains(@content-desc, '"+optName+"') and contains(@content-desc, '"+reqStat+"') and contains(@content-desc, '"+mulStat+"')]/android.widget.CheckBox[contains(@content-desc, '"+label+"')]");
    }
    public final By togglePromo(String promoName) {
        return By.xpath("//*[contains(@content-desc, '"+promoName+"')]/following-sibling::android.widget.Switch");
    }

    public void clickIvProduct(String prodName, String prodVariant) {
        TestLogger.startAction();
        action.click(ivProduct(prodName, prodVariant));
    }

    public void clickFilterCategory(String prodCategory, String prodSubCategory) {
        TestLogger.startAction();
        action.click(btnFilterCategory(prodCategory,prodSubCategory));
    }

    public void clickBtnRemove() {
        TestLogger.startAction();
        action.click(btnRemove);
    }

    public void clickBtnCancel() {
        TestLogger.startAction();
        action.click(btnCancel);
    }

    public void setCheckBoxProductOption(String optName, boolean requiredStatus, boolean multiStatus, String label) {
        TestLogger.startAction();
        action.click(checkBoxProductOption(optName, requiredStatus, multiStatus, label));
    }

    public void searchProduct(String product) {
        TestLogger.startAction(product);
        action.inputText(product, searchBar);
    }

    public void inputNotes(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etNotes);
    }

    public void clickBtnGotoPayment() {
        TestLogger.startAction();
        action.click(btnGotoPayment);
    }

    public void addOrderQty(int qty) {
        TestLogger.startAction();
        // check if btnAddOrderQty is on screen, if not scroll dwon
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(ivProductAddOrder),
                ExpectedConditions.visibilityOfElementLocated(ivProductCustomizeOrder)
        ));
        delay(1000);
        action.scrollDown(1000);

        for (int i=1; i<qty; i++) {
            action.click(btnAddOrderQty);
            delay(500);
        }
    }

    public void setCheckBoxCompliment(boolean check) {
        TestLogger.startAction();

        boolean checkboxStatus = Boolean.parseBoolean(driver.findElement(checkBoxCompliment).getAttribute("checked"));

        if (check != checkboxStatus) {
            action.click(checkBoxCompliment);
        }
    }

    public String getOrderProductPrice(String prodName, String prodVariant){
        String contentDesc = driver.findElement(dialogAddOrder).getAttribute("content-desc");
        long amount = Long.parseLong(contentDesc.substring(contentDesc.indexOf("Rp")).replaceAll("\\D", ""));

        DecimalFormatSymbols sym = new DecimalFormatSymbols();
        sym.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,###", sym);

        String result = "Rp" + df.format(amount);
        return result;
    }

    public String getProductPrice(String prodName, String prodVariant){
        String contentDesc = driver.findElement(ivProduct(prodName, prodVariant)).getAttribute("content-desc");
        long amount = Long.parseLong(contentDesc.substring(contentDesc.lastIndexOf("Rp")).replaceAll("\\D", ""));

        DecimalFormatSymbols sym = new DecimalFormatSymbols();
        sym.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,###", sym);

        String result = "Rp" + df.format(amount);
        return result;
    }

    public boolean getErrorMandatoryEmptyProduct(String optName, boolean requiredStatus, boolean multiStatus) {
        String contentDesc = driver.findElement(listProductOption(optName, requiredStatus, multiStatus)).getAttribute("content-desc");

        return contentDesc.contains("can't be empty");
    }

    public String getOrderQty() {
        return driver.findElement(tvOrderQty).getAttribute("content-desc");
    }

}
