package pages.web.web2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BaseWebWeb1Page;

public class DashboardPage extends BaseWebWeb1Page {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    private By cardLabel(String label) {
        return By.xpath("//div[./*[.='"+label+"']]/*[last()]");
    }

    private By amountLabel(String label) {
        return By.xpath("//div[./*[text()='"+label+"']]/following-sibling::*");
    }

    public static String url = "http://192.168.7.110:1970/dashboard";

    public By cardDoneTrx = cardLabel("Done");
    public By cardPendingTrx = cardLabel("Pending");
    public By cardTimeoutTrx = cardLabel("Timeout");
    public By cardCancelTrx = cardLabel("Cancel");
    public By cardNetIncome = cardLabel("Net Income");
    public By cardGrandTotal = cardLabel("Grand Total");
    public By amountNetSales = By.xpath("//*[.='Net Sales']/following-sibling::*[1]");
    public By amountBaseTotal = By.xpath("//*[.='Base Total']/following-sibling::*[1]");
    public By amountTotalAmount = amountLabel("Total Amount");
    public By amountInternalPromo = amountLabel("Internal Promo");
    public By amountGalileoPromo = amountLabel("Galileo Promo");
    public By amountVoucher = amountLabel("Voucher");
    public By amountTotalAdditionalCharge = amountLabel("Total Additional Charge");
    public By amountServiceCharge = amountLabel("Service Charge");
    public By amountPB1 = amountLabel("PB1");
    public By amountRounding = amountLabel("Rounding");
    public By tbDateRange = textBox("Select Date Range");
    public By comboBoxMerchant = comboBoxSelector("Merchant");
    public By comboBoxStore = comboBoxSelector("Store");



    // ============================================================================================= //
    // ============================================================================================= //
    // ============================================================================================= //
    // ============================================================================================= //



    public String getTransactionDone() {
        System.out.println("getTransactionDone");

        return driver.findElement(cardDoneTrx).getText();
    }

    public String getTransactionPending() {
        System.out.println("getTransactionPending");

        return driver.findElement(cardPendingTrx).getText();
    }

    public String getTransactionTimeout() {
        System.out.println("getTransactionTimeout");

        return driver.findElement(cardTimeoutTrx).getText();
    }

    public String getTransactionCancel() {
        System.out.println("getTransactionCancel");

        return driver.findElement(cardCancelTrx).getText();
    }

    public String getNetIncome() {
        System.out.println("getNetIncome");

        return driver.findElement(cardNetIncome).getText();
    }

    public String getNetGrandTotal() {
        System.out.println("getNetGrandTotal");

        return driver.findElement(cardGrandTotal).getText();
    }

    public String getAmountNetSales() {
        System.out.println("getAmountNetSales");

        return driver.findElement(amountNetSales).getText();
    }

    public String getAmountBaseTotal() {
        System.out.println("getAmountBaseTotal");

        return driver.findElement(amountBaseTotal).getText();
    }

    public String getAmountTotalAmount() {
        System.out.println("getAmountTotalAmount");

        return driver.findElement(amountTotalAmount).getText();
    }

    public String getAmountInternalPromo() {
        System.out.println("getAmountInternalPromo");

        return driver.findElement(amountInternalPromo).getText();
    }

    public String getAmountGalileoPromo() {
        System.out.println("getAmountGalileoPromo");

        return driver.findElement(amountGalileoPromo).getText();
    }

    public String getAmountVoucher() {
        System.out.println("getAmountVoucher");

        return driver.findElement(amountVoucher).getText();
    }

    public String getAmountTotalAdditionalCharge() {
        System.out.println("getAmountTotalAdditionalCharge");

        return driver.findElement(amountTotalAdditionalCharge).getText();
    }

    public String getAmountServiceCharge() {
        System.out.println("getAmountServiceCharge");

        return driver.findElement(amountServiceCharge).getText();
    }

    public String getAmountPB1() {
        System.out.println("getAmountPB1");

        return driver.findElement(amountPB1).getText();
    }

    public String getAmountRounding() {
        System.out.println("getAmountRounding");

        return driver.findElement(amountRounding).getText();
    }

    public void inputDateRange() {
        System.out.println("inputDateRange");

        action.click(tbDateRange);
        action.click(button("Current Month"));
    }

    public void inputComboBoxMerchant(String merchant) {
        System.out.println("inputComboBoxMerchant");

        clickComboBoxAndSelectValue(comboBoxMerchant, merchant);
    }

    public void inputComboBoxStore(String store) {
        System.out.println("inputComboBoxStore");
        action.inputText(comboBoxStore, store);
        action.arrowDownKey(comboBoxStore);
        action.enterKey(comboBoxStore);
        action.click(comboBoxStore);
    }

}
