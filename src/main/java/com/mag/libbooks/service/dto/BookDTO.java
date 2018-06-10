package com.mag.libbooks.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Book entity.
 */
public class BookDTO implements Serializable {

    private Long id;

    private String title;

    private Integer firstYearEdition;

    private Boolean hasNext;

    private String summary;

    @Lob
    private byte[] cover;
    private String coverContentType;

    private Integer numberOfPages;

    private Long metadatasId;

    private Long collectionId;

    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFirstYearEdition() {
        return firstYearEdition;
    }

    public void setFirstYearEdition(Integer firstYearEdition) {
        this.firstYearEdition = firstYearEdition;
    }

    public Boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Long getMetadatasId() {
        return metadatasId;
    }

    public void setMetadatasId(Long bookMetadatasId) {
        this.metadatasId = bookMetadatasId;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookDTO bookDTO = (BookDTO) o;
        if(bookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstYearEdition=" + getFirstYearEdition() +
            ", hasNext='" + isHasNext() + "'" +
            ", summary='" + getSummary() + "'" +
            ", cover='" + getCover() + "'" +
            ", numberOfPages=" + getNumberOfPages() +
            "}";
    }
}
