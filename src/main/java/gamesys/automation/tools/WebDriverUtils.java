package gamesys.automation.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import gamesys.automation.common.DriverProvider;
import gamesys.automation.common.primitive.elements.BasicElement;
import gamesys.automation.enums.Result;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import gamesys.automation.pageobjects.HomePage;
import gamesys.automation.enums.OSType;
import io.appium.java_client.TouchAction;
import java.time.Duration;

public class WebDriverUtils extends DriverProvider
{

	WebDriver augmentedDriver = null;
	//private Ocr ocr;
	public OSType CurrentOS = getOperatingSystemType();
	//navigation tools


	public void pause(int pauseInMs)
	{
		repo.Write("Paused for " + pauseInMs + " miliseconds", Result.INFO);
		try
		{
			Thread.sleep(pauseInMs);
		}
		catch (Exception ex)
		{}
	}

	public void setDriverTimeoutTo_SECONDS(int timeoutSeconds)
	{
		driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
	}

	public void setDriverTimeoutTo_MILLISECONDS(int timeoutMilliseconds)
	{
		driver.manage().timeouts().implicitlyWait(timeoutMilliseconds, TimeUnit.MILLISECONDS);
	}

	public void restartApp()
	{
		repo.Write("Restarting app", Result.INFO);
		driver.closeApp();
		driver.launchApp();
	}

	//common tools

