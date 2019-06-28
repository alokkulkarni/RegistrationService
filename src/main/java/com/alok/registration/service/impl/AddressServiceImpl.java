package com.alok.registration.service.impl;

import com.alok.registration.service.AddressService;
import com.alok.registration.domain.Address;
import com.alok.registration.repository.AddressRepository;
import com.alok.registration.repository.search.AddressSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Address}.
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressSearchRepository addressSearchRepository;

    public AddressServiceImpl(AddressRepository addressRepository, AddressSearchRepository addressSearchRepository) {
        this.addressRepository = addressRepository;
        this.addressSearchRepository = addressSearchRepository;
    }

    /**
     * Save a address.
     *
     * @param address the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Address save(Address address) {
        log.debug("Request to save Address : {}", address);
        Address result = addressRepository.save(address);
        addressSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the addresses.
     *
     * @return the list of entities.
     */
    @Override
    public List<Address> findAll() {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll();
    }


    /**
     * Get one address by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Address> findOne(String id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id);
    }

    /**
     * Delete the address by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
        addressSearchRepository.deleteById(id);
    }

    /**
     * Search for the address corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<Address> search(String query) {
        log.debug("Request to search Addresses for query {}", query);
        return StreamSupport
            .stream(addressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
