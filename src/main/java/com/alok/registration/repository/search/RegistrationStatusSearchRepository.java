package com.alok.registration.repository.search;

import com.alok.registration.domain.RegistrationStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RegistrationStatus} entity.
 */
public interface RegistrationStatusSearchRepository extends ElasticsearchRepository<RegistrationStatus, String> {
}
