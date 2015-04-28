/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lib;

/**
 *
 * @author Massa
 */
import java.io.File;
import java.sql.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataBaseManagement {
    public Statement state;
    public ResultSet result;
    public Connection con;
    private String host;
    private String databasename;
    private String username;
    private String password;
    private XMLReader xml;
   
    
   
    public Connection setConnetction() {
        try {
            xml = new XMLReader();
            xml.readXML();
            Class.forName("com.mysql.jdbc.Driver");
            host =xml.getHost();
            databasename = xml.getDatabasename();
            username = xml.getUsername();
            password = xml.getPassword();
           // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","massa@123");
             con = DriverManager.getConnection(host+databasename,username,password);
        } catch (Exception e) {
            
        } 
        return con;
    }
    
    public ResultSet getResult(String query,Connection con){
        try{
            state = con.createStatement();
            result = state.executeQuery(query);
        
        }catch(Exception e){
        
        }
        return result;
    }
    
    
}

