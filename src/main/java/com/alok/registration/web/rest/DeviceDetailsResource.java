package com.alok.registration.web.rest;

import com.alok.registration.domain.DeviceDetails;
import com.alok.registration.service.DeviceDetailsService;
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
 * REST controller for managing {@link com.alok.registration.domain.DeviceDetails}.
 */
@RestController
@RequestMapping("/api")
public class DeviceDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DeviceDetailsResource.class);

    private static final String ENTITY_NAME = "registrationServiceDeviceDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceDetailsService deviceDetailsService;

    public DeviceDetailsResource(DeviceDetailsService deviceDetailsService) {
        this.deviceDetailsService = deviceDetailsService;
    }

    /**
     * {@code POST  /device-details} : Create a new deviceDetails.
     *
     * @param deviceDetails the deviceDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceDetails, or with status {@code 400 (Bad Request)} if the deviceDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-details")
    public ResponseEntity<DeviceDetails> createDeviceDetails(@RequestBody DeviceDetails deviceDetails) throws URISyntaxException {
        log.debug("REST request to save DeviceDetails : {}", deviceDetails);
        if (deviceDetails.getId() != null) {
            throw new BadRequestAlertException("A new deviceDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceDetails result = deviceDetailsService.save(deviceDetails);
        return ResponseEntity.created(new URI("/api/device-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-details} : Updates an existing deviceDetails.
     *
     * @param deviceDetails the deviceDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceDetails,
     * or with status {@code 400 (Bad Request)} if the deviceDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-details")
    public ResponseEntity<DeviceDetails> updateDeviceDetails(@RequestBody DeviceDetails deviceDetails) throws URISyntaxException {
        log.debug("REST request to update DeviceDetails : {}", deviceDetails);
        if (deviceDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceDetails result = deviceDetailsService.save(deviceDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-details} : get all the deviceDetails.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceDetails in body.
     */
    @GetMapping("/device-details")
    public ResponseEntity<List<DeviceDetails>> getAllDeviceDetails(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of DeviceDetails");
        Page<DeviceDetails> page = deviceDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-details/:id} : get the "id" deviceDetails.
     *
     * @param id the id of the deviceDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-details/{id}")
    public ResponseEntity<DeviceDetails> getDeviceDetails(@PathVariable String id) {
        log.debug("REST request to get DeviceDetails : {}", id);
        Optional<DeviceDetails> deviceDetails = deviceDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceDetails);
    }

    /**
     * {@code DELETE  /device-details/:id} : delete the "id" deviceDetails.
     *
     * @param id the id of the deviceDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-details/{id}")
    public ResponseEntity<Void> deleteDeviceDetails(@PathVariable String id) {
        log.debug("REST request to delete DeviceDetails : {}", id);
        deviceDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/device-details?query=:query} : search for the deviceDetails corresponding
     * to the query.
     *
     * @param query the query of the deviceDetails search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/device-details")
    public ResponseEntity<List<DeviceDetails>> searchDeviceDetails(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of DeviceDetails for query {}", query);
        Page<DeviceDetails> page = deviceDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
