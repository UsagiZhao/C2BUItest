package com.glen.demo;

import com.c2b.settings.AppiumServerControl;
import com.c2b.utils.CommandUtils;
import com.c2b.utils.UIActionUtils;
import com.c2b.utils.PictureUtils;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;


public class ContactsTest {
    private final String ScreenShotPath = "Screenshot";
    private AndroidDriver driver;
    public final static BufferedImage entrypic = PictureUtils.getImageFromFile("Screenshot/1.jpeg");
    public static int widht;
    public static int height;
    AppiumServerControl appiumServerControl = new AppiumServerControl();
    public DesiredCapabilities capabilities;


    @Before
    public void setUp() throws Exception {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "zhuanzhuan.apk");
        //Set Capabilities
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", CommandUtils.getDevicesID("adb devices"));
        //设置安卓系统版本
        capabilities.setCapability("platformVersion", "4.4.2");
        //设置apk路径
        //capabilities.setCapability("app", app.getAbsolutePath());
        //设置app的主包名和主类名
        capabilities.setCapability("appPackage", "com.wuba.zhuanzhuan");
        capabilities.setCapability("appActivity", ".presentation.view.activity.LaunchActivity");
        //支持中文输入
        capabilities.setCapability("unicodeKeyboard", "True");
        capabilities.setCapability("resetKeyboard", "True");
        //不重新安装apk*/
        capabilities.setCapability("noReset", true);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        //UIActionUtils.isFileExists(ScreenShotPath);//检测文件夹是否存在
        //初始化
        //driver = appiumServerControl.setCapabilitiesAndStartServer("127.0.0.1", 4723);
        //先获取屏幕的尺寸，后续微信登录会用到。
        UIActionUtils.getScreenSize(driver);
        widht = UIActionUtils.Maxwidht;
        height = UIActionUtils.Maxheight;
    }

    @Test
    public void KongTiaoTestcase() throws Exception {
        Thread.sleep(5000);
        UIActionUtils.isSplashScreenShow(driver);
        UIActionUtils.isWXLogin(driver);//判断登录
        UIActionUtils.getC2BEntrace(driver, 6);
        driver.findElementByXPath("//android.webkit.WebView[@content-desc='c2b']/android.view.View[1]/android.widget.ListView[1]/android.view.View[1]").click();//点击空调
        driver.findElementByXPath("//android.view.View/android.view.View[1]/android.widget.ListView[1]/android.view.View[1]").click();//点击日立
        driver.findElementByXPath("//android.view.View/android.view.View[2]/android.widget.ListView[1]/android.view.View[2]").click();//点击1.5匹
        driver.findElementByXPath("//android.view.View/android.view.View[3]/android.widget.ListView[1]/android.view.View[5]").click();//点击5-8年
        driver.findElementByXPath("//android.view.View/android.view.View[4]/android.widget.ListView[1]/android.view.View[2]").click();//点击泛黄
        driver.findElementByXPath("//android.view.View/android.view.View[5]/android.widget.ListView[1]/android.view.View[2]").click();//点击有维修
        Thread.sleep(2000);
        //弹出估价详细说明，对展示的选项进行校验
        Assert.assertEquals(driver.findElementByXPath("//android.view.View/android.view.View[2]/android.view.View[2]/android.widget.ListView[1]/android.view.View[1]").getAttribute("name"), "日立");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View/android.view.View[2]/android.view.View[2]/android.widget.ListView[1]/android.view.View[2]").getAttribute("name"), "1.5匹挂机");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View/android.view.View[2]/android.view.View[2]/android.widget.ListView[1]/android.view.View[3]").getAttribute("name"), "5-8年");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View/android.view.View[2]/android.view.View[2]/android.widget.ListView[1]/android.view.View[4]").getAttribute("name"), "泛黄");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View/android.view.View[2]/android.view.View[2]/android.widget.ListView[1]/android.view.View[5]").getAttribute("name"), "有维修");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='231']").getAttribute("name"), "231");
        driver.findElementByXPath("//android.view.View[@content-desc='确认卖出']").click();//点击确认卖出
        Thread.sleep(5000);
        //进入填写信息页面对商品信息进行校验
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='日立']").getAttribute("name"), "日立");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='1.5匹挂机']").getAttribute("name"), "1.5匹挂机");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='5-8年']").getAttribute("name"), "5-8年");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='泛黄']").getAttribute("name"), "泛黄");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='有维修']").getAttribute("name"), "有维修");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='总价：¥231']").getAttribute("name"), "总价：¥231");
        //开始填写页操作
        UIActionUtils.uploadPicture(driver);//上传图片
        Thread.sleep(2000);
        driver.swipe(widht / 2, height * 8 / 9, widht / 2, height * 4 / 9, 1000);
        List<WebElement> webElementsList = driver.findElementsByClassName("android.widget.EditText");//0：用户名、1：手机号、2：城市、3：区域、4：地址
        UIActionUtils.clearText(driver, webElementsList.get(0));
        webElementsList.get(0).sendKeys("UItest");
        UIActionUtils.clearText(driver, webElementsList.get(1));
        webElementsList.get(1).sendKeys("13142223313");
        webElementsList.get(2).click();
        driver.findElementByXPath("//android.view.View[@content-desc='北京']").click();
        webElementsList.get(3).click();
        Thread.sleep(2000);
        driver.findElementByXPath("//android.view.View[@content-desc='朝阳']").click();
        UIActionUtils.clearText(driver, webElementsList.get(4));
        webElementsList.get(4).sendKeys("酒仙桥北路甲十号院");
        driver.tap(1, widht * 76 / 100, height * 95 / 100, 500);
        Thread.sleep(5000);
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='订单提交成功！']").getAttribute("name"), "订单提交成功！");
        //driver.execute();
    }

    @Test
    public void BingXiangTestcase() throws Exception {
        Thread.sleep(5000);
        UIActionUtils.isSplashScreenShow(driver);
        UIActionUtils.isWXLogin(driver);//判断登录
        UIActionUtils.getC2BEntrace(driver, 6);
        driver.findElementByXPath("//android.view.View[@content-desc='c2b']/android.widget.ListView[3]/android.view.View[2]").click();
        Assert.assertEquals(driver.findElementById("com.wuba.zhuanzhuan:id/fm").getText(), "冰箱估价");
        driver.findElementByXPath("//android.view.View[@content-desc='双门']").click();
        Thread.sleep(300);
        driver.findElementByXPath("//android.view.View[@content-desc='151-250升']").click();
        Thread.sleep(300);
        driver.findElementByXPath("//android.view.View[@content-desc='1年内']").click();
        Thread.sleep(300);
        driver.findElementByXPath("//android.view.View[@content-desc='外观无破损']").click();
        Thread.sleep(300);
        driver.findElementByXPath("//android.view.View[@content-desc='制冷正常']").click();
        Thread.sleep(300);
        driver.findElementByXPath("//android.view.View[@content-desc='确认卖出']").click();
        Thread.sleep(10000);
        //进入填写信息页面对商品信息进行校验
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='双门']").getAttribute("name"), "双门");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='151-250升']").getAttribute("name"), "151-250升");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='1年内']").getAttribute("name"), "1年内");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='外观无破损']").getAttribute("name"), "外观无破损");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='制冷正常']").getAttribute("name"), "制冷正常");
        Assert.assertEquals(driver.findElementByXPath("//android.view.View[@content-desc='总价：¥138']").getAttribute("name"), "总价：¥138");
        //开始填写页操作
        UIActionUtils.uploadPicture(driver);//上传图片
        Thread.sleep(2000);
        driver.swipe(widht / 2, height * 8 / 9, widht / 2, height * 4 / 9, 1000);
        List<WebElement> webElementsList = driver.findElementsByClassName("android.widget.EditText");//0：用户名、1：手机号、2：城市、3：区域、4：地址、6：取货日期
        UIActionUtils.clearText(driver, webElementsList.get(0));
        webElementsList.get(0).sendKeys("UItest");
        UIActionUtils.clearText(driver, webElementsList.get(1));
        webElementsList.get(1).sendKeys("13142223313");
        webElementsList.get(2).click();
        driver.findElementByXPath("//android.view.View[@content-desc='北京']").click();
        webElementsList.get(3).click();
        Thread.sleep(500);
        driver.findElementByXPath("//android.view.View[@content-desc='朝阳']").click();
        UIActionUtils.clearText(driver, webElementsList.get(4));
        webElementsList.get(4).sendKeys("酒仙桥北路甲十号院");
        webElementsList.get(5).click();
        Thread.sleep(500);
        driver.tap(1, widht * 166 / 1000, height * 251 / 1000, 500);//点击取货时间;
        driver.swipe(widht / 2, height * 8 / 9, widht / 2, height * 4 / 9, 1000);
        Assert.assertEquals(webElementsList.get(5).getText(), UIActionUtils.getPickupTime());
        Thread.sleep(10000);
    }

    @After
    public void tearDown() throws Exception {
        appiumServerControl.stopServer();
        driver.quit();
    }
}