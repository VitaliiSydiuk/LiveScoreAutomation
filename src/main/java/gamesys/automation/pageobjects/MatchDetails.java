package gamesys.automation.pageobjects;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import gamesys.automation.common.DriverProvider;
import gamesys.automation.common.primitive.elements.BasicElement;
import gamesys.automation.common.primitive.elements.Button;
import gamesys.automation.common.primitive.elements.RadioButton;
import gamesys.automation.common.primitive.elements.TextView;
import gamesys.automation.configuration.UserInfo;
import gamesys.automation.enums.Direction;
import gamesys.automation.enums.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MatchDetails extends HomePage
{
    protected UserInfo user = getConfig().getUser();

    //controls
    private RadioButton bellRBtn;
    private BasicElement matchInfoTabs;
    private BasicElement tourStatsList;
    private BasicElement baseListMatchDetails;
    private TextView matchDetailsTabItems;





    public MatchDetails()
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

            bellRBtn = new RadioButton(By.id(AndroidPackage + ":id/action_watchlist"), "Match Details -> Add/Remove Notification");
            matchInfoTabs = new BasicElement(By.id(AndroidPackage + ":id/MATCH_DETAIL_TAB_HOST"),"Match Info -> Info Tabs");
            tourStatsList = new BasicElement(By.id(AndroidPackage + ":id/TOURN_STATS_LIST"),"Match Details -> Tournament Statistic");
            baseListMatchDetails = new BasicElement(By.id(AndroidPackage + ":id/BASE_SCREEN_VIEWPAGER"),"Match Details -> Info List");
            matchDetailsTabItems=new TextView("Match Details -> Tabs List", By.id(AndroidPackage + ":id/MATCH_DETAIL_TAB_HOST"), By.className("android.widget.TextView"));

        }
    }

    public void addToNotificationsList()
    {
        if (!bellRBtn.isChecked())
        {
            bellRBtn.check();
        }

    }

    public void removeFromNotificationsList()
    {
        if (bellRBtn.isChecked())
        {
            bellRBtn.check();
        }

    }

    public MatchDetails swipeUpMatchDetailsContainer() throws Exception
    {
        baseListMatchDetails.swipe(Direction.UP);//0.9, 0.1, 0.5, 1000);
        sleep(500);
        return this;
    }

    public MatchDetails swipeUp() throws Exception
    {
        baseListMatchDetails.swipeVertical(0.9, 0.1, 0.5, 1000);
        sleep(500);
        return this;
    }

    public MatchDetails swipeDown() throws Exception
    {
        baseListMatchDetails.swipeVertical(0.1, 0.9, 0.5, 1000);
        sleep(500);
        return this;
    }

    public MatchDetails goToMatchInfoTab(String tabText)
    {
        if (matchInfoTabs.exists())
        {
            repo.Write("There are some tabs in Match Info. Lets click somewhere", Result.INFO);
            String firstItem="";
            for(int i=0;i<10;i++) {
                for (WebElement matchDetailsTab : matchDetailsTabItems.getAll()) {
                    if (matchDetailsTab.getText().equals(tabText)) {
                        matchDetailsTab.click();
                        return this;
                    }
                }
                firstItem = matchDetailsTabItems.get(0).getText();
//                matchInfoTabs.swipe(Direction.LEFT);
                if (firstItem.equals(matchDetailsTabItems.get(0).getText())) {
                    repo.Write("Item " + tabText + " not found in Match Info Screen", Result.FAIL);
//                    for (int j=i; j>0; j--){
//                        matchInfoTabs.swipe(Direction.RIGHT);
//                    }

                    return this;
                }
            }
            repo.Write("There is no Match Info Tab item: " + tabText , Result.FAIL);
        }
        else
        {
            repo.Write("There are no Match Info Tabs", Result.FAIL);
            return this;
        }
        return this;
    }

    public MatchDetails goToMatchInfoTab2 (String tabText)
    {
        if (matchInfoTabs.exists())
        {
            repo.Write("There are tabs. Lets click somewhere", Result.INFO);
            TextView tabItem = new TextView(By.xpath("//android.widget.TextView[@text='"+tabText+"']"), "Calendar item "+tabText);
            tabItem.click();
            return this;
        }
        else
        {
            repo.Write("There are no tabs in Match Info", Result.FAIL);
            return this;
        }
    }

    public List<String> collectAllTabs()
    {
        List<String> toReturn= new ArrayList<>();
        if (matchInfoTabs.exists())
        {
            repo.Write("There are Match Details Tabs. Lets click somewhere", Result.INFO);
            String firstItem="";
            for(int i=0;i<4;i++) {
                for (WebElement matchDetailsTab : matchDetailsTabItems.getAll()) {
                    toReturn.add(matchDetailsTab.getText());
                }
                firstItem = matchDetailsTabItems.get(0).getText();
                matchInfoTabs.swipe(Direction.LEFT);
                if (firstItem.equals(matchDetailsTabItems.get(0).getText())) {
                    toReturn=  stringUtils.removeDuplicatesFromList(toReturn);
                    repo.Write("Match Details Tabs return  " + toReturn.toString(), Result.INFO);
                    for (int j=i; j>0; j--){
                        matchInfoTabs.swipe(Direction.RIGHT);
                    }
                    return toReturn;
                }
            }
        }
        else
        {
            repo.Write("There are no tabs in Match Details", Result.FAIL);
            return null;
        }
        return null;
    }

    public void CheckAllMatchDetailsTabs(List<String> MatchInfoTabItems, boolean screenshotNeeded)
    {
        for (String item: MatchInfoTabItems)
        {
            goToMatchInfoTab(item);
            sleep(1000);

            if (screenshotNeeded = true)
            {
                matchInfoTabs.swipe(Direction.UP);
                repo.Write("Match Info" +item+" sreenshot", Result.INFO, webDriverUtils.getScreenshot());
                matchInfoTabs.swipe(Direction.DOWN);
            }

        }
    }

}
