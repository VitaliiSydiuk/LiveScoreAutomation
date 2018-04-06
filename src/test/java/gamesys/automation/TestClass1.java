package gamesys.automation;

import gamesys.automation.common.BaseSetup;
import gamesys.automation.common.ResultValidator;
import gamesys.automation.enums.Direction;
import gamesys.automation.enums.Result;
import gamesys.automation.pageobjects.HomePage;
import gamesys.automation.pageobjects.MatchDetails;
import gamesys.automation.pageobjects.StandingsPage;
import org.testng.annotations.Test;

import java.util.List;


public class TestClass1 extends BaseSetup
{
    @Override
    public void oneTimeTearDown(){}

    @Test(groups = {"Screenshoter"})
    public void allScreenShots() throws Exception {
        HomePage homePage = new HomePage();

        sleep(2000);
        repo.Write("SplashScreen page screenshot", Result.INFO, webDriverUtils.getScreenshot());

        homePage.waitForPageLoaded(2);
        repo.Write("Home page screenshot", Result.INFO, webDriverUtils.getScreenshot());

        HomePageCalendarNavigation();
        MatchPageNavigation();
        StandingPageNavigation();
        homePage.goToTeamsPage();
        homePage.goToNewsPage();
        sleep(2000);
        repo.Write("News page screenshot", Result.INFO, webDriverUtils.getScreenshot());

        homePage.goToHomePage();
    }

    @Test(groups = {"Sanity","Smoke"})
    public void HomePageCalendarNavigation() throws Exception {
        HomePage homePage = new HomePage();
        sleep(2000);

        List<String> calendarItems = homePage.collectAllCalendarItems();
        ResultValidator.isTrue(calendarItems != null, "Calendar list should not be empty");
        homePage.CheckAllEvents(calendarItems,true);

    }

    @Test(groups = {"Sanity","Smoke"})
    public void MatchPageNavigation() throws Exception {
        HomePage homePage = new HomePage();
        MatchDetails matchDetails = new MatchDetails();

        homePage.goToMatchPage();
        sleep(2000);

        List<String> matchDetailsTabItems = matchDetails.collectAllTabs();
        ResultValidator.isTrue(matchDetailsTabItems != null, "Match Tabs list should not be empty");
        matchDetails.CheckAllMatchDetailsTabs(matchDetailsTabItems,true);

        homePage.goToHomePage();
    }


    @Test(groups = {"Sanity","Smoke"})
    public void StandingPageNavigation() throws Exception {
        StandingsPage standingsPage = new StandingsPage();
        HomePage homePage = new HomePage();

        homePage.goToScoresPage();
        sleep(2000);

        List<String> standingsTabsItems = standingsPage.collectAllStandingsTabs();
        ResultValidator.isTrue(standingsTabsItems != null, "Standings Tabs list should not be empty");
        standingsPage.CheckAllStandingsTabs(standingsTabsItems,true);

        homePage.goToHomePage();

    }

    @Test(groups = {"Sanity","Smoke"})
    public void CollectDataMatchTabs(){
        HomePage homePage = new HomePage();
        MatchDetails matchDetails = new MatchDetails();

        homePage.goToMatchPage();

        List<String> Result = matchDetails.collectAllTabs();
        ResultValidator.isTrue(Result != null, "Tabs list should not be empty");
    }

    @Test(groups = {"Sanity","Smoke"})
    public void CollectDataStandingsTabs(){
        HomePage homePage = new HomePage();
        StandingsPage standingsPage = new StandingsPage();

        homePage.goToScoresPage();
        standingsPage.collectAllStandingsTabs();
    }

//    @Test(groups = {"Sanity","Smoke"})
//    public void LoginTest(){
//       //ResultValidator.isTrue(homePage.goToLoginPage().loginAs(getConfig().getUser()).isUserLoggedIn(),"Try to login with correct credentials");
//    }
//
//    @Test(groups = {"Sanity"})
//    public void LogOutTest() throws Exception {
//        //ResultValidator.isTrue(homePage.goToLoginPage().loginAs(getConfig().getUser()).isUserLoggedIn(),"Client should login with correct credentials");
//        //ResultValidator.isTrue(!homePage.logout().isUserLoggedIn(),"Client should have possibility to logout");
//    }

    @Test (groups = {"Stress Test"})
    public void StressTestPreparation() throws Exception {
        HomePage homePage = new HomePage();

        for (int i = 0; i<5; i++) {
            repo.Write("Running Cycle:" + i,Result.WARNING);
            sleep(2000);
            HomePageCalendarNavigation();
            MatchPageNavigation();
            StandingPageNavigation();
            homePage.goToTeamsPage();
            homePage.goToNewsPage();
            homePage.goToHomePage();
        }

        repo.Write("All Cycles have been completed successfully",Result.WARNING);
    }

//    @Test(groups = {"Sanity","Smoke"})
//    public void CollectDataMatchesTeams() throws Exception {
//        HomePage homePage = new HomePage();
//        homePage.collectMatchesTeams();
//    }


}
