package gamesys.automation.common.primitive.elements;

import gamesys.automation.enums.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TextView extends BasicElement
{
    public TextView(By elemId, String description)
    {
        super(elemId, description);
    }
    public TextView(WebElement parent, By elemId, String description)
    {
        super(parent, elemId, description);
    }
    public TextView(By elemId, By hasChildWithId, String description){ super(elemId, hasChildWithId, description);}
    public TextView(String description, By...elemIds){ super(description, elemIds);}

    public String getText()
    {
        String result = "";
        result = get().getText();
        repo.Write("Text of element '" + description + "' is: '" + result + "'", Result.INFO);
        return result;
    }
}
