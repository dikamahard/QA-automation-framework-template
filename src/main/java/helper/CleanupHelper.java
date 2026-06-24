package helper;

public class CleanupHelper {

    public static void safeCleanup(String cleanupName, boolean doCleanup, Runnable cleanupAction) {

        if (!doCleanup) {
            TestLogger.printCleanUpInfo("Skip " + cleanupName);
            return;
        }

        TestLogger.printCleanUpInfo(cleanupName);
        try {
            cleanupAction.run();
        }catch (Exception e) {
            TestLogger.printCleanUpWarning(cleanupName, e.getMessage());
        }

    }
}
