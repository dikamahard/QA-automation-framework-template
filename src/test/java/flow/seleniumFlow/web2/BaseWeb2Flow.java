package flow.seleniumFlow.web2;

import helper.TestLogger;
import org.openqa.selenium.WebDriver;

public abstract class BaseWeb2Flow {

    protected final WebDriver driver;
    protected final String hostPOS;

    public BaseWeb2Flow(WebDriver driver, String hostPos) {
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
