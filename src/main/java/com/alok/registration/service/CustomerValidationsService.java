package com.alok.registration.service;

import com.alok.registration.domain.CustomerValidations;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustomerValidations}.
 */
public interface CustomerValidationsService {

    /**
     * Save a customerValidations.
     *
     * @param customerValidations the entity to save.
     * @return the persisted entity.
     */
    CustomerValidations save(CustomerValidations customerValidations);

    /**
     * Get all the customerValidations.
     *
     * @return the list of entities.
     */
    List<CustomerValidations> findAll();


    /**
     * Get the "id" customerValidations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerValidations> findOne(String id);

    /**
     * Delete the "id" customerValidations.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the customerValidations corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<CustomerValidations> search(String query);
}
