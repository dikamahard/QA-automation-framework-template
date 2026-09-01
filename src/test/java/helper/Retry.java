package helper;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {

        Throwable throwable = result.getThrowable();

        if (!isRetryable(throwable)) {
            return false;
        }

        if (retryCount >= MAX_RETRY) {
            return false;
        }

        retryCount++;

        System.out.printf(
                "[RETRY] %s | Attempt %d/%d | %s: %s%n",
                result.getName(),
                retryCount,
                MAX_RETRY,
                throwable.getClass().getSimpleName(),
                throwable.getMessage()
        );

        return true;
    }

    private boolean isRetryable(Throwable throwable) {

        Throwable current = throwable;

        while (current != null) {

            // list exception that needs to be retry
            if (current instanceof TimeoutException
                    || current instanceof NoSuchElementException
                    || current instanceof StaleElementReferenceException) {
                return true;
            }

            current = current.getCause();
        }

        return false;
    }
}
