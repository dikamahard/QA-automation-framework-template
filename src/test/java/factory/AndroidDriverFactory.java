package factory;

import base.BaseTest;
import base.MobileApp;
import base.MobileDevice;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidDriverFactory extends BaseTest {

    private AndroidDriverFactory() {
        // prevent object creation
    }

    public static AndroidDriver createMultipleDriver(String deviceName, String platformVersion, String DeviceUDID, String appPkg, String appAct, String systemPort, String jpegServerPort, String chromeDriverPort) {
        // Setup Mobile
        DesiredCapabilities capabilities = new DesiredCapabilities();
        URL url = null;
        try {
            url = new URL(dotenv.get("URL_APPIUM_SERVER"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:platformVersion", platformVersion);
        capabilities.setCapability("appium:udid", DeviceUDID);


        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:appPackage", appPkg);
        capabilities.setCapability("appium:appActivity", appAct);
        // command no reinstall every test run
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:fullReset", false);
        // timeout driver mobile when no command is send to mobile (need this when web process is taking so long)
        capabilities.setCapability("appium:newCommandTimeout", 1800); // seconds

        // One server, multiple devices => unique ports are REQUIRED
        capabilities.setCapability("appium:systemPort", systemPort);          // system session port
        capabilities.setCapability("appium:mjpegServerPort", jpegServerPort);     // to broadcast the screen’s video/screenshot stream to your test framework
        capabilities.setCapability("appium:chromedriverPort", chromeDriverPort);    // to interact with the Chromium/Chrome instance (or WebView) on your Android device

        /* Example Default
        capabilities.setCapability("appium:systemPort", 8200);
        capabilities.setCapability("appium:mjpegServerPort", 7810);
        capabilities.setCapability("appium:chromedriverPort", 9515);
         */

        return new AndroidDriver(url, capabilities);
    }

    public static AndroidDriver createSingleDriver(String deviceName, String platformVersion, String appPkg, String appAct) {
        // Setup Mobile
        DesiredCapabilities capabilities = new DesiredCapabilities();
        URL url = null;
        try {
            url = new URL(dotenv.get("URL_APPIUM_SERVER"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:platformVersion", platformVersion);


        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:appPackage", appPkg);
        capabilities.setCapability("appium:appActivity", appAct);
        // command no reinstall every test run
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:fullReset", false);
        // timeout driver mobile when no command is send to mobile (need this when web process is taking so long)
        capabilities.setCapability("appium:newCommandTimeout", 1800); // seconds


        return new AndroidDriver(url, capabilities);
    }

    public static AndroidDriver createAndroidDriver(MobileDevice device, MobileApp defaultApp) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("appium:automationName", "UiAutomator2");

            capabilities.setCapability("appium:deviceName", device.getDeviceName());
            capabilities.setCapability("appium:udid", device.getUdid());
            //capabilities.setCapability("appium:platformVersion", device.getPlatformVersion());

            capabilities.setCapability("appium:appPackage", defaultApp.getAppPkg());
            capabilities.setCapability("appium:appActivity", defaultApp.getAppAct());

            capabilities.setCapability("appium:noReset", true);
            capabilities.setCapability("appium:fullReset", false);
            capabilities.setCapability("appium:newCommandTimeout", 1800);

            // Important for multiple Android drivers
            capabilities.setCapability("appium:systemPort", device.getSystemPort());

            return new AndroidDriver(
                    new URL(device.getAppiumServerUrl()),
                    capabilities
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to create AndroidDriver for device: " + device.name(),
                    e
            );
        }
    }
}
