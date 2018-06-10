package com.mag.libbooks.repository.search;

import com.mag.libbooks.domain.UserBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserBook entity.
 */
public interface UserBookSearchRepository extends ElasticsearchRepository<UserBook, Long> {
}
