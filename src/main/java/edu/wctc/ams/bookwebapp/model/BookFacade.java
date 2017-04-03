/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.model;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aschindler1
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    private AuthorFacade authService;
    
    @PersistenceContext(unitName = "edu.wctc.ams_bookWebApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
 
    public int deleteById(String id){
        Integer iId = Integer.parseInt(id);
        String jpql = "DELETE FROM Book b WHERE b.bookId = :id";
        Query q = this.getEntityManager().createQuery(jpql);
        q.setParameter("id", iId);
        return q.executeUpdate();  
    }
    public void addNew(String name, String isbn, int authorID){
        Book b = new Book();
        b.setTitle(name);
        b.setIsbn(isbn);
        Author idSet = findAnAuthor(authorID);
        b.setAuthorID(idSet);
        this.create(b);
    }
    public void update(String id, String title, String isbn, int authorID){
//        Author a = this.find(Integer.parseInt(id));
//        a.setAuthorName(name);
//        this.edit(a);
        
        Integer iId = Integer.parseInt(id);
        String jpql = "UPDATE Book b SET b.title = :title, b.isbn = :isbn, b.authorID = :aId WHERE b.bookId = :id";
        Query q = this.getEntityManager().createQuery(jpql);
        q.setParameter("id", iId);
        q.setParameter("title", title);
        q.setParameter("isbn", isbn);
        Author idSet = findAnAuthor(authorID);
        q.setParameter("aId", idSet);
        q.executeUpdate();     
    }
    
    private Author findAnAuthor(int authorID){
        Author idSet = null;
        authService = new AuthorFacade();
        List<Author> authorList = authService.findAll();
        for(Author a: authorList){
            if(a.getAuthorID() == authorID){
                idSet = a;
            }
        }
        return idSet;
    }
}
