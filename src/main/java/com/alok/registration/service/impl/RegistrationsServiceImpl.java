package com.alok.registration.service.impl;

import com.alok.registration.service.RegistrationsService;
import com.alok.registration.domain.Registrations;
import com.alok.registration.repository.RegistrationsRepository;
import com.alok.registration.repository.search.RegistrationsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Registrations}.
 */
@Service
public class RegistrationsServiceImpl implements RegistrationsService {

    private final Logger log = LoggerFactory.getLogger(RegistrationsServiceImpl.class);

    private final RegistrationsRepository registrationsRepository;

    private final RegistrationsSearchRepository registrationsSearchRepository;

    public RegistrationsServiceImpl(RegistrationsRepository registrationsRepository, RegistrationsSearchRepository registrationsSearchRepository) {
        this.registrationsRepository = registrationsRepository;
        this.registrationsSearchRepository = registrationsSearchRepository;
    }

    /**
     * Save a registrations.
     *
     * @param registrations the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Registrations save(Registrations registrations) {
        log.debug("Request to save Registrations : {}", registrations);
        Registrations result = registrationsRepository.save(registrations);
        registrationsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the registrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Registrations> findAll(Pageable pageable) {
        log.debug("Request to get all Registrations");
        return registrationsRepository.findAll(pageable);
    }


    /**
     * Get one registrations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Registrations> findOne(String id) {
        log.debug("Request to get Registrations : {}", id);
        return registrationsRepository.findById(id);
    }

    /**
     * Delete the registrations by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Registrations : {}", id);
        registrationsRepository.deleteById(id);
        registrationsSearchRepository.deleteById(id);
    }

    /**
     * Search for the registrations corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Registrations> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Registrations for query {}", query);
        return registrationsSearchRepository.search(queryStringQuery(query), pageable);    }
}
