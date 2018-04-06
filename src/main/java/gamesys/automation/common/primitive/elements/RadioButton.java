package gamesys.automation.common.primitive.elements;

import gamesys.automation.enums.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RadioButton extends BasicElement
{
    public RadioButton(By elemId, String description)
    {
        super(elemId, description);
    }
    public RadioButton(WebElement parent, By elemId, String description)
    {
        super(parent, elemId, description);
    }
    public RadioButton(By elemId, By hasChildWithId, String description){ super(elemId, hasChildWithId, description);}
    public RadioButton(String description, By...elemIds){ super(description, elemIds);}

    public boolean isChecked()
    {
        boolean result = false;
        result = get().isSelected();
        repo.Write("Element '" + description + "' is " + ((result)? "checked" : "unchecked"), Result.INFO);
        return result;
    }

    public void check()
    {
        repo.Write("Check element '" + description + "'", Result.INFO);
        WebElement elem = get();
        if(!elem.isSelected())
            elem.click();
    }
}
