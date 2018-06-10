package com.mag.libbooks.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Collection.
 */
@Entity
@Table(name = "collection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collection")
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "cover")
    private byte[] cover;

    @Column(name = "cover_content_type")
    private String coverContentType;

    @Column(name = "book_number")
    private Integer bookNumber;

    @OneToMany(mappedBy = "collection")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Book> books = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Collection name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getCover() {
        return cover;
    }

    public Collection cover(byte[] cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public Collection coverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
        return this;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public Integer getBookNumber() {
        return bookNumber;
    }

    public Collection bookNumber(Integer bookNumber) {
        this.bookNumber = bookNumber;
        return this;
    }

    public void setBookNumber(Integer bookNumber) {
        this.bookNumber = bookNumber;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public Collection books(Set<Book> books) {
        this.books = books;
        return this;
    }

    public Collection addBook(Book book) {
        this.books.add(book);
        book.setCollection(this);
        return this;
    }

    public Collection removeBook(Book book) {
        this.books.remove(book);
        book.setCollection(null);
        return this;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
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
        Collection collection = (Collection) o;
        if (collection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Collection{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cover='" + getCover() + "'" +
            ", coverContentType='" + getCoverContentType() + "'" +
            ", bookNumber=" + getBookNumber() +
            "}";
    }
}
