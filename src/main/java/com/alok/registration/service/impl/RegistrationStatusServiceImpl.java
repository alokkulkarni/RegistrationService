package com.alok.registration.service.impl;

import com.alok.registration.service.RegistrationStatusService;
import com.alok.registration.domain.RegistrationStatus;
import com.alok.registration.repository.RegistrationStatusRepository;
import com.alok.registration.repository.search.RegistrationStatusSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RegistrationStatus}.
 */
@Service
public class RegistrationStatusServiceImpl implements RegistrationStatusService {

    private final Logger log = LoggerFactory.getLogger(RegistrationStatusServiceImpl.class);

    private final RegistrationStatusRepository registrationStatusRepository;

    private final RegistrationStatusSearchRepository registrationStatusSearchRepository;

    public RegistrationStatusServiceImpl(RegistrationStatusRepository registrationStatusRepository, RegistrationStatusSearchRepository registrationStatusSearchRepository) {
        this.registrationStatusRepository = registrationStatusRepository;
        this.registrationStatusSearchRepository = registrationStatusSearchRepository;
    }

    /**
     * Save a registrationStatus.
     *
     * @param registrationStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegistrationStatus save(RegistrationStatus registrationStatus) {
        log.debug("Request to save RegistrationStatus : {}", registrationStatus);
        RegistrationStatus result = registrationStatusRepository.save(registrationStatus);
        registrationStatusSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the registrationStatuses.
     *
     * @return the list of entities.
     */
    @Override
    public List<RegistrationStatus> findAll() {
        log.debug("Request to get all RegistrationStatuses");
        return registrationStatusRepository.findAll();
    }


    /**
     * Get one registrationStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<RegistrationStatus> findOne(String id) {
        log.debug("Request to get RegistrationStatus : {}", id);
        return registrationStatusRepository.findById(id);
    }

    /**
     * Delete the registrationStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete RegistrationStatus : {}", id);
        registrationStatusRepository.deleteById(id);
        registrationStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the registrationStatus corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<RegistrationStatus> search(String query) {
        log.debug("Request to search RegistrationStatuses for query {}", query);
        return StreamSupport
            .stream(registrationStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
