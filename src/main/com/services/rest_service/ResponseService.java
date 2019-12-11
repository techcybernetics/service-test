package com.services.rest_service;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.services.utils.ExtentReportsUtil;
import com.services.utils.JsonUtil;
import com.services.utils.XmlUtil;
import io.restassured.response.Response;
import io.restassured.path.xml.element.Node;
import org.testng.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ResponseService {
    private static String responseStr=null;
    private static Response response=null;
    private static XmlUtil responseXml=null;
    private static JsonUtil responseJson=null;
    public static Map<String, String> batchKeys=new HashMap<>();

    public ResponseService(){}

    public ResponseService(String response){
        this.responseStr=response;
        responseXml().readXMLFromString(response);
    }

    public ResponseService(Response response){
        this.response=response;
        responseStr=response.getBody().asString();
    }

    public ResponseService(XMLUtil responseXML){
        this.responseXml=responseXML;
        responseStr=responseXML.getXMLString();
    }

    public ResponseService(JsonUtil responseJson){
    this.responseJson=responseJson;
    responseStr=responseJson.getJsonString();
    }

    private XmlUtil responseXml(){
        if(responseXml==null){
            responseXml=new XmlUtil();
        }
        return responseXml;
    }

    public Validate validate() {
        return new Validate();
    }
    public String getStatusCode(){
        String value=null;
        if(response.getContentType().contains("xml")){
            value=response.xmlPath().getString("//statusCode");
        }
        else if(response.getContentType().contains("json")){
        value=response.jsonPath().getString("responseStatus.statusCode");
        }
        return value;
    }

    public  String getBatchIdvalue(String templateName,String path){
    String Bat_id=null;
    Bat_id=getBatchIDData(templateName,path);
    return Bat_id;
    }

    public static String getBatchIDData(String tempName, String batchIdpath){
        String stat=null;
        String desc=null;
        String batchId=null;
        if(response.getContentType().contains("json")){
            stat=response.jsonPath().getString("status");
            desc=response.jsonPath().getString("description");
        }
        try {
            if(stat.contains("100") && (desc.contains("SUCCESS")) &&(tempName!=null)){
                batchId=response.jsonPath().getString("responseObject.batchId");
                File saveBatchPath=new File("."+batchIdpath+"\\batchId.txt");
                FileWriter fw;
                fw=new FileWriter(saveBatchPath,false);
                fw.write(batchId);
                fw.flush();
                fw.close();
            }
        }catch(Exception e){
            e.getMessage();
        }
        return  batchId;
    }

    public String getStatus(){
        String value=null;
        if(response.getContentType().contains("xml")){
            value=response.xmlPath().getString("//status");
        }else if(response.getContentType().contains("json")){
            value=response.jsonPath().getString("status");
        }
        return value;
    }

    public String getStatusMessage(){
        String value=null;
        Node node=null;
        boolean exists= false;
        if(response.getContentType().contains("xml")){
            value=response.xmlPath().getString("//statusMessage");
        }else if(response.getContentType().contains("json")){
            value=response.jsonPath().getString("responseStatus.statusMessage");
        }
        return value;
    }

    public String getDescriptionValue(){
        String value=null;
        Node node=null;
        boolean exists=false;
        if(response.getContentType().contains("xml")){
            value=response.xmlPath().getString("//description");
        }else if(response.getContentType().contains("json")){
            value=response.jsonPath().getString("description");
        }
        return value;
    }

    public String getErrorCode(int index){
        String value=null;
        boolean exists=false;
        if(response.getContentType().contains("json")){
            exists=getNodeExists("responseStatus.error["+index+"].code");
        }else if(response.getContentType().contains("xml")){
            exists=getNodeExists("//error["+index+"].code");
        }
        if(exists) {
            if (response.getContentType().contains("xml")) {
                value = response.xmlPath().getString("//error[" + index + "]/code");
            } else if (response.getContentType().contains("json")) {
                value = response.jsonPath().getString("responseStatus.error[" + index + "].code");
            }
        }    else
            {
                System.out.println("Response Error Code Not Found");
            }
        return value;
    }

    public String getErrorMessage(int index){
        int index= -1;
        String errorCode=null;
        if(response.getContentType().contains("json")){
            index=0;
        }else if(response.getContentType().contains("xml")){
            index=1;
        }
        errorCode=getErrorCode(index);
        return errorCode;
    }

    public String getErrorMessage(int index){
        String value=null;
        boolean exists=false;
        if(response.getContentType().contains("json")){
            exists=getNodeExists("//responseStatus.error["+index+"].message");
        }else if(response.getContentType().contains("xml")){
            exists=getNodeExists("//error["+index+"]/message");
        }
        if(exists){
            if(response.getContentType().contains("xml")){
                value=response.xmlPath().getString("//error["+index+"]/message");
            }else if(response.getContentType().contains("json")){
                value=response.jsonPath().getString("responseStatus.error["+index+"].message");
            }
        }
        else{
            System.out.println("Response Error Message Node not found");
        }
        return value;
    }

    public String getErrorMessage(){
        int index= -1;
        String errorMessage=null;
        if(response.getContentType().contains("json")){
            index=0;
        }else if(response.getContentType().contains("xml")){
            index=1;
        }
        errorMessage=getErrorMessage(index);
        return errorMessage;
    }

    public String getNodeValue(String path){
        String value=null;
        if(response.getContentType().contains("xml")){
            value=response.xmlPath().getString(path);
        }else if(response.getContentType().contains("json")){
            value=response.jsonPath().getString(path);
        }
        return value;
    }

    public void savePDFResponse(String templateName,String saveFilepath)throws Exception{
        String objPath="responseObject";
        String respObj=getNodeValue(objPath);
        File downloadPath=new File("."+saveFilepath+templateName+".pdf");
        byte[]pdfAsBytes= Base64.getDecoder().decode(respObj);
        FileOutputStream os;
        os=new FileOutputStream(downloadPath,false);
        os.write(pdfAsBytes);
        os.flush();
        os.close();
    }

    public void saveXmlResponse(String saveXmlPath) throws Exception{
        String respObj;
        respObj=getNodeValue("responseObject");
        File saveXml=new File(".\\"+saveXmlPath+"templateData.txt");
        FileWriter fw;
        fw=new FileWriter(saveXml,false);
        fw.write(respObj);
        fw.flush();
        fw.close();
    }

public boolean getNodeExists(String path){
        boolean nodeExists=false;
        if(response.getContentType().contains("xml")){
            Node node=null;
            node = response.xmlPath().getNode(path);
            if(node!=null){
                nodeExists=true;
            }
        }else if(response.getContentType().contains("json")){
            Object node=null;
            node=response.jsonPath().get(path);
            if(node!=null){
                nodeExists=true;
            }
        }
        return  nodeExists;
}

public class Validate
    {
        public Validate(){

    }
    private ExtentTest reportTest(){ return ExtentReportsUtil.reportTest();
    }

    public Validate statusCode(int expectedCode){
            int actualCode= Integer.valueOf(getStatusCode());
            try{
                Assert.assertEquals(expectedCode,actualCode);
                reportTest().log(Status.PASS,"Response service validate Status Code:" +response.getStatusCode());
            }catch(AssertionError e){
                TestScriptBase.testFailed= true;
                reportTest().log(Status.FAIL,"Response service status validate status code"+expectedCode+"didn't match with actual"+response.getStatusCode());
            TestScriptBase.testFailed=true;
            }
            return this;
    }
    public Validate statusMessage(String expectedStatus){
            String actualStatus=null;
            actualStatus=getStatusMessage();
            try{
                boolean contains=actualStatus.contains(expectedStatus);
                Assert.assertEquals(true, contains);
                reportTest().log(Status.PASS,"Response Service validate status message: "+expectedStatus);
            } catch(AssertionError e){
                TestScriptBase.testFailed=true;
                reportTest().log(Status.FAIL, "Response Service Validate status message expected: "+expectedStatus+"didn't match with actual" +actualStatus);
                if(expectedStatus.contains("SUCCESS")){
                    reportTest().log(Status.FAIL,"Response service validate ErrorCode:" +getErrorCode());
                    reportTest().log(Status.FAIL,"Response Servide validate ErrorMessage: "+getErrorMessage());
                }
                e.printStackTrace();
        }
            return this;
    }

    public Validate description(String description){
            String actualStatus=null;
            actualStatus=getDescriptionValue();
            try{
                Assert.assertEquals(description, actualStatus);
                reportTest().log(Status.PASS,"Response Service validate status Message: " +description);
            }catch (AssertionError e){
                TestScriptBase.testFailed=true;
                reportTest().log(Status.FAIL,"Resposnse Service Validate Status Message Expected: " +description+ "didn't match with actual "+actualStatus);
                if(description.contains("SUCCESS")){
                    reportTest().log(Status.FAIL,"Response Service validate ErrorCode: "+getErrorCode());
                    reportTest().log(Status.FAIL,"Response Service validate ErrorMessage: "+getErrorMessage());
                }
                e.printStackTrace();
            }
            return this;
    }

    public Validate status(String expectedStatus){
            String actualStatus=getStatus();
            try{
                Assert.assertEquals(expectedStatus,actualStatus);
                reportTest().log(Status.PASS,"Response Service Validate Status: "+expectedStatus);
            }catch(AssertionError e){
                TestScriptBase.testFailed=true;
                reportTest().log(Status.FAIL,"Response Service validate status code: "+expectedStatus+"didn't match with actual status"+actualStatus);
                TestScriptBase.testFailed=true;
            }
            return this;
    }

    public Validate nodeValue(String path, boolean expectedValue){
            boolean actualValue=false;
            actualValue=getNodeExists(path);
            if(expectedValue) {
                try {
                    Assert.assertEquals(true, actualValue);
                    reportTest().log(Status.PASS, "Response Service Validate Response Node Exists: " + path);
                } catch (AssertionError e) {
                    reportTest().log(Status.FAIL, "Response Service validate Response Node: " + path + ":" + actualValue);
                    TestScriptBase.testFailed = true;
                }
            }
                else{
                    try{
                    Assert.assertEquals(false, actualValue);
                    reportTest().log(Status.PASS,"Response Service validate Response Node doesn't exist: "+path);
                    }catch(AssertionError e){
                        reportTest().log(Status.FAIL,"Response Service validate Response Node doesn't exist: "+ path+ ":"+actualValue);
                        TestScriptBase.testFailed=true;

                }
            }
                return this;
    }

    public Validate  nodeValue(String path, String expectedValue){
            String actualValue=null;
            if(getNodeExists(path)) {
                try {
                    actualValue = getNodeValue(path);
                    Assert.assertEquals(expectedValue, actualValue);
                    reportTest().log(Status.PASS, "Response service Validate Response value by path: " + path + "Value : " + actualValue);
                } catch (AssertionError e) {
                    reportTest().log(Status.FAIL, "Expected Service Validate response value by path: " + path + " Value : " + expectedValue + "didn't match with actual" + actualValue);
                    TestScriptBase.testFailed = true;
                }
            }
                else{
                    reportTest().log(Status.FAIL,"Response Service Response Node can't validate node path: " +path+" Value : " + expectedValue+ "didn't match with actual " + actualValue);
                    TestScriptBase.testFailed=true;
                }
                return this;
            }

            public Validate nodeValueContains(String path,String expectedValue){
            String actualValue=null;
            boolean contains=false;
            if(expectedValue.toLowerCase().contains("true")||expectedValue.toLowerCase().contains("false")){
                nodeValue(path, exists);
            }else
            {
                if(getNodeExists(path)){
                    try{
                        actualValue=getNodeValue(path);
                        contains=actualValue.contains(expectedValue);
                        Assert.assertEquals(true,contains);
                        reportTest().log(Status.PASS,"Response Service validate Response value by path : "+path+ " Value : " +actualValue);
                    }catch (AssertionError e){
                        reportTest().log(Status.FAIL,"Expected Service validate Response value by path : "+path+ " Value : "+expectedValue+ "didn't match with actual "+actualValue);
                        TestScriptBase.testFailed=true;
                    }
                }else{
                    reportTest().log(Status.FAIL,"Response Service Response Node can't validate node path " +path+ " doesn't exist");
                    TestScriptBase.testFailed=true;
                }
            }
            return this;
    }
    public Validate nodeExists(String path){
            boolean exists=false;
            try{
                exists=getNodeExists(path);
                Assert.assertEquals(true, exists);
                reportTest().log(Status.PASS," Response Service Validate Response Node Exists: " + path +" : " + exists);
            }catch (AssertionError e){
                reportTest().log(Status.FAIL,"Response Service Validate response Node Exists: " + path + ": " + exists);
                TestScriptBase.testFailed=true;
            }
            return this;
    }

    public Validate errorCode(String expectedCode){
            String actualCode=null;
            boolean exists=false;
            actualCode=getErrorCode();
            if(actualCode!=null){
                try{
                    Assert.assertEquals(expectedCode,actualCode);
                    reportTest().log(Status.PASS,"Response Service Validate Error Code : "+actualCode);
                }catch (AssertionError e){
                    reportTest().log(Status.FAIL,"Response Service Validate Expected Error Code : " +expectedCode + "didn't match with actual: " +actualCode);
                    TestScriptBase.testFailed=true;
                }
            }else{
                reportTest().log(Status.FAIL, "Response Service Error Code Not Found");
                TestScriptBase.testFailed=true;
            }
            return  this;
    }

    public Validate errorMessage(String expectedCode){
            String actualCode=null;
            boolean exists=false;
            actualCode=getErrorMessage();
            if(actualCode!=null){
                try{
                    Assert.assertEquals(expectedCode,actualCode);
                    reportTest().log(Status.PASS,"Response Service Validate Error Message: " +actualCode);
                }catch (AssertionError e){
                    reportTest().log(Status.FAIL,"Response Service Validate Expected Error Message : " +expectedCode+ "didn't match with actual: "+actualCode);
                    TestScriptBase.testFailed=true;
                }
            }else{
                reportTest().log(Status.FAIL,"Response Service Error Message Not Found");
                TestScriptBase.testFailed=true;
            }
            return this;
    }
    }

}
