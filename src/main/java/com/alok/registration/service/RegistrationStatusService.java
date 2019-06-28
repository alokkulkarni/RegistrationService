package com.alok.registration.service;

import com.alok.registration.domain.RegistrationStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RegistrationStatus}.
 */
public interface RegistrationStatusService {

    /**
     * Save a registrationStatus.
     *
     * @param registrationStatus the entity to save.
     * @return the persisted entity.
     */
    RegistrationStatus save(RegistrationStatus registrationStatus);

    /**
     * Get all the registrationStatuses.
     *
     * @return the list of entities.
     */
    List<RegistrationStatus> findAll();


    /**
     * Get the "id" registrationStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegistrationStatus> findOne(String id);

    /**
     * Delete the "id" registrationStatus.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the registrationStatus corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<RegistrationStatus> search(String query);
}
