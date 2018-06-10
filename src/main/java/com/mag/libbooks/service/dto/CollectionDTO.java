package com.mag.libbooks.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Collection entity.
 */
public class CollectionDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private byte[] cover;
    private String coverContentType;

    private Integer bookNumber;

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

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public Integer getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(Integer bookNumber) {
        this.bookNumber = bookNumber;
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
            ", cover='" + getCover() + "'" +
            ", bookNumber=" + getBookNumber() +
            "}";
    }
}
