package com.mag.libbooks.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "first_year_edition")
    private Integer firstYearEdition;

    @Column(name = "has_next")
    private Boolean hasNext;

    @Column(name = "summary")
    private String summary;

    @Lob
    @Column(name = "cover")
    private byte[] cover;

    @Column(name = "cover_content_type")
    private String coverContentType;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @OneToOne
    @JoinColumn(unique = true)
    private BookMetadatas metadatas;

    @ManyToOne
    private Collection collection;

    @ManyToOne
    private Author author;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFirstYearEdition() {
        return firstYearEdition;
    }

    public Book firstYearEdition(Integer firstYearEdition) {
        this.firstYearEdition = firstYearEdition;
        return this;
    }

    public void setFirstYearEdition(Integer firstYearEdition) {
        this.firstYearEdition = firstYearEdition;
    }

    public Boolean isHasNext() {
        return hasNext;
    }

    public Book hasNext(Boolean hasNext) {
        this.hasNext = hasNext;
        return this;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getSummary() {
        return summary;
    }

    public Book summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public byte[] getCover() {
        return cover;
    }

    public Book cover(byte[] cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public Book coverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
        return this;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public Book numberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public BookMetadatas getMetadatas() {
        return metadatas;
    }

    public Book metadatas(BookMetadatas bookMetadatas) {
        this.metadatas = bookMetadatas;
        return this;
    }

    public void setMetadatas(BookMetadatas bookMetadatas) {
        this.metadatas = bookMetadatas;
    }


    public Collection getCollection() {
        return collection;
    }

    public Book collection(Collection collection) {
        this.collection = collection;
        return this;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Author getAuthor() {
        return author;
    }

    public Book author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
        Book book = (Book) o;
        if (book.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstYearEdition=" + getFirstYearEdition() +
            ", hasNext='" + isHasNext() + "'" +
            ", summary='" + getSummary() + "'" +
            ", cover='" + getCover() + "'" +
            ", coverContentType='" + getCoverContentType() + "'" +
            ", numberOfPages=" + getNumberOfPages() +
            "}";
    }
}
