package com.alok.registration.service.impl;

import com.alok.registration.service.CustomerValidationsService;
import com.alok.registration.domain.CustomerValidations;
import com.alok.registration.repository.CustomerValidationsRepository;
import com.alok.registration.repository.search.CustomerValidationsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CustomerValidations}.
 */
@Service
public class CustomerValidationsServiceImpl implements CustomerValidationsService {

    private final Logger log = LoggerFactory.getLogger(CustomerValidationsServiceImpl.class);

    private final CustomerValidationsRepository customerValidationsRepository;

    private final CustomerValidationsSearchRepository customerValidationsSearchRepository;

    public CustomerValidationsServiceImpl(CustomerValidationsRepository customerValidationsRepository, CustomerValidationsSearchRepository customerValidationsSearchRepository) {
        this.customerValidationsRepository = customerValidationsRepository;
        this.customerValidationsSearchRepository = customerValidationsSearchRepository;
    }

    /**
     * Save a customerValidations.
     *
     * @param customerValidations the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerValidations save(CustomerValidations customerValidations) {
        log.debug("Request to save CustomerValidations : {}", customerValidations);
        CustomerValidations result = customerValidationsRepository.save(customerValidations);
        customerValidationsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the customerValidations.
     *
     * @return the list of entities.
     */
    @Override
    public List<CustomerValidations> findAll() {
        log.debug("Request to get all CustomerValidations");
        return customerValidationsRepository.findAll();
    }


    /**
     * Get one customerValidations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<CustomerValidations> findOne(String id) {
        log.debug("Request to get CustomerValidations : {}", id);
        return customerValidationsRepository.findById(id);
    }

    /**
     * Delete the customerValidations by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete CustomerValidations : {}", id);
        customerValidationsRepository.deleteById(id);
        customerValidationsSearchRepository.deleteById(id);
    }

    /**
     * Search for the customerValidations corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<CustomerValidations> search(String query) {
        log.debug("Request to search CustomerValidations for query {}", query);
        return StreamSupport
            .stream(customerValidationsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