	public void tapHardwareBackButton() throws Exception
	{
		if(Is_iOS())
			throw new Exception("There is no hardware Back button on Apple devices");
		((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
	}

	public File getScrrenAsFile()
	{
		if(CurrentOS==OSType.MacOS)
		{
			WebDriver driver1 = new Augmenter().augment(driver);
			return ((TakesScreenshot)driver1).getScreenshotAs(OutputType.FILE);
		}
		else if(CurrentOS==OSType.Windows)
			return driver.getScreenshotAs(OutputType.FILE);
		else
			return null;
	}
	/*
    private BufferedImage GetScreenshotADB() throws Exception
    {
        String UDID=driver.getSessionDetails().get("deviceUDID").toString();
        return Screenshot.getAdbDeviceImage(UDID);
    }
    */
	private BufferedImage getScreenshotAppium() throws Exception
	{
		if (augmentedDriver == null)
			augmentedDriver = new Augmenter().augment(driver);
		return ImageIO.read(((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE));
	}

	public BufferedImage getScreenshot()
	{
		try
		{
			if (!Is_iOS())
				return getScreenshotAppium();

			if (CurrentOS == OSType.MacOS)
				return getScreenshotAppium();
			else if (CurrentOS == OSType.Windows)
				return ImageIO.read(driver.getScreenshotAs(OutputType.FILE));
			else
				return null;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Boolean waitForEnable(int timeout, WebElement parentElement, BasicElement searchElement)
	{
		return waitForEnable( timeout,  parentElement,  searchElement.getSelector());
	}

	public Boolean waitForEnable(int timeout, WebElement parentElement, By searchElement)
	{
		for(int i=timeout; i>0; i--)
		{
			try
			{
				setDriverTimeoutTo_SECONDS(1);
				Boolean isEnable=parentElement.findElement(searchElement).isEnabled();

				if(isEnable)
				{
					setDriverTimeoutTo_SECONDS(30);
					return true;
				}

			}
			catch(Exception e){}
			pause(500);
		}
		setDriverTimeoutTo_SECONDS(30);
		return false;
	}

	public Boolean waitForEnable(int timeout, By targetElem)
	{
		for (int i = timeout; i > 0; i--)
		{
			try
			{
				setDriverTimeoutTo_SECONDS(1);
				WebElement element = driver.findElement(targetElem);

				if (element.isEnabled())
				{
					setDriverTimeoutTo_SECONDS(30);
					return true;
				}
			} catch (Exception e){}
			pause(500);
		}
		setDriverTimeoutTo_SECONDS(30);
		return false;
	}

	public Boolean isExists(int timeout, By targetElem)
    {
        boolean result = false;
        for (int i = timeout; i > 0; i--)
        {
            try
            {
                setDriverTimeoutTo_MILLISECONDS(500);
                WebElement element = driver.findElement(targetElem);
                if (element.isDisplayed())
                    result = true;
            }
            catch (Exception e)
            {
            }
			pause(500);
        }
		setDriverTimeoutTo_SECONDS(30);
        return result;
    }

	public Boolean isExists(WebElement parentElement, By targetElem)
	{
		boolean result = false;
		try
		{
			setDriverTimeoutTo_MILLISECONDS(500);
			WebElement element = parentElement.findElement(targetElem);
			if (element.isDisplayed())
				result = true;
		}
		catch (Exception e)
		{
		}
		setDriverTimeoutTo_SECONDS(30);
		return result;
	}
	public Boolean waitForEnable(int timeout, By ... by)
	{
		for(int i=timeout; i>0; i--)
		{
			try
			{
				setDriverTimeoutTo_SECONDS(1);
				WebElement element = null;
				Boolean firstIter = true;
				for (By partBy : by)
				{
					if (firstIter)
					{
						element = driver.findElement(partBy);
						firstIter = false;
					}
					else
					{
						element = element.findElement(partBy);
					}
				}
				Boolean isEnable = element.isEnabled();

				if (isEnable)
				{
					setDriverTimeoutTo_SECONDS(30);
					return true;
				}
			}
			catch(Exception e){}

			pause(500);		}

		setDriverTimeoutTo_SECONDS(30);
		return false;
	}

	public Boolean waitForNotEnable(int timeout, By searchElement)
	{
		for (int i = timeout; i > 0; i--)
		{
			try
			{
				setDriverTimeoutTo_SECONDS(1);
				if (!driver.findElement(searchElement).isEnabled())
				{
					setDriverTimeoutTo_SECONDS(30);
					return true;
				}
			}
			catch (Exception e)
			{
				setDriverTimeoutTo_SECONDS(30);
				return true;
			}
			pause(500);
		}
		setDriverTimeoutTo_SECONDS(30);
		return false;
	}

	public Boolean waitForNotEnable(int timeout, WebElement parentElement, By searchElement)
	{
		for(int i=timeout; i>0; i--)
		{
			try
			{
				setDriverTimeoutTo_SECONDS(1);
				Boolean isEnable=parentElement.findElement(searchElement).isEnabled();
				if(!isEnable)
				{
					setDriverTimeoutTo_SECONDS(30);
					return true;
				}

			}
			catch(Exception e)
			{
				setDriverTimeoutTo_SECONDS(30);
				return true;
			}
			pause(500);
		}
		setDriverTimeoutTo_SECONDS(30);
		return false;
	}

	public Boolean waitForNotEnable(int timeout,By ... by)
	{
		for (int i = timeout; i > 0; i--)
		{
			try
			{
				setDriverTimeoutTo_SECONDS(1);
				WebElement element = null;
				Boolean firstIter = true;
				for (By partBy : by)
				{
					if (firstIter)
					{
						element = driver.findElement(partBy);
						firstIter = false;
					}
					else
					{
						element = element.findElement(partBy);
					}
				}
				Boolean isEnable = element.isEnabled();

				if (!isEnable)
				{
					setDriverTimeoutTo_SECONDS(30);
					return true;
				}
			}
			catch (Exception e)
			{
				setDriverTimeoutTo_SECONDS(30);
				return true;
			}
			pause(500);
		}
		setDriverTimeoutTo_SECONDS(30);
		return false;
	}

	/**
	 * Left swipe example<br>
	 * swipeHorizontal(WebElement,0.99,0.01,0.5,3000);<br>
	 * Right swipe example<br>
	 * swipeHorizontal(WebElement,0.01,0.99,0.5,3000);<br>
	 */
	public static void swipeHorizontal(WebElement element, double startPercentage, double finalPercentage, double anchorPercentage, int duration) throws Exception
	{
		String direction;
		if(startPercentage>finalPercentage) {
			direction="right";
			repo.Write("Horizontal swipe RIGHT to LEFT", Result.INFO);
		}
		else{
			direction="left";
			repo.Write("Horizontal swipe LEFT to RIGHT", Result.INFO);
		}
		Dimension size = element.getSize();
		Point pos = element.getLocation();
		int anchor = (int) (pos.y + size.height * anchorPercentage);
		int startPoint = (int) (size.width * startPercentage);
		int endPoint = (int) (size.width * finalPercentage);
		Duration dur = Duration.ofMillis(duration);

		if(Is_iOS())
		{
			swipelJSE(element,direction,duration);
			return;
		}
		new TouchAction(driver).press(startPoint, anchor).waitAction(dur).moveTo(endPoint, anchor).release().perform();


		//In documentation they mention moveTo coordinates are relative to initial ones, but thats not happening. When it does we need to use the function below
		//new TouchAction(driver).press(startPoint, anchor).waitAction(duration).moveTo(endPoint-startPoint,0).release().perform();
	}

	/**
	 * Down to Up swipe example<br>
	 * swipeVertical(WebElement,0.9,0.1,0.5,3000);<br>
	 * Up to Down swipe example<br>
	 * swipeVertical(WebElement,0.1,0.9,0.5,3000);<br>
	 */
	public static void swipeVertical(WebElement element, double startPercentage, double finalPercentage, double anchorPercentage, int duration) throws Exception
	{
		Dimension deviceSize = driver.manage().window().getSize();
		Point pos = element.getLocation();
		Dimension elementSize = element.getSize();
		int anchor = (int) (pos.x + deviceSize.width * anchorPercentage);
		int startPoint;
		int endPoint;
		String direction;
		if (startPercentage < finalPercentage)
		{
			repo.Write("Vertical swipe UP to DOWN", Result.INFO);
			//Swipe UP to DOWN
			direction="up";
			startPoint = (int) (pos.y + (elementSize.height * startPercentage));
			endPoint = (int) (pos.y + elementSize.height - (elementSize.height - elementSize.height * finalPercentage));
		}
		else
		{
			repo.Write("Vertical swipe DOWN to UP", Result.INFO);
			//Swipe DOWN to UP
			direction="down";
			startPoint = (int) (pos.y + elementSize.height - (elementSize.height * finalPercentage));
			endPoint = (int) (pos.y + (elementSize.height - elementSize.height * startPercentage));
		}
		if(Is_iOS())
		{
			swipelJSE(element,direction,duration);
			return;
		}

		Duration dur = Duration.ofMillis(duration);
		new TouchAction(driver).press(anchor, startPoint).waitAction(dur).moveTo(anchor, endPoint).release().perform();

		//In documentation they mention moveTo coordinates are relative to initial ones, but thats not happening. When it does we need to use the function below
		//new TouchAction(driver).press(anchor, startPoint).waitAction(duration).moveTo(0,endPoint-startPoint).release().perform();
	}

	public static void swipelJSE(WebElement element, String direction, int duration) throws Exception
	{
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			HashMap swipeObject = new HashMap();
			swipeObject.put("element", element);
			swipeObject.put("duration", duration);
			swipeObject.put("direction", direction);
			js.executeScript("mobile: scroll", swipeObject);
		}catch(Exception e){}
	}


	public static void longClick(WebElement element, int durationMiliseconds)
	{
		Dimension elementSize = element.getSize();
		Duration dur = Duration.ofMillis(durationMiliseconds);
		new TouchAction(driver).press(element,elementSize.width/2, elementSize.height/2).waitAction(dur).release().perform();
	}

	public static OSType getOperatingSystemType()
	{
		String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
		if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
			return OSType.MacOS;
		} else if (OS.indexOf("win") >= 0) {
			return OSType.Windows;
		} else {
			return OSType.Other;
		}
	}

	/**
	 * Up swipe example<br>
	 * swipeVertical(By,0.9,0.1,0.5,3000);<br>
	 * Down swipe example<br>
	 * swipeVertical(By,0.1,0.9,0.5,3000);<br>
	 */
	public static void swipeVertical(By by, double startPercentage, double finalPercentage, double anchorPercentage, int duration) throws Exception
	{
		swipeVertical(driver.findElement(by), startPercentage, finalPercentage, anchorPercentage, duration);
	}

	/**
	 * Right to Left swipe example<br>
	 * swipeHorizontal(By,0.99,0.01,0.5,3000);<br>
	 * Left to Right swipe example<br>
	 * swipeHorizontal(By,0.01,0.99,0.5,3000);<br>
	 */
	public static void swipeHorizontal(By by, double startPercentage, double finalPercentage, double anchorPercentage, int duration) throws Exception
	{
		swipeHorizontal(driver.findElement(by), startPercentage, finalPercentage, anchorPercentage, duration);
	}

	public void dragAndDrop(WebElement from, WebElement to, int duration) throws Exception
	{
		Duration dur= Duration.ofMillis(duration);
		new TouchAction(driver).press(from).waitAction(dur).moveTo(to, 0, 0).release().perform();
	}
	public void dragAndDrop(WebElement from, Point toPoint, int duration) throws Exception
	{
		Duration dur= Duration.ofMillis(duration);
		new TouchAction(driver).press(from).waitAction(dur).moveTo(toPoint.x,toPoint.y).release().perform();
	}

    public boolean isElementInContainer(WebElement element, WebElement container) {
		Rectangle containerRect;
		Rectangle elementRect;
		if(Is_iOS()) {
			containerRect = container.getRect();
			elementRect = element.getRect();
		}
		else
		{
			containerRect=new Rectangle(container.getLocation().x,container.getLocation().y,container.getSize().height,container.getSize().width);
			elementRect=new Rectangle(element.getLocation().x,element.getLocation().y,element.getSize().height,element.getSize().width);
		}
		return rectContainsRect(elementRect,containerRect);
    }

	public boolean isElementsOverlaps(WebElement element1, WebElement element2) {
		Rectangle elem1Rect;
		Rectangle elem2Rect;
		if(Is_iOS()) {
			elem1Rect = element1.getRect();
			elem2Rect = element2.getRect();
		}
		else
		{
			elem1Rect=new Rectangle(element1.getLocation().x,element1.getLocation().y,element1.getSize().height,element1.getSize().width);
			elem2Rect=new Rectangle(element2.getLocation().x,element2.getLocation().y,element2.getSize().height,element2.getSize().width);
		}
	return overlapsRectangles(elem1Rect,elem2Rect);
	}

	private Boolean rectContainsRect(Rectangle includedRect, Rectangle containerRect)
	{
		return  includedRect.x>=containerRect.x &&
				includedRect.y>=containerRect.y &&
				(containerRect.x+containerRect.width)>=(includedRect.x+includedRect.width) &&
				(containerRect.y+containerRect.height)>=(includedRect.y+includedRect.height);
	}

	private Boolean overlapsRectangles(Rectangle r1, Rectangle r2) {
		double difInd=0.7;
		return 	!(r2.y>r1.y+r1.height*difInd || r1.y>r2.y+r2.height*difInd) ||
				(r2.x>r1.x+r1.width || r1.x>r2.x+r2.width);
	}


}