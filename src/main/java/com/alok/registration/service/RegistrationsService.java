package com.alok.registration.service;

import com.alok.registration.domain.Registrations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Registrations}.
 */
public interface RegistrationsService {

    /**
     * Save a registrations.
     *
     * @param registrations the entity to save.
     * @return the persisted entity.
     */
    Registrations save(Registrations registrations);

    /**
     * Get all the registrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Registrations> findAll(Pageable pageable);


    /**
     * Get the "id" registrations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Registrations> findOne(String id);

    /**
     * Delete the "id" registrations.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the registrations corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Registrations> search(String query, Pageable pageable);
}
