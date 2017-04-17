package edu.wctc.ams.bookwebapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aschindler1
 */
@Entity
@Table(name = "author")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
    , @NamedQuery(name = "Author.findByAuthorID", query = "SELECT a FROM Author a WHERE a.authorID = :authorID")
    , @NamedQuery(name = "Author.findByAuthorName", query = "SELECT a FROM Author a WHERE a.authorName = :authorName")
    , @NamedQuery(name = "Author.findByDateAdded", query = "SELECT a FROM Author a WHERE a.dateAdded = :dateAdded")})
public class Author implements Serializable {

    @OneToMany(mappedBy = "authorID")
    private Set<Book> bookSet;
    /*@OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL) // this is the big change!!!
    private Set bookSet; */

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "author_ID")
    private Integer authorID;
    @Size(max = 80)
    @Column(name = "author_name")
    private String authorName;
    @Column(name = "date_added")
    @Temporal(TemporalType.DATE)
    private Date dateAdded;

    public Author() {
    }

    public Author(Integer authorID) {
        this.authorID = authorID;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Integer authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authorID != null ? authorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Author)) {
            return false;
        }
        Author other = (Author) object;
        if ((this.authorID == null && other.authorID != null) || (this.authorID != null && !this.authorID.equals(other.authorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.ams.bookwebapp.model.Author[ authorID=" + authorID + " ]";
    }

    @XmlTransient
    public Set<Book> getBookSet() {
        return bookSet;
    }

    public void setBookSet(Set<Book> bookSet) {
        this.bookSet = bookSet;
    }
    
}
