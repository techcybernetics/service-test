package com.services.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.logging.Logger;

public class WebDriverInitiate {
    private static WebDriver driver=null;
    private static String browserName=WebDriverProperties.getBrowser();
    private static String url=WebDriverProperties.getUrl();
    private static int implicitWait=Integer.valueOf(WebDriverProperties.getImplicitWaitTime()) ;
    private static DesiredCapabilities desiredCapabilities=null;
    static Logger log=Logger.getLogger(WebDriverInitiate.class.getName());
    public static void launch()
    {
        if(browserName.contentEquals("cr")){

        }
        else if(browserName.contentEquals("ff")){

        }
    }

    public static void quitDriver(){
        if(driver!=null)
        {
            driver.quit();
        }
    }

    public static WebDriver getDriver()
    {
        if(driver==null) {
            launch();
        }return driver;
    }

    public static void chrome()
    {
        ChromeOptions options=new ChromeOptions();
        options.addArguments("no-sandbox");
        desiredCapabilities=DesiredCapabilities.chrome();
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        killChromeDriver();
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "\\Library\\chromedriver.exe") ;
    }

    public static void killChromeDriver(){
        try{
            Runtime.getRuntime().exec("taskkill /F /M /IM chromedriver.exe");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();}
    }

    public String captureScreenshot(){
        WebDriver driver=WebDriverInitiate.getDriver();
        TakesScreenshot scrShot=((TakesScreenshot)driver);
        String image="data:image/jpg;base64, "+scrShot.getScreenshotAs(OutputType.BASE64);
        return image;
    }


}
