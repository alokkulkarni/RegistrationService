package com.alok.registration.service.impl;

import com.alok.registration.service.ProspectValidationsService;
import com.alok.registration.domain.ProspectValidations;
import com.alok.registration.repository.ProspectValidationsRepository;
import com.alok.registration.repository.search.ProspectValidationsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProspectValidations}.
 */
@Service
public class ProspectValidationsServiceImpl implements ProspectValidationsService {

    private final Logger log = LoggerFactory.getLogger(ProspectValidationsServiceImpl.class);

    private final ProspectValidationsRepository prospectValidationsRepository;

    private final ProspectValidationsSearchRepository prospectValidationsSearchRepository;

    public ProspectValidationsServiceImpl(ProspectValidationsRepository prospectValidationsRepository, ProspectValidationsSearchRepository prospectValidationsSearchRepository) {
        this.prospectValidationsRepository = prospectValidationsRepository;
        this.prospectValidationsSearchRepository = prospectValidationsSearchRepository;
    }

    /**
     * Save a prospectValidations.
     *
     * @param prospectValidations the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProspectValidations save(ProspectValidations prospectValidations) {
        log.debug("Request to save ProspectValidations : {}", prospectValidations);
        ProspectValidations result = prospectValidationsRepository.save(prospectValidations);
        prospectValidationsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the prospectValidations.
     *
     * @return the list of entities.
     */
    @Override
    public List<ProspectValidations> findAll() {
        log.debug("Request to get all ProspectValidations");
        return prospectValidationsRepository.findAll();
    }


    /**
     * Get one prospectValidations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ProspectValidations> findOne(String id) {
        log.debug("Request to get ProspectValidations : {}", id);
        return prospectValidationsRepository.findById(id);
    }

    /**
     * Delete the prospectValidations by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ProspectValidations : {}", id);
        prospectValidationsRepository.deleteById(id);
        prospectValidationsSearchRepository.deleteById(id);
    }

    /**
     * Search for the prospectValidations corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<ProspectValidations> search(String query) {
        log.debug("Request to search ProspectValidations for query {}", query);
        return StreamSupport
            .stream(prospectValidationsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
