/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aschindler1
 */
import java.sql.SQLException;import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author aschindler1
 */
public class MySQLDbAccessor {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    public List<Map<String,Object>> findRecordsFor(String tableName, int maxRecords) throws SQLException{
       String sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
       
       List<Map<String,Object>> results = new ArrayList<>();
       
       stmt = conn.createStatement();
       rs = stmt.executeQuery(sql);
       
       return results;
    }
    
    //consider creating a custom exception
    public void openConnection(String driverClass, String url, 
            String username, String password) throws ClassNotFoundException, SQLException{
        //needs validation
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, username, password);
        
    }
    
    public void closeConnection() throws SQLException{
        if(conn != null){
            conn.close();
        }
    }
}
