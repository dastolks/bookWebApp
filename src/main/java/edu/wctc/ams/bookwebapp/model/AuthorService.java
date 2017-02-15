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

/**
 *
 * @author aschindler1
 */
public class AuthorService implements DatabaseService{
    private AuthorDaoInterface dao;

    public AuthorService(AuthorDaoInterface dao) {
        this.dao = dao;
    }

    
    
    public AuthorDaoInterface getDao() {
        return dao;
    }

    public void setDao(AuthorDaoInterface dao) {
        this.dao = dao;
    }
    
    
    public final List<Author> createList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException{
        return dao.getAuthorList(tableName, maxRecords);
    }
    /*public static void main(String[] args) {
        AuthorService as = new AuthorService();
        List<Author> howdy = as.createList();
        for(Author a: howdy){
            System.out.println(a.getAuthorName());
        }
    }*/
    public static void main(String[] args) {
        AuthorService as = new AuthorService(
            new AuthorDao(new MySQLDbAccessor(),
            "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin")
        );
        try{
            List<Author> authors = as.createList("author", 50);

            for(Author record: authors){
                System.out.println(record);
            }
        }
        catch(Exception e){
            
        }
    }
}
