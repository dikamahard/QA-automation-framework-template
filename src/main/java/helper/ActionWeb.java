package helper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class ActionWeb {

    private final WebDriver driver;
    private final WebDriverWait wait;
    public ActionWeb(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void arrowDownKey(By textLocator){
        driver.findElement(textLocator).sendKeys(Keys.ARROW_DOWN);
    }

    public void enterKey (By textLocator){
        driver.findElement(textLocator).sendKeys(Keys.ENTER);
    }

    public void enterEscape (By textLocator){
        driver.findElement(textLocator).sendKeys(Keys.ESCAPE);
    }


    public void click(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
        driver.findElement(locator).click();
    }

    public void inputText(By editText, String text) {
        System.out.println("Input text : " + text);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        int attempts = 0;

//        if(!editText.toString().contains("Description")) {
//            wait.until(ExpectedConditions.visibilityOfElementLocated(editText));
//            System.out.println("Value " + driver.findElement(editText).getAttribute("value"));
//        }
//
//        while (!driver.findElement(editText).getAttribute("value").isEmpty()) {
//            System.out.println("Textbox not empty");
//            WebElement l = driver.findElement(editText);
//            String s = Keys.chord(Keys.CONTROL, "a");
//            l.sendKeys(s);
//            l.sendKeys(Keys.DELETE);
//        }

        while (true) {
            try {
                // Re-find a fresh element every attempt
                System.out.println("wait clickable edit text");
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(editText));
                System.out.println("done wait clickable edit text");

                // Focus + clear robustly
                el.click();
//                System.out.println("clicked");
                el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//                System.out.println("a");
                el.sendKeys(Keys.DELETE);
//                System.out.println("delete");
                el.sendKeys(text);
//                System.out.println("send key");
                return; // success
            } catch (StaleElementReferenceException e) {
                if (++attempts >= 3) throw e; // give up after a few tries
                // brief loop retry, the re-locate above will get a fresh handle
                System.out.println("stale element edit text");
            }
        }

    }

    public void uploadFile(By locator, String source){
        String imgLocator = locator.toString() + "/following-sibling::*//img";
        driver.findElement(locator).sendKeys(source);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            try {
                WebElement img = driver.findElement(locator).findElement(By.xpath("following-sibling::*//img"));
                return img.isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
        });

        //new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(imgLocator)));
    }

    public void uploadFileGalileo(By locator, String source){
        driver.findElement(locator).sendKeys(source);
    }

    public void uploadExcelFile(By locator, String source){
        File uploadFile = new File(source);
        driver.findElement(locator).sendKeys(uploadFile.getAbsolutePath());
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("tr"), 0));
    }

    public void uploadMultipleImage(By locator, String directory, List<String> fileNames) {
        StringBuilder combinedPaths = new StringBuilder();

        for (String name : fileNames) {
            File file = new File(directory + name);

            if(!combinedPaths.isEmpty()){
                combinedPaths.append("\n");
            }
            combinedPaths.append(file.getAbsolutePath());
        }

        driver.findElement(locator).sendKeys(combinedPaths);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            try {
                By simpleBarContent = By.cssSelector(".simplebar-content");
                WebElement contentArea = wait.until(ExpectedConditions.visibilityOfElementLocated(simpleBarContent));
                assert contentArea != null;
                WebElement img = contentArea.findElement(By.tagName("img"));
                return img.isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
        });
    }

    // Galileo Only
    public void inputTextDate(By editText, String text) {
        System.out.println("Input text : " + text);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        int attempts = 0;

        while (true) {
            try {
                // Re-find a fresh element every attempt
                System.out.println("wait clickable edit text");
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(editText));
                System.out.println("done wait clickable edit text");

                // Focus + clear robustly
                el.click();
                System.out.println("click");
                el.sendKeys(text);
                System.out.println("send key");
                return; // success
            } catch (StaleElementReferenceException e) {
                if (++attempts >= 3) throw e; // give up after a few tries
                // brief loop retry, the re-locate above will get a fresh handle
                System.out.println("stale element edit text");
            }
        }

    }
}
