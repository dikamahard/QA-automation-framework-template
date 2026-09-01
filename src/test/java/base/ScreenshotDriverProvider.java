package base;

import org.openqa.selenium.TakesScreenshot;

import java.util.Map;

public interface ScreenshotDriverProvider {
    Map<String, TakesScreenshot> getScreenshotDrivers();

}
