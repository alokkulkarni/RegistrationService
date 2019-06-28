package com.alok.registration.web.rest;

import com.alok.registration.domain.RegistrationStatus;
import com.alok.registration.service.RegistrationStatusService;
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
 * REST controller for managing {@link com.alok.registration.domain.RegistrationStatus}.
 */
@RestController
@RequestMapping("/api")
public class RegistrationStatusResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationStatusResource.class);

    private static final String ENTITY_NAME = "registrationServiceRegistrationStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistrationStatusService registrationStatusService;

    public RegistrationStatusResource(RegistrationStatusService registrationStatusService) {
        this.registrationStatusService = registrationStatusService;
    }

    /**
     * {@code POST  /registration-statuses} : Create a new registrationStatus.
     *
     * @param registrationStatus the registrationStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registrationStatus, or with status {@code 400 (Bad Request)} if the registrationStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registration-statuses")
    public ResponseEntity<RegistrationStatus> createRegistrationStatus(@RequestBody RegistrationStatus registrationStatus) throws URISyntaxException {
        log.debug("REST request to save RegistrationStatus : {}", registrationStatus);
        if (registrationStatus.getId() != null) {
            throw new BadRequestAlertException("A new registrationStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistrationStatus result = registrationStatusService.save(registrationStatus);
        return ResponseEntity.created(new URI("/api/registration-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registration-statuses} : Updates an existing registrationStatus.
     *
     * @param registrationStatus the registrationStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrationStatus,
     * or with status {@code 400 (Bad Request)} if the registrationStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registrationStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registration-statuses")
    public ResponseEntity<RegistrationStatus> updateRegistrationStatus(@RequestBody RegistrationStatus registrationStatus) throws URISyntaxException {
        log.debug("REST request to update RegistrationStatus : {}", registrationStatus);
        if (registrationStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegistrationStatus result = registrationStatusService.save(registrationStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, registrationStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /registration-statuses} : get all the registrationStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registrationStatuses in body.
     */
    @GetMapping("/registration-statuses")
    public List<RegistrationStatus> getAllRegistrationStatuses() {
        log.debug("REST request to get all RegistrationStatuses");
        return registrationStatusService.findAll();
    }

    /**
     * {@code GET  /registration-statuses/:id} : get the "id" registrationStatus.
     *
     * @param id the id of the registrationStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registrationStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registration-statuses/{id}")
    public ResponseEntity<RegistrationStatus> getRegistrationStatus(@PathVariable String id) {
        log.debug("REST request to get RegistrationStatus : {}", id);
        Optional<RegistrationStatus> registrationStatus = registrationStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registrationStatus);
    }

    /**
     * {@code DELETE  /registration-statuses/:id} : delete the "id" registrationStatus.
     *
     * @param id the id of the registrationStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registration-statuses/{id}")
    public ResponseEntity<Void> deleteRegistrationStatus(@PathVariable String id) {
        log.debug("REST request to delete RegistrationStatus : {}", id);
        registrationStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/registration-statuses?query=:query} : search for the registrationStatus corresponding
     * to the query.
     *
     * @param query the query of the registrationStatus search.
     * @return the result of the search.
     */
    @GetMapping("/_search/registration-statuses")
    public List<RegistrationStatus> searchRegistrationStatuses(@RequestParam String query) {
        log.debug("REST request to search RegistrationStatuses for query {}", query);
        return registrationStatusService.search(query);
    }

}
