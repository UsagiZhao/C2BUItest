package com.c2b.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 58 on 2017/9/10.
 */
public class UIActionUtils {
    public static int Maxwidht;
    public static int Maxheight;
    private static boolean SplashScreen = false;

    //获取手机宽和长
    public static void getScreenSize(AndroidDriver driver) {
        Maxheight = driver.manage().window().getSize().height;
        Maxwidht = driver.manage().window().getSize().width;
    }

    //删除截图Screenshot开头的文件
    public static void deleteScreenShot(String path) {
        File[] filelist = new File(path).listFiles();
        for (int i = 0; i <= filelist.length; i++) {
            if (filelist[i].getName().startsWith("Screenshot") && filelist[i].getName().endsWith("jpeg")) {
                filelist[i].delete();
            } else {
                break;
            }
        }
    }

    // 判断文件是否存在
    public static void isFileExists(String path) throws Exception {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("//ScreenShot文件夹不存在，自动创建");
            file.mkdir();
        } else {
            System.out.println("//ScreenShot目录已存在");
        }
    }

    //安装app后首次进入app的白屏页面
    public static void isSplashScreenShow(AndroidDriver driver) throws Exception {
        getScreenSize(driver);
        try {
            driver.findElementByXPath("//android.support.v4.view.ViewPager[@resource-id='com.wuba.zhuanzhuan:id/b6o']").isDisplayed();
            SplashScreen = true;
        } catch (Exception e) {
            SplashScreen = false;
        }
        if (SplashScreen) {
            driver.swipe(Maxwidht * 6 / 7, Maxheight / 9, Maxwidht / 7, Maxheight / 9, 1000);
            Thread.sleep(1000);
            driver.swipe(Maxwidht * 6 / 7, Maxheight / 9, Maxwidht / 7, Maxheight / 9, 1000);
            Thread.sleep(1000);
            driver.swipe(Maxwidht * 6 / 7, Maxheight / 9, Maxwidht / 7, Maxheight / 9, 1000);
            Thread.sleep(1000);
            driver.findElementByXPath("//android.widget.Button[@resource-id='com.wuba.zhuanzhuan:id/asv']").click();
            Thread.sleep(12000);
        }
    }

    //微信登录检测
    public static void isWXLogin(AndroidDriver driver) throws Exception {
        List<WebElement> HomePageElements=driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.ImageView\")");
        int x=HomePageElements.size();
        HomePageElements.get(x-2).click();//点击我的
        //driver.findElementById("com.wuba.zhuanzhuan:id/axw").click();
        try {
            if (driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\").textContains(\"立即登录\")").getText().equals("立即登录")) {
                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.TextView\").textContains(\"立即登录\")").click();//ZZ的点击立即登录
                Thread.sleep(3000);
                driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").textContains(\"微信登录\")").click();//点击微信登录
                Thread.sleep(3000);
                try {
                    if (driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").textContains(\"登录\")").isDisplayed()) {
                        List<WebElement> EditTextelement = driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
                        EditTextelement.get(0).sendKeys("13260392081");
                        EditTextelement.get(1).sendKeys("listing123");
                        driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").textContains(\"登录\")").click();
                        Thread.sleep(15000);
                    }
                } catch (Exception e) {
                }
                driver.tap(1, Maxwidht * 500 / 1000, Maxheight * 664 / 1000, 500);
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            System.out.println("账号已登录");
        }
        List<WebElement> MyPageElement=driver.findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.ImageView\")");
        int y=MyPageElement.size();
        MyPageElement.get(y-5).click();
    }

    //清除输入框中的文字
    public static void clearText(AndroidDriver driver, WebElement element) {
        element.click();
        String text = element.getText();
        for (int i = 0; i < text.length(); i++) {
            driver.pressKeyCode(123);
            driver.pressKeyCode(67);
        }
    }

    //获取C2B入口
    public static void getC2BEntrace(AndroidDriver driver, int Adsnumber) throws Exception {
        for (int i = 0; i <= Adsnumber; i++) {
            driver.findElementById("com.wuba.zhuanzhuan:id/mx").click();//点击转转顶部banner
            Thread.sleep(3000);
            try {
                if (driver.findElementById("com.wuba.zhuanzhuan:id/fm").getText().equals("同城快卖")) {
                    System.out.println("进入快卖页面");
                    break;
                } else {
                    driver.findElementById("com.wuba.zhuanzhuan:id/fl").click();//点击后退
                    driver.swipe(Maxwidht * 6 / 7, Maxheight / 9, Maxwidht / 7, Maxheight / 9, 500);
                }
            } catch (Exception e) {
            }
        }
    }

    //拍摄照片和录制视频权限提示框
    public static void getCameraAuthority(AndroidDriver driver){
        try{if(driver.findElementById("com.android.packageinstaller:id/permission_message").getText().endsWith("要允许转转拍摄照片和录制视频吗？"))
            driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
        }catch (Exception e){

        }
    }

    //上传照片
    public static void uploadPicture(AndroidDriver driver)throws Exception{
        driver.findElementByXPath("//android.view.View[@resource-id='user-ensure-uploads']/android.view.View[1]/android.view.View[1]").click();//添加外壳图片
        getCameraAuthority(driver);//判断是否弹出拍照权限确认框
        Thread.sleep(2000);
        driver.tap(1, Maxwidht * 78 / 100, Maxheight * 22 / 100, 500);//选择第一张图
        driver.findElementById("com.wuba.zhuanzhuan:id/bp1").click();//点击完成关闭图片选择页面并进行图片上传
    }

    public static String getPickupTime() {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month =calendar.get(Calendar.MONTH)+1;
        int date = calendar.get(Calendar.DATE);
        String newmonth= String.format("%0"+2+"d", month);
        String result=year+"-"+newmonth+"-"+(date+1)+" 6:00-20:00 取货";
        System.out.println(result);
        return result;
       // return driver.findElementByXPath(xpath);

    }

    public static void main(String[] args) {
        getPickupTime();
    }
}