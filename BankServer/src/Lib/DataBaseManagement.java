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
import java.sql.*;

public class DataBaseManagement {
    public Statement state;
    public ResultSet result;
    public Connection con;
   

    public Connection setConnetction() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","massa@123");
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

