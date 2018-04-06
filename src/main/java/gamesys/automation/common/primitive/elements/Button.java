package gamesys.automation.common.primitive.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Button extends TextView
{
    public Button(By elemId, String description)
    {
        super(elemId, description);
    }
    public Button(WebElement parent, By elemId, String description)
    {
        super(parent, elemId, description);
    }
    public Button(By elemId, By hasChildWithId, String description){ super(elemId, hasChildWithId, description);}
    public Button(String description, By...elemIds){ super(description, elemIds);}
}
