package gamesys.automation.tools;

import gamesys.automation.enums.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static gamesys.automation.common.DriverProvider.repo;

public class StringUtils
{
    public static Double substringDouble(String string)
    {
        repo.Write("StringUtils.substringDouble -> param: '" + string + "'", Result.INFO);
        string = string.replaceAll("[^-?0-9]+", "");
        try
        {
/*            //Pattern p = Pattern.compile("(\\d+(?:\\.)\\d+)");
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(string);
            while (m.find())
            {
                Double result = Double.parseDouble(m.group(0)) / 100;
                repo.Write("StringUtils.substringDouble -> result: " + String.valueOf(result), Result.INFO);
                return result;
             }
                */
            Double result = Double.parseDouble(string) / 100;
            repo.Write("StringUtils.substringDouble -> result: " + String.valueOf(result), Result.INFO);
            return result;
        }
        catch (Exception e)
        {
            repo.Write("StringUtils.substringDouble: unable to parse value", Result.WARNING);
        }
        return 0d;
    }

    public static String nomalizePathForCurrentOS(String path)
    {
        return path.replace("\\", File.separator).replace("/", File.separator);
    }

    public static String linerize(String sourceStr)
    {
        return new String(sourceStr.
                trim().
                replace('\t', ' ').
                replace('\r', ' ').
                replace('\n', ' ').
                replace("  ", ""));
    }

    public static List<String> removeDuplicatesFromList(List<String> list)
    {
        List<String> listResult = new ArrayList<String>();
        for (String item : list)
        {
            if (!listResult.contains(item))
            {
                listResult.add(item);
            }
        }
        return listResult;
    }
}
