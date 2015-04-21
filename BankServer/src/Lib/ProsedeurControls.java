/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lib;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Massa
 */
public class ProsedeurControls {

    DataBaseManagement dbmc;
    Connection con;
    String query;
    ResultSet rs;

   

    public ResultSet callProc(String procedure, String parameterSet) {
        dbmc = new DataBaseManagement();
        con = (Connection) dbmc.setConnetction();
        query = "call test." + procedure + parameterSet + ";";
        rs = dbmc.getResult(query, con);
        return rs;

    }

    public ResultSet callProc(String procedure) {
        dbmc = new DataBaseManagement();
        con = (Connection) dbmc.setConnetction();
        query = "call test." + procedure + "();";
        rs = dbmc.getResult(query, con);
        return rs;

    }
  
}
