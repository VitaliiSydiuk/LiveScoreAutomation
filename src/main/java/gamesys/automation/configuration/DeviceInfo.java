package gamesys.automation.configuration;

public class DeviceInfo
{
    public String Name;
    public String OsVersion;
    //IsLocal is used if we want to run on a real local device
    public boolean IsLocal;
    //Reinstall set to false is used if we want to run installed on a real local device
    public boolean Reinstall;
    public boolean IsPrivate;
    public boolean IsPhoneOnly;
    public boolean IsTabletOnly;
    public int DurationTestCount;

}
