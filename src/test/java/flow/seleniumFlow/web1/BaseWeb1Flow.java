package flow.seleniumFlow.web1;

import helper.TestLogger;
import org.openqa.selenium.WebDriver;

public abstract class BaseWeb1Flow {

    protected final WebDriver driver;
    protected final String hostGalileo;

    public BaseWeb1Flow(WebDriver driver, String hostGalileo) {
        this.driver = driver;
        this.hostGalileo = hostGalileo;
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
