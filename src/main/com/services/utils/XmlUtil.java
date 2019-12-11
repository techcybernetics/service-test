package com.services.utils;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.StringWriter;
import java.util.*;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XmlUtil {
    public static String currDir=System.getProperty("user.Dir");
    public static String testDataPath=currDir;
    public String m_Xml=null;
    public File m_file=null;
    public String m_fileName=null;
    public Document m_Doc=null;
    public Element m_RootNode=null;

    public void readXMLFromString(String xml){
        DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder=null;
        try{
            docBuilder=docFactory.newDocumentBuilder();
            InputSource is=new InputSource(new StringReader(xml));
            m_Doc=docBuilder.parse(is);
            m_RootNode=m_Doc.getDocumentElement();
            m_Xml=getXMLString(m_Doc);

        }catch(IOException e){
            e.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }
    }
    public void readXMLFile(String xmlFile){
        DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder=null;
        m_file= new File(xmlFile);
        m_fileName =xmlFile;
        try{
            docBuilder =docFactory.newDocumentBuilder();
            m_Doc=docBuilder.parse(m_file);
            m_RootNode=m_Doc.getDocumentElement();
            m_Xml=getXMLString(m_Doc);
        }catch(IOException e){
            e.printStackTrace();
        }catch(SAXException e) {
            e.printStackTrace();
        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }
    }
    public String modifyXMl(String itemToSearch, String updateValue){
        String xml="";
        return xml;
    }
    public String modifyXMl(String xmlToModify, String itemToSearch, String updateValue){
        String xml="";
        xml= StringUtils.replace(xmlToModify,itemToSearch,updateValue);
        return xml;
    }

    public String generateRandom(int length){
        Random random=new Random();
        char[] digits=new char[length];
        digits[0]=(char)(random.nextInt(9)+'1');
        for(int i=1;i<length;i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }

    public String getXMLString(Document doc){
        String xmlDoc=null;
        try{
            DOMSource domSource=new DOMSource(doc);
            StringWriter writer= new StringWriter();
            StreamResult result=new StreamResult(writer);
            TransformerFactory tf=TransformerFactory.newInstance();
            Transformer transformer=tf.newTransformer();
            xmlDoc=writer.toString();
        }catch(TransformerException ex){
            ex.printStackTrace();
            return null;
        }
        return xmlDoc;
    }
    public void printPretty(){
        try{
            TransformerFactory transformerFactory=TransformerFactory.newInstance();
            Transformer transformer=transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            int indent=2;
            if(indent>0){
                transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",Integer.toString(indent));

            }
            Result result=new StreamResult(System.out);
            Source source=new DOMSource(m_Doc);
            transformer.transform(source,result);

        }catch(TransformerException e){
            e.printStackTrace();
        }
    }

    public Object read(File xmlFile, String expression, QName returnType){
        XPath xpath= XPathFactory.newInstance().newXPath();
        Document doc=null;
        try{
            try{
                doc=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            }catch(SAXException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }catch(ParserConfigurationException e){
                e.printStackTrace();
            }
            XPathExpression xPathExpression= xpath.compile(expression);
            return xPathExpression.evaluate(doc,returnType);
        }catch (XPathExpressionException e){
            e.printStackTrace();
            return null;
        }
    }

    public Element getRootNode(){return m_RootNode;}

    public boolean isNodeExists(String xpath){
        boolean exists=false;
        Element node = getNode(xpath);
        if(node!=null){
            exists=true;
        }else
        {
            System.out.println(xpath+": Node not found");
        }
        return exists;
    }

    public Element getNode(String xpath){
        String xmlString="";
        Element element=null;
        XMLOutputter xmlOut=new XMLOutputter();
        String xmlDoc=getXMLString(m_Doc);
        XpathReader xPather=new XpathReader(xmlDoc);
        if(m_Doc!=null){
            m_Doc.getDocumentElement().normalize();
            Object node=xPather.read(xpath,XPathConstants.NODE);
            if(node!=null){
                element=(Element)node;
            }
        }
        return element;
    }

    public NodeList getNodelist(String xpath){
        String xmlString="";
        Element element=null;
        NodeList list=null;
        XMLOutputter xmlOut=new XMLOutputter();
        String xmlDoc=getXMLString(m_Doc);
        XpathReader xPather=new XpathReader(xmlDoc);
        if(m_Doc!=null){
            m_Doc.getDocumentElement().normalize();
            list=(NodeList)xPather.read(xpath,XPathConstants.NODESET);
            System.out.println(xpath + " : " + list.getLength());
        }
        return list;
    }

    public void setNodeValueByXPath(String xPath, String value){
        Element element=null;
        element=getNode(xPath);
        if(element!=null){
            element.setTextContent(value);
            m_Doc=element.getOwnerDocument();

        }else{
            System.out.println(xPath + ": Node not found");
        }
    }
    public void addXmlNode(Node currentNode, String newNodeName){
        String newXML=null;
        System.out.println(currentNode.getNodeName());
        Node newEle=m_Doc.createElement(newNodeName);
        Node element=m_Doc.getElementsByTagName(currentNode.getNodeName()).item(0);
        element.appendChild(newEle);
        org.w3c.dom.Text textNode=m_Doc.createTextNode("");
        newEle.appendChild(textNode);
        System.out.println(getXMLString());
    }

    public void addXmlNode(Node currentNode, String newNodeName,String value){
        String newXML=null;
        System.out.println(currentNode.getNodeName());
        Element newEle=m_Doc.createElement(newNodeName);
        Node element= m_Doc.getElementsByTagName(currentNode.getNodeName()).item(0);
        element.appendChild(newEle);
        addTextNode(newEle,value);
        System.out.println(getXMLString());
    }

    public void addTextNode(Node currentNode,String value){
        String newXML=null;
        System.out.println(currentNode.getNodeName());
        org.w3c.dom.Text newEle=m_Doc.createTextNode(value);
        Node element=m_Doc.getElementsByTagName(currentNode.getNodeName()).item(0);
        currentNode.appendChild(newEle);
        System.out.println(getXMLString());
    }
    public void addTextNode(Node currentNode,int itemNo, String value){
        String newXML=null;
        System.out.println(currentNode.getNodeName());
        org.w3c.dom.Text newEle=m_Doc.createTextNode(value);
        Node element=m_Doc.getElementsByTagName(currentNode.getNodeName()).item(itemNo+1);
        System.out.println(element.getNodeName());
        currentNode.appendChild(newEle);
        System.out.println(getXMLString());
    }
    public void setNodeAttributeByXpath(String xpath, String attribute, String value){
        Element element=null;
        element =getNode(xpath);
        if(element!=null){
            element.setAttribute(attribute,value);
            m_Doc=element.getOwnerDocument();
            System.out.println(getXMLString());
        }else{
            System.out.println("Node not found");
        }
    }
    public void setRootAttribute(String attribute,String value){
        Element element=null;
        element=getRootNode();
        if(element!=null){
            element.setAttribute(attribute,value);
            m_Doc=element.getOwnerDocument();

        }
    }
    public void saveToFile(){
        try{
            Source src=new DOMSource(m_Doc);
            File file=new File(m_fileName);
            StreamResult rs=new StreamResult(new File(m_fileName));
            TransformerFactory tmf=TransformerFactory.newInstance();
            Transformer trnsfrmr=tmf.newTransformer();
            trnsfrmr.setOutputProperty(OutputKeys.INDENT,"yes");
            trnsfrmr.transform(src,rs);
        }catch (TransformerConfigurationException e){
            e.printStackTrace();
        }catch(TransformerException e){
            e.printStackTrace();
        }
    }
    public String getXMLString(){
        return getXMLString(m_Doc);
    }

    public HashMap<String,String>getChildenNodevalue(Element node){
        HashMap map=new HashMap<String,String>();
        NodeList list=node.getChildNodes();
        for(int i=0;i<list.getLength();i++){
            map.put(list.item(i).getNodeName(),list.item(i).getTextContent());
            System.out.println(list.item(i).getNodeName()+ " : " +list.item(i).getTextContent());
        }
        return map;
    }
    public static void main(String [] args)
    {}
}
