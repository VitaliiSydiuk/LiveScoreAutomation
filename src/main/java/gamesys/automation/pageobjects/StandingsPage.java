package gamesys.automation.pageobjects;

import gamesys.automation.common.ResultValidator;
import gamesys.automation.common.primitive.elements.BasicElement;
import gamesys.automation.common.primitive.elements.RadioButton;
import gamesys.automation.common.primitive.elements.TextView;
import gamesys.automation.configuration.UserInfo;
import gamesys.automation.enums.Direction;
import gamesys.automation.enums.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class StandingsPage extends HomePage
{
    //controls
    private BasicElement stagesTabs;
    private BasicElement stagesListView;
    private TextView standgingsTabItems;
    private TextView standgingsTabItems2;


    public StandingsPage()
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
            stagesTabs = new BasicElement(By.id(AndroidPackage + ":id/MY_TABLAYOUT_STAGES_TABHOST"),"Standing Page -> Stages Tabs");
            stagesListView = new BasicElement(By.id(AndroidPackage + ":id/BASE_SCREEN_LIST_VIEW"),"Standing Page -> Stages Lists");
//            standgingsTabItems2 = new TextView("",By.id(AndroidPackage + ":id/MY_TABLAYOUT_STAGES_TABHOST"),By.className("android.support.v7.app.ActionBar$Tab"), By.className("android.widget.TextView"));
            standgingsTabItems2 = new TextView("",By.id(AndroidPackage + ":id/MY_TABLAYOUT_STAGES_TABHOST"),By.className("android.widget.LinearLayout"),By.className("android.support.v7.app.ActionBar$Tab"), By.className("android.widget.TextView"));
            standgingsTabItems = new TextView("",By.id(AndroidPackage + ":id/MY_TABLAYOUT_STAGES_TABHOST"),By.className("android.widget.TextView"));
        }
    }

    public StandingsPage swipeUpMatchDetailsContainer() throws Exception
    {
        stagesListView.swipe(Direction.UP);//0.9, 0.1, 0.5, 1000);
        sleep(500);
        return this;
    }

    public StandingsPage swipeUp() throws Exception
    {
        stagesListView.swipeVertical(0.9, 0.1, 0.5, 1000);
        sleep(500);
        return this;
    }

    public StandingsPage swipeDown() throws Exception
    {
        stagesListView.swipeVertical(0.1, 0.9, 0.5, 1000);
        sleep(500);
        return this;
    }

    public StandingsPage goToStandingsTab(String tabText)
    {
        if (stagesTabs.exists())
        {
            repo.Write("Standing Page tabs exists.", Result.INFO);
            String firstItem="";
            int elements = tabText.length();

            for(int i=0; i < elements; i++) {

                for (WebElement matchDetailsTab : standgingsTabItems2.getAll()) {
                    if (matchDetailsTab.getText().equals(tabText)) {
                        matchDetailsTab.click();
                        return this;
                    }
                }

                firstItem = standgingsTabItems2.get(0).getText();
                stagesTabs.swipe(Direction.LEFT);
                if (firstItem.equals(standgingsTabItems2.get(0).getText())) {
                    repo.Write("Item " + tabText + " not found in Standings Page", Result.FAIL);


                    /*Setting the position of tabs scroll list to the beginning*/
                    if (elements >3)
                    {
                        for (int j=i; j>0; j--){
                            stagesTabs.swipe(Direction.RIGHT);
                        }
                    }
                    return this;
                }
            }
            repo.Write("There is no Standings item: " + tabText , Result.FAIL);
        }
        else
        {
            repo.Write("There is no Standings Page", Result.FAIL);
            return this;
        }
        return this;
    }

    public StandingsPage goToStandingsTab2 (String tabText)
    {
        if (stagesTabs.exists())
        {
            repo.Write("There are tabs. Lets click somewhere", Result.INFO);
            TextView tabItem = new TextView(By.xpath("//android.widget.TextView[@text='"+tabText+"']"), "Standings Page item "+tabText);
            tabItem.click();
            return this;
        }
        else
        {
            repo.Write("There are no tabs in Standings Page", Result.FAIL);
            return this;
        }

    }

    public List<String> collectAllStandingsTabs()
    {

        List<String> toReturn = new ArrayList<>();
        if (stagesTabs.exists())
        {
            repo.Write("There are Standings Page tabs. Lets click somewhere", Result.INFO);
            String firstItem = "";

            for(int i=0;i<10;i++) {
                for (WebElement matchDetailsTab : standgingsTabItems.getAll()) {
                    toReturn.add(matchDetailsTab.getText());
                }
                firstItem = standgingsTabItems.get(0).getText();
                stagesTabs.swipe(Direction.LEFT);
                if (firstItem.equals(standgingsTabItems.get(0).getText())) {
                    toReturn=  stringUtils.removeDuplicatesFromList(toReturn);
                    repo.Write("Standings Page Tabs return:  " + toReturn.toString(), Result.INFO);
                    for (int j=i; j>0; j--){
                        stagesTabs.swipe(Direction.RIGHT);
                    }
                    return toReturn;
                }
            }
        }
        else
        {
            repo.Write("There are no tabs", Result.FAIL);
            return null;
        }
        return null;
    }

    public void CheckAllStandingsTabs(List<String> standingsTabsItems, boolean screenshotNeeded)
    {
        for (String item: standingsTabsItems)
        {
            goToStandingsTab2(item);
            sleep(1000);
            if (screenshotNeeded = true)
            {
                stagesListView.swipe(Direction.UP);
                repo.Write("Standings Page: " +item+" screenshot", Result.INFO, webDriverUtils.getScreenshot());
                stagesListView.swipe(Direction.DOWN);
            }
        }
    }


}
