package com.mag.libbooks.repository.search;

import com.mag.libbooks.domain.BookMetadatas;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BookMetadatas entity.
 */
public interface BookMetadatasSearchRepository extends ElasticsearchRepository<BookMetadatas, Long> {
}
