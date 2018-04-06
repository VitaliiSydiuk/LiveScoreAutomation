package gamesys.automation.common.primitive.elements;

import gamesys.automation.common.DriverProvider;
import gamesys.automation.common.ResultValidator;
import gamesys.automation.enums.Direction;
import gamesys.automation.enums.Result;
import gamesys.automation.tools.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

public class BasicElement extends DriverProvider
{
    protected By id;
    protected List<By> ids;
    protected By childId;
    protected String description;
    protected WebDriverUtils tools = new WebDriverUtils();
    protected WebElement parent;

    public BasicElement(By elemId, String description)
    {
        parent=null;
        id = elemId;
        childId =null;
        ids=null;
        this.description = description;
    }

    /**
     * Get element with specified child;
     */
    public BasicElement(By elemId, By hasChildWithId, String description)
    {
        parent=null;
        id = elemId;
        childId =hasChildWithId;
        ids=null;
        this.description = description;
    }

    public BasicElement(String description, By...elemIds)
    {
        parent=null;
        id=null;
        childId =null;
        ids = Arrays.asList(elemIds);
        this.description = description;
    }

    public BasicElement(WebElement parentElement, By elemId, String description)
    {
        parent=parentElement;
        id = elemId;
        childId =null;
        ids=null;
        this.description = description;
    }


    public By getSelector()
    {
        return id;
    }

