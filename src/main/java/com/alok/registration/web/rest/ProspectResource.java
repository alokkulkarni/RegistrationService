package com.alok.registration.web.rest;

import com.alok.registration.domain.Prospect;
import com.alok.registration.service.ProspectService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.alok.registration.domain.Prospect}.
 */
@RestController
@RequestMapping("/api")
public class ProspectResource {

    private final Logger log = LoggerFactory.getLogger(ProspectResource.class);

    private static final String ENTITY_NAME = "registrationServiceProspect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProspectService prospectService;

    public ProspectResource(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    /**
     * {@code POST  /prospects} : Create a new prospect.
     *
     * @param prospect the prospect to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prospect, or with status {@code 400 (Bad Request)} if the prospect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prospects")
    public ResponseEntity<Prospect> createProspect(@Valid @RequestBody Prospect prospect) throws URISyntaxException {
        log.debug("REST request to save Prospect : {}", prospect);
        if (prospect.getId() != null) {
            throw new BadRequestAlertException("A new prospect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prospect result = prospectService.save(prospect);
        return ResponseEntity.created(new URI("/api/prospects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prospects} : Updates an existing prospect.
     *
     * @param prospect the prospect to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prospect,
     * or with status {@code 400 (Bad Request)} if the prospect is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prospect couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prospects")
    public ResponseEntity<Prospect> updateProspect(@Valid @RequestBody Prospect prospect) throws URISyntaxException {
        log.debug("REST request to update Prospect : {}", prospect);
        if (prospect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prospect result = prospectService.save(prospect);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prospect.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prospects} : get all the prospects.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prospects in body.
     */
    @GetMapping("/prospects")
    public ResponseEntity<List<Prospect>> getAllProspects(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Prospects");
        Page<Prospect> page = prospectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prospects/:id} : get the "id" prospect.
     *
     * @param id the id of the prospect to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prospect, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prospects/{id}")
    public ResponseEntity<Prospect> getProspect(@PathVariable String id) {
        log.debug("REST request to get Prospect : {}", id);
        Optional<Prospect> prospect = prospectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prospect);
    }

    /**
     * {@code DELETE  /prospects/:id} : delete the "id" prospect.
     *
     * @param id the id of the prospect to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prospects/{id}")
    public ResponseEntity<Void> deleteProspect(@PathVariable String id) {
        log.debug("REST request to delete Prospect : {}", id);
        prospectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/prospects?query=:query} : search for the prospect corresponding
     * to the query.
     *
     * @param query the query of the prospect search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/prospects")
    public ResponseEntity<List<Prospect>> searchProspects(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Prospects for query {}", query);
        Page<Prospect> page = prospectService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
