package seleniumFlow.pos;

import com.fasterxml.jackson.databind.JsonNode;
import helper.TestLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.web.DashboardPage;
import pages.web.LoginPage;
import pages.web.PromoDetailPage;
import pages.web.PromoPage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static helper.TestDataLoader.getTestData;
import static helper.TestDataLoader.loadTree;

public class PromoFlow extends BaseFlow {

    public PromoFlow(WebDriver driver, String hostPos) {
        super(driver, hostPos);
    }



    public void addPromoStep(JsonNode testData, String uniqueness) {
        TestLogger.startFunc();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PromoPage promoPage = new PromoPage(driver);
        PromoDetailPage promoDetailPage = new PromoDetailPage(driver);

        JsonNode root2 = loadTree("testdata/web/login.json");
        String today = LocalDate.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));   // e.g. "12-11-2025"

        try {
            driver.get(hostPOS);
            loginPage.loginWithValidData(root2.get("valid").get("username").asText(), root2.get("valid").get("password").asText());
            dashboardPage.wait.until(ExpectedConditions.urlToBe(DashboardPage.url));

            dashboardPage.selectSubMenuPromo();

            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoPage.title));
            promoPage.clickBtnAddPromo();
            promoDetailPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoDetailPage.title));


            // Test Case
            promoDetailPage.clickDropdownStoreNameAndSelect(getTestData(testData, "storeName"));
            promoDetailPage.inputPromoName(getTestData(testData, "promoName") + uniqueness);
            promoDetailPage.inputPromoCode(getTestData(testData, "promoCode") + uniqueness);
            promoDetailPage.clickDropdownStatusAndSelect(getTestData(testData, "status"));
            promoDetailPage.inputStartedAt(today);
            promoDetailPage.inputEndedAt(today);
            promoDetailPage.inputDesc(getTestData(testData, "desc"));

            promoDetailPage.clickDropdownUsageTypeAndSelect(getTestData(testData, "usageType"));
            promoDetailPage.clickDropdownPromoTypeAndSelect(getTestData(testData, "promoType"));
            promoDetailPage.inputProgramFund(getTestData(testData, "programFund"));
            promoDetailPage.inputAmount(getTestData(testData, "amount"));
            promoDetailPage.inputMaxCount(getTestData(testData, "maxCount"));
            promoDetailPage.inputMinTrx(getTestData(testData, "minTrx"));

            promoDetailPage.clickBtnAddAllDayPromoTime();

            promoDetailPage.clickBtnAddPromo();

            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoPage.title));

        }finally {
            TestLogger.endFunc();
        }
    }

    // This method currently handle dynamic promo add of usage type : PER_TRANSACTION, PER_PRODUCT
    public void addPromoV2Flow(JsonNode testData, String[] variants, String uniqueness) {
        TestLogger.startFunc();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PromoPage promoPage = new PromoPage(driver);
        PromoDetailPage promoDetailPage = new PromoDetailPage(driver);

        JsonNode testDataLogin = loadTree("testdata/web/login.json").get("valid");
        String today = LocalDate.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));   // e.g. "12-11-2025"

        // test data login
        final String username = getTestData(testDataLogin, "username");
        final String password = getTestData(testDataLogin, "password");
        // test data promo
        final String storeName = getTestData(testData, "storeName");
        final String promoName = getTestData(testData, "promoName");
        final String promoCode = getTestData(testData, "promoCode");
        final String status = getTestData(testData, "status");
        final String desc = getTestData(testData, "desc");
        // test data time
        final String exceedTime = getTestData(testData, "exceedTime");

        // general promo restriction
        final String usageType = getTestData(testData, "usageType");
        final String promoType = getTestData(testData, "promoType");
        final String programFund = getTestData(testData, "programFund");
        final boolean unlimited = Boolean.parseBoolean(getTestData(testData, "unlimited"));
        final String maxCount = getTestData(testData, "maxCount");
        // per product
        final String promoAutoApply = getTestData(testData, "promoAutoApply");
        //final String[] variants = {"Product Auto Add M " + uniqueness + "- Variant1" + uniqueness, "Product Auto Add M " + uniqueness + "- Variant2" + uniqueness};
        // per trx
        final String minTrx = getTestData(testData, "minTrx");
        // per product - fix amount, per trx - fix amount
        final String amount = getTestData(testData, "amount");
        // per product - percentage, per trx - percentage
        final String percentage = getTestData(testData, "percentage");
        final String maxAmount = getTestData(testData, "maxAmount");


        try {
            driver.get(hostPOS);
            loginPage.loginWithValidData(username, password);
            dashboardPage.wait.until(ExpectedConditions.urlToBe(DashboardPage.url));

            dashboardPage.selectSubMenuPromo();

            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoPage.title));
            promoPage.clickBtnAddPromo();
            promoDetailPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoDetailPage.title));


            // Test Case
            promoDetailPage.clickDropdownStoreNameAndSelect(storeName);
            promoDetailPage.inputPromoName( promoName + uniqueness);
            promoDetailPage.inputPromoCode(promoCode + uniqueness);
            promoDetailPage.clickDropdownStatusAndSelect(status);
            promoDetailPage.inputStartedAt(today);
            promoDetailPage.inputEndedAt(today);
            promoDetailPage.inputDesc(desc);

            // promo restriction general
            promoDetailPage.clickDropdownUsageTypeAndSelect(usageType);
            promoDetailPage.clickDropdownPromoTypeAndSelect(promoType);
            if (unlimited) {
                promoDetailPage.setCheckBoxUnlimited();
            } else {
                promoDetailPage.inputProgramFund(programFund);
                promoDetailPage.inputMaxCount(maxCount);
            }

            // per product
            if(usageType.equals("PER_PRODUCT")) {
                promoDetailPage.chooseRbPromoAutomaticallyApplied(promoAutoApply);
                for (String variant: variants) {
                    promoDetailPage.inputComboboxVariants(variant);
                }

                if(promoType.equals("FIX_AMOUNT")) {
                    promoDetailPage.inputAmount(amount);
                }else if(promoType.equals("PERCENTAGE")) {
                    promoDetailPage.inputPercentage(percentage);
                    promoDetailPage.inputMaxAmount(maxAmount);
                }

            // per transaction
            }else if(usageType.equals("PER_TRANSACTION")) {
                promoDetailPage.inputMinTrx(minTrx);

                if(promoType.equals("FIX_AMOUNT")) {
                    promoDetailPage.inputAmount(amount);
                }else if(promoType.equals("PERCENTAGE")) {
                    promoDetailPage.inputPercentage(percentage);
                    promoDetailPage.inputMaxAmount(maxAmount);
                }
            }


            // submit
            if (!Boolean.parseBoolean(exceedTime)) {
                promoDetailPage.clickBtnAddAllDayPromoTime();
            }

            promoDetailPage.clickBtnAddPromo();

            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoPage.title));

        }finally {
            TestLogger.endFunc();
        }
    }

    public void addPromoWithValidData(JsonNode testData, String uniqueness) {
        TestLogger.startFunc();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PromoPage promoPage = new PromoPage(driver);
        PromoDetailPage promoDetailPage = new PromoDetailPage(driver);

        JsonNode root2 = loadTree("testdata/login.json");
//        JsonNode testData = root.get("perProductFix");
        String today = LocalDate.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));   // e.g. "12-11-2025"

        try {
            driver.get(hostPOS);
            loginPage.loginWithValidData(root2.get("valid").get("username").asText(), root2.get("valid").get("password").asText());
            dashboardPage.wait.until(ExpectedConditions.urlToBe(DashboardPage.url));

            dashboardPage.selectSubMenuPromo();

            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoPage.title));
            promoPage.clickBtnAddPromo();
            promoDetailPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoDetailPage.title));


            // Test Case
            promoDetailPage.clickDropdownStoreNameAndSelect(getTestData(testData, "storeName"));
            promoDetailPage.inputPromoName(getTestData(testData, "promoName") + uniqueness);
            promoDetailPage.inputPromoCode(getTestData(testData, "promoCode") + uniqueness);
            promoDetailPage.clickDropdownStatusAndSelect(getTestData(testData, "status"));
            promoDetailPage.inputStartedAt(today);
            promoDetailPage.inputEndedAt(today);
            promoDetailPage.inputDesc(getTestData(testData, "desc"));

            promoDetailPage.clickDropdownUsageTypeAndSelect(getTestData(testData, "usageType"));
            promoDetailPage.clickDropdownPromoTypeAndSelect(getTestData(testData, "promoType"));
            promoDetailPage.inputProgramFund(getTestData(testData, "programFund"));
            promoDetailPage.inputAmount(getTestData(testData, "amount"));
            promoDetailPage.inputMaxCount(getTestData(testData, "maxCount"));

            promoDetailPage.chooseRbPromoAutomaticallyApplied(getTestData(testData, "promoAutomaticallyApplied"));
            promoDetailPage.inputComboboxVariants(getTestData(testData, "variants1"));

            promoDetailPage.clickBtnAddAllDayPromoTime();

            promoDetailPage.clickBtnAddPromo();

            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoPage.title));

        }finally {
            TestLogger.endFunc();
        }

    }


    public void deletePromoWithValidData(String uniqueness) {
        TestLogger.startFunc();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PromoPage promoPage = new PromoPage(driver);

        JsonNode root2 = loadTree("testdata/web/login.json");

        try {
            driver.get(hostPOS);
            loginPage.loginWithValidData(root2.get("valid").get("username").asText(), root2.get("valid").get("password").asText());
            dashboardPage.wait.until(ExpectedConditions.urlToBe(DashboardPage.url));

            dashboardPage.selectSubMenuPromo();

            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(PromoPage.title));

            // Test Case
            promoPage.search(uniqueness);
            promoPage.clickDeletePromo();
            promoPage.wait.until(ExpectedConditions.visibilityOfElementLocated(promoPage.alertPopup("Delete promo success")));




        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            TestLogger.endFunc();
        }

    }

}
