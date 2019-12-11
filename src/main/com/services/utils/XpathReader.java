package com.services.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class XpathReader {
    private File xmlFile=null;
    private org.xml.sax.InputSource inStream=null;
    private Document xmlDocument;
    private XPath xpath;

    public XpathReader(File xmlFile){
        this.xmlFile=xmlFile;
        initObjectsFromStream();
    }
    public XpathReader(String xmlString){
        inStream=new org.xml.sax.InputSource();
        inStream.setCharacterStream(new java.io.StringReader(xmlString));
        initObjectsFromStream();
    }
    private void initObjectsFromStream(){
        try{
            xmlDocument= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inStream);
            xpath= XPathFactory.newInstance().newXPath();

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(SAXException ex){
            ex.printStackTrace();
        }
        catch(ParserConfigurationException ex){
            ex.printStackTrace();
        }

    }
    public Object read(String expression, QName returnType){
        try{
            XPathExpression xPathExpression=xpath.compile(expression);
            return xPathExpression.evaluate(xmlDocument,returnType);
        }
        catch(XPathExpressionException ex){
            ex.printStackTrace();
            return null;
        }
    }
private static void traverse(NodeList rootNode){
        for(int index=0;index<rootNode.getLength();index++){
            Node aNode=rootNode.item(index);
            if(aNode.getNodeType()==Node.ELEMENT_NODE){
                NodeList childNodes=aNode.getChildNodes();
                if(childNodes.getLength()>0){
                    aNode.getTextContent();

                }
                traverse(aNode.getChildNodes());
            }
        }
}


}
