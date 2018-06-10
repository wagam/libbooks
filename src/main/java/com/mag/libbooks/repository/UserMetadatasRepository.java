package com.mag.libbooks.repository;

import com.mag.libbooks.domain.UserMetadatas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserMetadatas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMetadatasRepository extends JpaRepository<UserMetadatas, Long> {

}
