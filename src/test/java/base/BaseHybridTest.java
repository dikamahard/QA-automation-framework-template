package base;

import helper.FileTransferUtils;
import helper.TestLogger;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class BaseHybridTest extends BaseTest{
    protected AndroidDriver driverAndroid;
    protected WebDriver driverWeb; // selenium driver for backoffice

    @BeforeSuite
    public void copyTestCaseTemplateForTestResult() {
        System.out.println("[Before Suite] Copy testcase template");
        FileTransferUtils.copyFile(SRC_TC_DIR, RESULT_TC_DIR);
    }

    @BeforeClass(alwaysRun = true)
    public void backofficeAndMobileSetup() throws MalformedURLException {
        TestLogger.startFunc();

        // Detect environment
        boolean isLinux    = System.getProperty("os.name").toLowerCase().contains("linux");
        boolean hasDisplay = System.getenv("DISPLAY") != null || System.getenv("WAYLAND_DISPLAY") != null;
        boolean isRoot     = "root".equals(System.getProperty("user.name"));


        // --- Selenium (desktop web) ---
        ChromeOptions options = new ChromeOptions();
        if (isLinux && !hasDisplay) {
            // Headless on Linux without a display (typical Jenkins)
            options.addArguments(
                    "--headless=new",
                    "--disable-gpu",
                    "--disable-dev-shm-usage",
                    "--window-size=1920,1080"
            );
            if (isRoot) {
                options.addArguments("--no-sandbox");
            }
            System.out.println("[Web] Chrome mode: HEADLESS");
        } else {
            // Your original UI flags
            options.addArguments("--start-maximized");
            options.addArguments("force-device-scale-factor=0.3");
            options.addArguments("high-dpi-support=0.5");
            System.out.println("[Web] Chrome mode: UI");
        }

        driverWeb = new ChromeDriver(options);


        // Setup Mobile
        DesiredCapabilities capabilities = new DesiredCapabilities();
        URL url = new URL(dotenv.get("URL_APPIUM_SERVER"));

        capabilities.setCapability("appium:deviceName", DEVICE_TAB);
        capabilities.setCapability("appium:platformVersion", PLATFORM_VERSION_TAB);

        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:appPackage", APP_PKG_POS);
        capabilities.setCapability("appium:appActivity", APP_ACT_POS);
        // command no reinstall every test run
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:fullReset", false);
        // timeout driver mobile when no command is send to mobile (need this when web process is taking so long)
        capabilities.setCapability("appium:newCommandTimeout", 1800); // seconds


        driverAndroid = new AndroidDriver(url, capabilities);
        TestLogger.endFunc();
    }

    @BeforeMethod(alwaysRun = true)
    public void activateApp() {
        TestLogger.startFunc();
        driverAndroid.activateApp(APP_PKG_POS);
        TestLogger.endFunc();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpBetweenTestsAndClearWebCookies() {
        // kill the app but keep the session alive
        TestLogger.startFunc();
        if (driverAndroid != null) {
            try {
                driverAndroid.terminateApp(APP_PKG_POS);
            } catch (Exception ignored) {
                // ignore
            }
        }

        // clear browser cookies
        driverWeb.manage().deleteAllCookies();
        TestLogger.endFunc();
    }

    @AfterClass
    public void exitDriver() {
        TestLogger.startFunc();
        if (driverWeb != null) {
            try {
                driverWeb.quit();
            } catch (Exception ignored) {}
            driverWeb = null;
        }

        if (driverAndroid != null) {
            try { driverAndroid.terminateApp(APP_PKG_POS);
            } catch (Exception ignored) {

            }

            try {
                driverAndroid.quit();
            } catch (Exception ignored) {}
            driverAndroid = null;
        }
        TestLogger.endFunc();
    }


    // method
    public void hardResetAndRelaunch(AndroidDriver driver) {
        TestLogger.startFunc();

        String appId = APP_PKG_POS;

        // 1) Stop app if running (avoids “half-alive” state)
        try {
            System.out.println("[APP] Terminating app : " + appId);
            driver.terminateApp(appId);
        } catch (Exception ignored) {

        }

        // 2) Clear data
        try {
            System.out.println("[APP] Clearing app data : " + appId);
            driver.executeScript("mobile: clearApp", Map.of("appId", appId));
        } catch (Exception e) {
            // fallback: adb pm clear
            System.out.println("[APP] Fail to clear app data : " + appId);
            driver.executeScript("mobile: shell",
                    Map.of("command", "pm", "args", List.of("clear", appId)));
        }

        // 3) Launch fresh
        System.out.println("[APP] Launch app : " + appId);
        driver.executeScript("mobile: activateApp", Map.of("appId", appId));

        TestLogger.endFunc();
    }
}
