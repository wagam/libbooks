package com.mag.libbooks.repository;

import com.mag.libbooks.domain.UserBook;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    @Query("select user_book from UserBook user_book where user_book.user.login = ?#{principal.username}")
    List<UserBook> findByUserIsCurrentUser();

}
