package gamesys.automation.common;

import gamesys.automation.configuration.TestConfigProvider;
import gamesys.automation.enums.*;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import static gamesys.automation.tools.WebDriverUtils.getOperatingSystemType;
import static org.springframework.util.StreamUtils.BUFFER_SIZE;

public class Driver extends TestConfigProvider           {
    private static String reportName = "LS World Cup -  Automation Report";
    private static String reportPath = TestEnvPaths.getReportPath();
    private static Reporter repo=new Reporter(reportName, reportPath);
    private static String AndroidPackage = "com.livescore.event.WorldCup";
    private static File appLocalDir = new File(System.getProperty("user.dir") + "/app/");
    private static AppiumDriver<?> _driver;
    private static Driver ourInstance = new Driver();

    private Driver() {
        CreateDriverIfNotExist();
    }

    public static Driver getInstance() {
        return ourInstance;
    }

    public String getAndroidPackage() {
        return AndroidPackage;
    }

    public Reporter getRepo() {
        return repo;
    }

    public AppiumDriver<?> driver()
    {
        return _driver;
    }

    private static void CreateDriverIfNotExist()
    {
        if (_driver != null)
            return;
        if (getConfig().Device.IsLocal)
            assert (CreateLocalDriver());
        else
        {
            assert (CreateRemoteDriver());
            printCapabilities();
        }
    }

    private static void printCapabilities()
    {
        repo.Write("####################################### CAPABILITIES #####################################", Result.INFO);
        repo.Write("testobject_test_live_view_url: "+_driver.getCapabilities().getCapability("testobject_test_live_view_url").toString(), Result.INFO);
        repo.Write("testobject_test_device: "+_driver.getCapabilities().getCapability("testobject_device").toString(), Result.INFO);
        repo.Write("PLATFORM_VERSION: "+_driver.getCapabilities().getCapability(MobileCapabilityType.PLATFORM_VERSION).toString(), Result.INFO);
        repo.Write("##########################################################################################", Result.INFO);
    }

    private static boolean CreateRemoteDriver()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (getConfig().TargetOS == MobileOS.Android)
        {
//            String pack = "com.livescore.event.WorldCup";
            String pack = "com.livescore.event.ChampionsLeague";
            capabilities.setCapability("testobjectApiKey", "{TEST OBJECT API KEY}");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, pack);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.livescore.event.views.SplashScreen");
            capabilities.setCapability("appiumVersion", "1.7.1");//LAST STABLE VERSION
            AndroidPackage = capabilities.getCapability(AndroidMobileCapabilityType.APP_PACKAGE).toString();
        }
        else
        {
            capabilities.setCapability("testobjectApiKey", "{TEST OBJECT API KEY}");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
            capabilities.setCapability("bundleId", "{IOS BUNDLE ID}");
            capabilities.setCapability("appiumVersion", "1.7.1");//LAST STABLE VERSION
        }
        //Device name has the highest priority
        if (getConfig().Device.Name != "")
            capabilities.setCapability("deviceName", getConfig().Device.Name);
        else
            {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, getConfig().Device.OsVersion);
                capabilities.setCapability("privateDevicesOnly", getConfig().Device.IsPrivate);
                capabilities.setCapability("phoneOnly", getConfig().Device.IsPhoneOnly);
                capabilities.setCapability("tabletOnly", getConfig().Device.IsTabletOnly);
            }
        capabilities.setCapability("newCommandTimeout", 600);
        return InitializeDriver(capabilities, "https://gamesys.testobject.com/wd/hub");
    }

    private static boolean CreateLocalDriver()
    {
        String Url = "http://0.0.0.0:4723/wd/hub";
        File app = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (Is_iOS())
        {
            if (getConfig().Device.Reinstall)
            {
                repo.Write("Reinstall app on device", Result.INFO);
                app =  new File(appLocalDir, "{FILE NAME}.ipa");
                capabilities.setCapability("app", app);
            }
            else
                repo.Write("Reinstall app on device is blocked by config Base", Result.WARNING);

            capabilities.setCapability("deviceName", "iOS Device");
            capabilities.setCapability("udid", "{IOS DEVICE UDID");
            capabilities.setCapability("bundleId", "{IOS BUNDLE ID}");
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("platformVersion", "11");
            capabilities.setCapability("newCommandTimeout", 120);
        }
        else
        {
//            String pack = "com.livescore.event.WorldCup";
            String pack = "com.livescore.event.ChampionsLeague";

            app = new File(appLocalDir, "{FILE NAME}.apk");

            if (getConfig().Device.Reinstall)
                capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
            else
                repo.Write("Reinstall app on device is blocked by config Base", Result.WARNING);

            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android device");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, pack);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.livescore.event.views.SplashScreen");
            capabilities.setCapability("newCommandTimeout", 120);
            AndroidPackage = capabilities.getCapability(AndroidMobileCapabilityType.APP_PACKAGE).toString();
        }
        return InitializeDriver(capabilities, Url);
    }


    private static Boolean InitializeDriver(DesiredCapabilities capa, String WebDriverURL)
    {
        repo.Write("Startig device initialization...WebDriverURL: " + WebDriverURL, Result.INFO);
        try
        {
            _driver = Is_iOS() ? new IOSDriver<>(new URL(WebDriverURL), capa) : new AndroidDriver<>(new URL(WebDriverURL), capa);
            _driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            return true;
        }
        catch (Exception e)
        {
            ResultValidator.fail("Unable to create WebDriver. " + e.getMessage());
            return false;
        }
    }

}
