package pages.android;

import helper.FormatUtils;
import helper.TestLogger;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderPaymentPage extends NavBar{
    public OrderPaymentPage(AndroidDriver driver) {
        super(driver);
    }

    public final By btnEditOrder = By.xpath("//*[@content-desc='Edit']");
    public final By btnBack = By.xpath("//android.view.View[@content-desc='Back']");
    public final By btnCash = By.xpath("//*[@content-desc='CASH']");
    public final By btnEDC = By.xpath("//*[@content-desc='EDC']");
    public final By btnQRIS = By.xpath("//*[@content-desc='QRIS']");
    public final By btnPosPromo = By.xpath("//*[@content-desc='POS PROMO']");
    public final By btnGalileoPromo = By.xpath("//*[@content-desc='GALILEO PROMO']");
    public final By tabGalileoPromo = By.xpath("//*[@conten-desc='Promo\nTab 1 of 3']");
    public final By tabGalileoVoucher = By.xpath("//*[@conten-desc='Voucher\nTab 2 of 3']");
    public final By tabGalileoPoints = By.xpath("//*[@conten-desc='Points\nTab 3 of 3']");

    public final By btnVoucher = By.xpath("//*[@content-desc='VOUCHER']");
    public final By tvOrderId = By.xpath("//*[contains(@content-desc, 'Order Code')]");
    public final By tvTotalAmount = By.xpath("//*[contains(@content-desc, 'Total\nRp.')]");
    public final By tvSubtotal = By.xpath("//*[contains(@content-desc, 'Subtotal')]/following-sibling::*[1]");
    public final By tvServiceCharge = By.xpath("//*[contains(@content-desc, 'Service Charge')]/following-sibling::*[1]");
    public final By tvPosPromo = By.xpath("//*[contains(@content-desc, 'POS Promo')]/following-sibling::*[1]");
    public final By tvProductPromo = By.xpath("//*[contains(@content-desc, 'Product Promo')]/following-sibling::*[1]");
    public final By tvGalileoPromo = By.xpath("//*[contains(@content-desc, 'Galileo Promo')]/following-sibling::*[1]");
    public final By tvVoucher = By.xpath("//*[contains(@content-desc, 'Voucher')]/following-sibling::*[1]");
    public final By tvPB1 = By.xpath("//*[contains(@content-desc, 'PB1')]/following-sibling::*[1]");
    public final By tvRoundingAmount = By.xpath("//*[contains(@content-desc, 'Rounding Amount')]/following-sibling::*[1]");
    public final By tvEmptyCart = By.xpath("//*[@content-desc='Must add items to the cart before proceeding to checkout.']");
    public final By dialogPosPromo = By.xpath("//*[@content-desc = 'POS Promo' and ./following-sibling::*[@content-desc = 'Cancel Promo']]");
    public final By btnApply = By.xpath("//*[@content-desc='Apply']");
    public final By btnSearch = By.xpath("//*[@content-desc='Search']");
    public final By etPhoneNum = By.xpath("//*[@content-desc='+62']/android.widget.EditText");
    public final By btnAddVoucher = By.xpath("//*[@content-desc='Add Voucher']");
    public final By btnCancelPromo = By.xpath("//*[@content-desc='Cancel Promo']");
    public final By btnCancelVoucher = By.xpath("//*[@content-desc='Cancel Voucher']");
    public final By etName = By.xpath("//*[@content-desc='Name']/following-sibling::*[2]");
    public final By etCode = By.xpath("//*[@content-desc='Code']/following-sibling::*[2]");
    public final By etProvider = By.xpath("//*[@content-desc='Provider']/following-sibling::android.widget.EditText[1]");
    public final By etAmount = By.xpath("//*[@content-desc='Amount']/following-sibling::android.widget.EditText[1]");
    public final By etNotes = By.xpath("//*[@content-desc='Notes: ']/following-sibling::android.widget.EditText[1]");
    public final By dialogErrorVoucherCode = By.xpath("//*[@content-desc=\"Can't add the same voucher code.\"]");
    public final By dialogErrorVoucherLimit = By.xpath("//*[@content-desc='The number of vouchers exceeds the allowed maximum.']");
    public final By dialogErrorPromoUsageLimit = By.xpath("//*[contains(@content-desc, 'has reached its usage limit and is no longer valid')]");
    public final By errorMandatoryEmpty = By.xpath("//*[@content-desc=\"Can't be empty\"]");
    public  final By tvNotes(String product){
        return By.xpath("//*[contains(@content-desc, 'Notes: ') and contains(@content-desc, '"+product+"')]");
    }
    public final By tvSummaryProductList(String product){
        return By.xpath("//*[contains(@content-desc, '"+product+"')]");
    }
    public final By cardPromoList(String promoName) {
        return By.xpath("//android.view.View[contains(@content-desc, '"+promoName+"') and ./android.widget.RadioButton]");
    }
    public final By cardVoucherList(String voucherCode) {
        return By.xpath("//*[contains(@content-desc, 'Code : "+voucherCode+"')]");
    }
    public final By cardAppliedPromo(String promoName) {
        return By.xpath("//*[contains(@content-desc, 'Supported payment methods') and contains(@content-desc, 'POS PROMO') and contains(@content-desc, '"+promoName+"')]");
    }
    public final By btnEdit = AppiumBy.accessibilityId("Edit");

    public final By etPaymentAmount = By.xpath(
            "//*[@content-desc='Proceed with Payment']/preceding-sibling::*[1]//android.widget.EditText"
                    + " | "
                    + "//*[.//*[@content-desc='Cash']]//android.widget.EditText"
    );
    public final By dropBankName = By.xpath("//*[contains(@content-desc, 'Bank Name')]");
    public final By dropList(String list) {
        return By.xpath("//android.widget.Button[@content-desc='"+list+"']");
    }
    public final By etCardHolderName = By.xpath("//*[contains(@content-desc, 'Bank Name')]/following-sibling::android.widget.EditText[1]");
    public final By etCardNumber = By.xpath("//*[contains(@content-desc, 'Bank Name')]/following-sibling::android.widget.EditText[2]");
    public final By etReferenceNumber = By.xpath("//*[contains(@content-desc, 'Bank Name')]/following-sibling::android.widget.EditText[3]");
    public final By btnProceedPayment = By.xpath("//*[@content-desc='Proceed with Payment']");


    // ======== method

    public void clickBtnCash() {
        TestLogger.startAction();
        action.click(btnCash);
    }

    public void clickBtnEDC() {
        TestLogger.startAction();
        action.click(btnEDC);
    }

    public void clickBtnQRIS() {
        TestLogger.startAction();
        action.click(btnQRIS);
    }

    public void clickBtnBack() {
        TestLogger.startAction();
        action.click(btnBack);
    }

    public void clickBtnEdit() {
        TestLogger.startAction();
        action.click(btnEdit);
    }

    public void clickBtnCancelPromo() {
        TestLogger.startAction();
        action.click(btnCancelPromo);
    }

    public void clickBtnSearch() {
        TestLogger.startAction();
        action.click(btnSearch);
    }

    public void inputPhoneNum(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etPhoneNum);
    }

    public void clickTabPromoGalileo() {
        TestLogger.startAction();
        action.click(tabGalileoPromo);
    }

    public void clickTabVoucherGalileo() {
        TestLogger.startAction();
        action.click(tabGalileoVoucher);
    }

    public void clickTabPointGalileo() {
        TestLogger.startAction();
        action.click(tabGalileoPoints);
    }

    public void clickBtnGalileoPromo() {
        TestLogger.startAction();
        action.click(btnGalileoPromo);
    }

    public void clickBtnPosPromo() {
        TestLogger.startAction();
        action.click(btnPosPromo);
    }

    public void clickBtnVoucher() {
        TestLogger.startAction();
        action.click(btnVoucher);
    }

    public void clickBtnAddVoucher() {
        TestLogger.startAction();
        action.click(btnAddVoucher);
    }

    public void clickBtnCancelVoucher() {
        TestLogger.startAction();
        action.click(btnCancelVoucher);
    }

    public void clickBtnApply() {
        TestLogger.startAction();
        action.click(btnApply);
    }

    public void clickBtnProceedPayment() {
        TestLogger.startAction();
        action.click(btnProceedPayment);
    }

    public void clickPromoList(String promoName) {
        TestLogger.startAction(promoName);
        action.click(cardPromoList(promoName));
    }

    public void inputName(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etName);
    }

    public void inputCode(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etCode);
    }

    public void inputProvider(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etProvider);
    }

    public void inputAmount(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etAmount);
    }

    public void inputNotes(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etNotes);
    }

    public void inputPaymentAmount(String amount) {
        TestLogger.startAction(amount);
        int maxLoop = 4;

        for (int i = 0; i < maxLoop; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(etPaymentAmount));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                driver.findElement(etPaymentAmount).click();
                System.out.println("click");

                driver.findElement(etPaymentAmount).clear();
                System.out.println("clear");

                driver.findElement(etPaymentAmount).sendKeys(amount);
                System.out.println("send key");

                driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                System.out.println("enter");

                wait.until(ExpectedConditions.visibilityOfElementLocated(etPaymentAmount));
                String value = driver.findElement(etPaymentAmount).getAttribute("text").stripTrailing();
                System.out.println("[LOG] validation actual inputText : " + value);
                if (amount.equals(value)) {
                    System.out.println("[INFO] Input succeeded on attempt " + (i + 1));
                    return; // done
                }

                System.out.println("[INFO] Value mismatch. Expected: '" + amount + "', actual: '" + value + "'");

            } catch (TimeoutException | StaleElementReferenceException | NoSuchElementException e) {
                System.out.println("[INFO] Attempt " + (i + 1) + " failed: " + e.getMessage());
            }

            // pause before retry
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {
            }

        }

    }

    public void clickDropDownBankAndSelect(String text) {
        TestLogger.startAction(text);
        action.click(dropBankName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropList(text)));
        action.click(dropList(text));
    }

    public void inputCardHolderName(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etCardHolderName);
    }

    public void inputCardNumber(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etCardNumber);
    }

    public void inputRefNumber(String text) {
        TestLogger.startAction(text);
        action.inputText(text, etReferenceNumber);
    }

    public int getTotalAmount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tvTotalAmount));
        String amount = driver.findElement(tvTotalAmount).getAttribute("content-desc");
        //Matcher m = Pattern.compile("Rp\\.?\\s*([0-9][0-9\\.]*)").matcher(amount);
        //String digits = m.group(1).replace(".", ""); // "23500"
        String digits = amount.replaceAll("[^0-9]","");
        return Integer.parseInt(digits);
    }

    public int getSubtotalAmount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tvSubtotal));
        String amount = driver.findElement(tvSubtotal).getAttribute("content-desc");
        String digits = amount.replaceAll("[^0-9]","");
        return Integer.parseInt(digits);
    }

    public int getPosPromoAmount() {
        try {
            wait(1).until(ExpectedConditions.visibilityOfElementLocated(tvPosPromo));
        }catch (TimeoutException e) {
            System.out.println("[INFO] No pos promo value, return 0");
            return 0;
        }
        String amount = driver.findElement(tvPosPromo).getAttribute("content-desc");
        return FormatUtils.rupiahToInteger(amount);
    }

    public int getProductPromoAmount() {
        try {
            wait(1).until(ExpectedConditions.visibilityOfElementLocated(tvProductPromo));
        }catch (TimeoutException e) {
            System.out.println("[INFO] No product promo value, return 0");
            return 0;
        }
        String amount = driver.findElement(tvProductPromo).getAttribute("content-desc");
        return FormatUtils.rupiahToInteger(amount);
    }

    public int getGalileoPromoAmount() {
        try {
            wait(1).until(ExpectedConditions.visibilityOfElementLocated(tvGalileoPromo));
        }catch (TimeoutException e) {
            System.out.println("[INFO] No galileo promo value, return 0");
            return 0;
        }
        String amount = driver.findElement(tvGalileoPromo).getAttribute("content-desc");
        return FormatUtils.rupiahToInteger(amount);
    }

    public int getVoucherAmount() {
        try {
            wait(1).until(ExpectedConditions.visibilityOfElementLocated(tvVoucher));
        }catch (TimeoutException e) {
            System.out.println("[INFO] No voucher value, return 0");
            return 0;
        }
        String amount = driver.findElement(tvVoucher).getAttribute("content-desc");
        return FormatUtils.rupiahToInteger(amount);
    }

    public int getServiceChargeAmount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tvServiceCharge));
        String amount = driver.findElement(tvServiceCharge).getAttribute("content-desc");
        String digits = amount.replaceAll("[^0-9]","");
        return Integer.parseInt(digits);
    }

    //TODO: THIS WILL BE DELETED SOON, USE THE UPDATED VERSION AND REFACTOR THE CODE
    public int calcServiceChargeAmount(int percentage) {
        return ((getSubtotalAmount() + getPosPromoAmount())  * percentage) / 100;
    }

    public int calcServiceChargeAmount(int percentage, int subtotalAmount) {    // UPDATED VER
        return (subtotalAmount  * percentage) / 100;
    }

    public int getPB1Amount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tvPB1));
        String amount = driver.findElement(tvPB1).getAttribute("content-desc");
        String digits = amount.replaceAll("[^0-9]","");
        return Integer.parseInt(digits);
    }

    //TODO: THIS WILL BE DELETED SOON, USE THE UPDATED VERSION AND REFACTOR THE CODE
    public int calcPB1(int percentagePB1, int percentageServiceCharge) {
        long subtotal = getSubtotalAmount();
        long serviceCharge = calcServiceChargeAmount(percentageServiceCharge);
        long base = subtotal + serviceCharge;

        long pb1 = (base * percentagePB1) / 100;  // floor
        return Math.toIntExact(pb1);
    }

    public int calcPB1(int subTotalAmount, int percentagePB1, int percentageServiceCharge) { // UPDATED VER
        long serviceCharge = calcServiceChargeAmount(percentageServiceCharge, subTotalAmount);
        long base = subTotalAmount + serviceCharge;

        long pb1 = (base * percentagePB1) / 100;  // floor
        return Math.toIntExact(pb1);
    }

    public int getRoundingAmount() {
        try {
            wait(1).until(ExpectedConditions.visibilityOfElementLocated(tvRoundingAmount));
        }catch (TimeoutException e) {
            System.out.println("[INFO] No rounding value, return 0");
            return 0;
        }
        String amount = driver.findElement(tvRoundingAmount).getAttribute("content-desc");

        return FormatUtils.rupiahToInteger(amount);
    }

    public int calculateTotalAmount(int percentageServiceCharge, int percentageTaxPB1, boolean promoBeforeTax) {

        // parameter
        int subTotalAmount = getSubtotalAmount();
        int posPromoAmount = getPosPromoAmount();
        int productPromoAmount = getProductPromoAmount();
        int promoAmount = posPromoAmount + productPromoAmount; // only 1 of them will have value, but both can be 0
        int serviceChargeAmount = getServiceChargeAmount();
        int calcServiceChargeAmount = calcServiceChargeAmount(percentageServiceCharge, subTotalAmount);;
        int PB1 = getPB1Amount();
        int calcPB1 = calcPB1(subTotalAmount, percentageTaxPB1, percentageServiceCharge);
        int roundingAmount = getRoundingAmount();
        int totalAmount = getTotalAmount();
        int calcTotalAmount = 0;

        // calculate need to check promo tax setting
        if(promoBeforeTax) {
            calcServiceChargeAmount = calcServiceChargeAmount(percentageServiceCharge, subTotalAmount + promoAmount);
            calcPB1 = calcPB1(subTotalAmount + promoAmount, percentageTaxPB1, percentageServiceCharge);

            calcTotalAmount = subTotalAmount + promoAmount + calcServiceChargeAmount + calcPB1 + roundingAmount;
        }else {
            calcTotalAmount = subTotalAmount + calcServiceChargeAmount + calcPB1 + promoAmount+ roundingAmount;
        }

        // log
        TestLogger.printLog("subtotal amount", String.valueOf(subTotalAmount));
        TestLogger.printLog("POS Promo", String.valueOf(posPromoAmount));
        TestLogger.printLog("Product Promo", String.valueOf(productPromoAmount));
        TestLogger.printLog("promoAmount", String.valueOf(promoAmount));
        TestLogger.printLog("service charge amount", String.valueOf(serviceChargeAmount));
        TestLogger.printLog("calc service charge amount", String.valueOf(calcServiceChargeAmount));
        TestLogger.printLog("PB1", String.valueOf(PB1));
        TestLogger.printLog("calc PB1", String.valueOf(calcPB1));
        TestLogger.printLog("rounding amount", String.valueOf(roundingAmount));
        TestLogger.printLog("total amount", String.valueOf(totalAmount));
        TestLogger.printLog("calc total amount", String.valueOf(calcTotalAmount));


        return calcTotalAmount;
    }

    public String getOrderId() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tvOrderId));
        String text = driver.findElement(tvOrderId).getAttribute("content-desc");
        return text.replaceAll(".*#\\s*", "").trim();
    }

    public String getNotes(String product) {
        String raw = driver.findElement(tvNotes(product)).getAttribute("content-desc");
        return raw.split("Notes:\\s*\\R")[1].trim().split("\\R")[0].trim();
    }

    public void clickEditSummaryProductList(String product) {
        TestLogger.startAction();
//        Rectangle r = driver.findElement(tvSummaryProductList(product)).getRect();
//        int x = r.x + (int) (r.width * 0.93);
//        int y = r.y + (int) (r.height * 0.15);

//        action.tapByCoordinate(x, y);
        action.click(tvSummaryProductList(product));
    }

}
