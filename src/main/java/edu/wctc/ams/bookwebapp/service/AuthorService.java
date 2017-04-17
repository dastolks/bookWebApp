/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.service;

import edu.wctc.ams.bookwebapp.entity.Author;
import edu.wctc.ams.bookwebapp.repository.BookRepository;
import edu.wctc.ams.bookwebapp.repository.AuthorRepository;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dad
 */
public class AuthorService {
    private transient final Logger LOG = LoggerFactory.getLogger(AuthorService.class);

    @Inject
    private AuthorRepository authorRepo;

    @Inject
    private BookRepository bookRepo;

    public AuthorService() {
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }
    
    public List<Author> findAllEagerly() {
        List<Author> authors = authorRepo.findAll();
        for(Author a : authors) {
            a.getBookSet().size();
        }
        
        return authors;
    }
    
    /**
     * This custom method is necessary because we are using Hibernate which
     * does not load lazy relationships (in this case Books).
     * @param id
     * @return 
     */
    public Author findByIdAndFetchBooksEagerly(String id) {
        Integer authorId = new Integer(id);
        Author author = authorRepo.findOne(authorId);

        // Eagerly fetches books within a transaction
        author.getBookSet().size();
        
        return author;
    }

    public Author findById(String id) {
        return authorRepo.findOne(new Integer(id));
    }

    public void update(String idForAuthor, String nameChange){
        Author placeHold = findById(idForAuthor);
        placeHold.setAuthorName(nameChange);
        edit(placeHold);
    }
    
    public void addNew(String setName){
        Author a = new Author(0);
        a.setAuthorName(setName);
        a.setDateAdded(new Date());
        edit(a);
    }
    
    public void deleteById(String id){
        remove(findById(id));
    }
    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param author 
     */
    @Transactional
    public void remove(Author author) {
        LOG.debug("Deleting author: " + author.getAuthorName());
        authorRepo.delete(author);
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param author 
     */
    @Transactional
    public Author edit(Author author) {
        return authorRepo.saveAndFlush(author);
    }    
}
