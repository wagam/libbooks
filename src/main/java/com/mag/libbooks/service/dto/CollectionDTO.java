package com.mag.libbooks.service.dto;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Collection entity.
 */
public class CollectionDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Integer booksNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Integer getBooksNumber() {
        return booksNumber;
    }

    public void setBooksNumber(Integer booksNumber) {
        this.booksNumber = booksNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollectionDTO collectionDTO = (CollectionDTO) o;
        if(collectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollectionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", booksNumber=" + getBooksNumber() +
            "}";
    }
}
