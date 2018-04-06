package gamesys.automation.common;

import gamesys.automation.configuration.UserInfo;
import gamesys.automation.pageobjects.HomePage;
import org.testng.annotations.*;

@Listeners(MakeScreenshotIfFailed.class)
public class BaseSetup extends DriverProvider
{
    //public WebDriverUtils tools = new WebDriverUtils();
    protected UserInfo user = getConfig().getUser();
    public MakeScreenshotIfFailed ruleScreenshotMaker = new MakeScreenshotIfFailed();

    @BeforeSuite(alwaysRun = true)
    public void oneTimeSetup()
    {
        //Setup screenshot maker
        ruleScreenshotMaker.setDriver(driver);
        //TO DO: Write code to rune once before all tests
    }

    @AfterSuite
    public void oneTimeTearDown()
    {
        //TO DO: Write code to rune once after all tests
        driver.quit();
    }

    @BeforeMethod(alwaysRun = true)
    public void BeforeTestSetUp() throws Exception {
        //TO DO: Write code to rune before each test

    }

    //Write here methods which can be access form any test
}

