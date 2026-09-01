package helper;

public class TestLogger {

    private static final StackWalker WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private static String caller() {
        StackWalker.StackFrame f = WALKER.walk(s ->
                s.dropWhile(frame -> frame.getDeclaringClass() == TestLogger.class)
                        .findFirst()
                        .orElseThrow()
        );
        return f.getDeclaringClass().getSimpleName() + "." + f.getMethodName();
    }

    public static void startTest(String testName) {
        System.out.println("===========================================================================");
        System.out.println("============== Start test " + testName + " ==============");
        System.out.println("===========================================================================");

    }

    public static void endTest(String testName) {
        System.out.println("===========================================================================");
        System.out.println("============== Done test " + testName + " ==============");
        System.out.println("===========================================================================");
    }

    public static void startFunc(String funcName) {
        System.out.println(">>>>>>> Start " + funcName);
    }

    public static void endFunc(String funcName) {
        System.out.println(">>>>>>> Done " + funcName);
    }

    public static void startAction(String actionName, String target) {
        System.out.println(actionName + " : " + target);
    }



    ////////////////// No-arg versions that auto-pick caller /////////////////////////////

    public static void startTest() {
        String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
        String testName = "============== Start test " + caller + " ==============";
        String banner = "=".repeat(testName.length());
        System.out.println(banner);
        System.out.println(testName);
        System.out.println(banner);
    }

    public static void endTest() {
        String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
        String testName = "============== Done test " + caller + " ==============";
        String banner = "=".repeat(testName.length());
        System.out.println(banner);
        System.out.println(testName);
        System.out.println(banner);
    }
    public static void startFunc() {
        System.out.println(">>>>>>> Start " + caller());
    }

    public static void endFunc() {
        System.out.println(">>>>>>> Done " + caller());
    }

    public static void startAction(String target) {
        System.out.println(caller() + " : " + target);
    }

    public static void startAction() {
        System.out.println(caller());
    }


    // Message info logging
    private static void printLogMessage(String tag, String msg) {
        System.out.printf("[%s] %s%n", tag, msg);
    }

    public static void printCleanUpWarning(String cleanUpName, String info) {
        String msg = String.format("Failed to %s: %s", cleanUpName, info);
        printLogMessage("CLEANUP WARNING", msg);
    }

    public static void printCleanUpInfo(String msg) {
        printLogMessage("CLEANUP INFO", msg);
    }

    public static void printInfo(String msg) {
        printLogMessage("INFO", msg);
    }

    public static void printLog(String itemToLog, String value) {
        String msg = String.format("%s: %s", itemToLog, value);
        printLogMessage("LOG", msg);
    }

    public static void printLog(String itemToLog, int value) {
        String msg = String.format("%s: %d", itemToLog, value);
        printLogMessage("LOG", msg);
    }

    public static void printLog(String itemToLog, boolean value) {
        String msg = String.format("%s: %b", itemToLog, value);
        printLogMessage("LOG", msg);
    }

    public static void printError(String itemToLog, String value) {
        String msg = String.format("%s: %s", itemToLog, value);
        printLogMessage("ERROR", msg);
    }



}
