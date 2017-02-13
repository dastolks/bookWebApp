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
public class AuthorDao {
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
 
    public List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException{
        List<Author> authorList = new ArrayList<>();
        db.openConnection(driverClass, url, username, password);
        
        List<Map<String,Object>> rawData = db.findRecordsFor(tableName, maxRecords);
        for(Map<String,Object> recData : rawData){
            Author author = new Author();
            author.setAuthorId((Integer)recData.get("author_id"));
            Object objName = recData.get("author_name");
            String name = objName != null ? objName.toString() : "";
            author.setAuthorName(name);
            Object objDate = recData.get("author_date");
            String date = objDate != null ? objDate.toString() : "";
            author.setAuthorName(name);
            author.setDateAdded((Date)recData.get("author_date"));
            authorList.add(author);
        }
        
        return authorList;
    } 
    
    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public DbAccessor getDb() {
        return db;
    }

    public void setDb(DbAccessor db) {
        this.db = db;
    }
}
