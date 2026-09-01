package helper;

import base.BaseTest;
import base.ScreenshotDriverProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        Object testClass = result.getInstance();

        if (!(testClass instanceof ScreenshotDriverProvider provider)) {
            System.out.println("Screenshot skipped: no screenshot driver provider");
            return;
        }

        // custom attribute passed from the test
        String tcId = (String) result.getAttribute("tcId");
        // method name
        String testName = result.getMethod().getMethodName();

        try {
            String baseName = tcId == null || tcId.isBlank() ? testName : tcId + "_" + testName;

            provider.getScreenshotDrivers()
                    .forEach((driverName, driver) ->
                            takeScreenshot(
                                    driver,
                                    baseName + "_" + driverName
                            )
                    );


        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }


    private void takeScreenshot(TakesScreenshot driver, String testName) {

        if (driver == null) {
            return;
        }

        try {


            Path directory = Path.of(BaseTest.SCREENSHOT_DIR);

            // Create directory if it doesn't exist
            Files.createDirectories(directory);

            Path destination = directory.resolve(
                    testName + ".png"
            );

            File screenshot = driver.getScreenshotAs(OutputType.FILE);

            Files.copy(
                    screenshot.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("Screenshot saved: " + destination);

        } catch (IOException e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}
