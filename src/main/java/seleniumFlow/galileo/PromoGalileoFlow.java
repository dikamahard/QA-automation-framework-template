package seleniumFlow.galileo;


import com.fasterxml.jackson.databind.JsonNode;
import helper.TestLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.web.galileo.DashboardPage;
import pages.web.galileo.LoginPage;
import pages.web.galileo.PromoDetailPage;
import pages.web.galileo.PromoPage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static helper.TestDataLoader.*;

public class PromoGalileoFlow extends BaseGalileoFlow {
    public PromoGalileoFlow(WebDriver driver, String hostGalileo) {
        super(driver, hostGalileo);
    }


    public void addPromoGalileoStep(JsonNode testData, String uniqueness) {
        TestLogger.startFunc();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PromoPage promoPage = new PromoPage(driver);
        PromoDetailPage promoDetailPage = new PromoDetailPage(driver);

        String today = LocalDate.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));   // e.g. "12-11-2025"

        JsonNode root2 = loadTree("testdata/web/galileo/login.json");


        try {
            driver.get(hostGalileo);
            loginPage.loginWithValidData(root2.get("valid").get("username").asText(), root2.get("valid").get("password").asText());
            dashboardPage.wait.until(ExpectedConditions.urlToBe(DashboardPage.url));

            dashboardPage.selectSubMenuPromo();
            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(promoPage.title));
            promoPage.clickBtnAddPromo();
            promoDetailPage.wait.until(ExpectedConditions.visibilityOfElementLocated(promoDetailPage.title));


            // Test Case
            promoDetailPage.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[.='Drop or Select file']")));
            promoDetailPage.uploadPromoBanner(getImgPathFromResource(getTestData(testData, "pathBanner")));
            promoDetailPage.inputComboboxLoyaltyProgram(getTestData(testData, "loyaltyProgram"));
            promoDetailPage.inputPromoName(getTestData(testData, "promoName") + uniqueness);
            promoDetailPage.inputPromoCode(getTestData(testData, "promoCode") + uniqueness);
            promoDetailPage.inputProgramFund(getTestData(testData, "programFund"));
            //promoDetailPage.inputEndedAt(today);    //TODO: something change with the date

            promoDetailPage.inputDropdownPromoType(getTestData(testData, "promoType"));
            promoDetailPage.inputDropdownCalculationType(getTestData(testData, "calcType"));

            // loyalty program
            promoDetailPage.clickAddPromoCalculationLP();
            promoDetailPage.inputComboBoxMembership("Select All");
            promoDetailPage.inputPromoAmountLP(1, getTestData(testData, "loyaltyPromoAmount1"));
            promoDetailPage.inputMinTrxLP(1, getTestData(testData, "loyaltyMinTrx1"));
//            promoDetailPage.inputMaxTrxLP(1, getTestData(testData, "loyaltyMaxTrx1"));
//            promoDetailPage.clickAddPromoCalculationLP();
//            promoDetailPage.inputPromoAmountLP(2, getTestData(testData, "loyaltyPromoAmount2"));
//            promoDetailPage.inputMinTrxLP(2, getTestData(testData, "loyaltyMinTrx2"));
            promoDetailPage.setCheckBoxEndTierLP();

            // galileo member
            promoDetailPage.clickAddPromoCalculationGM();
            promoDetailPage.inputPromoAmountGM(1, getTestData(testData, "galileoPromoAmount1"));
            promoDetailPage.inputMinTrxGM(1, getTestData(testData, "galileoMinTrx1"));
//            promoDetailPage.inputMaxTrxGM(1, getTestData(testData, "galileoMaxTrx1"));
//            promoDetailPage.clickAddPromoCalculationGM();
//            promoDetailPage.inputPromoAmountGM(2, getTestData(testData, "galileoPromoAmount2"));
//            promoDetailPage.inputMinTrxGM(2, getTestData(testData, "galileoMinTrx2"));
            promoDetailPage.setCheckBoxEndTierGM();

            // non galileo member
            promoDetailPage.clickAddPromoCalculationNGM();
            promoDetailPage.inputPromoAmountNGM(1, getTestData(testData, "nonGalileoPromoAmount1"));
            promoDetailPage.inputMinTrxNGM(1, getTestData(testData, "nonGalileoMinTrx1"));
//            promoDetailPage.inputMaxTrxNGM(1, "800");
//            promoDetailPage.clickAddPromoCalculationNGM();
//            promoDetailPage.inputPromoAmountNGM(2, "810");
//            promoDetailPage.inputMinTrxNGM(2, "810");
            promoDetailPage.setCheckBoxEndTierNGM();

            promoDetailPage.inputDropdownPeriodType(getTestData(testData, "periodType"));
            promoDetailPage.inputMaxCountUser(getTestData(testData, "maxCountUser"));

            promoDetailPage.setCheckBoxAllMerchant();
            promoDetailPage.clickAddAllDay();
            promoDetailPage.setCheckBoxAllPayment();

            promoDetailPage.clickBtnAddPromo();


            // Assert
            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(promoPage.alertPopup("Insert promo success")));
            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(promoPage.title));

        } finally{
            TestLogger.endFunc();
        }
    }

}
