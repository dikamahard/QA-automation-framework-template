package helper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class PageLoadHelper {

    public static void waitLoadingSpinner(AndroidDriver driver) {
        TestLogger.startFunc("waitLoadingSpinner");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By loadingSpinner = AppiumBy.accessibilityId("Loading");



        System.out.println("Start loading");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loadingSpinner));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Done loading");
    }

    public static void waitLoadingPlaceHolder(AndroidDriver driver) {
        // placeHolder to wait for image loaded for loading indicator
        By loadingPlaceHolder = By.xpath(
                "//android.widget.ImageView[" +
                        "contains(@content-desc,'Image Unavailable') and " +
                        "contains(@content-desc,'0 Points')" +
                        "]"
        );

        // wait placeholder animation to load succesfully
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(loadingPlaceHolder));
        }catch (Exception ignored){}
        long deadline = System.currentTimeMillis() + Duration.ofSeconds(30).toMillis();
        while (System.currentTimeMillis() < deadline) {
            if(!driver.findElements(loadingPlaceHolder).isEmpty()) {
                System.out.println("Wait loading place holder");
            }else {
                System.out.println("Done loading place holder");
                break;
            }
        }
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
    }

    public static void waitSplashScreen(AndroidDriver driver) {
        final By autoFillPicker = By.id("android:id/autofill_dataset_picker");
//        final String XPATH_ET_PIN =
//                // Union Xpath '|' for getting same element with different locator (pin register and pin login)
//                //etPin in register and sign in step
//                "//android.view.View[contains(@content-desc,'PIN')]//android.widget.EditText" +
//                        //etPin in other
//                        " | //android.view.View[contains(@content-desc,'PIN')]/following-sibling::android.widget.EditText" +
//                        //etPin in login
//                        " | //android.view.View[parent::*[contains(@content-desc, 'Login')]  and following-sibling::*[@content-desc='Forgot PIN']]";
//        final By etPin = By.xpath("(" + XPATH_ET_PIN + ")");
//        final By btnSignin = AppiumBy.accessibilityId("Sign In");
        final By etPin = By.xpath("//*[@content-desc='Merchant POS Login']/following-sibling::android.view.View[2]");
        final By btnRegister = By.xpath("//android.widget.Button[@content-desc='Register']");
        final By permissionDialog = By.xpath(
                "//*[@resource-id='com.android.permissioncontroller:id/grant_dialog'] | " +
                        "//*[@resource-id='com.android.permissioncontroller:id/content_container']"
        );


        System.out.println("Start waiting splash screen");
        new WebDriverWait(driver, Duration.ofSeconds(60)).ignoring(NoSuchElementException.class).until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(autoFillPicker),
                ExpectedConditions.visibilityOfElementLocated(etPin),
                ExpectedConditions.visibilityOfElementLocated(btnRegister),
                ExpectedConditions.visibilityOfElementLocated(permissionDialog)
                ));
        System.out.println("Done waiting splash screen");
    }

    public static void onboardingTutorialHandler(AndroidDriver driver) {
        System.out.println("Start onboardingTutorialHandler");

        ActionMobile action = new ActionMobile(driver);
        By homeTutorial = AppiumBy.accessibilityId("Here is your home screen, you can check your points and membership here");
        By exploreTutorial = AppiumBy.accessibilityId("Discover new promos, vouchers, and loyalty programs");
        By voucherTutorial = AppiumBy.accessibilityId("To get new voucher, click here");
        By reservationTutorial = AppiumBy.accessibilityId("Reserve items your loved landlord offers");
        By btnHomeTutorial = By.xpath("//android.view.View[@content-desc='Home']/following-sibling::android.widget.ImageView");
        By btnExploreTutorial = By.xpath("//android.view.View[@content-desc='Explore']/following-sibling::android.widget.ImageView");
        By btnVoucherTutorial = By.xpath("//android.view.View[@content-desc='Voucher']/following-sibling::android.widget.ImageView");
        By btnReservationTutorial = By.xpath("//android.view.View[@content-desc='Reservation']/following-sibling::android.widget.ImageView");


        By tutorial = By.xpath(
                "//*[@content-desc=\"Here is your home screen, you can check your points and membership here\" " +
                        "or @content-desc=\"Discover new promos, vouchers, and loyalty programs\" " +
                        "or @content-desc=\"To get new voucher, click here\" " +
                        "or @content-desc=\"Reserve items your loved landlord offers\"]"
        );


        //driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 5000);
        driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 0);

        try {
            // wait untill tutor gone
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(d -> {
                        try { // code goes here
                            WebElement el = d.findElement(tutorial);
                            if (!el.isDisplayed()) {
                                System.out.println("tutor no detected");
                                return true;
                            }

                            // if detected click whatever tutor is there
                            System.out.println("tutor detected");

                            // home tutor handling
                            new FluentWait<>(driver)
                                    .withTimeout(Duration.ofSeconds(5))
                                    .pollingEvery(Duration.ofMillis(200))
                                    .ignoring(NoSuchElementException.class)
                                    .ignoring(StaleElementReferenceException.class)
                                    .until(d1 -> {
                                        try { // code goes here
                                            WebElement homeTutor = d1.findElement(homeTutorial);
                                            if (!homeTutor.isDisplayed()) {
                                                System.out.println("Home tutor no detected");
                                                return true;
                                            }

                                            System.out.println("Home tutor detected");
                                            // if detected click the tutor
                                            driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 0);
                                            action.click(btnHomeTutorial);

                                            return true;
                                        } catch (StaleElementReferenceException | NoSuchElementException e) {
                                            System.out.println("Home tutor stale or no such");
                                            return false;
                                        }
                                    });

                            // explore tutor handling
                            new FluentWait<>(driver)
                                    .withTimeout(Duration.ofSeconds(5))
                                    .pollingEvery(Duration.ofMillis(200))
                                    .ignoring(NoSuchElementException.class)
                                    .ignoring(StaleElementReferenceException.class)
                                    .until(d1 -> {
                                        try { // code goes here
                                            WebElement exploreTutor = d1.findElement(exploreTutorial);
                                            if (!exploreTutor.isDisplayed()) {
                                                System.out.println("Explore tutor no detected");
                                                return true;
                                            }

                                            System.out.println("Explore tutor detected");
                                            // if detected click the tutor
                                            driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 0);
                                            action.click(btnExploreTutorial);

                                            return true;
                                        } catch (StaleElementReferenceException | NoSuchElementException e) {
                                            System.out.println("Explore tutor stale or no such");
                                            return false;
                                        }
                                    });

                            // voucher tutor handling
                            new FluentWait<>(driver)
                                    .withTimeout(Duration.ofSeconds(5))
                                    .pollingEvery(Duration.ofMillis(200))
                                    .ignoring(NoSuchElementException.class)
                                    .ignoring(StaleElementReferenceException.class)
                                    .until(d1 -> {
                                        try { // code goes here
                                            WebElement voucherTutor = d1.findElement(voucherTutorial);
                                            if (!voucherTutor.isDisplayed()) {
                                                System.out.println("Voucher tutor no detected");
                                                return true;
                                            }

                                            System.out.println("Voucher tutor detected");
                                            // if detected click the tutor
                                            driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 0);
                                            action.click(btnVoucherTutorial);

                                            return true;
                                        } catch (StaleElementReferenceException | NoSuchElementException e) {
                                            System.out.println("Voucher tutor stale or no such");
                                            return false;
                                        }
                                    });

                            // voucher tutor handling
                            new FluentWait<>(driver)
                                    .withTimeout(Duration.ofSeconds(5))
                                    .pollingEvery(Duration.ofMillis(200))
                                    .ignoring(NoSuchElementException.class)
                                    .ignoring(StaleElementReferenceException.class)
                                    .until(d1 -> {
                                        try { // code goes here
                                            WebElement reserveTutor = d1.findElement(reservationTutorial);
                                            if (!reserveTutor.isDisplayed()) {
                                                System.out.println("Reservation tutor no detected");
                                                return true;
                                            }

                                            System.out.println("Reservation tutor detected");
                                            // if detected click the tutor
                                            driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 0);
                                            action.click(btnReservationTutorial);

                                            return true;
                                        } catch (StaleElementReferenceException | NoSuchElementException e) {
                                            System.out.println("Reservation tutor stale or no such");
                                            return false;
                                        }
                                    });

                            // set timout back to normal
                            driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 10000);
                            return true;
                        } catch (StaleElementReferenceException | NoSuchElementException e) {
                            System.out.println("Onboarding tutor stale or no such");
                            return false;
                        }
                    });

            System.out.println("Onboarding tutorial dismiss");
            //driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 5000);
        } catch (Exception e) {
            System.out.println("No onboard");
            System.out.println(e.getMessage());
        }
    }

}
