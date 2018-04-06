package gamesys.automation.common;

        import gamesys.automation.enums.OSType;
        import gamesys.automation.tools.WebDriverUtils;

public class TestEnvPaths {
    public static String ConfigFilesDir = System.getProperty("user.dir");
    public static String TestEnvMainConfigFile = ConfigFilesDir + "/configBase.json";
    public static String UserConfigFile = ConfigFilesDir + "/configByEnv.json";

    public static String getReportPath() {
        if (WebDriverUtils.getOperatingSystemType() == OSType.MacOS) {
            String homeFolder=System.getProperty("user.home");
            return homeFolder+"/Desktop/TempReports";
        } else {
            return ConfigFilesDir + "/reports";
        }
    }
}
