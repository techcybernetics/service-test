package com.services.rest_service;

import com.aventstack.extentreports.ExtentTest;
import com.google.gson.internal.$Gson$Preconditions;
import com.services.utils.ExtentReportsUtil;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.internal.TestResult;

import java.util.Random;

public class TestScriptBase {
    public static Boolean testFailed=null;
    public static ExtentTest test=null;
    private static ResponseService responseSvc=null;
    private static ExtentTest classTest=null;
    private static ExtentTest reportTest=null;
    private static RestServiceController restServiceController=null;
    private static DBServiceController dbService=null;
    private static ITestResult testResult= Reporter.getCurrentTestResult();
    public static TestName name=new TestName();
    public static String methodName=null;
    public static boolean isShellMode=false;
    private static String existingTestName="";
    static int count =1;
    private static String featureScenario=null;
    private Class className=this.getClass();

    public ResponseService responseService(String response){
        if(responseSvc==null) {
            responseSvc=new ResponseService(response);
        }
        return responseSvc;
    }

    public static DBServiceController dbService(){
        if(dbService==null){
            dbService=new DBServiceController();
        }
        return dbService;
    }

    public RestServiceController smartForm(){
        return RestServiceController.getInstance();
    }

    public RestServiceController digitalDocs(){
        return RestServiceController.getInstance();
    }

    public SoapServiceController dashBoardService(){
        return SaopServiceController.getInstance();
    }

    public RestServiceController formsUtility(){
        return RestServiceController.getInstance();
    }

    public void assert All(String name){
        if(testFailed){
            fail(name+" : Test failed");
        }
    }
    public synchronized static ExtentTest reportTest(){ return ExtentReportsUtil.reportTest();}
    public static void delayFor(int sec){
        try{
            Thread.sleep(sec*1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public synchronized  static void initializeReportClass(){
        classTest=null;
    }

    public synchronized  void initiateReportTest(String testName){
        testFailed=false;
        System.out.println("****************initializing report for " + testName + "*****************");
        ExtentReportsUtil.initiateTest(testName);
    }
    public static ExtentTest classTest(){
        String className=null;
        System.out.println();
        if(classTest==null){
            className="";
            System.out.println("Class Name *************** : "+ className);
            String temp[]=className.split("\\.");
            className="Feature : "+ temp[temp.length-1];
            classTest=ExtentReportsUtil.report().createTest(className);
        }
        return classTest;
    }

    public synchronized ExtentTest initializeReportFeature(String feature){
        ExtentReportsUtil.initiateFeature(feature);
        return null;
    }
    @Before
    public void setUpBeforeMethod() throws Exception{
        if(!isShellMode){
            testFailed=false;
            String classname=this.getClass().getCanonicalName();
            ExtentReportsUtil.initiateFeature(classname);
        }
    }

    @After
    public void tearDownAfterMethod()throws Exception{
        if(!isShellMode){
            ExtentReportsUtil.finalizeReportTest();
        }
    }

    public static void finalizeReportTest(){
        System.out.println("***************finalizeReportTest****************");
        TestResult result=new TestResult();
        ExtentReportsUtil.report().removeTest(classTest);
        ExtentReportsUtil.report().flush();
        System.out.println("After Cucumber class"+count);
    }

    @BeforeClass
    public static void setupBeforeClass(){
        System.out.println("setUpBeforeClass");
        if(!isShellMode) {

        }
    }
    @AfterClass
    public static void tearDownAfterClass(){
        System.out.println("setUpAfterClass");
        if(!isShellMode){
            ExtentReportsUtil.report().flush();
            System.out.println("tearDownAfterClass");
        }
    }

    public String getRandomNumber(int length){
        Random rnd=new Random();
        char[] digits=new char[length];
        digit[0]=(char)(rnd.nextInt(9)+'1');
        for(int i=1;i<digits.length;i++){
            digits[i]=(char)(rnd.nextInt(10)+'0');
        }
        return new String(digits);
    }
}
