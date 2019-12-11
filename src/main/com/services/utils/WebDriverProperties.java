package com.services.utils;

import java.io.InputStream;
import java.util.Properties;

public class WebDriverProperties {
    private static Properties prop=new Properties();
    private static InputStream input=null;
    private static String filePath=System.getProperty("user.dir")+"/Library/webdriver.properties";
    public WebDriverProperties(){
        initiate();
    }

    private static void initiate(){

    }

    public static String getBrowser(){
        initiate();
        return prop.getProperty("BrowserName");
    }

    public static String getEnv(){

        initiate();
        return prop.getProperty("Env");
    }

    public static String getImplicitWaitTime()
    {
        initiate();
        return prop.getProperty("ImplicitWait");
    }
    public static String getUrl()
    {
        initiate();
        String env= prop.getProperty("Env");
        String url=null;
        if(getEnv().contains("uat")){
            url=prop.getProperty("uat");
        }
        else if(getEnv().contains("qa")){
            url=prop.getProperty("qa");
        }
        else if(getEnv().contains("dev")){
            url=prop.getProperty("dev");
        }
        return url;
    }
    public static String getChromeDriverPath(){
        initiate();
        return System.getProperty("user.dir")+ prop.getProperty("Chrome_Driver");
    }
}

