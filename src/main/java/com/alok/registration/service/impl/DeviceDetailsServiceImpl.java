package com.alok.registration.service.impl;

import com.alok.registration.service.DeviceDetailsService;
import com.alok.registration.domain.DeviceDetails;
import com.alok.registration.repository.DeviceDetailsRepository;
import com.alok.registration.repository.search.DeviceDetailsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DeviceDetails}.
 */
@Service
public class DeviceDetailsServiceImpl implements DeviceDetailsService {

    private final Logger log = LoggerFactory.getLogger(DeviceDetailsServiceImpl.class);

    private final DeviceDetailsRepository deviceDetailsRepository;

    private final DeviceDetailsSearchRepository deviceDetailsSearchRepository;

    public DeviceDetailsServiceImpl(DeviceDetailsRepository deviceDetailsRepository, DeviceDetailsSearchRepository deviceDetailsSearchRepository) {
        this.deviceDetailsRepository = deviceDetailsRepository;
        this.deviceDetailsSearchRepository = deviceDetailsSearchRepository;
    }

    /**
     * Save a deviceDetails.
     *
     * @param deviceDetails the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeviceDetails save(DeviceDetails deviceDetails) {
        log.debug("Request to save DeviceDetails : {}", deviceDetails);
        DeviceDetails result = deviceDetailsRepository.save(deviceDetails);
        deviceDetailsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the deviceDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<DeviceDetails> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceDetails");
        return deviceDetailsRepository.findAll(pageable);
    }


    /**
     * Get one deviceDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<DeviceDetails> findOne(String id) {
        log.debug("Request to get DeviceDetails : {}", id);
        return deviceDetailsRepository.findById(id);
    }

    /**
     * Delete the deviceDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete DeviceDetails : {}", id);
        deviceDetailsRepository.deleteById(id);
        deviceDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the deviceDetails corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<DeviceDetails> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DeviceDetails for query {}", query);
        return deviceDetailsSearchRepository.search(queryStringQuery(query), pageable);    }
}
