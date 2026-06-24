package pages.web.pos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BaseWebPage;

public class LoginPage extends BaseWebPage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public static String url = "http://192.168.7.110:1970/auth/login";
    public By tbUserName = textBox("Username");
    public By tbPassword = textBox("Password");
    public By btnLogin = button("Login");
    public By tbNewPassword = textBox("New Password");
    public By tbConfirmPassword = textBox("Confirm Password");
    public By btnChangePassword = button("Change Password");
    public By linkForgotPassword = By.xpath("//a[.='Forgot password?']");


    public void inputUserName(String userName) {
        System.out.println("inputUserName");
        action.inputText(tbUserName, userName);
    }

    public void inputPassword(String password) {
        System.out.println("inputPassword");
        action.inputText(tbPassword, password);
    }

    public void inputNewPassword(String password) {
        System.out.println("inputNewPassword");
        action.inputText(tbNewPassword, password);
    }

    public void inputConfirmPassword(String password) {
        System.out.println("inputConfirmPassword");
        action.inputText(tbConfirmPassword, password);
    }

    public void clickBtnLogin() {
        System.out.println("clickBtnLogin");
        action.click(btnLogin);
    }

    public void clickBtnChangePassword() {
        System.out.println("clickBtnChangePassword");
        action.click(btnChangePassword);
    }

    public void loginWithValidData(String username, String password) {
        System.out.println("Start loginWithValidData");
        inputUserName(username);
        inputPassword(password);
        clickBtnLogin();
    }

}
