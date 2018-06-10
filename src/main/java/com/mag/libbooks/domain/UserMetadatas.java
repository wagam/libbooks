package com.mag.libbooks.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserMetadatas.
 */
@Entity
@Table(name = "user_metadatas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "usermetadatas")
public class UserMetadatas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number_of_read_books")
    private Integer numberOfReadBooks;

    @Column(name = "number_of_comments")
    private Integer numberOfComments;

    @Column(name = "number_of_likes")
    private Integer numberOfLikes;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfReadBooks() {
        return numberOfReadBooks;
    }

    public UserMetadatas numberOfReadBooks(Integer numberOfReadBooks) {
        this.numberOfReadBooks = numberOfReadBooks;
        return this;
    }

    public void setNumberOfReadBooks(Integer numberOfReadBooks) {
        this.numberOfReadBooks = numberOfReadBooks;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public UserMetadatas numberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
        return this;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public UserMetadatas numberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
        return this;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public User getUser() {
        return user;
    }

    public UserMetadatas user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        UserMetadatas userMetadatas = (UserMetadatas) o;
        if (userMetadatas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMetadatas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMetadatas{" +
            "id=" + getId() +
            ", numberOfReadBooks=" + getNumberOfReadBooks() +
            ", numberOfComments=" + getNumberOfComments() +
            ", numberOfLikes=" + getNumberOfLikes() +
            "}";
    }
}
