package gamesys.automation.common;

import gamesys.automation.enums.Result;
import gamesys.automation.tools.WebDriverUtils;
import org.testng.SkipException;

import static gamesys.automation.common.DriverProvider.driver;
import static gamesys.automation.common.DriverProvider.repo;

public class ResultValidator
{
    public static void areEqual(Double expected, Double actual, String message)
    {
        isTrue(expected == actual, message);
    }

    public static void areEqual(String expected, String actual, String message)
    {
        isTrue(expected.trim().equalsIgnoreCase(actual.trim()), message);
    }

    public static void ignore(boolean condition, String message)
    {
        if(condition)
            throw new SkipException(message);
    }

    public static void ignore(String message)
    {
        ignore(true, message);
    }

    public static void info(String message)
    {
        repo.Write(message, Result.INFO);
    }


    public static void fail(String message)
    {
        isTrue(false, message);
    }

    public static void isTrue(boolean expected, String message)
    {
        if (!expected)
        {
            repo.Write("#############################################################################", Result.INFO);
            repo.Write("#############################################################################", Result.INFO);
            repo.Write(message, Result.FAIL, new WebDriverUtils().getScreenshot());
            repo.Write("#############################################################################", Result.INFO);
            repo.Write("#############################################################################", Result.INFO);
            assert (false);
        }
        else
        {
            repo.Write(message, Result.PASS);
        }
    }
}
