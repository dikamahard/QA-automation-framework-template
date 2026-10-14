package base;

import helper.FileTransferUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseWebTest extends BaseTest implements ScreenshotDriverProvider{
    protected WebDriver driver; // selenium driver for backoffice

    // For setting up screenshot driver
    @Override
    public Map<String, TakesScreenshot> getScreenshotDrivers() {

        Map<String, TakesScreenshot> drivers = new LinkedHashMap<>();

        if (driver != null) {
            drivers.put("WEB", (TakesScreenshot) driver);
        }

        return drivers;
    }

    @BeforeSuite
    public void copyQADocsTemplate() {
        System.out.println("[Before Suite] Copy QA Docs template");
        FileTransferUtils.copyFile(SRC_TC_DIR, RESULT_TC_DIR);
        FileTransferUtils.copyFile(SRC_ISSUE_DIR, RESULT_ISSUE_DIR);
    }

    @BeforeClass(alwaysRun = true)
    public void backofficeSetup() {

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

        driver = new ChromeDriver(options);
    }

    @BeforeMethod(alwaysRun = true)
    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {}
            driver = null;
        }
    }
}
