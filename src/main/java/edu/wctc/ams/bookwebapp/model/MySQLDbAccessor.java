/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import java.util.StringJoiner;
/**
 *
 * @author aschindler1
 */
public class MySQLDbAccessor implements DbAccessor {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    public void deleteById(String tableName, String columnName, Object id) throws SQLException{
        String sId;
        Integer intId, updateCount;
        
        if(id instanceof String){
            sId = id.toString();
        }
        else if(id instanceof Integer){
            intId = (Integer)id;
        }
        //SQL statement
        String sql = "";
        sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = " + id;
        //create statement
        stmt = conn.createStatement();
        updateCount = stmt.executeUpdate(sql);  
        sql = "ALTER TABLE " + tableName + " AUTO_INCREMENT = " + 1;
        stmt.executeUpdate(sql);  
    }
    public void updateRecord(String tableName, List<String> colNames, List colValues, String pk, Object idpk) throws SQLException{
        //                        int recId = 200; // pick an existing id from your search results
//                        sql = "UPDATE actor SET first_name = 'Bob', last_name = 'Smith'"
//                                + " WHERE actor_id = " + recId;
        String stringID;
        Integer intID;
        
        if(idpk instanceof String){
            stringID = idpk.toString();
        }
        else if(idpk instanceof Integer){
            intID = (Integer)idpk;
        }
        String sql = "UPDATE " + tableName + " SET";
        StringJoiner sj = new StringJoiner(",","","");
        for(int i = 0; i < colNames.size(); i++){
            sj.add(" " + colNames.get(i) + " = '" + colValues.get(i) + "'");
        }
        sql += sj.toString();
        sql += " WHERE " + pk + " = " + idpk;
        System.out.println(sql);
        
        stmt = conn.createStatement();
        int updateCount = stmt.executeUpdate(sql);  

    }
    public void insertRecord(String tableName, List<String> colNames, List colValues) throws SQLException {
        String sql = "INSERT INTO " + tableName + " ";
        StringJoiner sj = new StringJoiner(",","(",")");
        //match up lists togethers
        for(String col: colNames){
            sj.add(col);
        }
        sql += sj.toString();
        //construct sql statement
        sj = new StringJoiner(",","(",")");
        sql += " VALUES ";
        
        for(Object val: colNames){
            sj.add("?");
        }    
        sql += sj.toString();
        
        PreparedStatement ps = conn.prepareStatement(sql);
        
        for(int i = 0; i<colValues.size(); i++){
            ps.setObject(i+1, colValues.get(i));
        } 
        int recsUpdated = ps.executeUpdate();
    }
    
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
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        
        
        
        List<String> parameters = new ArrayList<String>();
        parameters.add("author_name");
        parameters.add("date_added");
        
        List<Object> attributes = new ArrayList<>();
        attributes.add("Hajime Kanzaka");
        attributes.add("2017-02-16");
    
        db.insertRecord("author", parameters, attributes);
        
        //db.updateRecord("author", parameters, attributes, "author_ID", 4);
        
        List<Map<String,Object>> records = db.findRecordsFor("author", 50);
        
        for(Map<String, Object> record: records){
            System.out.println(record);
        }
       // System.out.println("Deleting....");
       // db.deleteById("author", "author_id", 5);
        
       // records = db.findRecordsFor("author", 50);
        
//        for(Map<String, Object> record: records){
//            System.out.println(record);
//        }
//        
        db.closeConnection();
    }
}
