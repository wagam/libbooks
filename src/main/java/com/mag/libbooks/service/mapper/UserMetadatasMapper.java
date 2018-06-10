package com.mag.libbooks.service.mapper;

import com.mag.libbooks.domain.*;
import com.mag.libbooks.service.dto.UserMetadatasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserMetadatas and its DTO UserMetadatasDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserMetadatasMapper extends EntityMapper<UserMetadatasDTO, UserMetadatas> {

    @Mapping(source = "user.id", target = "userId")
    UserMetadatasDTO toDto(UserMetadatas userMetadatas);

    @Mapping(source = "userId", target = "user")
    UserMetadatas toEntity(UserMetadatasDTO userMetadatasDTO);

    default UserMetadatas fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserMetadatas userMetadatas = new UserMetadatas();
        userMetadatas.setId(id);
        return userMetadatas;
    }
}
