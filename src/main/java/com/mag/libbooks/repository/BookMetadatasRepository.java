package com.mag.libbooks.repository;

import com.mag.libbooks.domain.BookMetadatas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BookMetadatas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookMetadatasRepository extends JpaRepository<BookMetadatas, Long> {

}
