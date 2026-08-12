package helper.app1;

import helper.FormatUtils;
import helper.TestLogger;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.time.Duration.ofMillis;

public class ActionMobile {
    private final AndroidDriver driver;
    private final WebDriverWait wait;


    public ActionMobile(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void click(By locator) {
        new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(8))
            .pollingEvery(Duration.ofMillis(200))
            .ignoring(NoSuchElementException.class)              // ⬅ add this
            .ignoring(StaleElementReferenceException.class)
            .until(d -> {
                try {
                    WebElement el = d.findElement(locator); // re-find each poll
                    if (!el.isDisplayed() || !el.isEnabled()) return false;
                    el.click(); // attempt the click *inside* the wait
                    return true; // success
                } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                    return false; // repoll
                }
        });
    }

    public void click(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el)).click();
    }

    public WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void inputText(String text, By editText) {
        int maxLoop = 4;

        for (int i = 0; i < maxLoop; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(editText));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {
                }
                driver.findElement(editText).click();
                System.out.println("click");
                waitKeyboardAndHide();

                driver.findElement(editText).clear();
                System.out.println("clear");
                //waitKeyboardAndHide();

                driver.findElement(editText).sendKeys(text);
                System.out.println("send key");
                //waitKeyboardAndHide();

                driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                System.out.println("enter");
                //waitKeyboardAndHide();

                wait.until(ExpectedConditions.visibilityOfElementLocated(editText));
                String value = driver.findElement(editText).getAttribute("text").stripTrailing();
                System.out.println("[LOG] validation actual inputText : " + value);
                if (text.equals(value)) {
                    System.out.println("[INFO] Input succeeded on attempt " + (i + 1));
                    return; // done
                }

                // sometimes we input 1000 but converted to Rp. 1000 in the text box, handle here
                if( value.startsWith("Rp") && FormatUtils.toDigits(value).contains(text)) {
                    System.out.println("[INFO] Partial similarity (count succeeded). Expected: '" + text + "', actual: '" + value + "'");
                    return; // done
                }

                System.out.println("[INFO] Value mismatch. Expected: '" + text + "', actual: '" + value + "'");

            } catch (TimeoutException | StaleElementReferenceException | NoSuchElementException e) {
                System.out.println("[INFO] Attempt " + (i + 1) + " failed: " + e.getMessage());
            }

            // pause before retry
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {
            }

        }
    }

    // TODO:
    public void scrollIntoElement(String description) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().description(\"" + description + "\"));"
        ));
    }

    public void scrollIntoElementDescContains(String partialDesc) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().descriptionContains(\"" + partialDesc + "\"));"
        ));
    }

    private boolean isStuck(String prevSource) {
        String curr = driver.getPageSource();
        return curr.equals(prevSource);
    }

    public void scrollDown(long scrollDuration) {
        By listBy = AppiumBy.androidUIAutomator(
                "new UiSelector().scrollable(true)"
        );

        WebElement list = driver.findElement(listBy);
        Rectangle r = list.getRect();

        // swipe strictly inside the sheet
        int x = r.x + r.width / 2;
        int y1 = r.y + (int) (r.height * 0.80);
        int y2 = r.y + (int) (r.height * 0.25);
        int stagnant = 0;

        for (int i = 0; i < 1; i++) {
            String before = driver.getPageSource();

            // big swipe (fling-like)
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence s = new Sequence(finger, 1);
            s.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y1));
            System.out.println("Start coordinate: " + x + "," + y1);
            s.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            s.addAction(finger.createPointerMove(Duration.ofMillis(scrollDuration), PointerInput.Origin.viewport(), x, y2));
            System.out.println("End coordinate: " + x + "," + y2);
            s.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(s));
            try {
                Thread.sleep(180);
            } catch (InterruptedException ignored) {
            }

            //TODO: what if there is a loading indicator

            if (isStuck(before)) {
                if (++stagnant >= 2) break; // end reached
            } else {
                stagnant = 0;
            }
        }
    }
    public void scrollUntillFound(String target, long scrollDuration) {
        TestLogger.startFunc();
        By listBy = AppiumBy.androidUIAutomator(
                "new UiSelector().scrollable(true)"
        );
        By itemBy = By.xpath("//*[contains(@content-desc, '"+target+"')]");

        WebElement list = driver.findElement(listBy);
        Rectangle r = list.getRect();

        // swipe strictly inside the sheet
        int x = r.x + r.width / 2;
        int y1 = r.y + (int) (r.height * 0.80);
        int y2 = r.y + (int) (r.height * 0.25);
        int stagnant = 0;

        for (int i = 0; i < 60; i++) {
            if (!driver.findElements(itemBy).isEmpty()) {
                System.out.println("[INFO] Found element by scroll");
                break;
            }

            String before = driver.getPageSource();

            // big swipe (fling-like)
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence s = new Sequence(finger, 1);
            s.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y1));
            System.out.println("Start coordinate: " + x + "," + y1);
            s.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            s.addAction(finger.createPointerMove(Duration.ofMillis(scrollDuration), PointerInput.Origin.viewport(), x, y2));
            System.out.println("End coordinate: " + x + "," + y2);
            s.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(s));
            try {
                Thread.sleep(180);
            } catch (InterruptedException ignored) {
            }

            //TODO: what if there is a loading indicator

            if (isStuck(before)) {
                if (++stagnant >= 2) break; // end reached
            } else {
                stagnant = 0;
            }
        }

        if (driver.findElements(itemBy).isEmpty()) {
            throw new NoSuchElementException("Reached end of list, target not found: " + target);
        }
    }

    public void scrollUntillFound(String target1, String target2, long scrollDuration) {
        By listBy = AppiumBy.androidUIAutomator(
                "new UiSelector().scrollable(true)"
        );
        By itemBy = By.xpath("//*[contains(@content-desc,'"+target1+"') and contains(@content-desc,'"+target2+"')]");

        WebElement list = driver.findElement(listBy);
        Rectangle r = list.getRect();

        // swipe strictly inside the sheet
        int x = r.x + r.width / 2;
        int y1 = r.y + (int) (r.height * 0.80);
        int y2 = r.y + (int) (r.height * 0.25);
        int stagnant = 0;

        for (int i = 0; i < 60; i++) {
            if (!driver.findElements(itemBy).isEmpty()) {
                break;
            }

            String before = driver.getPageSource();

            // big swipe (fling-like)
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence s = new Sequence(finger, 1);
            s.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y1));
            System.out.println("Start coordinate: " + x + "," + y1);
            s.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            s.addAction(finger.createPointerMove(Duration.ofMillis(scrollDuration), PointerInput.Origin.viewport(), x, y2));
            System.out.println("End coordinate: " + x + "," + y2);
            s.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(s));
            try {
                Thread.sleep(180);
            } catch (InterruptedException ignored) {
            }

            //TODO: what if there is a loading indicator

            if (isStuck(before)) {
                if (++stagnant >= 2) break; // end reached
            } else {
                stagnant = 0;
            }
        }

        if (driver.findElements(itemBy).isEmpty()) {
            throw new NoSuchElementException("Reached end of list, target not found: " + target1 + "," + target2);
        }
    }

    public void scrollUntillFoundOneOf(List<String> targets, long scrollDuration) {
        By listBy = AppiumBy.androidUIAutomator(
                "new UiSelector().scrollable(true)"
        );

        List<String> locators = new ArrayList<>();
        for (String target : targets) {
            locators.add("contains(@content-desc,'" + target + "')");
        }

        String locator = "//*[" + String.join(" or ", locators) + "][1]";
        System.out.println("DEBUG: " + locator);

        By itemBy = By.xpath(locator);

        WebElement list = driver.findElement(listBy);
        Rectangle r = list.getRect();

        // swipe strictly inside the sheet
        int x = r.x + r.width / 2;
        int y1 = r.y + (int) (r.height * 0.80);
        int y2 = r.y + (int) (r.height * 0.25);
        int stagnant = 0;

        for (int i = 0; i < 60; i++) {
            if (!driver.findElements(itemBy).isEmpty()) {
                break;
            }

            String before = driver.getPageSource();

            // big swipe (fling-like)
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence s = new Sequence(finger, 1);
            s.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y1));
            System.out.println("Start coordinate: " + x + "," + y1);
            s.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            s.addAction(finger.createPointerMove(Duration.ofMillis(scrollDuration), PointerInput.Origin.viewport(), x, y2));
            System.out.println("End coordinate: " + x + "," + y2);
            s.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(s));
            try {
                Thread.sleep(180);
            } catch (InterruptedException ignored) {
            }

            //TODO: what if there is a loading indicator

            if (isStuck(before)) {
                if (++stagnant >= 2) break; // end reached
            } else {
                stagnant = 0;
            }
        }

        if (driver.findElements(itemBy).isEmpty()) {
            throw new NoSuchElementException("Reached end of list, target not found: " + targets);
        }
    }

    public void scrollUntillFound2(String target, long scrollDuration) {
        By listBy = AppiumBy.androidUIAutomator(
                "new UiSelector().scrollable(true)"
        );
        By itemBy = By.xpath("//*[contains(@content-desc, '"+target+"')]");


        WebElement list = driver.findElement(listBy);
        Rectangle r = list.getRect();

        // swipe strictly inside the sheet
        int x = r.x + r.width / 2;
        int y1 = r.y + (int) (r.height * 0.80);
        int y2 = r.y + (int) (r.height * 0.25);

        int stagnant = 0;

        for (int i = 0; i < 60; i++) {
            if (!driver.findElements(itemBy).isEmpty()) {
                break;
            }

            String before = driver.getPageSource();

            // big swipe (fling-like)
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence s = new Sequence(finger, 1);
            s.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y1));
            s.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            s.addAction(finger.createPointerMove(Duration.ofMillis(scrollDuration), PointerInput.Origin.viewport(), x, y2));
            s.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(s));
            try {
                Thread.sleep(180);
            } catch (InterruptedException ignored) {
            }

            //TODO: what if there is a loading indicator
            By loadingBy = AppiumBy.accessibilityId("Please Wait. . .");
            if(!driver.findElements(loadingBy).isEmpty()) {
                System.out.println("Start loading");
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Please Wait. . .")));
                    new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.accessibilityId("Please Wait. . .")));
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Done loading");
            }

            if (isStuck(before)) {
                if (++stagnant >= 2) break; // end reached
            } else {
                stagnant = 0;
            }
        }

        if (driver.findElements(itemBy).isEmpty()) {
            throw new NoSuchElementException("Reached end of list, target not found: " + target);
        }
    }

    public void scrollUntilEnd(long scrollDuration) {
        By listBy = AppiumBy.androidUIAutomator(
                "new UiSelector().scrollable(true)"
        );

        WebElement list = driver.findElement(listBy);
        Rectangle r = list.getRect();

        // swipe strictly inside the sheet
        int x = r.x + r.width / 2;
        int y1 = r.y + (int) (r.height * 0.80);
        int y2 = r.y + (int) (r.height * 0.25);

        int stagnant = 0;

        for (int i = 0; i < 60; i++) {

            String before = driver.getPageSource();

            // big swipe (fling-like)
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence s = new Sequence(finger, 1);
            s.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y1));
            s.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            s.addAction(finger.createPointerMove(Duration.ofMillis(scrollDuration), PointerInput.Origin.viewport(), x, y2));
            s.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Collections.singletonList(s));
            try {
                Thread.sleep(180);
            } catch (InterruptedException ignored) {
            }

            //TODO: what if there is a loading indicator
            By loadingBy = AppiumBy.accessibilityId("Please Wait. . .");
            if(!driver.findElements(loadingBy).isEmpty()) {
                System.out.println("Start loading");
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Please Wait. . .")));
                    new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.accessibilityId("Please Wait. . .")));
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Done loading");
            }

            if (isStuck(before)) {
                if (++stagnant >= 2) break; // end reached
            } else {
                stagnant = 0;
            }
        }
    }


    public void typeSomething(String text) {
        for (char digit : text.toCharArray()) {
            // Map the character to the appropriate AndroidKey and press it
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {
            }
            switch (digit) {
                case '0':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
                    break;
                case '1':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
                    break;
                case '2':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_2));
                    break;
                case '3':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
                    break;
                case '4':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));
                    break;
                case '5':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_5));
                    break;
                case '6':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_6));
                    break;
                case '7':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_7));
                    break;
                case '8':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_8));
                    break;
                case '9':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_9));
                    break;
                case 'A':
                    driver.pressKey(new KeyEvent(AndroidKey.A));
                    break;
                case 'B':
                    driver.pressKey(new KeyEvent(AndroidKey.B));
                    break;
                case 'C':
                    driver.pressKey(new KeyEvent(AndroidKey.C));
                    break;
                case 'D':
                    driver.pressKey(new KeyEvent(AndroidKey.D));
                    break;
                case 'E':
                    driver.pressKey(new KeyEvent(AndroidKey.E));
                    break;
                case 'F':
                    driver.pressKey(new KeyEvent(AndroidKey.F));
                    break;
                case 'G':
                    driver.pressKey(new KeyEvent(AndroidKey.G));
                    break;
                case 'H':
                    driver.pressKey(new KeyEvent(AndroidKey.H));
                    break;
                case 'I':
                    driver.pressKey(new KeyEvent(AndroidKey.I));
                    break;
                case 'J':
                    driver.pressKey(new KeyEvent(AndroidKey.J));
                    break;
                case 'K':
                    driver.pressKey(new KeyEvent(AndroidKey.K));
                    break;
                case 'L':
                    driver.pressKey(new KeyEvent(AndroidKey.L));
                    break;
                case 'M':
                    driver.pressKey(new KeyEvent(AndroidKey.M));
                    break;
                case 'N':
                    driver.pressKey(new KeyEvent(AndroidKey.N));
                    break;
                case 'O':
                    driver.pressKey(new KeyEvent(AndroidKey.O));
                    break;
                case 'P':
                    driver.pressKey(new KeyEvent(AndroidKey.P));
                    break;
                case 'Q':
                    driver.pressKey(new KeyEvent(AndroidKey.Q));
                    break;
                case 'R':
                    driver.pressKey(new KeyEvent(AndroidKey.R));
                    break;
                case 'S':
                    driver.pressKey(new KeyEvent(AndroidKey.S));
                    break;
                case 'T':
                    driver.pressKey(new KeyEvent(AndroidKey.T));
                    break;
                case 'U':
                    driver.pressKey(new KeyEvent(AndroidKey.V));
                    break;
                case 'W':
                    driver.pressKey(new KeyEvent(AndroidKey.W));
                    break;
                case 'X':
                    driver.pressKey(new KeyEvent(AndroidKey.X));
                    break;
                case 'Y':
                    driver.pressKey(new KeyEvent(AndroidKey.Y));
                    break;
                case 'Z':
                    driver.pressKey(new KeyEvent(AndroidKey.Z));
                    break;
                default:
                    throw new IllegalArgumentException("Character not available");
            }
            System.out.println("key " + digit + " pressed");
        }
    }

    public void tapByCoordinate(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Tap by coordinate method //

        // Create a sequence to simulate a touch action
        Sequence tap = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the action
        driver.perform(Arrays.asList(tap));
        System.out.println("Tapped coordinate: " + x + "," + y);
    }

    public void tapCenterScreen() {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int y = size.height / 2;
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create a sequence to simulate a touch action
        Sequence tap = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the action
        driver.perform(Arrays.asList(tap));
    }

    public void clickCenter(WebElement el) {
        // Wait until visible & enabled yourself before calling this if needed
        Rectangle r = el.getRect();
        int cx = r.getX() + r.getWidth() / 2;
        int cy = r.getY() + r.getHeight() / 2;

        tapByCoordinate(cx, cy);
    }

    public void clickCenter(By locator) {
        // Wait until visible & enabled yourself before calling this if needed
        WebElement el = driver.findElement(locator);
        Rectangle r = el.getRect();
        int cx = r.getX() + r.getWidth() / 2;
        int cy = r.getY() + r.getHeight() / 2;

        tapByCoordinate(cx, cy);
    }

    public void tapPinDotBAK(By locator) {
        WebElement el = driver.findElement(locator);
        int totalDots = 6;
        int indexPinDot = 0;
        Rectangle r = el.getRect();
        double cellW = (double) r.getWidth() / totalDots;
        int cx = (int) Math.round(r.getX() + (indexPinDot + 0.5) * cellW);
        int cy = r.getY() + r.getHeight() / 2;

        tapByCoordinate(cx, cy);
    }
    public void tapPinDot(By locator) {
        int attempts = 0;
        while (attempts++ < 5) {
            try {
                // 1) Re-find a fresh handle each attempt (handles reflow)
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement el = wait.until(
                        ExpectedConditions.refreshed(
                                ExpectedConditions.visibilityOfElementLocated(locator)
                        )
                );
                int totalDots = 4;
                int indexPinDot = 0;
                Rectangle r = el.getRect();
                //double cellW = (double) r.getWidth() / totalDots;
                //int cx = (int) Math.round(r.getX() + (indexPinDot + 0.5) * cellW);
                double pad = r.getHeight() / 2.0;                 // ≈ circle radius + margin
                double step = (r.getWidth() - 2*pad) / (totalDots - 1);
                int cx = (int) Math.round(r.getX() + pad + indexPinDot * step);
                int cy = r.getY() + r.getHeight() / 2;

                tapByCoordinate(cx, cy);
                return; // success
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                // retry with a fresh lookup
            }
        }
        throw new RuntimeException("tapPinDot: stale element after retries");
    }

    public void pullToRefresh() {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.15);    // start near the top
        int endY = (int) (size.height * 0.55);    // drag down ~40%

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence seq = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), x, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, ofMillis(150))) // small hold helps overscroll
                .addAction(finger.createPointerMove(ofMillis(500), PointerInput.Origin.viewport(), x, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(seq));
    }

    public void pullToRefreshHistory() {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.30);    // start near the top
        int endY = (int) (size.height * 0.70);    // drag down ~40%

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence seq = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), x, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, ofMillis(150))) // small hold helps overscroll
                .addAction(finger.createPointerMove(ofMillis(500), PointerInput.Origin.viewport(), x, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(seq));
    }

    public void swipeLeftOn(By locator) {
        WebElement el = driver.findElement(locator);
        Rectangle r = el.getRect();
        int startX = r.x + (int) (r.width * 0.90); // near right edge
        int endX = r.x + (int) (r.width * 0.10); // near left edge
        int y = r.y + r.height / 2;          // vertical center

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(new Pause(finger, Duration.ofMillis(150)));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, y));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }

    // composite = the one element whose content-desc is like
    // "Slide to Purchase\n10 Points\nMy Points: 100"
    public void slideCompositeSliderToBuyVoucher(By composite, double rightDeadZone01, double percent01) {
        // rightDeadZone01: fraction of the container width reserved for the points panel (e.g., 0.30–0.40)
        // percent01: how far along the usable track you want to slide (0..1)
        WebElement el = driver.findElement(composite);
        Rectangle r = el.getRect();

        // Geometry
        int pad = r.height / 2;                        // knob radius heuristic
        int startX = r.x + pad;                        // knob center at rest
        int usableRightX = r.x + (int) Math.round(r.width * (1.0 - rightDeadZone01)) - pad;
        int y = r.y + r.height / 2;

        if (usableRightX <= startX) usableRightX = r.x + r.width - pad - 4; // safety

        // Clamp and map percent to [startX .. usableRightX]
        double p = Math.max(0, Math.min(1, percent01));
        int targetX = (int) Math.round(startX + (usableRightX - startX) * p);

        // Drag (W3C actions)
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence drag = new Sequence(finger, 1);
        drag.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y));
        drag.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        drag.addAction(new Pause(finger, Duration.ofMillis(150)));
        drag.addAction(finger.createPointerMove(Duration.ofMillis(450), PointerInput.Origin.viewport(), targetX, y));
        drag.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(drag));
    }

    public void slideToRedeem(By thumb, int maxSteps) {
        WebElement t = driver.findElement(thumb);
        Rectangle rt = t.getRect();
        int r = rt.getHeight() / 2;

        int y = rt.getY() + r;
        int x = rt.getX() + r;

        // choose a conservative step: 0.25 of viewport width or ~3 thumb diameters
        int viewportW = driver.manage().window().getSize().getWidth();
        int step = Math.max((int) (viewportW * 0.25), r * 6);

        for (int i = 0; i < maxSteps; i++) {
            int target = x + step;

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence drag = new Sequence(finger, 1);
            drag.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            drag.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            drag.addAction(new Pause(finger, Duration.ofMillis(120)));
            drag.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), target, y));
            drag.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Arrays.asList(drag));

            // refresh thumb position for the next nudge
            t = driver.findElement(thumb);
            Rectangle now = t.getRect();
            int newX = now.getX() + now.getHeight() / 2;

            // stop if movement was tiny (we likely hit the end or a hard stop)
            if (newX - x < r / 2) break;
            x = newX;

            // optional: break when a success condition appears (label/dialog/toast)
            // if (isRedeemed()) break;
        }

    }

    public void slideByCoordinate(int startX, int endX, int startY, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        System.out.println("Start coordinate: " + startX + "," + startY);
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(new Pause(finger, Duration.ofMillis(150)));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));
        System.out.println("End coordinate: " + endX + "," + endY);
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

    public void waitKeyboardAndHide() {
        TestLogger.startFunc();
        try {
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(3))
                    .pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(d -> {
                        if(d.isKeyboardShown()) {
                            System.out.println("[INFO] Keyboard shown, hiding keyboard...");
                            d.hideKeyboard();
                            return  true;
                        }
                        return false;
                    });
        }catch (TimeoutException ignored) {
            System.out.println("[INFO] Keyboard TimeoutException");
        }
    }



}