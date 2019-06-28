package com.alok.registration.service.impl;

import com.alok.registration.service.RegionService;
import com.alok.registration.domain.Region;
import com.alok.registration.repository.RegionRepository;
import com.alok.registration.repository.search.RegionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Region}.
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

    private final RegionRepository regionRepository;

    private final RegionSearchRepository regionSearchRepository;

    public RegionServiceImpl(RegionRepository regionRepository, RegionSearchRepository regionSearchRepository) {
        this.regionRepository = regionRepository;
        this.regionSearchRepository = regionSearchRepository;
    }

    /**
     * Save a region.
     *
     * @param region the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Region save(Region region) {
        log.debug("Request to save Region : {}", region);
        Region result = regionRepository.save(region);
        regionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    @Override
    public List<Region> findAll() {
        log.debug("Request to get all Regions");
        return regionRepository.findAll();
    }


    /**
     * Get one region by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Region> findOne(String id) {
        log.debug("Request to get Region : {}", id);
        return regionRepository.findById(id);
    }

    /**
     * Delete the region by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Region : {}", id);
        regionRepository.deleteById(id);
        regionSearchRepository.deleteById(id);
    }

    /**
     * Search for the region corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<Region> search(String query) {
        log.debug("Request to search Regions for query {}", query);
        return StreamSupport
            .stream(regionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
