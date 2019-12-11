package com.services.utils;

import com.aventstack.extentreports.ExtentTest;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnviornmentUtility {
    private static Properties prop=new Properties();
    private static InputStream input=null;
    private static String filePath=System.getProperty("user.dir")+"/Library/ennviornment.properties";
    private static String env=null;
    private static Boolean setReport=null;
    private static ExtentTest reportTest=null;
    private static EnviornmentUtility enviornmentUtility=new EnviornmentUtility();
    public static EnviornmentUtility getInstance(){
        return enviornmentUtility;
    }
    public EnviornmentUtility(){
        initiate();
    }
    private static void initiate(){
        try{
            input=new FileInputStream(filePath);
            prop.load(input);
            env=prop.getProperty("env");
            setReport=Boolean.valueOf(prop.getProperty("overwriteReport"));
        }
        catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        finally {
            if(input!=null){
                try{
                    input.close();
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public String getEnviornment(){
        return env;
    }
    public static String getApigeeEndPoint(){
        initiate();
        env=prop.getProperty(env);
        String endPoint=prop.getProperty("apigee."+env+".endpoint");
        System.out.println("apigee."+env+".endpoint : "+endPoint);
return endPoint;
    }
    public static Boolean getReportStatus(){
        return  setReport;

    }
}
