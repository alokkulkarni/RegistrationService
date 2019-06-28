package com.alok.registration.web.rest;

import com.alok.registration.domain.ProspectValidations;
import com.alok.registration.service.ProspectValidationsService;
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
 * REST controller for managing {@link com.alok.registration.domain.ProspectValidations}.
 */
@RestController
@RequestMapping("/api")
public class ProspectValidationsResource {

    private final Logger log = LoggerFactory.getLogger(ProspectValidationsResource.class);

    private static final String ENTITY_NAME = "registrationServiceProspectValidations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProspectValidationsService prospectValidationsService;

    public ProspectValidationsResource(ProspectValidationsService prospectValidationsService) {
        this.prospectValidationsService = prospectValidationsService;
    }

    /**
     * {@code POST  /prospect-validations} : Create a new prospectValidations.
     *
     * @param prospectValidations the prospectValidations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prospectValidations, or with status {@code 400 (Bad Request)} if the prospectValidations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prospect-validations")
    public ResponseEntity<ProspectValidations> createProspectValidations(@RequestBody ProspectValidations prospectValidations) throws URISyntaxException {
        log.debug("REST request to save ProspectValidations : {}", prospectValidations);
        if (prospectValidations.getId() != null) {
            throw new BadRequestAlertException("A new prospectValidations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProspectValidations result = prospectValidationsService.save(prospectValidations);
        return ResponseEntity.created(new URI("/api/prospect-validations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prospect-validations} : Updates an existing prospectValidations.
     *
     * @param prospectValidations the prospectValidations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prospectValidations,
     * or with status {@code 400 (Bad Request)} if the prospectValidations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prospectValidations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prospect-validations")
    public ResponseEntity<ProspectValidations> updateProspectValidations(@RequestBody ProspectValidations prospectValidations) throws URISyntaxException {
        log.debug("REST request to update ProspectValidations : {}", prospectValidations);
        if (prospectValidations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProspectValidations result = prospectValidationsService.save(prospectValidations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prospectValidations.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prospect-validations} : get all the prospectValidations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prospectValidations in body.
     */
    @GetMapping("/prospect-validations")
    public List<ProspectValidations> getAllProspectValidations() {
        log.debug("REST request to get all ProspectValidations");
        return prospectValidationsService.findAll();
    }

    /**
     * {@code GET  /prospect-validations/:id} : get the "id" prospectValidations.
     *
     * @param id the id of the prospectValidations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prospectValidations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prospect-validations/{id}")
    public ResponseEntity<ProspectValidations> getProspectValidations(@PathVariable String id) {
        log.debug("REST request to get ProspectValidations : {}", id);
        Optional<ProspectValidations> prospectValidations = prospectValidationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prospectValidations);
    }

    /**
     * {@code DELETE  /prospect-validations/:id} : delete the "id" prospectValidations.
     *
     * @param id the id of the prospectValidations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prospect-validations/{id}")
    public ResponseEntity<Void> deleteProspectValidations(@PathVariable String id) {
        log.debug("REST request to delete ProspectValidations : {}", id);
        prospectValidationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/prospect-validations?query=:query} : search for the prospectValidations corresponding
     * to the query.
     *
     * @param query the query of the prospectValidations search.
     * @return the result of the search.
     */
    @GetMapping("/_search/prospect-validations")
    public List<ProspectValidations> searchProspectValidations(@RequestParam String query) {
        log.debug("REST request to search ProspectValidations for query {}", query);
        return prospectValidationsService.search(query);
    }

}
