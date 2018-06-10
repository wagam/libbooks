package com.mag.libbooks.service.mapper;

import com.mag.libbooks.domain.*;
import com.mag.libbooks.service.dto.BookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Book and its DTO BookDTO.
 */
@Mapper(componentModel = "spring", uses = {BookMetadatasMapper.class, CollectionMapper.class, AuthorMapper.class})
public interface BookMapper extends EntityMapper<BookDTO, Book> {

    @Mapping(source = "metadatas.id", target = "metadatasId")
    @Mapping(source = "collection.id", target = "collectionId")
    @Mapping(source = "author.id", target = "authorId")
    BookDTO toDto(Book book);

    @Mapping(source = "metadatasId", target = "metadatas")
    @Mapping(source = "collectionId", target = "collection")
    @Mapping(source = "authorId", target = "author")
    Book toEntity(BookDTO bookDTO);

    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
