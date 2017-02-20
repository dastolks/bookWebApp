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
import javax.swing.JOptionPane;

/**
 *
 * @author aschindler1
 */
public class AuthorService implements DatabaseService{
    private AuthorDaoInterface dao;

    public AuthorService(AuthorDaoInterface dao) {
        this.dao = dao;
    }
    
    @Override
    public AuthorDaoInterface getDao() {
        return dao;
    }
    @Override
    public void setDao(AuthorDaoInterface dao) {
        this.dao = dao;
    }
    
    @Override
    public final List<Author> createList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException{
        return dao.getAuthorList(tableName, maxRecords);
    }
    
    @Override
    public void deleteFromList(String tableName, String tablePK, Object pk) throws SQLException, Exception {
        dao.deleteFromAuthorList(tableName, tablePK, pk);
    }

    @Override
    public void insertNew(String tableName, List<String> colNames, List colValues) throws SQLException, ClassNotFoundException {
        dao.insertNewAuthor(tableName, colNames, colValues);
    }

    @Override
    public void updateRecord(String tableName, List<String> colNames, List colValues, String pk, Object idpk) throws SQLException, ClassNotFoundException {
        dao.updateRecord(tableName, colNames, colValues, pk, idpk);
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
            as.deleteFromList("author", "author_ID", 4);
            
            authors = as.createList("author", 50);

            for(Author record: authors){
                System.out.println(record);
            }
            
            List<String> parameters = new ArrayList<String>();
            parameters.add("author_name");
            parameters.add("date_added");
        
            List<Object> attributes = new ArrayList<>();
            attributes.add("Hajime Kanzaka");
            attributes.add("2017-02-16");
            
            as.insertNew("author", parameters, attributes);
            
            authors = as.createList("author", 50);

            for(Author record: authors){
                System.out.println(record);
            }
            parameters = new ArrayList<String>();
            parameters.add("author_name");
            parameters.add("date_added");
        
            attributes = new ArrayList<>();
            attributes.add("Hajime Kanzaaaaaaaaaaaaaka");
            attributes.add("2017-02-16");    
            as.updateRecord("author", parameters, attributes, "author_ID", 4);
            
            authors = as.createList("author", 50);

            for(Author record: authors){
                System.out.println(record);
            }
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

 
}
