package com.mag.libbooks.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BookMetadatas entity.
 */
public class BookMetadatasDTO implements Serializable {

    private Long id;

    private Integer numberOfComments;

    private Integer numberOfLikes;

    private Integer notation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getNotation() {
        return notation;
    }

    public void setNotation(Integer notation) {
        this.notation = notation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookMetadatasDTO bookMetadatasDTO = (BookMetadatasDTO) o;
        if(bookMetadatasDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookMetadatasDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookMetadatasDTO{" +
            "id=" + getId() +
            ", numberOfComments=" + getNumberOfComments() +
            ", numberOfLikes=" + getNumberOfLikes() +
            ", notation=" + getNotation() +
            "}";
    }
}
