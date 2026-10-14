package appiumTest.featureY;

import base.BaseHybridTest;
import base.MobileApp;
import base.MobileDevice;
import com.fasterxml.jackson.databind.JsonNode;
import helper.CleanupHelper;
import helper.ExcelUtils;
import helper.RandomAlphaNumGeneratorUtils;
import helper.TestLogger;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.Test;

import static helper.TestDataLoader.loadTree;

public class UserAddPositiveTest extends BaseHybridTest {

    @Test
    public void sampleTest() {
        TestLogger.startTest();

        // lazy driver init
        MobileDevice deviceGalileo = MobileDevice.POCO;
        MobileApp appGalileo = MobileApp.GALILEO;
        // set driver
        androidDriverSetup(deviceGalileo, appGalileo);
        // get mobile driver
        AndroidDriver driverAndroidGalileo = driverAndroids.get(deviceGalileo);

        // pages


        // test data bank
        JsonNode testDataSample = loadTree("testdata/mobile/app1/sample.json").get("valid");
        final String uniqueness = RandomAlphaNumGeneratorUtils.generateRandomAlphaNum(5);
        final String uniquenum = RandomAlphaNumGeneratorUtils.generateRandomNum(16);


        boolean pass = false;
        boolean cleanup = false;
        Throwable causeOfFailure = null;
        String cell = loadTree("excelSheet/sample.json").get("TC1").asText();

        try {
            // TODO: some code

            pass = true;
            cleanup = true;

        } catch (AssertionError e) {
            causeOfFailure = e;
            throw e;
        } catch (Exception e) {
            causeOfFailure = e;
            TestLogger.printError("Error Message", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            ExcelUtils.writeResult(RESULT_TC_DIR, RESULT_ISSUE_DIR, "Sheet 1", cell, pass ? 1 : 0, String.valueOf(causeOfFailure));

            // delete
            CleanupHelper.safeCleanup("Delete Sample", cleanup, ()-> {

                //TODO: code for delete

            });
        }

        TestLogger.endTest();
    }
}
