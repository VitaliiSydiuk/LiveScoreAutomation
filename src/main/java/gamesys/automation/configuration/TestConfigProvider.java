package gamesys.automation.configuration;


import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import gamesys.automation.common.ResultValidator;
import gamesys.automation.common.TestEnvPaths;
import gamesys.automation.enums.MobileOS;
import gamesys.automation.tools.StringUtils;

public class TestConfigProvider
{
    private static TestConfig instance = null;
    private static String configFile = TestEnvPaths.TestEnvMainConfigFile;

    public static boolean Is_iOS()
    {
        return getConfig().TargetOS == MobileOS.iOS;
    }

    public static TestConfig getConfig()
    {
        if (instance == null)
        {
            instance = getConfigFromFile();
        }
        return instance;
    }

    private static TestConfig getConfigFromFile()
    {
        TestConfig result = null;
        try
        {
            String content = StringUtils.linerize(new String(Files.readAllBytes(Paths.get(configFile))));
            result = new ObjectMapper().readValue(content, TestConfig.class);
        }
        catch (Exception ex)
        {
            ResultValidator.fail("Unable to parse json config file " + configFile + ". Error: " + ex.getMessage());
        }
        return result;
    }
}
