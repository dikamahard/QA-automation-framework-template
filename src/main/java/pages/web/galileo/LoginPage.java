package pages.web.galileo;

import helper.TestLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    public WebDriverWait wait;
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    public static By etUsername = By.xpath("//input[@name='username']");
    public static By etPassword = By.name("password");
    public static By btnLogin = By.xpath("//button[text()='Login']");

    public void inputUserName(String userName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(etUsername));
        driver.findElement(etUsername).sendKeys(userName);
    }

    public void inputPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(etPassword));
        driver.findElement(etPassword).sendKeys(password);
    }

    public void clickBtnLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
        driver.findElement(btnLogin).click();
    }

    public void loginWithValidData(String username, String password) {
        TestLogger.startAction(username);
        inputUserName(username);
        inputPassword(password);
        clickBtnLogin();
    }
}
