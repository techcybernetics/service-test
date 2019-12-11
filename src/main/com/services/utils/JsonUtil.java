package com.services.utils;


import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.io.*;
import java.util.*;

public class JsonUtil {
    private ObjectMapper mapper = null;
    private BufferedReader fileReader = null;
    private JsonNode rootNode = null;
    private InputStream input = null;
    private String LastFieldName = "";
    private String path = "";
    private String m_json = "";

    public JsonUtil() {

    }

    public JsonUtil(JsonNode root) {
        rootNode = root;

    }

    public JsonUtil(String json) {
        mapper = new ObjectMapper();
        try {
            rootNode = mapper.readTree(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void load(String fileName) {
        JsonFactory jfactory = new JsonFactory();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
            String json = scanner.useDelimiter("\\A").next();
            System.out.print(json);
            mapper = new ObjectMapper();
            rootNode = mapper.readTree(json);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public void convertStringToJson(String JsonString) {
        JsonFactory jfactory = new JsonFactory();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new String(JsonString));
            String json = scanner.useDelimiter("\\A").next();
            System.out.print(json);
            mapper = new ObjectMapper();
            rootNode = mapper.readTree(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public JsonNode node(JsonNode node, String fieldName, Boolean recursive) {
        JsonNode tempNode = null;
        Iterator<String> ite = node.getFieldNames();
        ArrayList<JsonNode> nodelist = null;
        while (ite.hasNext() && tempNode == null) {
            String temp = ite.next();
            if (temp.contentEquals(fieldName)) {
                tempNode = node.get(temp);
                break;
            } else {
                if (recursive) {
                    tempNode = node.get(temp);
                    tempNode = node(tempNode, fieldName, recursive);
                }
            }
        }
        return tempNode;
    }

    public static class Path {
        public Path(String path) {

        }

        public static Boolean isArray(String path) {
            Boolean array = false;
            if (path.indexOf("[") > -1) {
                array = true;
            }
            return array;

        }

        public static String getPathNode(String path) {
            String nodePath = "";
            if (path.indexOf("[") > -1) {
                nodePath = path.substring(0, path.indexOf("["));
            } else {
                nodePath = path;
            }
            return nodePath;
        }

        public static int getArrayIndex(String path) {
            int index = 0;
            String nodePath = "";
            if (path.indexOf("[") > -1) {
                nodePath = path.substring(path.indexOf("[") + 1, path.indexOf("]"));
                index = Integer.valueOf(nodePath);
            } else {
                index = 0;
            }
            return index;
        }
    }

    private LinkedHashMap<String, Boolean> getMappedPath(String path) {
        Boolean stchild = true;
        LinkedHashMap<String, Boolean> pathList = new LinkedHashMap<>();
        Boolean atChild = false;
        int emptyCount = 0;

        String[] list = path.split("/");
        for (int i = 0; i < list.length; i++) {
            if (list[i].contentEquals("")) {
                if (i > 0) {
                    atChild = true;

                }
            } else {
                pathList.put(list[i], atChild);
                atChild = false;
            }
        }

        Iterator it = pathList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            LastFieldName = pairs.getKey().toString();
        }
        return pathList;
    }

    public JsonNode getParentOfLastNode(String path) {
        JsonNode node = null;
        Boolean stChild = true;

        LinkedHashMap<String, Boolean> pathList = getMappedPath(path);
        int count = pathList.size();
        Iterator it = pathList.entrySet().iterator();
        node = rootNode;
        int i = 1;
        while (i < count) {
            Map.Entry pairs = (Map.Entry) it.next();
            Boolean isArray = Path.isArray(pairs.getKey().toString());
            String fieldName = Path.getPathNode(pairs.getKey().toString());
            boolean recursive = (Boolean) pairs.getValue();
            if (node.isArray()) {
                int index = Path.getArrayIndex(pairs.getKey().toString());
                if (fieldName.contentEquals("") && index > 0) {
                    Iterator<JsonNode> ite = node.getElements();
                    int iteIndex = 1;
                    while (ite.hasNext()) {
                        JsonNode iteNode = ite.next();
                        if (iteIndex == index) {
                            node = iteNode;
                        }
                        iteIndex++;
                    }
                } else {
                    List<JsonNode> listNodes = node.findValues(fieldName);
                    node = listNodes.get(index - 1);

                }


            } else {
                node = node(node, fieldName, recursive);

            }
            i++;

        }
        return node;
    }

    public String modifyNodeValue (String path, String value){
        String json = "";
        this.path = path;
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = null;
        node = getParentOfLastNode(path);
        if (node != null) {
            try {
                if (node != null) {
                    ((ObjectNode) node).put(LastFieldName, value);
                    strWriter = new StringWriter();
                    mapper.writeValue(strWriter, rootNode);

                }
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m_json = strWriter.toString();
        System.out.println(m_json);
        return m_json;
    }

    public String modifyNodeValue (String path,int value){
        String json = "";
        this.path = path;
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = null;
        node = getParentOfLastNode(path);
        if (node != null) {
            try {
                if (node != null) {
                    ((ObjectNode) node).put(LastFieldName,value);
                    strWriter = new StringWriter();
                    mapper.writeValue(strWriter, rootNode);
                }
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m_json = strWriter.toString();
        return m_json;
    }
    public String modifyNodeValue (String path,long value){
        String json = "";
        this.path = path;
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = null;
        node = getParentOfLastNode(path);
        if (node != null) {
            try {
                if (node != null) {
                    ((ObjectNode) node).put(LastFieldName,value);
                    strWriter = new StringWriter();
                    mapper.writeValue(strWriter, rootNode);
                }
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m_json = strWriter.toString();
        return m_json;
    }
    public String modifyNodeValue (String path,boolean value){
        String json = "";
        this.path = path;
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = null;
        node = getParentOfLastNode(path);
        if (node != null) {
            try {
                if (node != null) {
                    ((ObjectNode) node).put(LastFieldName,value);
                    strWriter = new StringWriter();
                    mapper.writeValue(strWriter, rootNode);
                }
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m_json = strWriter.toString();
        return m_json;
    }
    public String modifyNodeValue (String path,double value){
        String json = "";
        this.path = path;
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = null;
        node = getParentOfLastNode(path);
        if (node != null) {
            try {
                if (node != null) {
                    ((ObjectNode) node).put(LastFieldName,value);
                    strWriter = new StringWriter();
                    mapper.writeValue(strWriter, rootNode);
                }
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m_json = strWriter.toString();
        return m_json;
    }

    public String modifyNodeValue (String path,ObjectNode objNode){
        String json = "";
        this.path = path;
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = null;
        node = getParentOfLastNode(path);
        if (node != null) {
            try {
                if (node != null) {
                    ((ObjectNode) node).put(LastFieldName,objNode);
                    strWriter = new StringWriter();
                    mapper.writeValue(strWriter, rootNode);
                }
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m_json = strWriter.toString();
        return m_json;
    }

    public String getNodeValue(String path){
        String value="";
        this.path=path;
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = null;
        node = getParentOfLastNode(path);
        if (node != null) {
            value=((ObjectNode) node).get(LastFieldName).getTextValue();
        }
        return value;
    }

    public String getJsonString(){
        Writer strWriter = null;
        strWriter=new StringWriter();
        try{
            mapper.writeValue(strWriter,rootNode);

        }
        catch(JsonGenerationException ex){
            ex.printStackTrace();
        }
        catch(JsonMappingException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return strWriter.toString();
    }
    public String addJsonObjectNode(String path,HashMap value) {
        String json = "";
        this.path = path;
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = null;
        node = getParentOfLastNode(path);
        if (node != null) {
            try {
                if (node != null) {
                    JsonNode jsonNode = mapper.valueToTree(value);
                    ((ObjectNode) node).put(LastFieldName, jsonNode);
                    strWriter = new StringWriter();
                    mapper.writeValue(strWriter, rootNode);
                }
            } catch (JsonGenerationException ex) {
                ex.printStackTrace();
            } catch (JsonMappingException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            m_json = strWriter.toString();

        }
        return json;
    }
    public String addArrayNodeValue(String path, ArrayList<String>value){
        for(int i=0;i<value.size();i++){
            String val=value.get(i);
            String json="";
            this.path=path;
            JsonNode node=null;
            ObjectMapper mapper=new ObjectMapper();
            Writer strWriter=null;
            node=getParentOfLastNode(path);
            if(node!=null){
                try {
                    if(node!=null){
                        ((ArrayNode)node).add(val);
                        strWriter =new StringWriter();
                        mapper.writeValue(strWriter,rootNode);
                    }
                }catch(JsonGenerationException e){
                    e.printStackTrace();
                }catch(JsonMappingException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            m_json=strWriter.toString();
        }
        return m_json;
    }

    public String addJsonArrayObjectNode(String path, HashMap value){
        String json="";
        this.path=path;
        JsonNode node=null;
        ObjectMapper mapper=new ObjectMapper();
        Writer strWriter=null;
        node=getParentOfLastNode(path);
        if(node!=null){
            try{
                if(node!=null){
                    JsonNode jsonNode=mapper.valueToTree(value);
                    ((ArrayNode)node).add(jsonNode);
                    strWriter=new StringWriter();
                    mapper.writeValue(strWriter, rootNode);
                }
            }catch(JsonGenerationException e){
                e.printStackTrace();
            }catch(JsonMappingException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        m_json=strWriter.toString();
        return json;
    }
    public String createNode(String nodeName,JsonNode parentNode){
        ObjectNode newNode=mapper.createObjectNode();
        ((ObjectNode)parentNode).put(nodeName,newNode);
        try{
            m_json=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        }catch(IOException e){
            e.printStackTrace();
        }
        return m_json;
    }
    public JsonNode getRootNode(){return rootNode;}

    public JsonNode getNode(String nodeName){
        Iterator<JsonNode> node=rootNode.getElements();
        while(node.hasNext()){
            Iterator<Map.Entry<String,JsonNode>>element=node.next().getFields();
            while(element.hasNext()){
                Map.Entry<String,JsonNode> currentNode=element.next();
                if(currentNode.getKey().equalsIgnoreCase(nodeName)){
                    return currentNode.getValue();
                }
            }
        }
        return null;
    }
    public JsonNode getNode(JsonNode parentNode,String nodeName){
        for(JsonNode root:parentNode){
            JsonNode node=root.path(nodeName);
            if(node.isMissingNode()){

            }else{
                return node;
            }
        }
        return null;
    }
    public ArrayNode createArrayNode(){
        ArrayNode arrayNode=mapper.createArrayNode();
        return  arrayNode;

    }
    public ObjectNode createNode(){
        ObjectNode node=mapper.createObjectNode();
        return node;
    }
    public String removeNode(String nodeName,JsonNode parentNode){
        ((ObjectNode)parentNode).remove(nodeName);
        try{
            m_json=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        }catch(IOException e){
            e.printStackTrace();
        }
        return m_json;
    }

    public void addField(String fieldName, String fieldValue, ObjectNode node){ node.put(fieldName,fieldValue);}

    public void addNullField(ObjectNode node,String fieldName){ node.putNull(fieldName);}

    public void addNodetoArray(ArrayNode arrNode, ObjectNode node){ arrNode.add(node);}

    public String setArrayNodeValue(JsonNode node, ArrayNode arrNode, String nodeName){
        ((ObjectNode)node).put(nodeName,arrNode);
        try{
            m_json=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        }catch(IOException e){
            e.printStackTrace();
        }
        return m_json;
    }

    public void addNodetoNode(ObjectNode parentNode, ObjectNode node, String nodeName){ parentNode.put(nodeName,node);}

    public static void main(String [] args){
        /*Util.load("I:\\DWM_Smart_Forms\\dwm_smartforms_services_automation\\TestData\\smartform_service\\CWM Digital WS\\updateBene\\Request_Template.json");
        System.out.println("Result: \n"+jUtil.getJSONString());
        try{
            JsonFactory factory=new JsonFactory();
            ObjectMapper mapper=new ObjectMapper();
            JsonNode root= mapper.readTree(new File("I:\\DWM_Smart_Forms\\dwm_smartforms_service_automation\\TestData\\smartform_service\\CWM Digital WS\\updateBene\\output.json"));
            JsonGenerator generator=factory.createJsonGenerator(new File("I:\\DWM_Smart_Forms\\dwm_smartforms_service_automation\\TestData\\smartform_service\\CWM Digital WS\\updateBene\\output.json)), JsonEncoding.UTF8);
        }*/
    }





}



