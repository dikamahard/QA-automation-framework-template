package pages.web.galileo;

import org.openqa.selenium.WebDriver;
import pages.base.BaseWebGalileoPage;

public class DashboardPage extends BaseWebGalileoPage {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public static String url = "http://192.168.7.110:9797/dashboard";
}
