package com.alok.registration.repository.search;

import com.alok.registration.domain.Prospect;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Prospect} entity.
 */
public interface ProspectSearchRepository extends ElasticsearchRepository<Prospect, String> {
}
