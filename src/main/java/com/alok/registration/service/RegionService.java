package com.alok.registration.service;

import com.alok.registration.domain.Region;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Region}.
 */
public interface RegionService {

    /**
     * Save a region.
     *
     * @param region the entity to save.
     * @return the persisted entity.
     */
    Region save(Region region);

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    List<Region> findAll();


    /**
     * Get the "id" region.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Region> findOne(String id);

    /**
     * Delete the "id" region.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the region corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Region> search(String query);
}
