package com.alok.registration.repository.search;

import com.alok.registration.domain.DeviceDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DeviceDetails} entity.
 */
public interface DeviceDetailsSearchRepository extends ElasticsearchRepository<DeviceDetails, String> {
}
