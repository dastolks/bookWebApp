/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author aschindler1
 */
import java.sql.SQLException;import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author aschindler1
 */
public class MySQLDbAccessor implements DbAccessor {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    @Override
    public List<Map<String,Object>> findRecordsFor(String tableName, int maxRecords) throws SQLException{
       
        String sql = "";
        if(maxRecords > 0){
            sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        }
        else{
            sql = "SELECT * FROM " + tableName;
        }

        List<Map<String,Object>> results = new ArrayList<>();

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String,Object> record = null;
       
        while(rs.next()){
            record = new LinkedHashMap<>();
            for(int colNo = 1; colNo<(colCount+1); colNo++){
                //retrieve the name
                String colName = rsmd.getColumnName(colNo);
                //add it to the record
                record.put(colName, rs.getObject(colNo));
            }
            results.add(record);
        }
       
        return results;
    }
    
    //consider creating a custom exception
    @Override
    public void openConnection(String driverClass, String url, 
            String username, String password) throws ClassNotFoundException, SQLException{
        //needs validation
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, username, password);
        
    }
    
    @Override
    public void closeConnection() throws SQLException{
        if(conn != null){
            conn.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        DbAccessor db = new MySQLDbAccessor();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://bit.glassfish.wctc.edu:3306/sakila", "advjava", "advjava");
        
        List<Map<String,Object>> records = db.findRecordsFor("actor", 50);
        
        for(Map<String, Object> record: records){
            System.out.println(record);
        }
        db.closeConnection();
    }
}
