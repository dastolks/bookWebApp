/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aschindler1
 */
public class AuthorDao implements AuthorDaoInterface {
    private DbAccessor db;
    private String driverClass;
    private String url;
    private String username;
    private String password;

    public AuthorDao(DbAccessor db, String driverClass, String url, String username, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
    }
 
    @Override
    public List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException{
        
        List<Author> authorList = new ArrayList<>();
        db.openConnection(driverClass, url, username, password);
        
        List<Map<String,Object>> rawData = db.findRecordsFor(tableName, maxRecords);
        
        db.closeConnection();
        
        for(Map<String,Object> recData : rawData){
            Author author = new Author();
            
            author.setAuthorId((Integer)recData.get("author_ID"));
            
            Object objName = recData.get("author_name");
            String name = objName != null ? objName.toString() : "";
            author.setAuthorName(name);
            
            Object objDate = recData.get("date_added");
            Date date = (objDate != null) ? (Date)objDate: null;
            author.setDateAdded(date);
            
            authorList.add(author);
        }
        
        return authorList;
    } 
    
    @Override
    public String getDriverClass() {
        return driverClass;
    }

    @Override
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public DbAccessor getDb() {
        return db;
    }

    @Override
    public void setDb(DbAccessor db) {
        this.db = db;
    }
    public static void main(String[] args) throws Exception {
        AuthorDaoInterface dao = new AuthorDao(new MySQLDbAccessor(),
        "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        
        List<Author> authors = dao.getAuthorList("author", 50);
          
        for(Author record: authors){
            System.out.println(record);
        }
    }
}
