package gamesys.automation.common;

import java.awt.image.BufferedImage;
import java.io.File;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;

import gamesys.automation.enums.OSType;
import gamesys.automation.tools.WebDriverUtils;

public final class Screenshot {
    static AndroidDebugBridge bridge = null;

    public static void Initialization() {
        if (bridge == null) {
            String adbLocation = "";
            if (WebDriverUtils.getOperatingSystemType() == OSType.MacOS) {
                adbLocation = System.getenv("HOME"); //$NON-NLS-1$
                String homeFolder=System.getProperty("user.home");
                if (adbLocation != null && adbLocation.length() != 0)
                    adbLocation += File.separator + "Library/Android/sdk/platform-tools" + File.separator + "adb"; //$NON-NLS-1$
                else
                    adbLocation = homeFolder+"/Library/Android/sdk/platform-tools/adb"; //$NON-NLS-1$
            } else {
                adbLocation = System.getenv("ANDROID_HOME"); //$NON-NLS-1$
                if (adbLocation != null && adbLocation.length() != 0)
                    adbLocation += File.separator + "platform-tools" + File.separator + "adb"; //$NON-NLS-1$
                else
                    adbLocation = "adb"; //$NON-NLS-1$
            }

            AndroidDebugBridge.initIfNeeded(false);
            //AndroidDebugBridge.init(false /* debugger support */);

            bridge = AndroidDebugBridge.createBridge(adbLocation, true /* forceNewBridge */);
        }
    }

    public static BufferedImage getAdbDeviceImage(String serial) throws Exception {
        Initialization();

        IDevice target = null;
        // we can't just ask for the device list right away, as the internal thread getting
        // them from ADB may not be done getting the first list.
        // Since we don't really want getDevices() to be blocking, we wait here manually.
        int count = 0;
        while (bridge.hasInitialDeviceList() == false) {
            try {
                Thread.sleep(100);
                count++;
            } catch (InterruptedException e) {
                // pass
            }

            // let's not wait > 10 sec.
            if (count > 100) {
                System.err.println("Timeout getting device list!");
                AndroidDebugBridge.terminate();
                return null;
            }
        }

        // now get the devices
        IDevice[] devices = bridge.getDevices();

        for (IDevice d : devices) {
            if (serial.equals(d.getSerialNumber())) {
                target = d;
                break;
            }
        }

        RawImage rawImage;
        rawImage = target.getScreenshot();

        //AndroidDebugBridge.terminate();

        // device/adb not available?
        if (rawImage == null)
            return null;


        // convert raw data to an Image
        BufferedImage image = new BufferedImage(rawImage.width, rawImage.height, BufferedImage.TYPE_INT_ARGB);

        int index = 0;
        int IndexInc = rawImage.bpp >> 3;
        for (int y = 0; y < rawImage.height; y++) {
            for (int x = 0; x < rawImage.width; x++) {
                int value = rawImage.getARGB(index);
                index += IndexInc;
                image.setRGB(x, y, value);
            }
        }
        return image;
    }
}
