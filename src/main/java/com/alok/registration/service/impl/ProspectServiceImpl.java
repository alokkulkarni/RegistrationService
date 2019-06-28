package com.alok.registration.service.impl;

import com.alok.registration.service.ProspectService;
import com.alok.registration.domain.Prospect;
import com.alok.registration.repository.ProspectRepository;
import com.alok.registration.repository.search.ProspectSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Prospect}.
 */
@Service
public class ProspectServiceImpl implements ProspectService {

    private final Logger log = LoggerFactory.getLogger(ProspectServiceImpl.class);

    private final ProspectRepository prospectRepository;

    private final ProspectSearchRepository prospectSearchRepository;

    public ProspectServiceImpl(ProspectRepository prospectRepository, ProspectSearchRepository prospectSearchRepository) {
        this.prospectRepository = prospectRepository;
        this.prospectSearchRepository = prospectSearchRepository;
    }

    /**
     * Save a prospect.
     *
     * @param prospect the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Prospect save(Prospect prospect) {
        log.debug("Request to save Prospect : {}", prospect);
        Prospect result = prospectRepository.save(prospect);
        prospectSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the prospects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Prospect> findAll(Pageable pageable) {
        log.debug("Request to get all Prospects");
        return prospectRepository.findAll(pageable);
    }


    /**
     * Get one prospect by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Prospect> findOne(String id) {
        log.debug("Request to get Prospect : {}", id);
        return prospectRepository.findById(id);
    }

    /**
     * Delete the prospect by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Prospect : {}", id);
        prospectRepository.deleteById(id);
        prospectSearchRepository.deleteById(id);
    }

    /**
     * Search for the prospect corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Prospect> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Prospects for query {}", query);
        return prospectSearchRepository.search(queryStringQuery(query), pageable);    }
}
