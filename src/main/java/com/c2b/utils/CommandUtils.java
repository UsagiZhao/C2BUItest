package com.c2b.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by 58 on 2017/10/9.
 */
public class CommandUtils {
    public static String getDevicesID(String cmd) throws Exception {
        String devicesID=null;
        Runtime runtime = Runtime.getRuntime();
        Process p = runtime.exec(cmd);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String readLine = bufferedReader.readLine();
        while (readLine != null) {
            if(readLine.indexOf("\t") !=-1){
                String [] res=readLine.split("\t");
                devicesID=res[0];
            }
            readLine = bufferedReader.readLine();
        }
        if(bufferedReader!=null){
            bufferedReader.close();
        }
        p.destroy();
        return  devicesID;
    }
    public static String getDevicesVersion(String cmd) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process p = runtime.exec(cmd);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String devicesVersion = bufferedReader.readLine();
        if(bufferedReader!=null){
            bufferedReader.close();
        }
        p.destroy();
        return  devicesVersion;
    }
/*       public static void main(String[] args) throws Exception {
        CommandUtils commandUtils = new CommandUtils();
        String ID=commandUtils.getDevicesVersion("adb shell getprop ro.build.version.release");
        System.out.println(ID);
    }*/
}
