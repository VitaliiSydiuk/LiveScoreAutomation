package gamesys.automation.common;

import gamesys.automation.enums.Result;
import gamesys.automation.tools.WebDriverUtils;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import static gamesys.automation.common.DriverProvider.repo;

public class MakeScreenshotIfFailed extends TestListenerAdapter
{
    private static WebDriverUtils tools;
    AppiumDriver driver;

    public void setDriver(AppiumDriver driver)
    {
        this.driver = driver;
        tools = new WebDriverUtils();
    }

    @Override
    public void onTestFailure(ITestResult result)
    {
        repo.Write("FAILED: ################## " + result.getMethod().getMethodName() + " ##################", Result.FAIL, tools.getScreenshot());
        tools.setDriverTimeoutTo_SECONDS(30);
        tools.restartApp();
    }

    @Override
    public void onTestStart(ITestResult result)
    {
        repo.Write("STARTED: ################## " + result.getMethod().getMethodName() + " ##################", Result.INFO);
    }

    @Override
    public void onTestSuccess(ITestResult result)
    {
        repo.Write("FINISHED: ################## " + result.getMethod().getMethodName() + " ##################", Result.PASS);
    }
}