package gamesys.automation;

import gamesys.automation.common.Reporter;
import gamesys.automation.enums.Result;

import java.awt.image.BufferedImage;


public class Main {
    public static void main(String[] args)
    {
        Reporter repo = new Reporter("Test report","d:/repo");
        repo.Write("dfsdfsdfsdfsdf", Result.INFO);
        repo.Write("2222222222222", Result.WARNING);
        repo.Write("33333333333333", Result.PASS);
        repo.Write("444444444444444", Result.FAIL);
        repo.Write("555555555555", Result.INFO, new BufferedImage(100,100,1));
    }
}
