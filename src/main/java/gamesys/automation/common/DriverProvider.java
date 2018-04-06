package gamesys.automation.common;

import gamesys.automation.configuration.TestConfigProvider;
import gamesys.automation.tools.StringUtils;
import gamesys.automation.tools.WebDriverUtils;
import io.appium.java_client.*;


public class DriverProvider extends TestConfigProvider
{
    public static AppiumDriver<?> driver = Driver.getInstance().driver();
    public static Reporter repo = Driver.getInstance().getRepo();
	public static String AndroidPackage = Driver.getInstance().getAndroidPackage();
    public static WebDriverUtils webDriverUtils = new WebDriverUtils();
    public static StringUtils stringUtils = new StringUtils();

    public static void sleep(int milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
