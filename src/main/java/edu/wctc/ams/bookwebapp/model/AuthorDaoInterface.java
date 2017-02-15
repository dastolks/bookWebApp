/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author aschindler1
 */
public interface AuthorDaoInterface {

    List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException;

    DbAccessor getDb();

    String getDriverClass();

    String getPassword();

    String getUrl();

    String getUsername();

    void setDb(DbAccessor db);

    void setDriverClass(String driverClass);

    void setPassword(String password);

    void setUrl(String url);

    void setUsername(String username);
    
}
