package com.mag.libbooks.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.mag.libbooks.domain.enumeration.BookStatus;

/**
 * A UserBook.
 */
@Entity
@Table(name = "user_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userbook")
public class UserBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookStatus status;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "is_liked")
    private Boolean isLiked;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book books;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookStatus getStatus() {
        return status;
    }

    public UserBook status(BookStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public UserBook comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isIsLiked() {
        return isLiked;
    }

    public UserBook isLiked(Boolean isLiked) {
        this.isLiked = isLiked;
        return this;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public User getUser() {
        return user;
    }

    public UserBook user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBooks() {
        return books;
    }

    public UserBook books(Book book) {
        this.books = book;
        return this;
    }

    public void setBooks(Book book) {
        this.books = book;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserBook userBook = (UserBook) o;
        if (userBook.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userBook.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserBook{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", isLiked='" + isIsLiked() + "'" +
            "}";
    }
}
