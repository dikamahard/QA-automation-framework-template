package base;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.io.File;
import java.nio.file.Paths;

public abstract class BaseTest {

    protected static Dotenv dotenv = Dotenv.load();

    // === Device Data ===
    public static final String UDID_POCO = dotenv.get("UDID_POCO");
    public static final String UDID_SAMSUNG = dotenv.get("UDID_SAMSUNG");
    public static final String UDID_REDMI = dotenv.get("UDID_REDMI");
    public static final String UDID_TAB = dotenv.get("UDID_TAB");

    public static final String DEVICE_POCO = dotenv.get("DEVICE_POCO");
    public static final String DEVICE_SAMSUNG = dotenv.get("DEVICE_SAMSUNG");
    public static final String DEVICE_REDMI = dotenv.get("DEVICE_REDMI");
    public static final String DEVICE_TAB = dotenv.get("DEVICE_TAB");
    public static final String DEVICE_EMULATOR = dotenv.get("DEVICE_EMULATOR");


    public static final String PLATFORM_VERSION_TAB = dotenv.get("PLATFORM_VERSION_TAB");
    public static final String PLATFORM_VERSION_EMULATOR = dotenv.get("PLATFORM_VERSION_EMULATOR");

    public static final String PATH_APK = "";
    public static final String APP_PKG_GALILEO = dotenv.get("APP_PKG_GALILEO");
    public static final String APP_ACT_GALILEO = dotenv.get("APP_ACT_GALILEO");
    public static final String APP_PKG_MONEI = dotenv.get("APP_PKG_MONEI");
    public static final String APP_ACT_MONEI = dotenv.get("APP_ACT_MONEI");
    public static final String APP_PKG_POS = dotenv.get("APP_PKG_POS");
    public static final String APP_ACT_POS = dotenv.get("APP_ACT_POS");


    public static final String APP_PATH = Paths.get("").toAbsolutePath().resolve("galileo.apk").toString();
    // =====================================

    // === Backoffice Data ===
    public static final String url = dotenv.get("URL_QA");
    public static final String port = dotenv.get("PORT_POS");
    public static final String portGalileo = dotenv.get("PORT_GALILEO");

    public static final String hostPos = url + ":"  + port;
    public static final String hostGalileo = url + ":"  + portGalileo;

    public static final String hostGmail = "https://mail.google.com/";
    // =====================================

    // === QA Docs File ===
    public static final String TEST_CASE_FILE = "Test_Scenario_Galileo_Mobile_Auto_V2.xlsx";
    public static final String ISSUE_LOG_FILE = "Issue_Log_Galileo_Mobile_Auto_V2.xlsx";

    public static final String SRC_TC_DIR = System.getProperty("user.dir") + File.separator + "testcase" + File.separator + "template" + File.separator + TEST_CASE_FILE;
    public static final String RESULT_TC_DIR = System.getProperty("user.dir") + File.separator + "testcase" + File.separator + "generated" + File.separator + TEST_CASE_FILE;
    public static final String SRC_ISSUE_DIR = System.getProperty("user.dir") + File.separator + "testcase" + File.separator + "template" + File.separator + ISSUE_LOG_FILE;
    public static final String RESULT_ISSUE_DIR = System.getProperty("user.dir") + File.separator + "testcase" + File.separator + "generated" + File.separator + ISSUE_LOG_FILE;
    public static final String SCREENSHOT_DIR = System.getProperty("user.dir") + File.separator + "testcase" + File.separator + "generated" + File.separator + "screenshot";


    // =====================================

    // === Appium Data ===
    public static final String URL_APPIUM = dotenv.get("URL_APPIUM_SERVER");

    protected void setScreenshotAttribute(String atr, String value) {
        Reporter.getCurrentTestResult()
                .setAttribute(atr, value);
    }
}
