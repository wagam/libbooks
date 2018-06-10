package com.mag.libbooks.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mag.libbooks.domain.UserBook;

import com.mag.libbooks.repository.UserBookRepository;
import com.mag.libbooks.repository.search.UserBookSearchRepository;
import com.mag.libbooks.web.rest.errors.BadRequestAlertException;
import com.mag.libbooks.web.rest.util.HeaderUtil;
import com.mag.libbooks.service.dto.UserBookDTO;
import com.mag.libbooks.service.mapper.UserBookMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UserBook.
 */
@RestController
@RequestMapping("/api")
public class UserBookResource {

    private final Logger log = LoggerFactory.getLogger(UserBookResource.class);

    private static final String ENTITY_NAME = "userBook";

    private final UserBookRepository userBookRepository;

    private final UserBookMapper userBookMapper;

    private final UserBookSearchRepository userBookSearchRepository;

    public UserBookResource(UserBookRepository userBookRepository, UserBookMapper userBookMapper, UserBookSearchRepository userBookSearchRepository) {
        this.userBookRepository = userBookRepository;
        this.userBookMapper = userBookMapper;
        this.userBookSearchRepository = userBookSearchRepository;
    }

    /**
     * POST  /user-books : Create a new userBook.
     *
     * @param userBookDTO the userBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userBookDTO, or with status 400 (Bad Request) if the userBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-books")
    @Timed
    public ResponseEntity<UserBookDTO> createUserBook(@RequestBody UserBookDTO userBookDTO) throws URISyntaxException {
        log.debug("REST request to save UserBook : {}", userBookDTO);
        if (userBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new userBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBook userBook = userBookMapper.toEntity(userBookDTO);
        userBook = userBookRepository.save(userBook);
        UserBookDTO result = userBookMapper.toDto(userBook);
        userBookSearchRepository.save(userBook);
        return ResponseEntity.created(new URI("/api/user-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-books : Updates an existing userBook.
     *
     * @param userBookDTO the userBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userBookDTO,
     * or with status 400 (Bad Request) if the userBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the userBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-books")
    @Timed
    public ResponseEntity<UserBookDTO> updateUserBook(@RequestBody UserBookDTO userBookDTO) throws URISyntaxException {
        log.debug("REST request to update UserBook : {}", userBookDTO);
        if (userBookDTO.getId() == null) {
            return createUserBook(userBookDTO);
        }
        UserBook userBook = userBookMapper.toEntity(userBookDTO);
        userBook = userBookRepository.save(userBook);
        UserBookDTO result = userBookMapper.toDto(userBook);
        userBookSearchRepository.save(userBook);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-books : get all the userBooks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userBooks in body
     */
    @GetMapping("/user-books")
    @Timed
    public List<UserBookDTO> getAllUserBooks() {
        log.debug("REST request to get all UserBooks");
        List<UserBook> userBooks = userBookRepository.findAll();
        return userBookMapper.toDto(userBooks);
        }

    /**
     * GET  /user-books/:id : get the "id" userBook.
     *
     * @param id the id of the userBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-books/{id}")
    @Timed
    public ResponseEntity<UserBookDTO> getUserBook(@PathVariable Long id) {
        log.debug("REST request to get UserBook : {}", id);
        UserBook userBook = userBookRepository.findOne(id);
        UserBookDTO userBookDTO = userBookMapper.toDto(userBook);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userBookDTO));
    }

    /**
     * DELETE  /user-books/:id : delete the "id" userBook.
     *
     * @param id the id of the userBookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-books/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserBook(@PathVariable Long id) {
        log.debug("REST request to delete UserBook : {}", id);
        userBookRepository.delete(id);
        userBookSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-books?query=:query : search for the userBook corresponding
     * to the query.
     *
     * @param query the query of the userBook search
     * @return the result of the search
     */
    @GetMapping("/_search/user-books")
    @Timed
    public List<UserBookDTO> searchUserBooks(@RequestParam String query) {
        log.debug("REST request to search UserBooks for query {}", query);
        return StreamSupport
            .stream(userBookSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(userBookMapper::toDto)
            .collect(Collectors.toList());
    }

}
