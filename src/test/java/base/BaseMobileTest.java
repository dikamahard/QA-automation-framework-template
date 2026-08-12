package base;

import factory.AndroidDriverFactory;
import helper.FileTransferUtils;
import helper.TestLogger;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class BaseMobileTest extends BaseTest{
    protected final Map<MobileDevice, AndroidDriver> driverAndroids = new EnumMap<>(MobileDevice.class);

    @BeforeSuite
    public void copyQADocsTemplate() {
        System.out.println("[Before Suite] Copy QA Docs template");
        FileTransferUtils.copyFile(SRC_TC_DIR, RESULT_TC_DIR);
        FileTransferUtils.copyFile(SRC_ISSUE_DIR, RESULT_ISSUE_DIR);
    }

    public void androidDriverSetup(MobileDevice device, MobileApp defaultApp) {
        if (!driverAndroids.containsKey(device)) {
            TestLogger.printLog("MOBILE", "Creating AndroidDriver for " + device.name());

            AndroidDriver driver = AndroidDriverFactory.createAndroidDriver(device, defaultApp);
            driverAndroids.put(device, driver);
        }
    }

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
    public void cleanUpBetweenTest() {
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

        TestLogger.endFunc();
    }

    @AfterClass
    public void exitDriver() {
        TestLogger.startFunc();

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
