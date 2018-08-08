package com.c2b.settings;

import com.c2b.utils.CommandUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

/**
 * Created by 58 on 2017/10/9.
 */
public class AppiumServerControl {
    private AppiumDriverLocalService service;
    private AppiumServiceBuilder builder;
    private DesiredCapabilities capabilities;
    private AndroidDriver driver;

    public boolean checkIfServerIsRunnning(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }

    public AndroidDriver setCapabilitiesAndStartServer(String ip, int port) throws Exception {
        AppiumServerControl appiumServerControl = new AppiumServerControl();
        if (!appiumServerControl.checkIfServerIsRunnning(port)) {
            File classpathRoot = new File(System.getProperty("user.dir"));
            File appDir = new File(classpathRoot, "apps");
            File app = new File(appDir, "zhuanzhuan.apk");
            //Set Capabilities
            capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", CommandUtils.getDevicesID("adb devices"));
            //设置安卓系统版本
            capabilities.setCapability("platformVersion", CommandUtils.getDevicesVersion("adb shell getprop ro.build.version.release"));
            //设置apk路径
            //capabilities.setCapability("app", app.getAbsolutePath());
            //设置app的主包名和主类名
            capabilities.setCapability("appPackage", "");
            capabilities.setCapability("appActivity", "");
            //支持中文输入
            capabilities.setCapability("unicodeKeyboard", "True");
            capabilities.setCapability("resetKeyboard", "True");
            //不重新安装apk*/
            capabilities.setCapability("noReset", true);
            //Build the Appium service
            builder = new AppiumServiceBuilder();
            builder.withIPAddress(ip);
            builder.usingPort(port);
            builder.withCapabilities(capabilities);
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            //Start the server with the builder
            service = AppiumDriverLocalService.buildService(builder);
            service.start();
            //初始化
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            return driver;
        }
        return null;
    }

    public void stopServer() {
        service.stop();
    }


    public static void main(String[] args)throws Exception {
        AppiumServerControl appiumServer = new AppiumServerControl();
        int port = 4723;
        if(!appiumServer.checkIfServerIsRunnning(port)) {
            appiumServer.setCapabilitiesAndStartServer("127.0.0.1",port);
            //appiumServer.stopServer();
        } else {
            System.out.println("端口:"+ port+ "已经被占用");
        }
    }
}
