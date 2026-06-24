package base;

public enum MobileDevice {

    TAB(
            BaseTest.DEVICE_TAB,
            BaseTest.UDID_TAB,
            //BaseTest.PLATFORM_VERSION_TAB,
            BaseTest.URL_APPIUM,
            8201
    ),

    POCO(
            BaseTest.DEVICE_POCO,
            BaseTest.UDID_POCO,
            //BaseTest.PLATFORM_VERSION_POCO,
            BaseTest.URL_APPIUM,
            8202
    ),

    SAMSUNG(
            BaseTest.DEVICE_SAMSUNG,
            BaseTest.UDID_SAMSUNG,
            //BaseTest.PLATFORM_VERSION_SAMSUNG,
            BaseTest.URL_APPIUM,
            8203
    ),

    REDMI(
            BaseTest.DEVICE_REDMI,
            BaseTest.UDID_REDMI,
            //BaseTest.PLATFORM_VERSION_REDMI,
            BaseTest.URL_APPIUM,
            8204
    );

    private final String deviceName;
    private final String udid;
    //private final String platformVersion;
    private final String appiumServerUrl;
    private final int systemPort;

    MobileDevice(
            String deviceName,
            String udid,
            //String platformVersion,
            String appiumServerUrl,
            int systemPort
    ) {
        this.deviceName = deviceName;
        this.udid = udid;
        //this.platformVersion = platformVersion;
        this.appiumServerUrl = appiumServerUrl;
        this.systemPort = systemPort;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getUdid() {
        return udid;
    }

//    public String getPlatformVersion() {
//        return platformVersion;
//    }

    public String getAppiumServerUrl() {
        return appiumServerUrl;
    }

    public int getSystemPort() {
        return systemPort;
    }
}