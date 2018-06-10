package com.mag.libbooks.service;

import com.mag.libbooks.service.dto.CollectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Collection.
 */
public interface CollectionService {

    /**
     * Save a collection.
     *
     * @param collectionDTO the entity to save
     * @return the persisted entity
     */
    CollectionDTO save(CollectionDTO collectionDTO);

    /**
     * Get all the collections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CollectionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" collection.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CollectionDTO findOne(Long id);

    /**
     * Delete the "id" collection.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the collection corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CollectionDTO> search(String query, Pageable pageable);
}
