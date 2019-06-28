package com.alok.registration.web.rest;

import com.alok.registration.RegistrationServiceApp;
import com.alok.registration.domain.RegistrationStatus;
import com.alok.registration.repository.RegistrationStatusRepository;
import com.alok.registration.repository.search.RegistrationStatusSearchRepository;
import com.alok.registration.service.RegistrationStatusService;
import com.alok.registration.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.Collections;
import java.util.List;

import static com.alok.registration.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RegistrationStatusResource} REST controller.
 */
@SpringBootTest(classes = RegistrationServiceApp.class)
public class RegistrationStatusResourceIT {

    private static final Integer DEFAULT_REGISTRATION_STATUS_CODE = 1;
    private static final Integer UPDATED_REGISTRATION_STATUS_CODE = 2;

    private static final String DEFAULT_REGISTRATIONS_STATUS_DESC = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATIONS_STATUS_DESC = "BBBBBBBBBB";

    @Autowired
    private RegistrationStatusRepository registrationStatusRepository;

    @Autowired
    private RegistrationStatusService registrationStatusService;

    /**
     * This repository is mocked in the com.alok.registration.repository.search test package.
     *
     * @see com.alok.registration.repository.search.RegistrationStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegistrationStatusSearchRepository mockRegistrationStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restRegistrationStatusMockMvc;

    private RegistrationStatus registrationStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistrationStatusResource registrationStatusResource = new RegistrationStatusResource(registrationStatusService);
        this.restRegistrationStatusMockMvc = MockMvcBuilders.standaloneSetup(registrationStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistrationStatus createEntity() {
        RegistrationStatus registrationStatus = new RegistrationStatus()
            .registrationStatusCode(DEFAULT_REGISTRATION_STATUS_CODE)
            .registrationsStatusDesc(DEFAULT_REGISTRATIONS_STATUS_DESC);
        return registrationStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistrationStatus createUpdatedEntity() {
        RegistrationStatus registrationStatus = new RegistrationStatus()
            .registrationStatusCode(UPDATED_REGISTRATION_STATUS_CODE)
            .registrationsStatusDesc(UPDATED_REGISTRATIONS_STATUS_DESC);
        return registrationStatus;
    }

    @BeforeEach
    public void initTest() {
        registrationStatusRepository.deleteAll();
        registrationStatus = createEntity();
    }

    @Test
    public void createRegistrationStatus() throws Exception {
        int databaseSizeBeforeCreate = registrationStatusRepository.findAll().size();

        // Create the RegistrationStatus
        restRegistrationStatusMockMvc.perform(post("/api/registration-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationStatus)))
            .andExpect(status().isCreated());

        // Validate the RegistrationStatus in the database
        List<RegistrationStatus> registrationStatusList = registrationStatusRepository.findAll();
        assertThat(registrationStatusList).hasSize(databaseSizeBeforeCreate + 1);
        RegistrationStatus testRegistrationStatus = registrationStatusList.get(registrationStatusList.size() - 1);
        assertThat(testRegistrationStatus.getRegistrationStatusCode()).isEqualTo(DEFAULT_REGISTRATION_STATUS_CODE);
        assertThat(testRegistrationStatus.getRegistrationsStatusDesc()).isEqualTo(DEFAULT_REGISTRATIONS_STATUS_DESC);

        // Validate the RegistrationStatus in Elasticsearch
        verify(mockRegistrationStatusSearchRepository, times(1)).save(testRegistrationStatus);
    }

    @Test
    public void createRegistrationStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registrationStatusRepository.findAll().size();

        // Create the RegistrationStatus with an existing ID
        registrationStatus.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationStatusMockMvc.perform(post("/api/registration-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationStatus)))
            .andExpect(status().isBadRequest());

        // Validate the RegistrationStatus in the database
        List<RegistrationStatus> registrationStatusList = registrationStatusRepository.findAll();
        assertThat(registrationStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the RegistrationStatus in Elasticsearch
        verify(mockRegistrationStatusSearchRepository, times(0)).save(registrationStatus);
    }


    @Test
    public void getAllRegistrationStatuses() throws Exception {
        // Initialize the database
        registrationStatusRepository.save(registrationStatus);

        // Get all the registrationStatusList
        restRegistrationStatusMockMvc.perform(get("/api/registration-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrationStatus.getId())))
            .andExpect(jsonPath("$.[*].registrationStatusCode").value(hasItem(DEFAULT_REGISTRATION_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].registrationsStatusDesc").value(hasItem(DEFAULT_REGISTRATIONS_STATUS_DESC.toString())));
    }
    
    @Test
    public void getRegistrationStatus() throws Exception {
        // Initialize the database
        registrationStatusRepository.save(registrationStatus);

        // Get the registrationStatus
        restRegistrationStatusMockMvc.perform(get("/api/registration-statuses/{id}", registrationStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registrationStatus.getId()))
            .andExpect(jsonPath("$.registrationStatusCode").value(DEFAULT_REGISTRATION_STATUS_CODE))
            .andExpect(jsonPath("$.registrationsStatusDesc").value(DEFAULT_REGISTRATIONS_STATUS_DESC.toString()));
    }

    @Test
    public void getNonExistingRegistrationStatus() throws Exception {
        // Get the registrationStatus
        restRegistrationStatusMockMvc.perform(get("/api/registration-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRegistrationStatus() throws Exception {
        // Initialize the database
        registrationStatusService.save(registrationStatus);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRegistrationStatusSearchRepository);

        int databaseSizeBeforeUpdate = registrationStatusRepository.findAll().size();

        // Update the registrationStatus
        RegistrationStatus updatedRegistrationStatus = registrationStatusRepository.findById(registrationStatus.getId()).get();
        updatedRegistrationStatus
            .registrationStatusCode(UPDATED_REGISTRATION_STATUS_CODE)
            .registrationsStatusDesc(UPDATED_REGISTRATIONS_STATUS_DESC);

        restRegistrationStatusMockMvc.perform(put("/api/registration-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistrationStatus)))
            .andExpect(status().isOk());

        // Validate the RegistrationStatus in the database
        List<RegistrationStatus> registrationStatusList = registrationStatusRepository.findAll();
        assertThat(registrationStatusList).hasSize(databaseSizeBeforeUpdate);
        RegistrationStatus testRegistrationStatus = registrationStatusList.get(registrationStatusList.size() - 1);
        assertThat(testRegistrationStatus.getRegistrationStatusCode()).isEqualTo(UPDATED_REGISTRATION_STATUS_CODE);
        assertThat(testRegistrationStatus.getRegistrationsStatusDesc()).isEqualTo(UPDATED_REGISTRATIONS_STATUS_DESC);

        // Validate the RegistrationStatus in Elasticsearch
        verify(mockRegistrationStatusSearchRepository, times(1)).save(testRegistrationStatus);
    }

    @Test
    public void updateNonExistingRegistrationStatus() throws Exception {
        int databaseSizeBeforeUpdate = registrationStatusRepository.findAll().size();

        // Create the RegistrationStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationStatusMockMvc.perform(put("/api/registration-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationStatus)))
            .andExpect(status().isBadRequest());

        // Validate the RegistrationStatus in the database
        List<RegistrationStatus> registrationStatusList = registrationStatusRepository.findAll();
        assertThat(registrationStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RegistrationStatus in Elasticsearch
        verify(mockRegistrationStatusSearchRepository, times(0)).save(registrationStatus);
    }

    @Test
    public void deleteRegistrationStatus() throws Exception {
        // Initialize the database
        registrationStatusService.save(registrationStatus);

        int databaseSizeBeforeDelete = registrationStatusRepository.findAll().size();

        // Delete the registrationStatus
        restRegistrationStatusMockMvc.perform(delete("/api/registration-statuses/{id}", registrationStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistrationStatus> registrationStatusList = registrationStatusRepository.findAll();
        assertThat(registrationStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RegistrationStatus in Elasticsearch
        verify(mockRegistrationStatusSearchRepository, times(1)).deleteById(registrationStatus.getId());
    }

    @Test
    public void searchRegistrationStatus() throws Exception {
        // Initialize the database
        registrationStatusService.save(registrationStatus);
        when(mockRegistrationStatusSearchRepository.search(queryStringQuery("id:" + registrationStatus.getId())))
            .thenReturn(Collections.singletonList(registrationStatus));
        // Search the registrationStatus
        restRegistrationStatusMockMvc.perform(get("/api/_search/registration-statuses?query=id:" + registrationStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrationStatus.getId())))
            .andExpect(jsonPath("$.[*].registrationStatusCode").value(hasItem(DEFAULT_REGISTRATION_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].registrationsStatusDesc").value(hasItem(DEFAULT_REGISTRATIONS_STATUS_DESC)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistrationStatus.class);
        RegistrationStatus registrationStatus1 = new RegistrationStatus();
        registrationStatus1.setId("id1");
        RegistrationStatus registrationStatus2 = new RegistrationStatus();
        registrationStatus2.setId(registrationStatus1.getId());
        assertThat(registrationStatus1).isEqualTo(registrationStatus2);
        registrationStatus2.setId("id2");
        assertThat(registrationStatus1).isNotEqualTo(registrationStatus2);
        registrationStatus1.setId(null);
        assertThat(registrationStatus1).isNotEqualTo(registrationStatus2);
    }
}
