package com.alok.registration.service;

import com.alok.registration.domain.DeviceDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DeviceDetails}.
 */
public interface DeviceDetailsService {

    /**
     * Save a deviceDetails.
     *
     * @param deviceDetails the entity to save.
     * @return the persisted entity.
     */
    DeviceDetails save(DeviceDetails deviceDetails);

    /**
     * Get all the deviceDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceDetails> findAll(Pageable pageable);


    /**
     * Get the "id" deviceDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceDetails> findOne(String id);

    /**
     * Delete the "id" deviceDetails.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the deviceDetails corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceDetails> search(String query, Pageable pageable);
}
