package com.alok.registration.web.rest;

import com.alok.registration.domain.CustomerValidations;
import com.alok.registration.service.CustomerValidationsService;
import com.alok.registration.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.alok.registration.domain.CustomerValidations}.
 */
@RestController
@RequestMapping("/api")
public class CustomerValidationsResource {

    private final Logger log = LoggerFactory.getLogger(CustomerValidationsResource.class);

    private static final String ENTITY_NAME = "registrationServiceCustomerValidations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerValidationsService customerValidationsService;

    public CustomerValidationsResource(CustomerValidationsService customerValidationsService) {
        this.customerValidationsService = customerValidationsService;
    }

    /**
     * {@code POST  /customer-validations} : Create a new customerValidations.
     *
     * @param customerValidations the customerValidations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerValidations, or with status {@code 400 (Bad Request)} if the customerValidations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-validations")
    public ResponseEntity<CustomerValidations> createCustomerValidations(@RequestBody CustomerValidations customerValidations) throws URISyntaxException {
        log.debug("REST request to save CustomerValidations : {}", customerValidations);
        if (customerValidations.getId() != null) {
            throw new BadRequestAlertException("A new customerValidations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerValidations result = customerValidationsService.save(customerValidations);
        return ResponseEntity.created(new URI("/api/customer-validations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-validations} : Updates an existing customerValidations.
     *
     * @param customerValidations the customerValidations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerValidations,
     * or with status {@code 400 (Bad Request)} if the customerValidations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerValidations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-validations")
    public ResponseEntity<CustomerValidations> updateCustomerValidations(@RequestBody CustomerValidations customerValidations) throws URISyntaxException {
        log.debug("REST request to update CustomerValidations : {}", customerValidations);
        if (customerValidations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerValidations result = customerValidationsService.save(customerValidations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerValidations.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-validations} : get all the customerValidations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerValidations in body.
     */
    @GetMapping("/customer-validations")
    public List<CustomerValidations> getAllCustomerValidations() {
        log.debug("REST request to get all CustomerValidations");
        return customerValidationsService.findAll();
    }

    /**
     * {@code GET  /customer-validations/:id} : get the "id" customerValidations.
     *
     * @param id the id of the customerValidations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerValidations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-validations/{id}")
    public ResponseEntity<CustomerValidations> getCustomerValidations(@PathVariable String id) {
        log.debug("REST request to get CustomerValidations : {}", id);
        Optional<CustomerValidations> customerValidations = customerValidationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerValidations);
    }

    /**
     * {@code DELETE  /customer-validations/:id} : delete the "id" customerValidations.
     *
     * @param id the id of the customerValidations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-validations/{id}")
    public ResponseEntity<Void> deleteCustomerValidations(@PathVariable String id) {
        log.debug("REST request to delete CustomerValidations : {}", id);
        customerValidationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/customer-validations?query=:query} : search for the customerValidations corresponding
     * to the query.
     *
     * @param query the query of the customerValidations search.
     * @return the result of the search.
     */
    @GetMapping("/_search/customer-validations")
    public List<CustomerValidations> searchCustomerValidations(@RequestParam String query) {
        log.debug("REST request to search CustomerValidations for query {}", query);
        return customerValidationsService.search(query);
    }

}
