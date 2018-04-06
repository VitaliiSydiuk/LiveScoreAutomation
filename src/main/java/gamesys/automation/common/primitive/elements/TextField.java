package gamesys.automation.common.primitive.elements;

import gamesys.automation.enums.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TextField extends TextView
{
    public TextField(By elemId, String description)
    {
        super(elemId, description);
    }
    public TextField(WebElement parent, By elemId, String description)
    {
        super(parent, elemId, description);
    }
    public TextField(By elemId, By hasChildWithId, String description){ super(elemId, hasChildWithId, description);}
    public TextField(String description, By...elemIds){ super(description, elemIds);}

    public void setText(String value)
    {
        repo.Write("Set text of element '" + description + "' to: '" + value + "'", Result.INFO);
        get().sendKeys(value);
    }

    public void clearText()
    {
        repo.Write("Clear text of element '" + description + "'", Result.INFO);
        get().clear();
    }

    public void sendKey(Keys key)
    {
        repo.Write("Sending key '" + key.name() + " to element '" + description + "'", Result.INFO);
        get().sendKeys(key);
    }
}
