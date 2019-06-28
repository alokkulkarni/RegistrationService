package com.alok.registration.service;

import com.alok.registration.domain.ProspectValidations;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProspectValidations}.
 */
public interface ProspectValidationsService {

    /**
     * Save a prospectValidations.
     *
     * @param prospectValidations the entity to save.
     * @return the persisted entity.
     */
    ProspectValidations save(ProspectValidations prospectValidations);

    /**
     * Get all the prospectValidations.
     *
     * @return the list of entities.
     */
    List<ProspectValidations> findAll();


    /**
     * Get the "id" prospectValidations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProspectValidations> findOne(String id);

    /**
     * Delete the "id" prospectValidations.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the prospectValidations corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProspectValidations> search(String query);
}
