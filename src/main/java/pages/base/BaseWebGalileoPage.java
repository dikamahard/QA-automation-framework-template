package pages.base;

import helper.ActionWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class BaseWebGalileoPage {

    protected final WebDriver driver;
    protected final ActionWeb action;
    public WebDriverWait wait;

    public BaseWebGalileoPage(WebDriver driver) {
        this.driver = driver;
        this.action = new ActionWeb(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    //public By alertPopup = By.xpath("((//div[@class=\"Toastify__toast-icon Toastify--animate-icon Toastify__zoom-enter\"]/parent::div)//child::*/following-sibling::div)[last()]");
    public By alertPopup(String textAlert) {
        return By.xpath("(//div[@class=\"Toastify__toast-icon Toastify--animate-icon Toastify__zoom-enter\"]/parent::div)//child::*/following-sibling::div[.='"+textAlert+"']");
    }
    public By buttonFlagLanguage = By.xpath("//button[@class='MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeMedium css-13be9o2']");

    public By selectLanguage(String language) {
        return By.xpath("//div[@class='MuiStack-root css-1hieftg']/descendant::*[text()='"+ language +"']");
    }

    public static By menu(String menuName) {
        return By.xpath("//*[@role='button']/descendant::*[text()='" + menuName + "']");
    }

    public static By subMenu(String menuName, String subMenuName) {
        return By.xpath("//div[./div[.='"+menuName+"']]/following-sibling::div[1]//*[@role='button']/descendant::*[text()='"+subMenuName+"']");
    }

    public static By button(String buttonName) {
        return By.xpath("//button[text()='" + buttonName + "']");
    }
    public static By button(String buttonName, int index) {
        return By.xpath("(//button[text()='" + buttonName + "'])["+index+"]");
    }

    public static By textArea (String marking){
        return By.xpath ("//*[./label[.='"+marking+"']]//textarea[1]");
    }

    public static By textArea (String marking, int index){
        return By.xpath ("(//*[contains(.,'"+marking+"')]//textarea[1])["+index+"]");
    }

    public static By textBox (String marking){
        return By.xpath ("//*[.='"+marking+"']/following-sibling::*/input");
    }

    public static By textBox (String marking, int index){
        return By.xpath ("(//*[.='"+marking+"']/following-sibling::*/input)["+index+"]");
    }

    public By errorValidationMandatory(String error) {
        return  By.xpath("//*[contains(@class, 'Mui-error') and .='"+error+"']");
    }

    public static By checkbox (String marking){
        return By.xpath("//*[.='"+marking+"']//input[@type='checkbox']");
    }

    public static By checkbox (String marking, int index){
        return By.xpath("(//*[.='"+marking+"']//input[@type='checkbox'])["+index+"]");
    }

    public static By radioButton (String groupName, String labelRadio){
        return By.xpath("//*[.='"+groupName+"']/following-sibling::*//label[contains(., '"+labelRadio+"')]//input[@type='radio']");
    }

    public static By clickable(By locator) {
        String s = locator.toString();
        String raw = s.substring("By.xpath: ".length()); // now it's a pure xpath
        return By.xpath("(" + raw + ")/parent::*");
    }

    public static By switchButton2(String marking){
        return By.xpath("//span[text()='"+marking+"']/preceding-sibling::span/child::*");
    }

    public static By switchButton(String marking){
        return By.xpath("//*[text()='"+marking+"']/preceding-sibling::*//input[@checked]");
    }

    public static By comboBoxSelector (String marking){
        return By.xpath("//label[text()='"+marking+"']/following-sibling::div/child::input");
        //*[text()='"+marking+"']/parent::*//input[@role='combobox']
        //label[text()='"+marking+"']/following-sibling::div/child::input
    }

    public static By dropdownSelector(String dropdownName){
        return By.xpath("//label[text()='"+dropdownName+"']//following-sibling::div/child::div[@role='combobox']");
    }
    public static By dropdownSelector(String dropdownName, int index){
        return By.xpath("(//label[text()='"+dropdownName+"']//following-sibling::div/child::div[@role='combobox'])["+index+"]");
    }

    public By titlePage(String title) {
        return By.xpath("//h4[contains(@class, 'MuiTypography-root MuiTypography-h4 MuiTypography-gutterBottom') and .='"+title+"']");
    }

    public By burgerMenu(int index){
        String xpathBurgerMenu = burgerMenu.toString().replaceFirst("^By\\.xpath:\\s*", "");;
        return By.xpath("("+xpathBurgerMenu+")["+index+"]");
    }

    public By tbMarking(String marking, String label, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//label[text()='"+label+"']//following-sibling::div/child::input)["+index+"]");
    }

    public By taMarking(String marking, String label, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//label[text()='"+label+"']//following-sibling::div/child::textarea[1])["+index+"]");
    }

    public By dropMarking(String marking, String label, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//label[text()='"+label+"']//following-sibling::div/child::div[@role='combobox'])["+index+"]");
    }

    public By deleteMarking(String marking, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//button[contains(@class, 'MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textError MuiButton-sizeSmall MuiButton-textSizeSmall MuiButton-colorError MuiButton-root MuiButton-text MuiButton-textError MuiButton-sizeSmall MuiButton-textSizeSmall MuiButton-colorError')])["+index+"]");
    }

    public By inputImageMarking(String marking, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//child::input[@accept='image/jpeg,image/png,image/jpg'])["+index+"]");
    }

    public By buttonMarking(String marking, String button) {
        return By.xpath("(//div[./h6[.='"+marking+"']]/following-sibling::div//button[.='"+button+"'])[1]");
    }



    public static By dropdownList (String targetValue){
        return By.xpath("//li[text()='"+targetValue+"']");
    }
    public static By searchBar = By.xpath("//input[@placeholder='Search']");
    public static By burgerMenu = By.xpath("//td[@class='MuiTableCell-root MuiTableCell-body MuiTableCell-alignRight MuiTableCell-sizeMedium css-1r9te5h']//child::button");
    public static By burgerList(String list) {
        return By.xpath("//li[.='"+list+"' and @role='menuitem']");
    }
    public static By mandatoryText (String textboxName){
        return By.xpath("//label[text()='"+ textboxName +"']/following-sibling::div/following-sibling::p");
    }

    public static By photoExtensionValidate() {
        return By.xpath("//p[text()='File type must be image/jpeg,image/png,image/jpg']");
    }

    public By errorValidationFileInvalid = By.xpath("//*[text()='File type is invalid or oversize']");
    public By errorValidationFileTypeMustBe = By.xpath("//*[text()='File type must be one of image/jpeg, image/png, image/jpg']");
    public By fileUploadedIndicator = By.xpath("//*[@class='MuiListItem-root MuiListItem-gutters MuiListItem-padding css-vwc31b']");


    // =======  Method ============

    public void clickBtnCancel(int index) {
        action.click(button("Cancel", index));
    }

    public void clickBtnSaveChanges() {
        System.out.println("clickBtnSaveChanges");
        action.click(button("Save Changes"));
    }
    public void selectBurgerList(String list) {
        action.click(burgerList(list));
    }

    public void search(String query) {
        action.inputText(searchBar, query);
    }

    public void clickComboBoxAndSelectValue(String comboBoxName, String targetValue)    {
        action.inputText(comboBoxSelector(comboBoxName), targetValue);
        action.arrowDownKey(comboBoxSelector(comboBoxName));
        action.enterKey(comboBoxSelector(comboBoxName));
    }

    public void clickComboBoxAndSelectValue(By comboBox, String targetValue)    {
        action.inputText(comboBox, targetValue);
        delay(500);
        action.arrowDownKey(comboBox);
        delay(500);
        action.enterKey(comboBox);
        delay(500);
    }

    public void clickDropdownAndSelectValue(By dropdownSelector, String targetValue) {
        action.click(dropdownSelector);
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownList(targetValue)));
        action.click(dropdownList(targetValue));
    }

    public void clickBurgerAndSelectValue(By burgerMenu, String targetValue) {
        action.click(burgerMenu);
        wait.until(ExpectedConditions.visibilityOfElementLocated(burgerList(targetValue)));
        wait.until(ExpectedConditions.elementToBeClickable(burgerList(targetValue)));
        action.click(burgerList(targetValue));
    }

    //TODO: input date need different locator than textbox (regarding MUI library update, it changes the structure)
    public By textInputDateSection(String marking, String dateSection) {
        return By.xpath("//div[./*[text()='"+marking+"']]//*[@aria-label = '"+dateSection+"']");
    }
    public void inputDate(String marking, String date) {
        // DD-MM-YYYY
        //div[./*[text()='Ended at *']]//*[@aria-label = 'Day']
        String[] parts = date.split("-");
        String day   = parts[0];
        String month = parts[1];
        String year  = parts[2];

        // input day
        action.inputTextDate(textInputDateSection(marking,"Day"), day);
        action.inputTextDate(textInputDateSection(marking,"Month"), month);
        action.inputTextDate(textInputDateSection(marking,"Year"), year);

    }

    public void selectMenuLocation() {
        System.out.println("selectMenuLocation");
        action.click(menu("Location"));
    }

    public void selectMenuGamification() {
        System.out.println("selectMenuGamification");
        action.click(menu("Gamification"));
    }

    public void selectMenuFAQ() {
        System.out.println("selectMenuFAQ");
        action.click(menu("FAQ"));
    }

    public void selectMenuClaimRequest() {
        System.out.println("selectMenuClaimRequest");
        action.click(menu("Claim Request"));
    }

    public void selectSubMenuLoyalty() {
        System.out.println("selectSubMenuLoyalty");
        if(driver.findElements(subMenu("Loyalty Program", "Loyalty")).isEmpty()) {
            action.click(menu("Loyalty Program"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Loyalty Program", "Loyalty")));
            action.click(subMenu("Loyalty Program", "Loyalty"));
        }else {
            action.click(subMenu("Loyalty Program", "Loyalty"));
        }

    }

    public void selectSubMenuPromo() {
        System.out.println("selectSubMenuPromo");
        if(driver.findElements(subMenu("Loyalty Program", "Promo")).isEmpty()) {
            action.click(menu("Loyalty Program"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Loyalty Program", "Promo")));
            action.click(subMenu("Loyalty Program", "Promo"));
        }else {
            action.click(subMenu("Loyalty Program", "Promo"));
        }

    }

    public void selectSubMenuVoucher() {
        System.out.println("selectSubMenuVoucher");
        if(driver.findElements(subMenu("Loyalty Program", "Voucher")).isEmpty()) {
            action.click(menu("Loyalty Program"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Loyalty Program", "Voucher")));
            action.click(subMenu("Loyalty Program", "Voucher"));
        }else {
            action.click(subMenu("Loyalty Program", "Voucher"));
        }

    }

    public void selectSubMenuMerchantList() {
        System.out.println("selectSubMenuMerchantList");
        if(driver.findElements(subMenu("Merchant", "Merchant List")).isEmpty()) {
            action.click(menu("Merchant"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Merchant", "Merchant List")));
            action.click(subMenu("Merchant", "Merchant List"));
        }else {
            action.click(subMenu("Merchant", "Merchant List"));
        }

    }

    public void selectSubMenuPaymentMethod() {
        System.out.println("selectSubMenuPaymentMethod");
        action.click(menu("Payment Issuer"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(menu("Payment Method")));
        action.click(menu("Payment Method"));
    }

    public void selectSubMenuIssuer() {
        System.out.println("selectSubMenuIssuer");
        action.click(menu("Payment Issuer"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(menu("Issuer")));
        action.click(menu("Issuer"));
    }

    public void selectSubMenuGroupMember() {
        System.out.println("selectSubMenuGroupMember");
        if(driver.findElements(subMenu("Member", "Group Member")).isEmpty()) {
            action.click(menu("Member"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Member", "Group Member")));
            action.click(subMenu("Member", "Group Member"));
        }else {
            action.click(subMenu("Member", "Group Member"));
        }

    }

    public void selectSubMenuBIN() {
        System.out.println("selectSubMenuBIN");
        action.click(menu("Payment Issuer"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(menu("BIN")));
        action.click(menu("BIN"));
    }

    public void selectSubMenuFeature() throws InterruptedException {
        System.out.println("selectSubMenuFeature");

        if(!driver.findElements(menu("Feature")).isEmpty()) {
            action.click(menu("Feature"));
        }else {
            action.click(menu("Config"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(menu("Feature")));
            action.click(menu("Feature"));
        }
    }

    public void selectSubMenuApprovalLoyalty() {
        System.out.println("selectSubMenuApprovalLoyalty");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Approval", "Loyalty Program")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        action.click(subMenu("Approval", "Loyalty Program"));
    }

    public void selectSubMenuApprovalUser() {
        System.out.println("selectSubMenuApprovalUser");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(menu("User")));
        action.click(menu("User"));
    }

    public void selectSubMenuApprovalGroup() {
        System.out.println("selectSubMenuApprovalGroup");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(menu("Group")));
        action.click(menu("Group"));
    }

    public void selectSubMenuApprovalSubgroup() {
        System.out.println("selectSubMenuApprovalSubgroup");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(menu("Subgroup")));
        action.click(menu("Subgroup"));
    }

    public void selectSubMenuApprovalPackage() {
        System.out.println("selectSubMenuApprovalPackage");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(menu("Package")));
        action.click(menu("Package"));
    }

    public void selectSubMenuApprovalPromo() {
        System.out.println("selectSubMenuApprovalPromo");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Approval", "Promo")));
        action.click(subMenu("Approval", "Promo"));
    }

    public void selectSubMenuApprovalVoucher() {
        System.out.println("selectSubMenuApprovalVoucher");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Approval", "Voucher")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        action.click(subMenu("Approval", "Voucher"));
    }

    public void selectSubMenuApprovalMerchant() {
        System.out.println("selectSubMenuApprovalMerchant");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Approval", "Merchant")));
        action.click(subMenu("Approval", "Merchant"));
    }

    public void selectSubMenuApprovalGroupMember() {
        System.out.println("selectSubMenuApprovalGroupMember");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Approval", "Group Member")));
        action.click(subMenu("Approval", "Group Member"));
    }

    public void selectSubMenuApprovalLocation() {
        System.out.println("selectSubMenuApprovalLocation");
        action.click(menu("Approval"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Approval", "Location")));
        action.click(subMenu("Approval", "Location"));
    }

//    public boolean verifyToast (String expectedAlert){
//        String actualAlert = driver.findElement(alertPopup).getText();
//        System.out.println(actualAlert);
//        return Objects.equals(actualAlert, expectedAlert);
//    }

    public boolean verifyToast (String expectedAlert){
        System.out.println("Verif toast" + !driver.findElements(alertPopup(expectedAlert)).isEmpty());
        return !driver.findElements(alertPopup(expectedAlert)).isEmpty();
    }

    public boolean verifSearchExist(String query, By itemList) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
        driver.findElement(searchBar).sendKeys(query);
        System.out.println("Verif search exist " + !driver.findElements(itemList).isEmpty());
        return !driver.findElements(itemList).isEmpty();
    }

    public boolean verifTextValue(By locator, String expectedText) {
        System.out.println("Verif text value");
        String text1 =  driver.findElement(locator).getText();
        String text2 = driver.findElement(locator).getAttribute("value");

        if (!Objects.equals(text1, "")) {
            System.out.println("getText1 : " + text1);
            return text1.equals(expectedText);
        }else {
            System.out.println("getText2 : " + text2);
            return text2.equals(expectedText);
        }
    }

    public boolean verifSelected(By locator) {
        System.out.println("Verif selected" + driver.findElement(locator).isSelected());
        return driver.findElement(locator).isSelected();
    }

    public boolean verifEnabled(By locator) {
        System.out.println("Verif enabled" + driver.findElement(locator).isEnabled());
        return driver.findElement(locator).isEnabled();
    }

    public boolean verifErrorMandatory(String error) {
        System.out.println("Verif error mandatory" + !driver.findElements(errorValidationMandatory(error)).isEmpty());
        return !driver.findElements(errorValidationMandatory(error)).isEmpty();
    }

    public boolean verifTextExist(String text) {
        System.out.println("Verif text exist" + !driver.findElements(By.xpath("//*[text()='"+text+"']")).isEmpty());
        return !driver.findElements(By.xpath("//*[text()='"+text+"']")).isEmpty();
    }

    public void delay(int mil) {
        System.out.println("delay " + mil);
        try {
            Thread.sleep(mil);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
