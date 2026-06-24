package pages.base;

import helper.ActionWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public abstract class BaseWebPage {

    protected final WebDriver driver;
    protected final ActionWeb action;
    public WebDriverWait wait;

    public BaseWebPage(WebDriver driver) {
        this.driver = driver;
        this.action = new ActionWeb(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    //public By alertPopup = By.xpath("((//div[@class=\"Toastify__toast-icon Toastify--animate-icon Toastify__zoom-enter\"]/parent::div)//child::*/following-sibling::div)[last()]");
    public By alertPopup(String textAlert) {
        return By.xpath("(//div[@class=\"Toastify__toast-icon Toastify--animate-icon Toastify__zoom-enter\"]/parent::div)//child::*/following-sibling::div[.='"+textAlert+"']");
    }

    public By alertPopupContains(String textAlert) {
        return By.xpath("(//div[@class=\"Toastify__toast-icon Toastify--animate-icon Toastify__zoom-enter\"]/parent::div)//child::*/following-sibling::div[contains(.,'"+textAlert+"')]");
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

    public static By textArea (String textAreaName){
        return By.xpath ("//*[./label[.='"+textAreaName+"']]//textarea[1]");
    }

    public static By textArea (String textAreaName, int index){
        return By.xpath ("(//*[contains(.,'"+textAreaName+"')]//textarea[1])["+index+"]");
    }

    public static By textBox (String textBoxName){
        return By.xpath ("//*[.='"+textBoxName+"']/following-sibling::*/input");
    }

    public static By textBox (String textBoxName, int index){
        return By.xpath ("(//*[.='"+textBoxName+"']/following-sibling::*/input)["+index+"]");
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

    public static By comboBoxSelector (String marking, int index){
        return By.xpath("(//label[text()='"+marking+"']/following-sibling::div/child::input)["+index+"]");
        //*[text()='"+marking+"']/parent::*//input[@role='combobox']
        //label[text()='"+marking+"']/following-sibling::div/child::input
    }

    public static By dropdownSelector(String dropdownName){
        return By.xpath("//label[text()='"+dropdownName+"']//following-sibling::div/child::div[@role='button']");
    }
    public static By dropdownSelector(String dropdownName, int index){
        return By.xpath("(//label[text()='"+dropdownName+"']//following-sibling::div/child::div[@role='button'])["+index+"]");
    }

    public static By titlePage(String title) {
        return By.xpath("//h4[contains(@class, 'MuiTypography-root MuiTypography-h4 MuiTypography-gutterBottom') and .='"+title+"']");
    }

    public By burgerMenu(int index){
        return By.xpath("(//td[@class='MuiTableCell-root MuiTableCell-body MuiTableCell-alignRight MuiTableCell-sizeMedium css-1lymf83']//child::button)["+index+"]");
    }

    public By tbMarking(String marking, String label, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//label[text()='"+label+"']//following-sibling::div/child::input)["+index+"]");
    }

    public By taMarking(String marking, String label, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//label[text()='"+label+"']//following-sibling::div/child::textarea[1])["+index+"]");
    }

    public By dropMarking(String marking, String label, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//label[text()='"+label+"']//following-sibling::div/child::div[@role='button'])["+index+"]");
    }

    public By deleteMarking(String marking, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//button[.//*[@data-testid='DeleteIcon']])["+index+"]");
    }

    public By inputImageMarking(String marking, int index) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//child::input[@type='file'])["+index+"]");
    }

    public By inputImageMarking(String marking) {
        return By.xpath("(//div[./*[.='"+marking+"']]/following-sibling::div//child::input[@type='file'])");
    }

    public By buttonMarking(String marking, String button) {
        return By.xpath("(//div[./*[.='"+marking+"']])[last()]/following-sibling::div//button[.='"+button+"'][1]");
    }

    public By alertPartialMessage(String textAlert) {
        return By.xpath("//*[contains(text(), '" + textAlert + "')]");
    }

    public By tableRows(String tableName) {
        return By.xpath("//*[.='"+tableName+"']/following-sibling::*[.//table]//tbody/tr");
    }

    public By iconX(int index) {
        return By.xpath("(//button[./*[@role='img' and contains(@class, 'MuiBox-root css-0 iconify iconify--eva')]])["+index+"]");
    }

    public static By dropdownList (String targetValue){
        return By.xpath("//li[text()='"+targetValue+"']");
    }
    public static By searchBar = By.xpath("//input[@placeholder='Search']");
    public static By burgerMenu = By.xpath("//td[@class='MuiTableCell-root MuiTableCell-body MuiTableCell-alignRight MuiTableCell-sizeMedium css-1lymf83']//child::button");
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
    public By errorValidationFileTypeMustBe = By.xpath("//*[text()='File type must be image/jpeg,image/png,image/jpg']");


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
        action.arrowDownKey(comboBox);
        action.enterKey(comboBox);
    }

    public void clickDropdownAndSelectValue(By dropdownSelector, String targetValue) {
        action.click(dropdownSelector);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownList(targetValue)));
        action.click(dropdownList(targetValue));
    }

    public void clickBurgerAndSelectValue(By burgerMenu, String targetValue) {
        action.click(burgerMenu);
        wait.until(ExpectedConditions.visibilityOfElementLocated(burgerList(targetValue)));
        wait.until(ExpectedConditions.elementToBeClickable(burgerList(targetValue)));
        action.click(burgerList(targetValue));
    }

    public void clickButtonDropdownAndSelectValue(By buttonDropdown, String targetValue) {
        action.click(buttonDropdown);
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownList(targetValue)));
        action.click(dropdownList(targetValue));
    }

    public void clickButtonDropdownAndSelectValue(String buttonDropdown, String targetValue) {
        action.click(button(buttonDropdown));
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownList(targetValue)));
        action.click(dropdownList(targetValue));
    }

    public void selectSubMenu(String menuName, String subMenuName) {
        System.out.println("Select Sub Menu " + subMenuName);
        if(driver.findElements(subMenu(menuName, subMenuName)).isEmpty()) {
            action.click(menu(menuName));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu(menuName, subMenuName)));
        }
        action.click(subMenu(menuName, subMenuName));
    }

    public void selectSubMenuStore() {
        System.out.println("selectSubMenuStore");
        if(driver.findElements(subMenu("Administration", "Store")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Store")));
            action.click(subMenu("Administration", "Store"));
        }else {
            action.click(subMenu("Administration", "Store"));
        }
    }

    public void selectSubMenuMerchant() {
        System.out.println("selectSubMenuMerchant");
        if(driver.findElements(subMenu("Administration", "Merchant")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Merchant")));
            action.click(subMenu("Administration", "Merchant"));
        }else {
            action.click(subMenu("Administration", "Merchant"));
        }
    }

    public void selectSubMenuPromo() {
        System.out.println("selectSubMenuPromo");
        if(driver.findElements(subMenu("Administration", "Promo")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Promo")));
            action.click(subMenu("Administration", "Promo"));
        }else {
            action.click(subMenu("Administration", "Promo"));
        }
    }

    public void selectSubMenuProductOption() {
        System.out.println("selectSubMenuProductOption");
        if(driver.findElements(subMenu("Administration", "Product Option")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Product Option")));
            action.click(subMenu("Administration", "Product Option"));
        }else {
            action.click(subMenu("Administration", "Product Option"));
        }
    }

    public void selectSubMenuProductCategory() {
        System.out.println("selectSubMenuProductCategory");
        if(driver.findElements(subMenu("Administration", "Product Category")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Product Category")));
            action.click(subMenu("Administration", "Product Category"));
        }else {
            action.click(subMenu("Administration", "Product Category"));
        }
    }

    public void selectSubMenuProduct() {
        System.out.println("selectSubMenuProduct");
        if(driver.findElements(subMenu("Administration", "Product")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Product")));
            action.click(subMenu("Administration", "Product"));
        }else {
            action.click(subMenu("Administration", "Product"));
        }
    }

    public void selectSubMenuMaterialCategory() {
        System.out.println("selectSubMenuMaterialCategory");
        if(driver.findElements(subMenu("Raw Material", "Material Category")).isEmpty()) {
            action.click(menu("Raw Material"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Raw Material", "Material Category")));
            action.click(subMenu("Raw Material", "Material Category"));
        }else {
            action.click(subMenu("Raw Material", "Material Category"));
        }
    }

    public void selectSubMenuMaterial() {
        System.out.println("selectSubMenuMaterial");
        if(driver.findElements(subMenu("Raw Material", "Material")).isEmpty()) {
            action.click(menu("Raw Material"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Raw Material", "Material")));
            action.click(subMenu("Raw Material", "Material"));
        }else {
            action.click(subMenu("Raw Material", "Material"));
        }
    }

    public void selectSubMenuMaterialProductOption() {
        System.out.println("selectSubMenuMaterialProductOption");
        if(driver.findElements(subMenu("Raw Material", "Material Product Option")).isEmpty()) {
            action.click(menu("Raw Material"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Raw Material", "Material Product Option")));
            action.click(subMenu("Raw Material", "Material Product Option"));
        }else {
            action.click(subMenu("Raw Material", "Material Product Option"));
        }
    }

    public void selectSubMenuMaterialVariant() {
        System.out.println("selectSubMenuMaterialVariant");
        if(driver.findElements(subMenu("Raw Material", "Material Variant")).isEmpty()) {
            action.click(menu("Raw Material"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Raw Material", "Material Variant")));
            action.click(subMenu("Raw Material", "Material Variant"));
        }else {
            action.click(subMenu("Raw Material", "Material Variant"));
        }
    }

    public void selectSubMenuDevice() {
        System.out.println("selectSubMenuDevice");
        if(driver.findElements(subMenu("Administration", "Device")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Device")));
            action.click(subMenu("Administration", "Device"));
        }else {
            action.click(subMenu("Administration", "Device"));
        }
    }

    public void selectSubMenuEmployee() {
        System.out.println("selectSubMenuEmployee");
        if(driver.findElements(subMenu("Administration", "Employee")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Employee")));
            action.click(subMenu("Administration", "Employee"));
        }else {
            action.click(subMenu("Administration", "Employee"));
        }
    }

    public void selectSubMenuRoom() {
        System.out.println("selectSubMenuRoom");
        if(driver.findElements(subMenu("Administration", "Room")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Room")));
            action.click(subMenu("Administration", "Room"));
        }else {
            action.click(subMenu("Administration", "Room"));
        }
    }

    public void selectSubMenuPrinter() {
        System.out.println("selectSubMenuPrinter");
        if(driver.findElements(subMenu("Administration", "Printer")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Printer")));
            action.click(subMenu("Administration", "Printer"));
        }else {
            action.click(subMenu("Administration", "Printer"));
        }
    }

    public void selectMenuReservation() {
        System.out.println("selectMenuReservation");
        action.click(menu("Reservation"));
    }

    public void selectMenuStoreConfig() {
        System.out.println("selectMenuStoreConfig");
        action.click(menu("Store Config"));
    }

    public void selectSubMenuSalesMode() {
        System.out.println("selectSubMenuSalesMode");
        if(driver.findElements(subMenu("Administration", "Sales Mode")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Sales Mode")));
            action.click(subMenu("Administration", "Sales Mode"));
        }else {
            action.click(subMenu("Administration", "Sales Mode"));
        }
    }

    public void selectSubMenuAdditionalCharge() {
        System.out.println("selectSubMenuAdditionalCharge");
        if(driver.findElements(subMenu("Administration", "Additional Charge")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Additional Charge")));
            action.click(subMenu("Administration", "Additional Charge"));
        }else {
            action.click(subMenu("Administration", "Additional Charge"));
        }
    }

    public void selectSubMenuTable() {
        System.out.println("selectSubMenuTable");
        if(driver.findElements(subMenu("Administration", "Table")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Table")));
            action.click(subMenu("Administration", "Table"));
        }else {
            action.click(subMenu("Administration", "Table"));
        }
    }

    public void selectSubSubMenuUserManagement() {
        System.out.println("selectSubSubMenuUserManagement");
        if(driver.findElements(subMenu("Portal Management", "User Management")).isEmpty()) {
            action.click(menu("Portal Management"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Portal Management", "User Management")));
            action.click(subMenu("Portal Management", "User Management"));

            if(driver.findElements(subMenu("User Management", "User")).isEmpty()) {
                action.click(menu("User Management"));
                wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("User Management", "User")));
                action.click(subMenu("User Management", "User"));
            }else {
                action.click(subMenu("User Management", "User"));
            }
        }else {
            if(driver.findElements(subMenu("User Management", "User")).isEmpty()) {
                action.click(menu("User Management"));
                wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("User Management", "User")));
                action.click(subMenu("User Management", "User"));
            }else {
                action.click(subMenu("User Management", "User"));
            }        }
    }

    public void selectSubSubMenuSubGroupManagement() {
        System.out.println("selectSubSubMenuSubGroupManagement");
        if(driver.findElements(subMenu("Portal Management", "User Management")).isEmpty()) {
            action.click(menu("Portal Management"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Portal Management", "User Management")));
            action.click(subMenu("Portal Management", "User Management"));

            if(driver.findElements(subMenu("User Management", "Subgroup")).isEmpty()) {
                action.click(menu("User Management"));
                wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("User Management", "Subgroup")));
                action.click(subMenu("User Management", "Subgroup"));
            }else {
                action.click(subMenu("User Management", "Subgroup"));
            }
        }else {
            if(driver.findElements(subMenu("User Management", "Subgroup")).isEmpty()) {
                action.click(menu("User Management"));
                wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("User Management", "Subgroup")));
                action.click(subMenu("User Management", "Subgroup"));
            }else {
                action.click(subMenu("User Management", "Subgroup"));
            }
        }
    }

    public void selectMenuLayoutTable() {
        System.out.println("selectMenuLayoutTable");
        action.click(menu("Layout Table"));
    }

    public void selectSubMenuReportSummary() {
        System.out.println("selectSubMenuReportSummary");
        if(driver.findElements(subMenu("Report", "Summary")).isEmpty()) {
            action.click(menu("Report"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Report", "Summary")));
            action.click(subMenu("Report", "Summary"));
        }else {
            action.click(subMenu("Report", "Summary"));
        }
    }

    public void selectSubMenuReportDetail() {
        System.out.println("selectSubMenuReportDetail");
        if(driver.findElements(subMenu("Report", "Detail")).isEmpty()) {
            action.click(menu("Report"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Report", "Detail")));
            action.click(subMenu("Report", "Detail"));
        }else {
            action.click(subMenu("Report", "Detail"));
        }
    }

    public void selectSubMenuReportSales() {
        System.out.println("selectSubMenuReportSales");
        if(driver.findElements(subMenu("Report", "Sales")).isEmpty()) {
            action.click(menu("Report"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Report", "Sales")));
            action.click(subMenu("Report", "Sales"));
        }else {
            action.click(subMenu("Report", "Sales"));
        }
    }

    public void selectSubMenuReportRawMaterial() {
        System.out.println("selectSubMenuReportRawMaterial");
        if(driver.findElements(subMenu("Report", "Raw Material")).isEmpty()) {
            action.click(menu("Report"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Report", "Raw Material")));
            action.click(subMenu("Report", "Raw Material"));
        }else {
            action.click(subMenu("Report", "Raw Material"));
        }
    }

    public void selectSubMenuReportQris() {
        System.out.println("selectSubMenuReportQris");
        if(driver.findElements(subMenu("Report", "Qris")).isEmpty()) {
            action.click(menu("Report"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Report", "Qris")));
            action.click(subMenu("Report", "Qris"));
        }else {
            action.click(subMenu("Report", "Qris"));
        }
    }

    public void selectSubMenuReportMonei() {
        System.out.println("selectSubMenuReportMonei");
        if(driver.findElements(subMenu("Report", "Monei")).isEmpty()) {
            action.click(menu("Report"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Report", "Monei")));
            action.click(subMenu("Report", "Monei"));
        }else {
            action.click(subMenu("Report", "Monei"));
        }
    }

    public void selectSubMenuReportEDC() {
        System.out.println("selectSubMenuReportEDC");
        if(driver.findElements(subMenu("Report", "EDC")).isEmpty()) {
            action.click(menu("Report"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Report", "EDC")));
            action.click(subMenu("Report", "EDC"));
        }else {
            action.click(subMenu("Report", "EDC"));
        }
    }

    public void selectMenuTransaction() {
        System.out.println("selectMenuTransaction");
        action.click(menu("Transaction"));
    }

    public void selectSubMenuStock() {
        System.out.println("selectSubMenuStock");
        if(driver.findElements(subMenu("Administration", "Stock")).isEmpty()) {
            action.click(menu("Administration"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subMenu("Administration", "Stock")));
            action.click(subMenu("Administration", "Stock"));
        }else {
            action.click(subMenu("Administration", "Stock"));
        }
    }

//    public boolean verifyToast (String expectedAlert){
//        String actualAlert = driver.findElement(alertPopup).getText();
//        System.out.println(actualAlert);
//        return Objects.equals(actualAlert, expectedAlert);
//    }

    public boolean verifyToast (String expectedAlert){
        System.out.println("Verif toast " + !driver.findElements(alertPopup(expectedAlert)).isEmpty());
        return !driver.findElements(alertPopup(expectedAlert)).isEmpty();
    }

    public boolean verifyToastContains (String expectedAlert){
        System.out.println("Verif toast " + !driver.findElements(alertPopupContains(expectedAlert)).isEmpty());
        return !driver.findElements(alertPopupContains(expectedAlert)).isEmpty();
    }

    public boolean verifSearchExist(String query, By itemList) {
        driver.findElement(searchBar).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        driver.findElement(searchBar).sendKeys(query);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(itemList));
        }catch (Exception ignored) {
        }
        System.out.println("Verif search exist " + !driver.findElements(itemList).isEmpty());
        return !driver.findElements(itemList).isEmpty();
    }

    public boolean verifTextValue(By locator, String expectedText) {
        System.out.println("Verif text value");
        String text1 =  driver.findElement(locator).getText();
        String text2 = driver.findElement(locator).getAttribute("value");

        if (!Objects.equals(text1, "")) {
            System.out.println("actual text1 : " + text1 + " | expected : " + expectedText);
            return text1.equals(expectedText);
        }else {
            System.out.println("actual text2 : " + text2+ " | expected : " + expectedText);
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
        System.out.println("Verif error mandatory of ["+ error + "] "+ !driver.findElements(errorValidationMandatory(error)).isEmpty());
        return !driver.findElements(errorValidationMandatory(error)).isEmpty();
    }

    public boolean verifButtonExist(By locator) {
        System.out.println("Verif selected" + driver.findElements(locator).isEmpty());
        return !driver.findElements(locator).isEmpty();
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
