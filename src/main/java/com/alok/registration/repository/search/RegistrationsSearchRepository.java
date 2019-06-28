package com.alok.registration.repository.search;

import com.alok.registration.domain.Registrations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Registrations} entity.
 */
public interface RegistrationsSearchRepository extends ElasticsearchRepository<Registrations, String> {
}
