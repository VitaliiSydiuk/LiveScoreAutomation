package gamesys.automation.common.primitive.elements;

import gamesys.automation.enums.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CheckBox extends RadioButton
{
    public CheckBox(By elemId, String description)
    {
        super(elemId, description);
    }
    public CheckBox(WebElement parent, By elemId, String description)
    {
        super(parent, elemId, description);
    }
    public CheckBox(By elemId, By hasChildWithId, String description){ super(elemId, hasChildWithId, description);}
    public CheckBox(String description, By...elemIds){ super(description, elemIds);}

    public void uncheck()
    {
        repo.Write("Uncheck element '" + description + "'", Result.INFO);
        WebElement elem = get();
        if(elem.isSelected())
            elem.click();
    }
}
