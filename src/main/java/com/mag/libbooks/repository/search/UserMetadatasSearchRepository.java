package com.mag.libbooks.repository.search;

import com.mag.libbooks.domain.UserMetadatas;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserMetadatas entity.
 */
public interface UserMetadatasSearchRepository extends ElasticsearchRepository<UserMetadatas, Long> {
}
