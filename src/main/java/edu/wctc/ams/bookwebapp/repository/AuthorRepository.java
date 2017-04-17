/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ams.bookwebapp.repository;

import edu.wctc.ams.bookwebapp.entity.Author;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dad
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable{
       
    @Query("SELECT a.authorName FROM Author a")
    public Object[] findAllWithNameOnly();
}
