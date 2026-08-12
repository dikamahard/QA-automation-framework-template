package pages.web.web1;

import helper.TestDataLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BaseWebWeb1Page;
import pages.web.PromoPage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PromoDetailPage extends BaseWebWeb1Page {
    public PromoDetailPage(WebDriver driver) {
        super(driver);
    }

    public static String urlAdd = "http://192.168.7.110:9797/loyalty-program/promo/add";
    public static String urlEdit = "http://192.168.7.110:9797/loyalty-program/promo/edit";
    public By title = titlePage("Promo");
    public By inputImageBanner = By.xpath("//div[contains(.,'Banner')]/following-sibling::div//input[@type='file']");
    public By comboLoyaltyProgram = comboBoxSelector("Loyalty Program *");
    public By tbPromoName = textBox("Promo Name *");
    public By tbPromoCode = textBox("Promo Code *");
    public By tbProgramFund = textBox("Program Fund *");
    public By tbStartedAt = textBox("Started at *");
    public By tbEndeddAt = textBox("Ended at *");
    public By switchPublishPromo = switchButton("Publish Promo");
    public By switchCombinable = switchButton("Combinable with voucher");
    public By taPromoDescriptions = textArea("Description, Additional Terms & Conditions");
    public By dropPromoType = dropdownSelector("Promo Type *");
    public By dropCalculationType = dropdownSelector("Calculation Type *");
    public By btnAddLPCalculation = By.xpath("//*[text()='Loyalty Program Member']/following-sibling::button");
    public By btnAddGMCalculation = By.xpath("//*[text()='Galileo Member']/following-sibling::button");
    public By btnAddNGMCalculation = By.xpath("//*[text()='Non-Galileo User']/following-sibling::button");

    public By comboMembership = comboBoxSelector("Membership");

    public By btnDeleteTier(String member, int level) {
        return By.xpath("//div[./div/h6[.='"+member+"']]//div[.='Level "+level+"']/button");
    }
    public By tbPromoCalculation(String member, int level, String marking) {
        return By.xpath("//div[./div/h6[.='"+member+"']]//div[.='Level "+level+"']/following-sibling::div//input[./parent::div/preceding-sibling::label[.='"+marking+"']]");
    }
    public By tbPromoAmountLP(int level) {
        return tbPromoCalculation("Loyalty Program Member", level, "Promo Amount *");
    }

    public By tbMinTrxLP(int level) {
        return tbPromoCalculation("Loyalty Program Member", level, "Minimum Transaction *");
    }

    public By tbMaxTrxLP(int level) {
        return tbPromoCalculation("Loyalty Program Member", level, "Maximum Transaction *");
    }

    public By tbPercentageLP(int level) {
        return tbPromoCalculation("Loyalty Program Member", level, "Percentage *");
    }


    public By tbPromoAmountGM(int level) {
        return tbPromoCalculation("Galileo Member", level, "Promo Amount *");
    }

    public By tbMinTrxGM(int level) {
        return tbPromoCalculation("Galileo Member", level, "Minimum Transaction *");
    }

    public By tbMaxTrxGM(int level) {
        return tbPromoCalculation("Galileo Member", level, "Maximum Transaction *");
    }

    public By tbPercentageGM(int level) {
        return tbPromoCalculation("Galileo Member", level, "Percentage *");
    }

    public By tbPromoAmountNGM(int level) {
        return tbPromoCalculation("Non-Galileo User", level, "Promo Amount *");
    }

    public By tbMinTrxNGM(int level) {
        return tbPromoCalculation("Non-Galileo User", level, "Minimum Transaction *");
    }

    public By tbMaxTrxNGM(int level) {
        return tbPromoCalculation("Non-Galileo User", level, "Maximum Transaction *");
    }

    public By tbPercentageNGM(int level) {
        return tbPromoCalculation("Non-Galileo User", level, "Percentage *");
    }

    public By cbEndTier(int index) {
        return checkbox("End of Tiering", index);
    }

    public By dropPeriodType = dropdownSelector("Period Type");
    public By tbMaxAmount = textBox("Max Amount");
    public By tbMaxCount = textBox("Maximum Count");
    public By tbMaxCountUser = textBox("Maximum Count Per User");
    public By cbAllMerchant = checkbox("All Merchant");
    public By comboMerchant = comboBoxSelector("Merchant");
    public By comboMerchantGroup = comboBoxSelector("Merchant Group");
    public By btnClearAllPromoTime = By.xpath("//div[./button[.='Add All Day']]/button[.='Clear All']");
    public By btnAddAllPromoTime = By.xpath("//div[./button[.='Add All Day']]/button[.='Add All Day']");
    public By btnAddPromoTime = By.xpath("//div[./button[.='Add All Day']]/button[.='Add']");

    public By btnDeletePromoTime(int index) {
        return By.xpath("(//div[.='Promo Time']/following-sibling::*//button[contains(@class, 'MuiButton-colorError')])["+index+"]");
//        return By.xpath("(//div[.='Promo Time']/following-sibling::*//button[.//*[@data-testid='DeleteIcon']])["+index+"]");
    }
    public By dropDayPromoTime (int row){
        return By.xpath("(//label[text()='Day']/following-sibling::div/child::div[1])["+row+"]");
    }
    public By dropdownStartedAt (String buttonName, int row){
        return By.xpath("(//p[text()='Started at']/parent::label/following-sibling::div//child::label[text()='"+buttonName+"']/following-sibling::div)["+row+"]");
    }

    public By dropdownEndedAt (String buttonName, int row){
        return By.xpath("(//p[text()='Ended at']/parent::label/following-sibling::div//child::label[text()='"+buttonName+"']/following-sibling::div)["+row+"]");
    }
    public By btnClearAllWallet = By.xpath("//div[./button[.='Add All Wallet']]/button[.='Clear All']");
    public By btnAddAllWallet = By.xpath("//div[./button[.='Add All Wallet']]/button[.='Add All Wallet']");
    public By btnAddWallet = By.xpath("//div[./button[.='Add All Wallet']]/button[.='Add']");
    public By btnClearAllBNPL = By.xpath("//div[./button[.='Add All BNPL']]/button[.='Clear All']");
    public By btnAddAllBNPL = By.xpath("//div[./button[.='Add All BNPL']]/button[.='Add All BNPL']");
    public By btnAddBNPL = By.xpath("//div[./button[.='Add All BNPL']]/button[.='Add']");
    public By btnClearAllBank = By.xpath("//div[./button[.='Add All Bank']]/button[.='Clear All']");
    public By btnAddAllBank = By.xpath("//div[./button[.='Add All Bank']]/button[.='Add All Bank']");
    public By btnAddBank = By.xpath("//div[./button[.='Add All Bank']]/button[.='Add']");
    public By cbAllPayment = checkbox("All Payment Method Including Cash");
    public By cbAllWallet = checkbox("All Wallet");
    public By cbAllBNPL = checkbox("All BNPL");
    public By cbAllBank = checkbox("All Bank");
    public By dropWallet = dropdownSelector("Wallet");
    public By dropBNPL = dropdownSelector("BNPL");
    public By dropBank = dropdownSelector("Bank");
    public By checkBoxPaymentContent(String payment) {
        return checkbox(payment);
    }

    public By btnDeleteWallet(int index) {
        return By.xpath("(//button[./parent::div/following-sibling::div[contains(.,'Wallet')]])["+index+"]");
    }
    public By btnDeleteBNPL(int index) {
        return By.xpath("(//button[./parent::div/following-sibling::div[contains(.,'BNPL')]])["+index+"]");
    }
    public By btnDeleteBank(int index) {
        return By.xpath("(//button[./parent::div/following-sibling::div[contains(.,'Bank')]])["+index+"]");
    }
    public By btnAddPromo = button("Add Promo");
    public By btnSaveChanges =  button("Save Changes");


    // ============================================================================================= //
    // ============================================================================================= //
    // ============================================================================================= //
    // ============================================================================================= //


    public void uploadPromoBanner(String path) {
        System.out.println("uploadPromoBanner");
        action.uploadFileGalileo(inputImageBanner, path);
    }

    public void inputComboboxLoyaltyProgram(String loyalty) {
        System.out.println("inputComboboxLoyaltyProgram");
        clickComboBoxAndSelectValue(comboLoyaltyProgram, loyalty);
    }

    public void inputPromoName(String name) {
        System.out.println("inputPromoName");
        action.inputText(tbPromoName, name);
    }

    public void inputPromoCode(String code) {
        System.out.println("inputPromoCode");
        action.inputText(tbPromoCode, code);
    }

    public void inputProgramFund(String fund) {
        System.out.println("inputProgramFund");
        action.inputText(tbProgramFund, fund);
    }

    public void inputStartedAt(String date) {
        System.out.println("inputStartedAt");
        //action.inputText(tbStartedAt, date);
        inputDate("Started at *", date);
    }

    public void inputEndedAt(String date) {
        System.out.println("inputEndedAt");
//        action.inputText(tbEndeddAt, date);
        inputDate("Ended at *", date);
    }
    public void inputPromoDesc(String desc) {
        System.out.println("inputPromoDesc");
        action.inputText(taPromoDescriptions, desc);
    }

    public void inputDropdownPromoType(String dropDownList) {
        System.out.println("inputDropdownPromoType");
        clickDropdownAndSelectValue(dropPromoType, dropDownList);
    }

    public void inputDropdownCalculationType(String dropDownList) {
        System.out.println("inputDropdownCalculationType");
        clickDropdownAndSelectValue(dropCalculationType, dropDownList);
    }

    public void clickAddPromoCalculationLP() {
        System.out.println("clickAddPromoCalculationLP");
        action.click(btnAddLPCalculation);
    }

    public void inputComboBoxMembership(String membership) {
        System.out.println("inputComboBoxMembership");
        action.inputText(comboMembership, membership);
        action.arrowDownKey(comboMembership);
        action.arrowDownKey(comboMembership);
        action.enterKey(comboMembership);
        action.click(comboMembership);
    }

    public void clickAddPromoCalculationGM() {
        System.out.println("clickAddPromoCalculationGM");
        action.click(btnAddGMCalculation);
    }

    public void clickAddPromoCalculationNGM() {
        System.out.println("clickAddPromoCalculationNGM");
        action.click(btnAddNGMCalculation);
    }

    public void clickDeleteTierLP(int level) {
        System.out.println("clickDeleteTierLP");
        action.click(btnDeleteTier("Loyalty Program Member", level));
    }

    public void clickDeleteTierGM(int level) {
        System.out.println("clickDeleteTierGM");
        action.click(btnDeleteTier("Galileo Member", level));
    }

    public void clickDeleteTierNGM(int level) {
        System.out.println("clickDeleteTierNGM");
        action.click(btnDeleteTier("Non-Galileo User", level));
    }

    public void inputPromoAmountLP(int level, String amount) {
        System.out.println("inputPromoAmountLP");
        action.inputText(tbPromoAmountLP(level), amount);
    }

    public void inputMinTrxLP(int level, String amount) {
        System.out.println("inputMinTrxLP");
        action.inputText(tbMinTrxLP(level), amount);
    }

    public void inputMaxTrxLP(int level, String amount) {
        System.out.println("inputMaxTrxLP");
        action.inputText(tbMaxTrxLP(level), amount);
    }

    public void inputPercentageLP(int level, String amount) {
        System.out.println("inputPercentageLP");
        action.inputText(tbPercentageLP(level), amount);
    }

    public void setCheckBoxEndTierLP() {
        System.out.println("setCheckBoxEndTierLP");
        action.click(clickable(cbEndTier(1)));
    }

    public void inputPromoAmountGM(int level, String amount) {
        System.out.println("inputPromoAmountGM");
        action.inputText(tbPromoAmountGM(level), amount);
    }

    public void inputMinTrxGM(int level, String amount) {
        System.out.println("inputMinTrxGM");
        action.inputText(tbMinTrxGM(level), amount);
    }

    public void inputMaxTrxGM(int level, String amount) {
        System.out.println("inputMaxTrxGM");
        action.inputText(tbMaxTrxGM(level), amount);
    }

    public void inputPercentageGM(int level, String amount) {
        System.out.println("inputPercentageGM");
        action.inputText(tbPercentageGM(level), amount);
    }

    public void setCheckBoxEndTierGM() {
        System.out.println("setCheckBoxEndTierGM");
        action.click(clickable(cbEndTier(2)));
    }

    public void inputPromoAmountNGM(int level, String amount) {
        System.out.println("inputPromoAmountNGM");
        action.inputText(tbPromoAmountNGM(level), amount);
    }

    public void inputMinTrxNGM(int level, String amount) {
        System.out.println("inputMinTrxNGM");
        action.inputText(tbMinTrxNGM(level), amount);
    }

    public void inputMaxTrxNGM(int level, String amount) {
        System.out.println("inputMaxTrxNGM");
        action.inputText(tbMaxTrxNGM(level), amount);
    }

    public void inputPercentageNGM(int level, String amount) {
        System.out.println("inputPercentageNGM");
        action.inputText(tbPercentageNGM(level), amount);
    }

    public void setCheckBoxEndTierNGM() {
        System.out.println("setCheckBoxEndTierNGM");
        action.click(clickable(cbEndTier(3)));
    }

    public void inputDropdownPeriodType(String dropdownList) {
        System.out.println("inputDropdownPeriodType");
        clickDropdownAndSelectValue(dropPeriodType, dropdownList);
    }

    public void inputMaxAmount(String amount) {
        System.out.println("inputMaxAmount");
        action.inputText(tbMaxAmount, amount);
    }

    public void inputMaxCount(String count) {
        System.out.println("inputMaxCount");
        action.inputText(tbMaxCount, count);
    }

    public void inputMaxCountUser(String count) {
        System.out.println("inputMaxCountUser");
        action.inputText(tbMaxCountUser, count);
    }

    public void setCheckBoxAllMerchant() {
        System.out.println("setCheckBoxAllMerchant");
        action.click(clickable(cbAllMerchant));
    }

    public void inputComboboxMerchant(String merchantName) {
        System.out.println("inputComboboxMerchant");
        action.inputText(comboMerchant, merchantName);
        action.arrowDownKey(comboMerchant);
        action.arrowDownKey(comboMerchant);
        action.enterKey(comboMerchant);
    }

    public void clickClearAllPromoTime() {
        System.out.println("clickClearAllPromoTime");
        action.click(btnClearAllPromoTime);
    }

    public void clickAddAllDay() {
        System.out.println("clickAddAllDay");
        action.click(btnAddAllPromoTime);
    }

    public void clickAddPromoTime() {
        System.out.println("clickAddPromoTime");
        action.click(btnAddPromoTime);
    }

    public void inputDropdownPromoDay(int row, String day) {
        clickDropdownAndSelectValue(dropDayPromoTime(row),day);
    }

    public void inputDropStartedAt(int row, String label, String time) {
        clickDropdownAndSelectValue(dropdownStartedAt(label, row), time);
    }

    public void inputDropEndedAt(int row, String label, String time) {
        clickDropdownAndSelectValue(dropdownEndedAt(label, row), time);
    }

    public void clickBtnDeletePromoTime(int index) {
        action.click(btnDeletePromoTime(index));
    }

    public void clickClearAllWallet() {
        System.out.println("clickClearAllWallet");
        action.click(btnClearAllWallet);
    }

    public void clickAddAllWallet() {
        System.out.println("clickAddAllWallet");
        action.click(btnAddAllWallet);
    }

    public void clickAddWallet() {
        System.out.println("clickAddWallet");
        action.click(btnAddWallet);
    }

    public void inputDropDownWallet(String dropList) {
        System.out.println("inputDropDownWallet");
        clickDropdownAndSelectValue(dropWallet, dropList);
    }

    public void clickClearAllBNPL() {
        System.out.println("clickClearAllBNPL");
        action.click(btnClearAllBNPL);
    }

    public void clickAddAllBNPL() {
        System.out.println("clickAddAllBNPL");
        action.click(btnAddAllBNPL);
    }

    public void clickAddBNPL() {
        System.out.println("clickAddBNPL");
        action.click(btnAddBNPL);
    }

    public void inputDropDownBNPL(String dropList) {
        System.out.println("inputDropDownBNPL");
        clickDropdownAndSelectValue(dropBNPL, dropList);
    }

    public void clickClearAllBank() {
        System.out.println("clickClearAllBank");
        action.click(btnClearAllBank);
    }

    public void clickAddAllBank() {
        System.out.println("clickAddAllBank");
        action.click(btnAddAllBank);
    }

    public void clickAddBank() {
        System.out.println("clickAddBank");
        action.click(btnAddBank);
    }

    public void inputDropDownBank(String dropList) {
        System.out.println("inputDropDownBank");
        clickDropdownAndSelectValue(dropBank, dropList);
    }


    public void setCheckBoxAllPayment() {
        System.out.println("setCheckBoxAllPayment");
        action.click(clickable(cbAllPayment));
    }

    public void setCheckBoxAllWallet(){
        System.out.println("setCheckBoxAllWallet");
        action.click(clickable(cbAllWallet));
    }

    public void setCheckBoxAllBNPL() {
        System.out.println("setCheckBoxAllBNPL");
        action.click(clickable(cbAllBNPL));
    }

    public void setCheckBoxAllBank() {
        System.out.println("setCheckBoxAllBank");
        action.click(clickable(cbAllBank));
    }

    public void clickBtnAddPromo() {
        System.out.println("clickBtnAddPromo");
        action.click(btnAddPromo);
    }

    public void setCheckBoxPaymentContent(String content) {
        System.out.println("setCheckBoxPaymentContent");
        action.click(clickable(checkbox(content)));
    }

    public void clickBtnDeleteWallet(int index) {
        System.out.println("clickBtnDeleteWallet");
        action.click(btnDeleteWallet(index));
    }

    public void clickBtnDeleteBNPL(int index) {
        System.out.println("clickBtnDeleteBNPL");
        action.click(btnDeleteBNPL(index));
    }

    public void clickBtnDeleteBank(int index) {
        System.out.println("clickBtnDeleteBank");
        action.click(btnDeleteBank(index));
    }

    public void clickBtnSaveChanges() {
        System.out.println("clickBtnSaveChanges");
        action.click(btnSaveChanges);
    }

    public void createPromo1(String promoName, String promoCode, String loyaltyName) {
        System.out.println("Start createPromo1");
        PromoPage promoPage = new PromoPage(driver);
        PromoDetailPage promoDetailPage = new PromoDetailPage(driver);

        String imgPath = TestDataLoader.getImgPathFromResource("testdata/images/game.png");
        String today = LocalDate.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));   // e.g. "12-11-2025"

        // Test Case
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[.='Drop or Select file']")));
        promoDetailPage.uploadPromoBanner(imgPath);
        promoDetailPage.inputComboboxLoyaltyProgram(loyaltyName);
        promoDetailPage.inputPromoName(promoName);
        promoDetailPage.inputPromoCode(promoCode);
        promoDetailPage.inputProgramFund("100000");
        promoDetailPage.inputEndedAt(today);

        promoDetailPage.inputDropdownPromoType("Discount");
        promoDetailPage.inputDropdownCalculationType("Fix Amount");

        promoDetailPage.clickAddPromoCalculationLP();
        promoDetailPage.inputComboBoxMembership("Bronze");
        promoDetailPage.inputPromoAmountLP(1, "1000");
        promoDetailPage.inputMinTrxLP(1, "1000");
        promoDetailPage.inputMaxTrxLP(1, "1000");
        promoDetailPage.clickAddPromoCalculationLP();
        promoDetailPage.inputPromoAmountLP(2, "1100");
        promoDetailPage.inputMinTrxLP(2, "1100");
        promoDetailPage.inputMaxTrxLP(2, "1100");
        promoDetailPage.clickAddPromoCalculationLP();
        promoDetailPage.inputPromoAmountLP(3, "1200");
        promoDetailPage.inputMinTrxLP(3, "1200");
        promoDetailPage.setCheckBoxEndTierLP();

        promoDetailPage.clickAddPromoCalculationGM();
        promoDetailPage.inputPromoAmountGM(1, "900");
        promoDetailPage.inputMinTrxGM(1, "900");
        promoDetailPage.inputMaxTrxGM(1, "900");
        promoDetailPage.clickAddPromoCalculationGM();
        promoDetailPage.inputPromoAmountGM(2, "910");
        promoDetailPage.inputMinTrxGM(2, "910");
        promoDetailPage.inputMaxTrxGM(2, "910");
        promoDetailPage.clickAddPromoCalculationGM();
        promoDetailPage.inputPromoAmountGM(3, "920");
        promoDetailPage.inputMinTrxGM(3, "920");
        promoDetailPage.setCheckBoxEndTierGM();

        promoDetailPage.clickAddPromoCalculationNGM();
        promoDetailPage.inputPromoAmountNGM(1, "800");
        promoDetailPage.inputMinTrxNGM(1, "800");
        promoDetailPage.inputMaxTrxNGM(1, "800");
        promoDetailPage.clickAddPromoCalculationNGM();
        promoDetailPage.inputPromoAmountNGM(2, "810");
        promoDetailPage.inputMinTrxNGM(2, "810");
        promoDetailPage.inputMaxTrxNGM(2, "810");
        promoDetailPage.clickAddPromoCalculationNGM();
        promoDetailPage.inputPromoAmountNGM(3, "820");
        promoDetailPage.inputMinTrxNGM(3, "820");
        promoDetailPage.setCheckBoxEndTierNGM();

        promoDetailPage.inputDropdownPeriodType("Daily");
        promoDetailPage.inputMaxAmount("100000");
        promoDetailPage.inputMaxCount("5");
        promoDetailPage.inputMaxCountUser("4");

        promoDetailPage.setCheckBoxAllMerchant();
        promoDetailPage.clickAddPromoTime();
        promoDetailPage.clickAddPromoTime();
        promoDetailPage.clickAddPromoTime();
        promoDetailPage.clickAddWallet();
        promoDetailPage.inputDropDownWallet("AstraPay");
        promoDetailPage.setCheckBoxPaymentContent("Astra Point");
        promoDetailPage.clickAddBNPL();
        //promoDetailPage.inputDropDownBNPL("Kredivo");
        promoDetailPage.setCheckBoxPaymentContent("Kredivo Credit");
        promoDetailPage.setCheckBoxPaymentContent("Kredivo Pay");
        promoDetailPage.clickAddBank();
        promoDetailPage.inputDropDownBank("BNI");
        promoDetailPage.setCheckBoxPaymentContent("BNI Debit");
        promoDetailPage.clickBtnAddPromo();

        //promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(promoPage.alertPopup("Insert promo success")));
        promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(promoPage.title));

        System.out.println("Done createPromo1");
    }

}
