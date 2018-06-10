package com.mag.libbooks.service.mapper;

import com.mag.libbooks.domain.*;
import com.mag.libbooks.service.dto.BookMetadatasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookMetadatas and its DTO BookMetadatasDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookMetadatasMapper extends EntityMapper<BookMetadatasDTO, BookMetadatas> {



    default BookMetadatas fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookMetadatas bookMetadatas = new BookMetadatas();
        bookMetadatas.setId(id);
        return bookMetadatas;
    }
}
