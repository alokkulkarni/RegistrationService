package com.alok.registration.service;

import com.alok.registration.domain.Prospect;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Prospect}.
 */
public interface ProspectService {

    /**
     * Save a prospect.
     *
     * @param prospect the entity to save.
     * @return the persisted entity.
     */
    Prospect save(Prospect prospect);

    /**
     * Get all the prospects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Prospect> findAll(Pageable pageable);


    /**
     * Get the "id" prospect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prospect> findOne(String id);

    /**
     * Delete the "id" prospect.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the prospect corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Prospect> search(String query, Pageable pageable);
}
