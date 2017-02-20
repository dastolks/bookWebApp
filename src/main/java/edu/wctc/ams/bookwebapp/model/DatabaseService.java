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
 * @author Alec
 */
public interface DatabaseService {

    AuthorDaoInterface getDao();

    void setDao(AuthorDaoInterface dao);

    List<Author> createList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException;
    
    void deleteFromList(String tableName, String tablePK, Object pk) throws SQLException, Exception;
    
    void insertNew(String tableName, List<String> colNames, List colValues) throws SQLException, ClassNotFoundException;
    
    void updateRecord(String tableName, List<String> colNames, List colValues, String pk, Object idpk) throws SQLException, ClassNotFoundException;
}
