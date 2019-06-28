package com.alok.registration.repository.search;

import com.alok.registration.domain.CustomerValidations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CustomerValidations} entity.
 */
public interface CustomerValidationsSearchRepository extends ElasticsearchRepository<CustomerValidations, String> {
}
