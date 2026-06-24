package helper;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.android.LoginPage;

import java.time.Duration;
import java.util.List;

public final class SystemUiHelper {

    public static int permissionHandler(AndroidDriver driver, LoginPage loginPage) {
        TestLogger.startFunc("permissionHandler");
        // permission handling
        List<List<By>> permissionButtons = List.of(
                // 1) Allow one time
                List.of(
                        By.id("com.android.permissioncontroller:id/permission_allow_one_time_button")
                ),
                // 2) Allow while using the app (foreground)
                List.of(
                        By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"),
                        By.id("com.android.permissioncontroller:id/permission_allow_button")
                ),
                // 3) Deny
                List.of(
                        By.id("com.android.permissioncontroller:id/permission_deny_button"),
                        By.id("com.android.permissioncontroller:id/permission_deny_and_dont_ask_again_button")
                )
        );

        By permissionDialog = loginPage.permissionDialog;

        Duration overallTimeout = Duration.ofSeconds(20);
        Duration idleGrace      = Duration.ofSeconds(5);   // key: handles “gap then next dialog”
        long lastSeenDialogAt = System.nanoTime(); // updated whenever dialog is present
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        long deadline = System.currentTimeMillis() + overallTimeout.toMillis();
        int clicks = 0;



        while (System.currentTimeMillis() < deadline) {

            boolean dialogPresent = !driver.findElements(permissionDialog).isEmpty();

            if (!dialogPresent) {
                // Don’t exit immediately—allow time for the next permission to appear
                if (System.nanoTime() - lastSeenDialogAt > idleGrace.toNanos()) {
                    System.out.println("No permission dialog for " + idleGrace.getSeconds() + "s. Stopping.");
                    break;
                }
                try { Thread.sleep(250); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                continue;   // back to outer loop
            }

            lastSeenDialogAt = System.nanoTime();

            boolean clicked = false;

            // try button by priority
            for (List<By> buttonGroup : permissionButtons) {
                for (By button : buttonGroup) {

                    List<WebElement> found = driver.findElements(button);
                    if(found.isEmpty()) {
                        continue;
                    }

                    try {
                        WebElement btn = shortWait
                                .ignoring(StaleElementReferenceException.class)
                                .until(ExpectedConditions.elementToBeClickable(button));

                        btn.click();
                        clicks++;
                        clicked = true;

                        // Wait until the current dialog goes away or morphs (button becomes stale)
                        new WebDriverWait(driver, Duration.ofSeconds(5))
                                .until(ExpectedConditions.or(
                                        ExpectedConditions.invisibilityOfElementLocated(permissionDialog),
                                        ExpectedConditions.stalenessOf(btn)
                                ));

                        break;
                    }catch (StaleElementReferenceException | NoSuchElementException | TimeoutException e) {
                        System.out.println(e.getMessage());
                    }
                    //break;
                }
                if (clicked) break;
            }

            if (!clicked) {
                System.out.println("No matching permission buttons found - stopping to avoid infinite loop.");
                break;
            }
        }

        return clicks;
    }

    /*
    public static int permissionHandlerMonei(AndroidDriver driver, pages.android.monei.LoginPage loginPage) {
        // permission handling
        List<List<By>> permissionButtons = List.of(
                // 1) Allow one time
                List.of(
                        loginPage.permissionAllowOneTime,
                        By.id("com.android.permissioncontroller:id/permission_allow_one_time_button")
                ),
                // 2) Allow while using the app (foreground)
                List.of(
                        loginPage.permissionAllowForeground,
                        By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"),
                        By.id("com.android.permissioncontroller:id/permission_allow_button")
                ),
                // 3) Deny
                List.of(
                        loginPage.permissionDeny,
                        By.id("com.android.permissioncontroller:id/permission_deny_button"),
                        By.id("com.android.permissioncontroller:id/permission_deny_and_dont_ask_again_button")
                )
        );

        By permissionDialog = loginPage.permissionDialog; // Use a By, not a cached element
        Duration overallTimeout = Duration.ofSeconds(20);
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        long deadline = System.currentTimeMillis() + overallTimeout.toMillis();
        int clicks = 0;

        // short wait just to make sure
        try {
            loginPage.wait(1).until(ExpectedConditions.visibilityOfElementLocated(permissionDialog));
        }catch (TimeoutException e) {
            e.getMessage();
        }

        while (System.currentTimeMillis() < deadline) {

            if(driver.findElements(permissionDialog).isEmpty()) {
                System.out.println("Permission dialog gone");
                break;
            }

            boolean clicked = false;

            // try button by priority
            for (List<By> buttonGroup : permissionButtons) {
                for (By button : buttonGroup) {

                    List<WebElement> found = driver.findElements(button);
                    if(found.isEmpty()) {
                        continue;
                    }

                    try {
                        WebElement btn = shortWait
                                .ignoring(StaleElementReferenceException.class)
                                .until(ExpectedConditions.elementToBeClickable(button));

                        btn.click();
                        clicks++;
                        clicked = true;

                        // Wait until the current dialog goes away or morphs (button becomes stale)
                        new WebDriverWait(driver, Duration.ofSeconds(2))
                                .until(ExpectedConditions.or(
                                        ExpectedConditions.invisibilityOfElementLocated(permissionDialog),
                                        ExpectedConditions.stalenessOf(btn)
                                ));

                    }catch (StaleElementReferenceException | NoSuchElementException | TimeoutException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                if (clicked) break;
            }

            if (!clicked) {
                System.out.println("No matching permission buttons found - stopping to avoid infinite loop.");
                break;
            }
        }

        return clicks;
    }

    public static void otpWaHandler(AndroidDriver driver, BasePage page, OtpPage otpPage) {
        // otp automation
        driver.openNotifications();
        try {
            page.wait(3).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Clear all notifications.")));
            if(driver.findElement(AppiumBy.accessibilityId("Clear all notifications.")).isDisplayed()) {
                driver.findElement(AppiumBy.accessibilityId("Clear all notifications.")).click();
            }
        }catch (Exception e) {
            System.out.println("No Notif Clear All");
            System.out.println(e.getMessage());
            driver.navigate().back();
        }

        // choose otp method via wa
        otpPage.clickBtnWa();
        otpPage.wait(3).until(ExpectedConditions.invisibilityOfElementLocated(otpPage.btnWa));
        driver.openNotifications();

        // waiting for the otp notification to show up
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='android:id/text' and contains(@text, 'verification code')]")
        ));

        String otpText = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text' and contains(@text, 'verification code')]")).getText();
        String otpCode = otpText.replaceAll("[^0-9]", "");

        driver.navigate().back();
        otpPage.getOtpEditText().sendKeys(otpCode);
        System.out.println("OTP : " + otpCode);
    }

    public static void otpWaFromDbHandler(OtpPage otpPage) {

        // choose otp method via wa
        otpPage.clickBtnWa();
        otpPage.wait(3).until(ExpectedConditions.invisibilityOfElementLocated(otpPage.btnWa));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String otpCode = DatabaseHelper.fetchOtp();
        otpPage.getOtpEditText().sendKeys(otpCode);
        System.out.println("OTP : " + otpCode);
    }

    public static void otpRegisterPhoneFromDbHandler(OtpPage otpPage) {

        String otpCode = DatabaseHelper.fetchOtp();
        otpPage.getOtpEditText().sendKeys(otpCode);
        System.out.println("OTP : " + otpCode);
    }

    // TODO: trigger otp inside fucntion
    public static void otpRegisterPhoneHandler(AndroidDriver driver, BasePage page, OtpPage otpPage) {
        // otp automation
        driver.openNotifications();
        try {
            page.wait(3).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Clear all notifications.")));
            if(driver.findElement(AppiumBy.accessibilityId("Clear all notifications.")).isDisplayed()) {
                driver.findElement(AppiumBy.accessibilityId("Clear all notifications.")).click();
            }
        }catch (Exception e) {
            System.out.println("No Notif Clear All");
            System.out.println(e.getMessage());
            driver.navigate().back();
        }

        driver.openNotifications();

        // waiting for the otp notification to show up
        new WebDriverWait(driver, Duration.ofSeconds(120)).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='android:id/text' and contains(@text, 'Kode OTP')]")
        ));

        String otpText = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text' and contains(@text, 'Kode OTP')]")).getText();
        String otpCode = otpText.replaceAll("[^0-9]", "");
        if(otpCode.length() > 6) {
            otpCode = otpCode.substring(otpCode.length() -6);
        }

        driver.navigate().back();
        otpPage.getOtpEditText().sendKeys(otpCode);
        System.out.println("OTP : " + otpCode);
    }

    // TODO: Dynamic otp method
    public static void otpDynamicHandler(AndroidDriver driver, BasePage page, OtpPage otpPage, By btnOtpMethod) {
        // otp automation
        driver.openNotifications();
        try {
            page.wait(3).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Clear all notifications.")));
            if(driver.findElement(AppiumBy.accessibilityId("Clear all notifications.")).isDisplayed()) {
                driver.findElement(AppiumBy.accessibilityId("Clear all notifications.")).click();
            }
        }catch (Exception e) {
            System.out.println("No Notif Clear All");
            System.out.println(e.getMessage());
            driver.navigate().back();
        }

        // TODO: Dynamic otp method
        // choose otp method via wa
        driver.findElement(btnOtpMethod).click();
        otpPage.wait(3).until(ExpectedConditions.invisibilityOfElementLocated(btnOtpMethod));
        driver.openNotifications();

        // waiting for the otp notification to show up
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='android:id/text' and contains(@text, 'verification code')]")
        ));

        String otpText = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='android:id/text' and contains(@text, 'verification code')]")).getText();
        String otpCode = otpText.replaceAll("[^0-9]", "");

        driver.navigate().back();
        otpPage.getOtpEditText().sendKeys(otpCode);
        System.out.println("OTP : " + otpCode);
    }

    public static void otpEmailHandlerBAK(AndroidDriver driver, BasePage page, OtpPage otpPage) {
        String gmailApp = "com.google.android.gm";
        String galileoApp = "com.pvs.project.galileo.qa";
        By ivEmailMsgExist = By.xpath("(//android.widget.ImageView[@resource-id='com.google.android.gm:id/contact_image'])[1]");
        By tvSenderGalileo = By.xpath("//android.widget.TextView[@resource-id='com.google.android.gm:id/senders' and @text='PVS Galileo']");
        By tvOTP = By.xpath("//android.widget.TextView[@text='Please use the following OTP Code to continue the login process:']/following-sibling::android.widget.TextView");

        // make sure page on otp
        otpPage.wait.until(ExpectedConditions.visibilityOfElementLocated(otpPage.etOtp));

        // switch to email
        driver.activateApp(gmailApp);

        // wait for galileo email to come and get otp
        new WebDriverWait(driver, Duration.ofSeconds(360)).until(ExpectedConditions.visibilityOfElementLocated(tvSenderGalileo)).click();
        // TODO: pull to refresh if no email
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(tvOTP));
        String otpText = driver.findElement(tvOTP).getText();
        System.out.println(otpText);

        //navigate back to galileo
        driver.activateApp(galileoApp);

        // input otp
        String otpCode = otpText.replaceAll("[^0-9]", "");
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(otpPage.etOtp));
        driver.findElement(otpPage.etOtp).sendKeys(otpCode);
    }

    private static void switchToCorrectEmail(String email, AndroidDriver driver) {
        Action action = new Action(driver);
        By profileAccount = By.id("com.google.android.gm:id/selected_account_disc_gmail");
        By btnManageYourAccount = By.xpath("//*[@text='Manage your Google Account']");
        By currentEmail = By.xpath("//*[contains(@content-desc, 'Signed in as') and contains(@text,'@gmail.com')]");
        By itemListAccountChosen = By.xpath("//*[@text='"+email+"']/parent::*");

        action.click(profileAccount);
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(btnManageYourAccount));
        String currentEmailDesc = driver.findElement(currentEmail).getAttribute("text");

        // if email is correct
        if (currentEmailDesc.contains(email)) {
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
        }else {
            // switch to correct account
            action.click(itemListAccountChosen);
        }
    }

    public static void otpEmailHandler(AndroidDriver driver, BasePage page, OtpPage otpPage, String email, Runnable triggerSendOtp) {
        Action action = new Action(driver);
        String gmailApp = "com.google.android.gm";
        String galileoApp = "com.pvs.project.galileo.qa";
        By ivEmailContactPictGalileo = By.xpath("(//android.widget.TextView[@resource-id='com.google.android.gm:id/senders' and contains(@text, 'PVS Galileo')]/preceding-sibling::*)[1]");
        By btnComposeEmail = By.id("com.google.android.gm:id/compose_button");
        By btnDeleteEmail = AppiumBy.accessibilityId("Delete");
        By tvSenderGalileo = By.xpath("//android.widget.TextView[@resource-id='com.google.android.gm:id/senders' and contains(@text, 'PVS Galileo')]");
        By tvOTP = By.xpath("//android.widget.TextView[@text='Please use the following OTP Code to continue the login process:']/following-sibling::android.widget.TextView");
        long deadline = System.currentTimeMillis() + Duration.ofSeconds(360).toMillis();
        driver.activateApp(gmailApp);
        System.out.println("switch gmail");
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(btnComposeEmail));
        System.out.println("compose email");

        // switch account if not correct
        switchToCorrectEmail(email, driver);
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(btnComposeEmail));

        while (System.currentTimeMillis() < deadline) {
            System.out.println("inside while");
            if(!driver.findElements(ivEmailContactPictGalileo).isEmpty()) {
                System.out.println("inside if");
                driver.findElement(ivEmailContactPictGalileo).click();
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(btnDeleteEmail)).click();
            }else {
                break;
            }
        }



        // switch galileo and trigger otp
        // caller decides how the OTP is sent. This triggers it
        driver.activateApp(galileoApp);
        Objects.requireNonNull(triggerSendOtp, "triggerSendOtp").run();


        // make sure page on otp
        otpPage.wait.until(ExpectedConditions.visibilityOfElementLocated(otpPage.etOtp));

        // switch to email
        driver.activateApp(gmailApp);

        // wait for galileo email to come and get otp
        // TODO: pull to refresh if no email
        while (System.currentTimeMillis() < deadline) {
            System.out.println("inside while");
            if(driver.findElements(tvSenderGalileo).isEmpty()) {
                System.out.println("inside if");
                action.pullToRefresh();
            }else {
                break;
            }
        }

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(tvSenderGalileo)).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(tvOTP));
        String otpText = driver.findElement(tvOTP).getText();
        System.out.println(otpText);

        //navigate back to galileo
        driver.terminateApp(gmailApp);
        driver.activateApp(galileoApp);

        // input otp
        String otpCode = otpText.replaceAll("[^0-9]", "");
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(otpPage.etOtp));
        driver.findElement(otpPage.etOtp).sendKeys(otpCode);
        System.out.println("OTP : " + otpCode);
    }

    public static void removeAutoFillHandler(AndroidDriver driver, BasePage loginPage, PinPage pinPage) {
        // remove autofill if displayed
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                loginPage.wait(3).until(ExpectedConditions.visibilityOfElementLocated(pinPage.etPin));
                System.out.println("No autofill");
                return;
            } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
                // likely blocked by overlay -> dismiss and try again
                Duration orig = driver.manage().timeouts().getImplicitWaitTimeout();
                System.out.println("duration : " + orig);
                driver.manage().timeouts().implicitlyWait(Duration.ZERO);

                try {
                    if (!driver.findElements(loginPage.autoFillPicker).isEmpty()) {
                        driver.pressKey(new KeyEvent(AndroidKey.BACK));
                        loginPage.wait(2).until(ExpectedConditions.invisibilityOfElementLocated(loginPage.autoFillPicker));
                        System.out.println("Autofill remove");
                    }
                } finally {
                    driver.manage().timeouts().implicitlyWait(orig);
                }
            }
        }
    }

    public static void removeAutoFillHandlerMonei(AndroidDriver driver, BasePage loginPage, pages.android.monei.PinPage pinPage) {
        // remove autofill if displayed
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                loginPage.wait(3).until(ExpectedConditions.visibilityOfElementLocated(pinPage.etPin));
                System.out.println("No autofill");
                return;
            } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
                // likely blocked by overlay -> dismiss and try again
                Duration orig = driver.manage().timeouts().getImplicitWaitTimeout();
                System.out.println("duration : " + orig);
                driver.manage().timeouts().implicitlyWait(Duration.ZERO);

                try {
                    if (!driver.findElements(loginPage.autoFillPicker).isEmpty()) {
                        driver.pressKey(new KeyEvent(AndroidKey.BACK));
                        loginPage.wait(2).until(ExpectedConditions.invisibilityOfElementLocated(loginPage.autoFillPicker));
                        System.out.println("Autofill remove");
                    }
                } finally {
                    driver.manage().timeouts().implicitlyWait(orig);
                }
            }
        }
    }

    public static void openWithIntentHandler(AndroidDriver driver, BasePage page) {
        By openWithIntent = By.xpath("//*[@text = 'Open with']");
        By option1 = By.xpath("//android.widget.GridView/android.widget.LinearLayout[1]");
        By btnOnce = By.id("android:id/button_once");
        By btnAlways   = By.id("android:id/button_always");

        try {
            page.wait(1).until(ExpectedConditions.visibilityOfElementLocated(openWithIntent));
            for(int i=0; i<2; i++) {
                if(!driver.findElements(openWithIntent).isEmpty()) {
                    new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(option1)).click();
                    if(!driver.findElements(btnOnce).isEmpty()) {
                        driver.findElement(btnOnce).click();
                    } else if(!driver.findElements(btnAlways).isEmpty()) {
                        driver.findElement(btnAlways).click();
                    }
                    break;
                }
            }
        }catch (TimeoutException | NoSuchElementException ignored) {
            // ignore exception
            System.out.println("No open with intent");
        }
    }

    public static void resetPinHandler(AndroidDriver driver, BasePage page, ForgotPinPage forgotPinPage, String pin, String email, Runnable triggerResetPin) {
        Action action = new Action(driver);
        String gmailApp = "com.google.android.gm";
        String galileoApp = "com.pvs.project.galileo.qa";
        By ivEmailContactPictGalileo = By.xpath("(//android.widget.TextView[@resource-id='com.google.android.gm:id/senders' and contains(@text, 'WIDE Galileo')]/preceding-sibling::*)[1]");
        By btnComposeEmail = By.id("com.google.android.gm:id/compose_button");
        By btnDeleteEmail = AppiumBy.accessibilityId("Delete");
        By tvSenderGalileo = By.xpath("//android.widget.TextView[@resource-id='com.google.android.gm:id/senders' and contains(@text, 'WIDE Galileo')]");
        By emailResetPin = By.xpath("//android.widget.TextView[contains(@text, 'reset PIN')]/parent::*");
        By btnResetPin = AppiumBy.accessibilityId("Reset PIN");
        By linkReset = By.xpath("//android.widget.TextView[contains(@text, 'track.primavista-solusi')]");
        By btnSaveNewPin = By.xpath("//android.widget.Button[@text='Save New PIN']");
        By etNewPin = By.xpath("//android.widget.Button[@text='Save New PIN']/preceding-sibling::android.widget.EditText[1]");
        By tvNewPinChangedSuccess = By.xpath("//android.widget.TextView[@text='Your pin has been successfully changed, please login with your new PIN']");
        By btnProceedSuspiciousLink = By.xpath("//android.widget.Button[@text='Proceed']");
        long deadline = System.currentTimeMillis() + Duration.ofSeconds(500).toMillis();


        // TODO: switch to gmail and clean email if there are any
        driver.activateApp(gmailApp);
        System.out.println("switch gmail");
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(btnComposeEmail));
        System.out.println("compose email");

        // switch account if not correct
        switchToCorrectEmail(email.toLowerCase(), driver);
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(btnComposeEmail));


        while (System.currentTimeMillis() < deadline) {
            System.out.println("inside while");
            if(!driver.findElements(ivEmailContactPictGalileo).isEmpty()) {
                System.out.println("inside if");
                driver.findElement(ivEmailContactPictGalileo).click();
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(btnDeleteEmail)).click();
            }else {
                break;
            }
        }



        // switch galileo and trigger reset pin
        // caller decides how the reset pin is sent. This triggers it
        driver.activateApp(galileoApp);
        Objects.requireNonNull(triggerResetPin, "triggerResetPin").run();

        // some process on galileo
        forgotPinPage.wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPinPage.dialogForgotPinProcessed));
        forgotPinPage.clickBtnOk();
        forgotPinPage.wait.until(ExpectedConditions.visibilityOfElementLocated(((LoginPage) page).btnSignIn));

        // switch to email
        driver.activateApp(gmailApp);

        // wait for galileo email to come and get otp
        // TODO: pull to refresh if no email
        while (System.currentTimeMillis() < deadline) {
            System.out.println("inside while");
            if(driver.findElements(tvSenderGalileo).isEmpty()) {
                System.out.println("inside if");
                action.pullToRefresh();
            }else {
                break;
            }
        }

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(emailResetPin));
        action.click(emailResetPin);
        action.click(btnResetPin);

        // handle suspicious link message from google if available
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(btnProceedSuspiciousLink));
            action.click(btnProceedSuspiciousLink);
        }catch (TimeoutException e) {
            System.out.println("No Suspicious Link message");
        }
        openWithIntentHandler(driver, page);

        //browser
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(linkReset));
            driver.findElement(linkReset).click();
        }catch (TimeoutException e) {
            System.out.println("No link reset");
        }


        // input new pin
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(btnSaveNewPin));
        action.click(etNewPin);

        action.typeSomething(pin);
        action.click(btnSaveNewPin);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(tvNewPinChangedSuccess));

        //navigate back to galileo
        driver.terminateApp(gmailApp);
        driver.activateApp(galileoApp);
    }

    public static void onBoardingTutorialHandler() {
        By homeTutorial = AppiumBy.accessibilityId("Here is your home screen, you can check your points and membership here");
    }

     */


}
