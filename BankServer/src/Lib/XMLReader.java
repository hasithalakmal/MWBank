/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lib;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 *
 * @author Massa
 */
public class XMLReader {
    private static String host;
    private static String databasename;
    private static String username;
    private static String password;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XMLReader xml=new XMLReader();
        xml.readXML();
        System.out.println(xml.getHost());
        System.out.println(xml.getDatabasename());
        System.out.println(xml.getUsername());
        System.out.println(xml.getPassword());
    }
    
    public void readXML(){
        try {

           File fXmlFile = new File(".\\src\\Lib\\Config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

           // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("dbconfig");

         //   System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                  /*  System.out.println("Databse number : " + eElement.getAttribute("id"));
                    System.out.println("host : " + eElement.getElementsByTagName("host").item(0).getTextContent());
                    System.out.println("databasename : " + eElement.getElementsByTagName("databasename").item(0).getTextContent());
                    System.out.println("username : " + eElement.getElementsByTagName("username").item(0).getTextContent());
                    System.out.println("password : " + eElement.getElementsByTagName("password").item(0).getTextContent());
                  */  
                    host = eElement.getElementsByTagName("host").item(0).getTextContent();
                    databasename = eElement.getElementsByTagName("databasename").item(0).getTextContent();
                    username = eElement.getElementsByTagName("username").item(0).getTextContent();
                    password = eElement.getElementsByTagName("password").item(0).getTextContent();
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getHost(){
        return host;
    }
    
    public String getDatabasename(){
        return databasename;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
}
