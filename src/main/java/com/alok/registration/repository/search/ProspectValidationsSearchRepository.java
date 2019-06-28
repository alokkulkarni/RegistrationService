package com.alok.registration.repository.search;

import com.alok.registration.domain.ProspectValidations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProspectValidations} entity.
 */
public interface ProspectValidationsSearchRepository extends ElasticsearchRepository<ProspectValidations, String> {
}
