package com.mag.libbooks.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.mag.libbooks.domain.enumeration.BookStatus;

/**
 * A DTO for the UserBook entity.
 */
public class UserBookDTO implements Serializable {

    private Long id;

    private BookStatus status;

    private String comment;

    private Boolean isLiked;

    private Long userId;

    private Long booksId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBooksId() {
        return booksId;
    }

    public void setBooksId(Long bookId) {
        this.booksId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserBookDTO userBookDTO = (UserBookDTO) o;
        if(userBookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userBookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserBookDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", isLiked='" + isIsLiked() + "'" +
            "}";
    }
}
