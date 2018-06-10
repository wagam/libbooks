package com.mag.libbooks.service.mapper;

import com.mag.libbooks.domain.*;
import com.mag.libbooks.service.dto.UserBookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserBook and its DTO UserBookDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface UserBookMapper extends EntityMapper<UserBookDTO, UserBook> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "books.id", target = "booksId")
    UserBookDTO toDto(UserBook userBook);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "booksId", target = "books")
    UserBook toEntity(UserBookDTO userBookDTO);

    default UserBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserBook userBook = new UserBook();
        userBook.setId(id);
        return userBook;
    }
}
