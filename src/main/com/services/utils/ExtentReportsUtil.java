package com.services.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.internal.TestResult;


public class ExtentReportsUtil {
    public static ExtentReports extent=null;
    private static EnviornmentUtility env= new EnviornmentUtility();
    public static ExtentTest classTest=null;
    private static ExtentTest reportTest=null;
    private static String featureName=null;
    private static String testName=null;

    public ExtentReportsUtil(){

    }

    public static ExtentTest feature(){
        if (classTest==null){
            classTest=ExtentReportsUtil.report().createTest("ClassTest");
        }
        return classTest;
    }

    public static ExtentTest reportTest(){
        if(reportTest== null){
            reportTest=ExtentReportsUtil.report().createTest("Test");

        }
        return reportTest;
    }

    public synchronized static void init(){
        String reportName= EnviornmentUtility.getReportStatus()? "ExtentReports" : "ExtentReports_" + DateAndTimeUtil.getNewTimeStamp();
        extent =new ExtentReports();
        System.out.println("Extent Reports Initialized");
        System.out.println(System.getProperty("user.dir") + "/ExtentReports/" + reportName+ ".html");
        ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir") + "/ExtentReports/" + reportName+ ".html");
        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);
        htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/Library/extent-config.xml");
    }

    public synchronized static ExtentReports report(){
        if(extent==null){
            init();
        }
        return extent;
    }

    public static ExtentTest reportTest(String testName){
        return report().createTest(testName);
    }

    public static void initiateFeature(String feature){
        System.out.println(feature);
        if(classTest==null && featureName== null){
            classTest=ExtentReportsUtil.reportTest(feature);
            ExtentReportsUtil.featureName=feature;
        }
        else if(classTest!=null && !featureName.contentEquals(feature)){
            finalizeReportTest();
            classTest=ExtentReportsUtil.reportTest(feature);
            ExtentReportsUtil.featureName=feature;
        }
        else if(classTest== null && feature!=null){
            classTest = ExtentReportsUtil.reportTest(feature);
            ExtentReportsUtil.featureName=feature;

        }
    }

    public static void initiateTest(String testName){
        System.out.println(testName);
        if(ExtentReportsUtil.testName==null){
            reportTest =ExtentReportsUtil.classTest.createNode(testName);
            ExtentReportsUtil.testName=testName;
            ExtentReportsUtil.report().flush();
        }
        else if(ExtentReportsUtil.testName!=null && !testName.contentEquals(ExtentReportsUtil.testName)){
            reportTest=ExtentReportsUtil.classTest.createNode(testName);
            ExtentReportsUtil.testName=testName;
            ExtentReportsUtil.report().flush();
        }
        else{
            reportTest=ExtentReportsUtil.classTest.createNode(testName);
            ExtentReportsUtil.report().flush();
        }
    }

    public synchronized static void finalizeReportTest(){
        System.out.println("**********finalizeReportTest**********");
        //TestResult result=new TestResult();
        ExtentReportsUtil.report().flush();
        featureName=null;
        testName=null;
        classTest=null;
        reportTest=null;
        System.out.println("After Cucumber class");
    }

    public static String embedTableData(HashMap<String,String>listElement){
        StringBuilder reportDom=new StringBuilder();
        reportDom= reportDom.append("<br><table style>=\"bordered table-result;background-color: #1A1C1E;border: 10px solid;\"><thead><tr><th><font color=\"white\">Type</font></th><th><font color=\"white\">Value</font></th></tr></thead><tbody>");
        Set<Map.Entry<String,String>> e=listElement.entrySet();
        for(Map.Entry entry:e){
            reportDom=reportDom.append("<tr><td><b><font color=\"#ffff00\">"+ entry.getKey().toString()+"</font color></b></td><td><font color=\"#ffff00\">"+ entry.getValue()+ "</font color></td></tr>");
        }
        reportDom=reportDom.append("</tbody></table>");
        return reportDom.toString();
    }
    public synchronized static void initializeReportClass(){
        classTest=null;
    }
}
