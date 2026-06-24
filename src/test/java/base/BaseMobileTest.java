package base;

import helper.FileTransferUtils;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseMobileTest extends BaseTest{
    protected AndroidDriver driver;

    @BeforeSuite
    public void copyTestCaseTemplateForTestResult() {
        System.out.println("[Before Suite] Copy testcase template");
        FileTransferUtils.copyFile(SRC_TC_DIR, RESULT_TC_DIR);
    }

    @BeforeClass(groups = {"default"}, alwaysRun = true)
    public void androidSetup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        URL url = new URL("http://127.0.0.1:4723/");

        // Tab
        capabilities.setCapability("appium:deviceName", DEVICE_TAB);
        capabilities.setCapability("appium:platformVersion", PLATFORM_VERSION_TAB);

        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:appPackage", APP_PKG_POS);
        capabilities.setCapability("appium:appActivity", APP_ACT_POS);
        // command for no reinstall every test run
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:fullReset", false);

        // experimental
        // capabilities.setCapability("appium:forceAppLaunch", "true");
//        capabilities.setCapability("appium:appWaitActivity", "com.pvs.project.galileo.*");
//        capabilities.setCapability("appium:appWaitDuration", 30000); // ms

        driver = new AndroidDriver(url, capabilities);
    }

    @BeforeClass(groups = {"cleanInstall"}, alwaysRun = true)
    public void androidSetupCleanInstall() throws MalformedURLException {
        System.out.println("[SETUP] clearAppDataAndLaunch");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        URL url = new URL("http://127.0.0.1:4723/");

        // Tab
        capabilities.setCapability("appium:deviceName", DEVICE_TAB);
        capabilities.setCapability("appium:platformVersion", PLATFORM_VERSION_TAB);

        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:appPackage", APP_PKG_POS);
        capabilities.setCapability("appium:appActivity", APP_ACT_POS);
        // command for reinstall every test run
        capabilities.setCapability("appium:noReset", false);
        capabilities.setCapability("appium:fullReset", false);

        // experimental
        // capabilities.setCapability("appium:forceAppLaunch", "true");
//        capabilities.setCapability("appium:appWaitActivity", "com.pvs.project.galileo.*");
//        capabilities.setCapability("appium:appWaitDuration", 30000); // ms

        driver = new AndroidDriver(url, capabilities);
    }


    @AfterMethod(alwaysRun = true)
    public void cleanupBetweenTests() {
        // kill the app but keep the session alive
        if (driver != null) {
            try {
                driver.terminateApp(APP_PKG_POS);
            } catch (Exception ignored) {
                // ignore
            }
        }
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
