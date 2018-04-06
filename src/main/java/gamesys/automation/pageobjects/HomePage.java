package gamesys.automation.pageobjects;

import gamesys.automation.common.DriverProvider;
import gamesys.automation.common.ResultValidator;
import gamesys.automation.common.primitive.elements.BasicElement;
import gamesys.automation.common.primitive.elements.Button;
import gamesys.automation.common.primitive.elements.TextView;
import gamesys.automation.enums.Direction;
import gamesys.automation.enums.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends DriverProvider
{

    //controls
    private BasicElement homePageContainer;
    private BasicElement matchesListView;
    private BasicElement matchRow;
    private BasicElement calenderView;
    private BasicElement videoCarousel;
    private Button homeBtn;
    private Button standingsBtn;
    private Button teamsBtn;
    private Button newsBtn;
    private TextView calenderItems;
    private TextView matchHomeTeam;
    private TextView matchAwayTeam;
    private BasicElement machListContainer;

    //    private Button settingsBtn;
    private TextView groupText;


    public HomePage()
    {
        if (Is_iOS())
        {
//            homePageContainer = new Button(By.id("GSLHomeAccountMenuButton"), "HomePage -> Container");
//            loginBtn = new Button(By.id("GSLHomeLoginButton"), "HomePage -> Login Button");
//            registerBtn = new Button(By.id("GSLHomeRegisterButton"), "HomePage -> Register Button");
//            logOutBtn = new Button(By.id("GSLHomeLogoutButton"), "HomePage -> Log Out Button");
//            usernameTitle = new TextView(By.id("usernameTitleID"), "HomePage -> Username Title");
//            balanceLine = new TextView(By.id("BalanceLineID"), "HomePage -> Balance Line");
        }
        else
        {
            homeBtn = new Button(By.id(AndroidPackage + ":id/BOTTOM_BAR_MENU_ITEM_SCORES"), "MainPage -> Home Button");
            standingsBtn = new Button(By.id(AndroidPackage + ":id/BOTTOM_BAR_MENU_ITEM_STANDINGS"), "MainPage -> Standings Button");
//            teamsBtn = new Button(By.id(AndroidPackage + ":id/BOTTOM_BAR_MENU_ITEM_NEWS"), "MainPage -> Teams Button");
            newsBtn = new Button(By.id(AndroidPackage + ":id/BOTTOM_BAR_MENU_ITEM_NEWS"), "MainPage -> News Button");
//            settingsBtn = new Button(By.id(AndroidPackage + ":id/btnSettings"), "MainPage -> Settings Button");
            homePageContainer = new BasicElement(By.id(AndroidPackage + ":id/content"), "HomePage -> Container");
            machListContainer = new BasicElement(By.id(AndroidPackage + ":id/BASE_SCREEN_GROUP"),"machListContainer");

            matchesListView = new BasicElement(By.id(AndroidPackage + ":id/RECYCLER_VIEW_LAYOUT"), "MainPage -> Matches List");
            matchRow = new BasicElement(By.id(AndroidPackage + ":id/MATCH_ROW_NOTIF_LAYOUT"), "MainPage -> Match Row");
            matchHomeTeam = new TextView(By.id(AndroidPackage + ":id/MATCH_ROW_HOME_TEAM_TEXT"),"MainPage -> Match Row -> Home Team");
            matchAwayTeam = new TextView(By.id(AndroidPackage + ":id/MATCH_ROW_AWAY_TEAM_TEXT"),"MainPage -> Match Row -> Away Team");

            calenderView = new BasicElement(By.id(AndroidPackage + ":id/SCORES_CALENDAR"), "MainPage -> Calendar");
            calenderItems = new TextView("MainPage -> Calendar Items",By.id(AndroidPackage + ":id/BASE_SCREEN_CALENDER"),By.className("android.widget.TextView"));

            videoCarousel = new BasicElement(By.id(AndroidPackage + ":id/VIDEO_CAROUSEL_PREVIEW"),"MainPage -> Video Carousel");
        }
    }

    public void goToTeamsPage()
    {
        teamsBtn.click();
    }
    public void goToScoresPage()
    {
        standingsBtn.click();
    }
    public void goToMatchPage()
    {
        matchRow.click();
    }
    public void goToHomePage()
    {
        homeBtn.click();
    }
    public void goToNewsPage()
    {
        newsBtn.click();
    }
    public boolean waitForPageLoaded(int timeout)
    {
        return homePageContainer.waitForEnable(timeout);
    }

    public HomePage swipeUpmachListContainer() throws Exception
    {
        machListContainer.swipe(Direction.UP);//0.9, 0.1, 0.5, 1000);
        sleep(500);
        return this;
    }

    public HomePage swipeUp() throws Exception
    {
        homePageContainer.swipeVertical(0.9, 0.1, 0.5, 1000);
        sleep(250);
        return this;
    }

    public HomePage swipeDown() throws Exception
    {
        homePageContainer.swipeVertical(0.1, 0.9, 0.5, 1000);
        sleep(250);
        return this;
    }

    public HomePage swipeDown(int iter) throws Exception
    {
        for (int i = iter; i > 0; i--)
            swipeDown();
        return this;
    }

    public HomePage swipeUp(int iter) throws Exception
    {
        for (int i = iter; i > 0; i--)
            swipeUp();
        return this;
    }

    public HomePage swipeCalendarRight(int iter) throws Exception
    {
        for (int i = iter; i > 0; i--)
            calenderView.swipe(Direction.RIGHT);
        return this;
    }
    public HomePage swipeCalendarLeft(int iter) throws Exception
    {
        for (int i = iter; i > 0; i--)
            calenderView.swipe(Direction.LEFT);
        return this;
    }

    public HomePage goToDate(String dateText) throws Exception {
        if (calenderView.exists())
        {
            repo.Write("Calendar is present.", Result.INFO);
            String firstItem="";
            for(int i=0;i<10;i++) {
                for (WebElement calendarItem : calenderItems.getAll()) {
                    if (calendarItem.getText().equals(dateText)) {
                        calendarItem.click();
                        return this;
                    }
                }
                firstItem = calenderItems.get(0).getText();
                calenderView.swipe(Direction.LEFT);
                if (firstItem.equals(calenderItems.get(0).getText())) {
                    repo.Write("Item " + dateText + " not found in the calendar", Result.FAIL);
                    for (int j=i; j>0; j--){
                        calenderView.swipe(Direction.RIGHT);
                    }

                    return this;
                }
            }
            repo.Write("There is no calendar item: " + dateText , Result.FAIL);
        }
        else
        {
            repo.Write("There is no calendar", Result.FAIL);
            return this;
        }
        return this;
    }

    public List<String> collectAllCalendarItems() throws Exception {

    List<String> toReturn = new ArrayList<>();
    if (calenderView.exists())
    {
        repo.Write("There is a calendar. Lets click somewhere", Result.INFO);
        swipeCalendarRight(4);

        String firstItem="";
        for(int i=0;i<10;i++) {
            for (WebElement calendarItem : calenderItems.getAll()) {
                toReturn.add(calendarItem.getText());
            }
            firstItem = calenderItems.get(0).getText();


            calenderView.swipe(Direction.LEFT);
            if (firstItem.equals(calenderItems.get(0).getText())) {
                toReturn =  stringUtils.removeDuplicatesFromList(toReturn);

                repo.Write("Calendar return  " + toReturn.toString(), Result.INFO);
                for (int j=i; j>0; j--){
                    calenderView.swipe(Direction.RIGHT);
                }
                return toReturn;
            }
        }
    }
    else
    {
        repo.Write("There is no calendar", Result.FAIL);
        return null;
    }
    return null;
}

    public void CheckAllEvents(List<String> CalendarItems, boolean screenshotNeeded) throws Exception {
        swipeCalendarRight(4);

        for (String item: CalendarItems)
        {
            goToDate(item);
            matchesListView.swipe(Direction.UP);
            matchesListView.swipe(Direction.UP);
            matchesListView.swipe(Direction.UP);
            matchesListView.swipe(Direction.UP);
            if (screenshotNeeded = true)
            {
                repo.Write("Matches  for day: " +item+" sсreenshot", Result.INFO, webDriverUtils.getScreenshot());
                if (matchesListView.exists()){collectMatchesTeams();}

            }
            matchesListView.swipe(Direction.DOWN);
            matchesListView.swipe(Direction.DOWN);
            matchesListView.swipe(Direction.DOWN);
            matchesListView.swipe(Direction.DOWN);
        }
    }

    public HomePage goToDate1 (String dateText) throws Exception {
        if (calenderView.exists())
        {

            repo.Write("There is a calendar. Lets click somewhere", Result.INFO);
            TextView dateCalendarItem1 = new TextView(By.xpath("//android.widget.TextView[@text='"+dateText+"']"), "Calendar item "+dateText);
            dateCalendarItem1.click();
            return this;
        }
        else
        {
            repo.Write("There is no calendar", Result.FAIL);
            return this;
        }

    }


    public List<String> collectMatchesTeams() {
        List<String> toReturn = new ArrayList<>();
        for (WebElement Item : matchRow.getAll())
        {
            toReturn.add(Item.findElement(By.id(AndroidPackage + ":id/MATCH_ROW_HOME_TEAM_TEXT")).getText());
            toReturn.add(Item.findElement(By.id(AndroidPackage + ":id/MATCH_ROW_AWAY_TEAM_TEXT")).getText());
        }
        repo.Write("Match row return: " + toReturn.toString(), Result.WARNING);
        return toReturn;
    }
}
