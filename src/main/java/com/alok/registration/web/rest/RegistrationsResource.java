package com.alok.registration.web.rest;

import com.alok.registration.domain.Registrations;
import com.alok.registration.service.RegistrationsService;
import com.alok.registration.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.alok.registration.domain.Registrations}.
 */
@RestController
@RequestMapping("/api")
public class RegistrationsResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationsResource.class);

    private static final String ENTITY_NAME = "registrationServiceRegistrations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistrationsService registrationsService;

    public RegistrationsResource(RegistrationsService registrationsService) {
        this.registrationsService = registrationsService;
    }

    /**
     * {@code POST  /registrations} : Create a new registrations.
     *
     * @param registrations the registrations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registrations, or with status {@code 400 (Bad Request)} if the registrations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registrations")
    public ResponseEntity<Registrations> createRegistrations(@RequestBody Registrations registrations) throws URISyntaxException {
        log.debug("REST request to save Registrations : {}", registrations);
        if (registrations.getId() != null) {
            throw new BadRequestAlertException("A new registrations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Registrations result = registrationsService.save(registrations);
        return ResponseEntity.created(new URI("/api/registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registrations} : Updates an existing registrations.
     *
     * @param registrations the registrations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrations,
     * or with status {@code 400 (Bad Request)} if the registrations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registrations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registrations")
    public ResponseEntity<Registrations> updateRegistrations(@RequestBody Registrations registrations) throws URISyntaxException {
        log.debug("REST request to update Registrations : {}", registrations);
        if (registrations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Registrations result = registrationsService.save(registrations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, registrations.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /registrations} : get all the registrations.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registrations in body.
     */
    @GetMapping("/registrations")
    public ResponseEntity<List<Registrations>> getAllRegistrations(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Registrations");
        Page<Registrations> page = registrationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /registrations/:id} : get the "id" registrations.
     *
     * @param id the id of the registrations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registrations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registrations/{id}")
    public ResponseEntity<Registrations> getRegistrations(@PathVariable String id) {
        log.debug("REST request to get Registrations : {}", id);
        Optional<Registrations> registrations = registrationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registrations);
    }

    /**
     * {@code DELETE  /registrations/:id} : delete the "id" registrations.
     *
     * @param id the id of the registrations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registrations/{id}")
    public ResponseEntity<Void> deleteRegistrations(@PathVariable String id) {
        log.debug("REST request to delete Registrations : {}", id);
        registrationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/registrations?query=:query} : search for the registrations corresponding
     * to the query.
     *
     * @param query the query of the registrations search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/registrations")
    public ResponseEntity<List<Registrations>> searchRegistrations(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Registrations for query {}", query);
        Page<Registrations> page = registrationsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
