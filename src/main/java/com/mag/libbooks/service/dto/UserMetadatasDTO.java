package com.mag.libbooks.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserMetadatas entity.
 */
public class UserMetadatasDTO implements Serializable {

    private Long id;

    private Integer numberOfReadBooks;

    private Integer numberOfComments;

    private Integer numberOfLikes;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfReadBooks() {
        return numberOfReadBooks;
    }

    public void setNumberOfReadBooks(Integer numberOfReadBooks) {
        this.numberOfReadBooks = numberOfReadBooks;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserMetadatasDTO userMetadatasDTO = (UserMetadatasDTO) o;
        if(userMetadatasDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMetadatasDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMetadatasDTO{" +
            "id=" + getId() +
            ", numberOfReadBooks=" + getNumberOfReadBooks() +
            ", numberOfComments=" + getNumberOfComments() +
            ", numberOfLikes=" + getNumberOfLikes() +
            "}";
    }
}
