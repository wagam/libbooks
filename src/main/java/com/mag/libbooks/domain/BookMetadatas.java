package com.mag.libbooks.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BookMetadatas.
 */
@Entity
@Table(name = "book_metadatas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bookmetadatas")
public class BookMetadatas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number_of_comments")
    private Integer numberOfComments;

    @Column(name = "number_of_likes")
    private Integer numberOfLikes;

    @Column(name = "notation")
    private Integer notation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public BookMetadatas numberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
        return this;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public BookMetadatas numberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
        return this;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Integer getNotation() {
        return notation;
    }

    public BookMetadatas notation(Integer notation) {
        this.notation = notation;
        return this;
    }

    public void setNotation(Integer notation) {
        this.notation = notation;
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
        BookMetadatas bookMetadatas = (BookMetadatas) o;
        if (bookMetadatas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookMetadatas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookMetadatas{" +
            "id=" + getId() +
            ", numberOfComments=" + getNumberOfComments() +
            ", numberOfLikes=" + getNumberOfLikes() +
            ", notation=" + getNotation() +
            "}";
    }
}
