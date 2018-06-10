package com.mag.libbooks.repository.search;

import com.mag.libbooks.domain.Collection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Collection entity.
 */
public interface CollectionSearchRepository extends ElasticsearchRepository<Collection, Long> {
}
