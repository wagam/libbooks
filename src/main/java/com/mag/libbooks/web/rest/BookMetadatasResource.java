package com.mag.libbooks.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mag.libbooks.domain.BookMetadatas;

import com.mag.libbooks.repository.BookMetadatasRepository;
import com.mag.libbooks.repository.search.BookMetadatasSearchRepository;
import com.mag.libbooks.web.rest.errors.BadRequestAlertException;
import com.mag.libbooks.web.rest.util.HeaderUtil;
import com.mag.libbooks.service.dto.BookMetadatasDTO;
import com.mag.libbooks.service.mapper.BookMetadatasMapper;
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
 * REST controller for managing BookMetadatas.
 */
@RestController
@RequestMapping("/api")
public class BookMetadatasResource {

    private final Logger log = LoggerFactory.getLogger(BookMetadatasResource.class);

    private static final String ENTITY_NAME = "bookMetadatas";

    private final BookMetadatasRepository bookMetadatasRepository;

    private final BookMetadatasMapper bookMetadatasMapper;

    private final BookMetadatasSearchRepository bookMetadatasSearchRepository;

    public BookMetadatasResource(BookMetadatasRepository bookMetadatasRepository, BookMetadatasMapper bookMetadatasMapper, BookMetadatasSearchRepository bookMetadatasSearchRepository) {
        this.bookMetadatasRepository = bookMetadatasRepository;
        this.bookMetadatasMapper = bookMetadatasMapper;
        this.bookMetadatasSearchRepository = bookMetadatasSearchRepository;
    }

    /**
     * POST  /book-metadatas : Create a new bookMetadatas.
     *
     * @param bookMetadatasDTO the bookMetadatasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookMetadatasDTO, or with status 400 (Bad Request) if the bookMetadatas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-metadatas")
    @Timed
    public ResponseEntity<BookMetadatasDTO> createBookMetadatas(@RequestBody BookMetadatasDTO bookMetadatasDTO) throws URISyntaxException {
        log.debug("REST request to save BookMetadatas : {}", bookMetadatasDTO);
        if (bookMetadatasDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookMetadatas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookMetadatas bookMetadatas = bookMetadatasMapper.toEntity(bookMetadatasDTO);
        bookMetadatas = bookMetadatasRepository.save(bookMetadatas);
        BookMetadatasDTO result = bookMetadatasMapper.toDto(bookMetadatas);
        bookMetadatasSearchRepository.save(bookMetadatas);
        return ResponseEntity.created(new URI("/api/book-metadatas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-metadatas : Updates an existing bookMetadatas.
     *
     * @param bookMetadatasDTO the bookMetadatasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookMetadatasDTO,
     * or with status 400 (Bad Request) if the bookMetadatasDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookMetadatasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-metadatas")
    @Timed
    public ResponseEntity<BookMetadatasDTO> updateBookMetadatas(@RequestBody BookMetadatasDTO bookMetadatasDTO) throws URISyntaxException {
        log.debug("REST request to update BookMetadatas : {}", bookMetadatasDTO);
        if (bookMetadatasDTO.getId() == null) {
            return createBookMetadatas(bookMetadatasDTO);
        }
        BookMetadatas bookMetadatas = bookMetadatasMapper.toEntity(bookMetadatasDTO);
        bookMetadatas = bookMetadatasRepository.save(bookMetadatas);
        BookMetadatasDTO result = bookMetadatasMapper.toDto(bookMetadatas);
        bookMetadatasSearchRepository.save(bookMetadatas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookMetadatasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-metadatas : get all the bookMetadatas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bookMetadatas in body
     */
    @GetMapping("/book-metadatas")
    @Timed
    public List<BookMetadatasDTO> getAllBookMetadatas() {
        log.debug("REST request to get all BookMetadatas");
        List<BookMetadatas> bookMetadatas = bookMetadatasRepository.findAll();
        return bookMetadatasMapper.toDto(bookMetadatas);
        }

    /**
     * GET  /book-metadatas/:id : get the "id" bookMetadatas.
     *
     * @param id the id of the bookMetadatasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookMetadatasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-metadatas/{id}")
    @Timed
    public ResponseEntity<BookMetadatasDTO> getBookMetadatas(@PathVariable Long id) {
        log.debug("REST request to get BookMetadatas : {}", id);
        BookMetadatas bookMetadatas = bookMetadatasRepository.findOne(id);
        BookMetadatasDTO bookMetadatasDTO = bookMetadatasMapper.toDto(bookMetadatas);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookMetadatasDTO));
    }

    /**
     * DELETE  /book-metadatas/:id : delete the "id" bookMetadatas.
     *
     * @param id the id of the bookMetadatasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-metadatas/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookMetadatas(@PathVariable Long id) {
        log.debug("REST request to delete BookMetadatas : {}", id);
        bookMetadatasRepository.delete(id);
        bookMetadatasSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/book-metadatas?query=:query : search for the bookMetadatas corresponding
     * to the query.
     *
     * @param query the query of the bookMetadatas search
     * @return the result of the search
     */
    @GetMapping("/_search/book-metadatas")
    @Timed
    public List<BookMetadatasDTO> searchBookMetadatas(@RequestParam String query) {
        log.debug("REST request to search BookMetadatas for query {}", query);
        return StreamSupport
            .stream(bookMetadatasSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(bookMetadatasMapper::toDto)
            .collect(Collectors.toList());
    }

}
