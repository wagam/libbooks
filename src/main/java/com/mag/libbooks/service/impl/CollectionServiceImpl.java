package com.mag.libbooks.service.impl;

import com.mag.libbooks.service.CollectionService;
import com.mag.libbooks.domain.Collection;
import com.mag.libbooks.repository.CollectionRepository;
import com.mag.libbooks.repository.search.CollectionSearchRepository;
import com.mag.libbooks.service.dto.CollectionDTO;
import com.mag.libbooks.service.mapper.CollectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Collection.
 */
@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final Logger log = LoggerFactory.getLogger(CollectionServiceImpl.class);

    private final CollectionRepository collectionRepository;

    private final CollectionMapper collectionMapper;

    private final CollectionSearchRepository collectionSearchRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository, CollectionMapper collectionMapper, CollectionSearchRepository collectionSearchRepository) {
        this.collectionRepository = collectionRepository;
        this.collectionMapper = collectionMapper;
        this.collectionSearchRepository = collectionSearchRepository;
    }

    /**
     * Save a collection.
     *
     * @param collectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CollectionDTO save(CollectionDTO collectionDTO) {
        log.debug("Request to save Collection : {}", collectionDTO);
        Collection collection = collectionMapper.toEntity(collectionDTO);
        collection = collectionRepository.save(collection);
        CollectionDTO result = collectionMapper.toDto(collection);
        collectionSearchRepository.save(collection);
        return result;
    }

    /**
     * Get all the collections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CollectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Collections");
        return collectionRepository.findAll(pageable)
            .map(collectionMapper::toDto);
    }

    /**
     * Get one collection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CollectionDTO findOne(Long id) {
        log.debug("Request to get Collection : {}", id);
        Collection collection = collectionRepository.findOne(id);
        return collectionMapper.toDto(collection);
    }

    /**
     * Delete the collection by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Collection : {}", id);
        collectionRepository.delete(id);
        collectionSearchRepository.delete(id);
    }

    /**
     * Search for the collection corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CollectionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Collections for query {}", query);
        Page<Collection> result = collectionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(collectionMapper::toDto);
    }
}
