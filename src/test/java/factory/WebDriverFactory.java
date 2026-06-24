package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class WebDriverFactory {

    private WebDriverFactory() {
        // prevent object creation
    }
    public static WebDriver createChromeDriver() {
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

        return new ChromeDriver(options);
    }
}