    public List<By> getSelectors()
    {
        return ids;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean exists()
    {
        boolean result = false;
        try
        {
            if(parent!=null)
            {
                result=tools.isExists(parent, id);
            }
            else if(childId!=null) {
                result=tools.isExists(driver.findElement(id), childId);
            }
            else if(childId ==null & ids==null)
            {
                result = tools.isExists(1, id);
            }
            else if(ids!=null)
            {
                List<By> parentIds= (ArrayList<By>)((ArrayList<By>)ids).clone();
                By lastBy=parentIds.get(parentIds.size()-1);
                parentIds.remove(parentIds.size()-1);
                WebElement parentElem=null;
                for(By by : parentIds)
                {
                    if(parentElem==null){
                        parentElem=driver.findElement(by);
                    }
                     else {
                        parentElem = parentElem.findElement(by);
                    }
                }
                result = tools.isExists(parentElem,lastBy);
            }
        }
        catch (Exception ex)
        {
            result = false;
        }
        repo.Write("Element '" + description + "' " + ((result)? "exists" : "doesn't exist"), Result.INFO);
        return result;
    }

    public WebElement getChild(By childId)
    {
        return get().findElement(childId);
    }

    public WebElement getChild(BasicElement child)
    {
        return get().findElement(child.getSelector());
    }

    public List<WebElement> getChilds(By childId)
    {
        return get().findElements(childId);
    }

    public List<WebElement> getChilds(BasicElement child)
    {
        return get().findElements(child.getSelector());
    }

    public boolean enabled()
    {
        boolean result;
        if(parent!=null)
        {
            result=tools.waitForEnable(1, parent, id);
        }
        else {
            result = tools.waitForEnable(1, id);
        }
        repo.Write("Element '" + description + "' " + ((result)? " enabled" : " is not enabled or doesn't exist"), Result.INFO);
        return result;
    }

    public boolean waitForNotEnable(int timeoutSeconds)
    {
        repo.Write("Waiting for " + timeoutSeconds + " seconds until '" + description + "' enabled", Result.INFO);
        boolean result;
        if(parent!=null)
        {
            result=tools.waitForNotEnable(timeoutSeconds, parent, id);
        }
        else {
            result = tools.waitForNotEnable(timeoutSeconds, id);
        }
        repo.Write("Element '" + description + "' " + ((result)? "is disabled now" : "is still enabled"), Result.INFO);
        return result;
    }

    public boolean waitForEnable(int timeoutSeconds)
    {
        boolean result = false;
        repo.Write("Waiting for " + timeoutSeconds + " seconds for enabled element '" + description + "'", Result.INFO);
        if(parent!=null)
        {
            result=tools.waitForEnable(timeoutSeconds, parent, id);
        }
        else {
            result = tools.waitForEnable(timeoutSeconds, id);
        }
        repo.Write("Element '" + description + "' " + ((result)?  "is enabled now" : "is still disabled"), Result.INFO);
        return result;
    }

    public void click()
    {
        repo.Write("Clicking element '" + description + "'", Result.INFO);
        get().click();
    }

    public List<WebElement> getAll()
    {
        List<WebElement> forReturn = new ArrayList<WebElement>();
        if(parent!=null)
        {
            return (List<WebElement>)parent.findElements(id);
        }
        else if(childId !=null) {
            for(WebElement elem : driver.findElements(id))
            {
                if(tools.isExists(elem, childId))
                    forReturn.add(elem);
            }
            return forReturn;
        }
        else if(childId ==null & ids==null)
        {
            return (List<WebElement>)driver.findElements(id);
        }
        else if(ids!=null)
        {
            WebElement elem=null;

            int last=ids.size()-1;
            for(int i=0;i<last;i++)
            {
                if(elem==null){
                    elem=driver.findElement(ids.get(i));
                }
                else {
                    elem = elem.findElement(ids.get(i));
                }
            }
            return elem.findElements(ids.get(last));
        }
        return null;

    }

    public WebElement get(int i)
    {
        List<WebElement> list=getAll();
        if(i==-1)
        {
            return list.get(list.size()-1);
        }
        if(i>=0 && i<list.size())
        {
            return list.get(i);
        }
        else
        {
            return null;
        }
    }

    public WebElement get()
    {
        try {
            if (parent != null) {
                return parent.findElement(id);
            } else if (childId != null) {
                for (WebElement elem : driver.findElements(id)) {
                    if (tools.isExists(elem, childId))
                        return elem;
                }
                return null;
            } else if (childId == null & ids == null) {
                return driver.findElement(id);
            } else if (ids != null) {
                WebElement elem = null;
                for (By by : ids) {
                    if (elem == null) {
                        elem = driver.findElement(by);
                    } else {
                        elem = elem.findElement(by);
                    }
                }
                return elem;
            }
        }
        catch(Exception e)
        {
            repo.Write("Impossible to get element: "+description,Result.FAIL);
            ResultValidator.fail(e.getMessage());
        }
        return null;
    }

    public String getAttribute(String attrName)
    {
        return get().getAttribute(attrName);
    }

    public void swipeVertical(double startPercentage, double finalPercentage, double anchorPercentage, int duration)
    {
        try {
            tools.swipeVertical(get(), startPercentage,  finalPercentage,  anchorPercentage,  duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Left swipe example<br>
     * swipeHorizontal(WebElement,0.99,0.01,0.5,3000);<br>
     * Right swipe example<br>
     * swipeHorizontal(WebElement,0.01,0.99,0.5,3000);<br>
     */
    public void swipeHorizontal(double startPercentage, double finalPercentage, double anchorPercentage, int duration)
    {
        try {
            tools.swipeHorizontal(get(), startPercentage,  finalPercentage,  anchorPercentage,  duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void swipe(Direction direction){
        switch (direction) {
            case UP:
                swipeVertical(0.9, 0.1, 0.5, 1000);
                break;
            case DOWN:
                swipeVertical(0.1, 0.9, 0.5, 1000);
                break;
            case RIGHT:
                swipeHorizontal(0.1, 0.99, 0.5, 1000);
                break;
            case LEFT:
                swipeHorizontal(0.9, 0.01, 0.5, 1000);
                break;
        }
    }

    public boolean checkIsExist()
    {
        if (waitForEnable(3))
        {
            repo.Write("Control '" + description+ "' exists - PASS", Result.PASS);
            return true;
        }
        else
        {
            repo.Write("Control '" + description + "' doesn't exist - FAIL", Result.FAIL);
            return false;
        }
    }

    public boolean checkIsNotExist() {

        if (waitForNotEnable(3)) {
            repo.Write("Check is control NOT exist - Control "+ description +" is not found", Result.PASS);
            return true;
        }
        else
        {
            repo.Write("Check is control NOT exist - Control "+ description + " is found", Result.FAIL);
            return false;
        }
    }


    public boolean checkIsExistWithSwipe(Direction direction, BasicElement swipeLayout) {

        if (swipeToSelf(direction, swipeLayout)) {
            repo.Write("Control "+ description +" is found", Result.PASS);
            return true;
        }
        else
        {
            repo.Write("Control "+ description +" not found", Result.FAIL);
            return false;
        }
    }

    public boolean swipeToSelf(Direction direction, BasicElement swipeLayout){
        try {
            for (int i = 0; i < 10; i++) {

                if (waitForEnable(1))
                    return true;

                switch (direction) {
                    case UP:
                        swipeLayout.swipeVertical(0.8, 0.1, 0.5, 2000);
                        break;
                    case DOWN:
                        swipeLayout.swipeVertical(0.1, 0.8, 0.5, 2000);
                        break;
                    case RIGHT:
                        swipeLayout.swipeHorizontal(0.1, 0.8, 0.5, 2000);
                        break;
                    case LEFT:
                        swipeLayout.swipeHorizontal(0.8, 0.1, 0.5, 2000);
                        break;
                }
            }
            return false;
        }
        catch(Exception e) {return false;}

    }



}
