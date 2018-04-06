package gamesys.automation.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gamesys.automation.common.TestEnvPaths;
import gamesys.automation.enums.Environment;
import gamesys.automation.enums.MobileOS;
import gamesys.automation.tools.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestConfig
{
    public Environment Environment;
    public MobileOS TargetOS;
    public DeviceInfo Device;


    private UserInfo user = null;
    private String userConfigFile = TestEnvPaths.UserConfigFile;

    public UserInfo getUser() {
        if (user == null)
            user = getUserByEnv(Environment);
        return user;
    }

    private UserInfo getUserByEnv(Environment env)
    {
        try
        {
            String content = StringUtils.linerize(new String(Files.readAllBytes(Paths.get(userConfigFile))));
            JsonNode user = new ObjectMapper().readTree(content).get(env.name()).get("User");
            return new UserInfo(user.get("Name").asText(), user.get("Password").asText());
        }
        catch (Exception ex)
        {
            System.out.println("Unable to parse json users file " + userConfigFile + ". Error: " + ex.getMessage());
            return null;
        }
    }
}