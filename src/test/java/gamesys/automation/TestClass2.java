package gamesys.automation;

import gamesys.automation.common.BaseSetup;
import gamesys.automation.common.ResultValidator;
import gamesys.automation.enums.Direction;
import gamesys.automation.enums.Result;
import gamesys.automation.pageobjects.HomePage;
import gamesys.automation.pageobjects.MatchDetails;
import org.testng.annotations.Test;

import java.util.List;


public class TestClass2 extends BaseSetup
{
//    HomePage homePage = new HomePage();
//    @Override
//    public void BeforeTestSetUp() throws Exception {
//        homePage.logout();
//    }
//
//    @Test(groups = {"Sanity"})
//    public void CheckUsernameAfterLogin(){
//        ResultValidator.isTrue(homePage.loginWithSessionUser(), "Login to client with session user");
//        ResultValidator.isTrue(homePage.getUserName().toLowerCase().equals(getConfig().getUser().Name.toLowerCase()), "Home Page user name text should be same with session username");
//        repo.Write("Home page screen", Result.INFO,webDriverUtils.getScreenshot());
//    }
//
//    @Test(groups = {"Sanity","Smoke"})
//    public void CheckBalanceUpdateAfterLogin(){
//        Double beforeLoginBalance=homePage.getBalance();
//        ResultValidator.isTrue(beforeLoginBalance==0.0, "Balance before login should equals 0");
//        homePage.loginWithSessionUser();
//        ResultValidator.isTrue(beforeLoginBalance!=homePage.getBalance(), "Balance should update after login");
//    }

}
