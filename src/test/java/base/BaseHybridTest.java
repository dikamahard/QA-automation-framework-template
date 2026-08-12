package base;

import factory.AndroidDriverFactory;
import factory.WebDriverFactory;
import helper.FileTransferUtils;
import helper.TestLogger;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;


/*
 * TODO:THIS IS AN EXPERIMENTAL SETUP FOR LAZY DRIVER INITIATION
 * TODO:NEED TO SEE HOW IT WORKS BEFORE IMPLEMENTING IT TO ALL TEST
 * */
public class BaseHybridTest extends BaseTest{
    //protected AndroidDriver driverAndroid;
    protected final Map<MobileDevice, AndroidDriver> driverAndroids = new EnumMap<>(MobileDevice.class);
    protected WebDriver driverWeb; // selenium driver for backoffice

    @BeforeSuite
    public void copyQADocsTemplate() {
        System.out.println("[Before Suite] Copy QA Docs template");
        FileTransferUtils.copyFile(SRC_TC_DIR, RESULT_TC_DIR);
        FileTransferUtils.copyFile(SRC_ISSUE_DIR, RESULT_ISSUE_DIR);
    }

    //TODO: THIS GONNA BE LAZY CALL, NO MORE BEFORE METHOD, CALL WHEN A DRIVER IS NEEDED
    public void androidDriverSetup(MobileDevice device, MobileApp defaultApp) {
        if (!driverAndroids.containsKey(device)) {
            TestLogger.printLog("MOBILE", "Creating AndroidDriver for " + device.name());

            AndroidDriver driver = AndroidDriverFactory.createAndroidDriver(device, defaultApp);
            driverAndroids.put(device, driver);
        }
    }

    public void webDriverSetup() {
        TestLogger.printLog("WEB", "Creating Selenium WebDriver");

        driverWeb =  WebDriverFactory.createChromeDriver();
    }


    //TODO: THIS GONNA BE LAZY CALL, NO MORE BEFORE METHOD, CALL WHEN AN APP IS NEEDED TO OPEN
    //@BeforeMethod(alwaysRun = true)
    public void activateApp(AndroidDriver driverAndroid, String appPkg) {
        TestLogger.startFunc();
        if (driverAndroid != null) {
            driverAndroid.activateApp(appPkg);
        }else {
            throw new RuntimeException("driverAndroid is null !!!");
        }
        TestLogger.endFunc();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpBetweenTestsAndClearWebCookies() {
        TestLogger.startFunc();
        // kill the app (web, mobile) but keep the session alive

        // terminate all known test app from any active android driver, even if the app is never open
        for (MobileDevice device: MobileDevice.values()) {
            AndroidDriver driver = driverAndroids.get(device);

            if (driver == null) {
                continue;
            }

            for (MobileApp app : MobileApp.values()) {
                driver.terminateApp(app.getAppPkg());
            }
        }

        // clear browser cookies
        if(driverWeb != null) {
            driverWeb.manage().deleteAllCookies();
        }
        TestLogger.endFunc();
    }

    @AfterClass(alwaysRun = true)
    public void exitDriver() {
        TestLogger.startFunc();
        if (driverWeb != null) {
            try {
                driverWeb.quit();
            } catch (Exception ignored) {}
            driverWeb = null;
        }

        for (MobileDevice device: MobileDevice.values()) {
            AndroidDriver driverAndroid = driverAndroids.get(device);

            if (driverAndroid == null) {
                continue;
            }

            try {
                driverAndroid.quit();
            } catch (Exception ignored) {}
        }
        driverAndroids.clear();

        TestLogger.endFunc();
    }


    // method
    public void hardResetAndRelaunch(AndroidDriver driver, String appPkg) {
        TestLogger.startFunc();

        // 1) Stop app if running (avoids “half-alive” state)
        try {
            System.out.println("[APP] Terminating app : " + appPkg);
            driver.terminateApp(appPkg);
        } catch (Exception ignored) {

        }

        // 2) Clear data
        try {
            System.out.println("[APP] Clearing app data : " + appPkg);
            driver.executeScript("mobile: clearApp", Map.of("appId", appPkg));
        } catch (Exception e) {
            // fallback: adb pm clear
            System.out.println("[APP] Fail to clear app data : " + appPkg);
            driver.executeScript("mobile: shell",
                    Map.of("command", "pm", "args", List.of("clear", appPkg)));
        }

        // 3) Launch fresh
        System.out.println("[APP] Launch app : " + appPkg);
        driver.executeScript("mobile: activateApp", Map.of("appId", appPkg));

        TestLogger.endFunc();
    }
}
