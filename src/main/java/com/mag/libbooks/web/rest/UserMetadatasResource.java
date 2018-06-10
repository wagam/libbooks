package com.mag.libbooks.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mag.libbooks.domain.UserMetadatas;

import com.mag.libbooks.repository.UserMetadatasRepository;
import com.mag.libbooks.repository.search.UserMetadatasSearchRepository;
import com.mag.libbooks.web.rest.errors.BadRequestAlertException;
import com.mag.libbooks.web.rest.util.HeaderUtil;
import com.mag.libbooks.service.dto.UserMetadatasDTO;
import com.mag.libbooks.service.mapper.UserMetadatasMapper;
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
 * REST controller for managing UserMetadatas.
 */
@RestController
@RequestMapping("/api")
public class UserMetadatasResource {

    private final Logger log = LoggerFactory.getLogger(UserMetadatasResource.class);

    private static final String ENTITY_NAME = "userMetadatas";

    private final UserMetadatasRepository userMetadatasRepository;

    private final UserMetadatasMapper userMetadatasMapper;

    private final UserMetadatasSearchRepository userMetadatasSearchRepository;

    public UserMetadatasResource(UserMetadatasRepository userMetadatasRepository, UserMetadatasMapper userMetadatasMapper, UserMetadatasSearchRepository userMetadatasSearchRepository) {
        this.userMetadatasRepository = userMetadatasRepository;
        this.userMetadatasMapper = userMetadatasMapper;
        this.userMetadatasSearchRepository = userMetadatasSearchRepository;
    }

    /**
     * POST  /user-metadatas : Create a new userMetadatas.
     *
     * @param userMetadatasDTO the userMetadatasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userMetadatasDTO, or with status 400 (Bad Request) if the userMetadatas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-metadatas")
    @Timed
    public ResponseEntity<UserMetadatasDTO> createUserMetadatas(@RequestBody UserMetadatasDTO userMetadatasDTO) throws URISyntaxException {
        log.debug("REST request to save UserMetadatas : {}", userMetadatasDTO);
        if (userMetadatasDTO.getId() != null) {
            throw new BadRequestAlertException("A new userMetadatas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMetadatas userMetadatas = userMetadatasMapper.toEntity(userMetadatasDTO);
        userMetadatas = userMetadatasRepository.save(userMetadatas);
        UserMetadatasDTO result = userMetadatasMapper.toDto(userMetadatas);
        userMetadatasSearchRepository.save(userMetadatas);
        return ResponseEntity.created(new URI("/api/user-metadatas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-metadatas : Updates an existing userMetadatas.
     *
     * @param userMetadatasDTO the userMetadatasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userMetadatasDTO,
     * or with status 400 (Bad Request) if the userMetadatasDTO is not valid,
     * or with status 500 (Internal Server Error) if the userMetadatasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-metadatas")
    @Timed
    public ResponseEntity<UserMetadatasDTO> updateUserMetadatas(@RequestBody UserMetadatasDTO userMetadatasDTO) throws URISyntaxException {
        log.debug("REST request to update UserMetadatas : {}", userMetadatasDTO);
        if (userMetadatasDTO.getId() == null) {
            return createUserMetadatas(userMetadatasDTO);
        }
        UserMetadatas userMetadatas = userMetadatasMapper.toEntity(userMetadatasDTO);
        userMetadatas = userMetadatasRepository.save(userMetadatas);
        UserMetadatasDTO result = userMetadatasMapper.toDto(userMetadatas);
        userMetadatasSearchRepository.save(userMetadatas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userMetadatasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-metadatas : get all the userMetadatas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userMetadatas in body
     */
    @GetMapping("/user-metadatas")
    @Timed
    public List<UserMetadatasDTO> getAllUserMetadatas() {
        log.debug("REST request to get all UserMetadatas");
        List<UserMetadatas> userMetadatas = userMetadatasRepository.findAll();
        return userMetadatasMapper.toDto(userMetadatas);
        }

    /**
     * GET  /user-metadatas/:id : get the "id" userMetadatas.
     *
     * @param id the id of the userMetadatasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userMetadatasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-metadatas/{id}")
    @Timed
    public ResponseEntity<UserMetadatasDTO> getUserMetadatas(@PathVariable Long id) {
        log.debug("REST request to get UserMetadatas : {}", id);
        UserMetadatas userMetadatas = userMetadatasRepository.findOne(id);
        UserMetadatasDTO userMetadatasDTO = userMetadatasMapper.toDto(userMetadatas);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userMetadatasDTO));
    }

    /**
     * DELETE  /user-metadatas/:id : delete the "id" userMetadatas.
     *
     * @param id the id of the userMetadatasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-metadatas/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserMetadatas(@PathVariable Long id) {
        log.debug("REST request to delete UserMetadatas : {}", id);
        userMetadatasRepository.delete(id);
        userMetadatasSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-metadatas?query=:query : search for the userMetadatas corresponding
     * to the query.
     *
     * @param query the query of the userMetadatas search
     * @return the result of the search
     */
    @GetMapping("/_search/user-metadatas")
    @Timed
    public List<UserMetadatasDTO> searchUserMetadatas(@RequestParam String query) {
        log.debug("REST request to search UserMetadatas for query {}", query);
        return StreamSupport
            .stream(userMetadatasSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(userMetadatasMapper::toDto)
            .collect(Collectors.toList());
    }

}
