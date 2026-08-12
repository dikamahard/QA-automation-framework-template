package base;

public enum MobileApp {

    POS(
            BaseTest.APP_PKG_POS,
            BaseTest.APP_ACT_POS
    ),

    GALILEO(
            BaseTest.APP_PKG_GALILEO,
            BaseTest.APP_ACT_GALILEO
    ),

    MONEI(
            BaseTest.APP_PKG_MONEI,
            BaseTest.APP_ACT_MONEI
    );

    private final String appPkg;
    private final String appAct;

    MobileApp(String appPkg, String appAct) {
        this.appPkg = appPkg;
        this.appAct = appAct;
    }

    public String getAppPkg() {
        return appPkg;
    }

    public String getAppAct() {
        return appAct;
    }
}
