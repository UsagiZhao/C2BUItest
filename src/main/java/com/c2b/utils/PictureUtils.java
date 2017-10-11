package com.c2b.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.*;
import org.openqa.selenium.OutputType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Driver;

import static org.testng.internal.Utils.copyFile;

/**
 * Created by 58 on 2017/9/10.
 */

public class PictureUtils {
    //截图并保存到项目中
    public static String ScreenShot(AppiumDriver driver, String path){
        String filename="/ScreenshotFile"+System.currentTimeMillis()+".jpeg";
        String fullpathname=path+filename;
        File file=driver.getScreenshotAs(OutputType.FILE);
        copyFile(file,new File(fullpathname));
        return fullpathname;
    }

    //读取本地图片
    public static BufferedImage getImageFromFile(String path) {
        File f = new File(path);
        BufferedImage img = null;

        try {
            img = ImageIO.read(f);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return img;
    }

    /**按照像素点进行对比(图片分辨率必须保持一致)
     * @param myImage 第一张图片
     * @param otherImage 第二张图片
     * @param percent 期望一致的百分比
     * @return
     */
    public static boolean sameAs(BufferedImage myImage, BufferedImage otherImage, double percent) {
        if(otherImage.getWidth() != myImage.getWidth()) {
            return false;
        } else if(otherImage.getHeight() != myImage.getHeight()) {
            return false;
        } else {
            int width = myImage.getWidth();
            int height = myImage.getHeight();
            int numDiffPixels = 0;

            for(int y = 0; y < height; ++y) {
                for(int x = 0; x < width; ++x) {
                    if(myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
                        ++numDiffPixels;
                    }
                }
            }
            double numberPixels = (double)(height * width);
            double diffPercent = (double)numDiffPixels / numberPixels;
            System.out.println("两张图片相似度为："+(1-diffPercent));
            boolean res=(percent <=1.0D-diffPercent);
            return  res;
        }
    }
}
