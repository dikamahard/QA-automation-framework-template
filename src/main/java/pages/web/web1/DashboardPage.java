package pages.web.web1;

import org.openqa.selenium.WebDriver;
import pages.base.BaseWebWeb1Page;

public class DashboardPage extends BaseWebWeb1Page {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public static String url = "http://192.168.7.110:9797/dashboard";
}
