/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author aschindler1
 */
public class AuthorService {
    
    public final List<Author> createList(){
        List<Author> newList = new ArrayList<Author>();
        newList.add(new Author(1, "Mark Twain", new Date()));
        newList.add(new Author(2, "Tom Clancy", new Date()));
        newList.add(new Author(3, "Sir Arthur Conan Doyle", new Date()));
        
        return newList;
    }
    
}
