package seleniumFlow.pos;

import helper.TestLogger;
import org.openqa.selenium.WebDriver;

public abstract class BaseFlow {

    protected final WebDriver driver;
    protected final String hostPOS;

    public BaseFlow(WebDriver driver, String hostPos) {
        this.driver = driver;
        this.hostPOS = hostPos;
    }

    public void clearCookies() {
        TestLogger.startFunc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        driver.manage().deleteAllCookies();
        TestLogger.endFunc();
    }
}
