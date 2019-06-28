package com.alok.registration.web.rest;

import com.alok.registration.RegistrationServiceApp;
import com.alok.registration.domain.ProspectValidations;
import com.alok.registration.repository.ProspectValidationsRepository;
import com.alok.registration.repository.search.ProspectValidationsSearchRepository;
import com.alok.registration.service.ProspectValidationsService;
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


import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@Link ProspectValidationsResource} REST controller.
 */
@SpringBootTest(classes = RegistrationServiceApp.class)
public class ProspectValidationsResourceIT {

    private static final String DEFAULT_VALIDATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_RESULT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALIDATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProspectValidationsRepository prospectValidationsRepository;

    @Autowired
    private ProspectValidationsService prospectValidationsService;

    /**
     * This repository is mocked in the com.alok.registration.repository.search test package.
     *
     * @see com.alok.registration.repository.search.ProspectValidationsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProspectValidationsSearchRepository mockProspectValidationsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProspectValidationsMockMvc;

    private ProspectValidations prospectValidations;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProspectValidationsResource prospectValidationsResource = new ProspectValidationsResource(prospectValidationsService);
        this.restProspectValidationsMockMvc = MockMvcBuilders.standaloneSetup(prospectValidationsResource)
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
    public static ProspectValidations createEntity() {
        ProspectValidations prospectValidations = new ProspectValidations()
            .validationType(DEFAULT_VALIDATION_TYPE)
            .validationResult(DEFAULT_VALIDATION_RESULT)
            .validationDate(DEFAULT_VALIDATION_DATE);
        return prospectValidations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProspectValidations createUpdatedEntity() {
        ProspectValidations prospectValidations = new ProspectValidations()
            .validationType(UPDATED_VALIDATION_TYPE)
            .validationResult(UPDATED_VALIDATION_RESULT)
            .validationDate(UPDATED_VALIDATION_DATE);
        return prospectValidations;
    }

    @BeforeEach
    public void initTest() {
        prospectValidationsRepository.deleteAll();
        prospectValidations = createEntity();
    }

    @Test
    public void createProspectValidations() throws Exception {
        int databaseSizeBeforeCreate = prospectValidationsRepository.findAll().size();

        // Create the ProspectValidations
        restProspectValidationsMockMvc.perform(post("/api/prospect-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectValidations)))
            .andExpect(status().isCreated());

        // Validate the ProspectValidations in the database
        List<ProspectValidations> prospectValidationsList = prospectValidationsRepository.findAll();
        assertThat(prospectValidationsList).hasSize(databaseSizeBeforeCreate + 1);
        ProspectValidations testProspectValidations = prospectValidationsList.get(prospectValidationsList.size() - 1);
        assertThat(testProspectValidations.getValidationType()).isEqualTo(DEFAULT_VALIDATION_TYPE);
        assertThat(testProspectValidations.getValidationResult()).isEqualTo(DEFAULT_VALIDATION_RESULT);
        assertThat(testProspectValidations.getValidationDate()).isEqualTo(DEFAULT_VALIDATION_DATE);

        // Validate the ProspectValidations in Elasticsearch
        verify(mockProspectValidationsSearchRepository, times(1)).save(testProspectValidations);
    }

    @Test
    public void createProspectValidationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prospectValidationsRepository.findAll().size();

        // Create the ProspectValidations with an existing ID
        prospectValidations.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProspectValidationsMockMvc.perform(post("/api/prospect-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectValidations)))
            .andExpect(status().isBadRequest());

        // Validate the ProspectValidations in the database
        List<ProspectValidations> prospectValidationsList = prospectValidationsRepository.findAll();
        assertThat(prospectValidationsList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProspectValidations in Elasticsearch
        verify(mockProspectValidationsSearchRepository, times(0)).save(prospectValidations);
    }


    @Test
    public void getAllProspectValidations() throws Exception {
        // Initialize the database
        prospectValidationsRepository.save(prospectValidations);

        // Get all the prospectValidationsList
        restProspectValidationsMockMvc.perform(get("/api/prospect-validations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospectValidations.getId())))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validationResult").value(hasItem(DEFAULT_VALIDATION_RESULT.toString())))
            .andExpect(jsonPath("$.[*].validationDate").value(hasItem(DEFAULT_VALIDATION_DATE.toString())));
    }
    
    @Test
    public void getProspectValidations() throws Exception {
        // Initialize the database
        prospectValidationsRepository.save(prospectValidations);

        // Get the prospectValidations
        restProspectValidationsMockMvc.perform(get("/api/prospect-validations/{id}", prospectValidations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prospectValidations.getId()))
            .andExpect(jsonPath("$.validationType").value(DEFAULT_VALIDATION_TYPE.toString()))
            .andExpect(jsonPath("$.validationResult").value(DEFAULT_VALIDATION_RESULT.toString()))
            .andExpect(jsonPath("$.validationDate").value(DEFAULT_VALIDATION_DATE.toString()));
    }

    @Test
    public void getNonExistingProspectValidations() throws Exception {
        // Get the prospectValidations
        restProspectValidationsMockMvc.perform(get("/api/prospect-validations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProspectValidations() throws Exception {
        // Initialize the database
        prospectValidationsService.save(prospectValidations);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockProspectValidationsSearchRepository);

        int databaseSizeBeforeUpdate = prospectValidationsRepository.findAll().size();

        // Update the prospectValidations
        ProspectValidations updatedProspectValidations = prospectValidationsRepository.findById(prospectValidations.getId()).get();
        updatedProspectValidations
            .validationType(UPDATED_VALIDATION_TYPE)
            .validationResult(UPDATED_VALIDATION_RESULT)
            .validationDate(UPDATED_VALIDATION_DATE);

        restProspectValidationsMockMvc.perform(put("/api/prospect-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProspectValidations)))
            .andExpect(status().isOk());

        // Validate the ProspectValidations in the database
        List<ProspectValidations> prospectValidationsList = prospectValidationsRepository.findAll();
        assertThat(prospectValidationsList).hasSize(databaseSizeBeforeUpdate);
        ProspectValidations testProspectValidations = prospectValidationsList.get(prospectValidationsList.size() - 1);
        assertThat(testProspectValidations.getValidationType()).isEqualTo(UPDATED_VALIDATION_TYPE);
        assertThat(testProspectValidations.getValidationResult()).isEqualTo(UPDATED_VALIDATION_RESULT);
        assertThat(testProspectValidations.getValidationDate()).isEqualTo(UPDATED_VALIDATION_DATE);

        // Validate the ProspectValidations in Elasticsearch
        verify(mockProspectValidationsSearchRepository, times(1)).save(testProspectValidations);
    }

    @Test
    public void updateNonExistingProspectValidations() throws Exception {
        int databaseSizeBeforeUpdate = prospectValidationsRepository.findAll().size();

        // Create the ProspectValidations

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProspectValidationsMockMvc.perform(put("/api/prospect-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectValidations)))
            .andExpect(status().isBadRequest());

        // Validate the ProspectValidations in the database
        List<ProspectValidations> prospectValidationsList = prospectValidationsRepository.findAll();
        assertThat(prospectValidationsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProspectValidations in Elasticsearch
        verify(mockProspectValidationsSearchRepository, times(0)).save(prospectValidations);
    }

    @Test
    public void deleteProspectValidations() throws Exception {
        // Initialize the database
        prospectValidationsService.save(prospectValidations);

        int databaseSizeBeforeDelete = prospectValidationsRepository.findAll().size();

        // Delete the prospectValidations
        restProspectValidationsMockMvc.perform(delete("/api/prospect-validations/{id}", prospectValidations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProspectValidations> prospectValidationsList = prospectValidationsRepository.findAll();
        assertThat(prospectValidationsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProspectValidations in Elasticsearch
        verify(mockProspectValidationsSearchRepository, times(1)).deleteById(prospectValidations.getId());
    }

    @Test
    public void searchProspectValidations() throws Exception {
        // Initialize the database
        prospectValidationsService.save(prospectValidations);
        when(mockProspectValidationsSearchRepository.search(queryStringQuery("id:" + prospectValidations.getId())))
            .thenReturn(Collections.singletonList(prospectValidations));
        // Search the prospectValidations
        restProspectValidationsMockMvc.perform(get("/api/_search/prospect-validations?query=id:" + prospectValidations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospectValidations.getId())))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE)))
            .andExpect(jsonPath("$.[*].validationResult").value(hasItem(DEFAULT_VALIDATION_RESULT)))
            .andExpect(jsonPath("$.[*].validationDate").value(hasItem(DEFAULT_VALIDATION_DATE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProspectValidations.class);
        ProspectValidations prospectValidations1 = new ProspectValidations();
        prospectValidations1.setId("id1");
        ProspectValidations prospectValidations2 = new ProspectValidations();
        prospectValidations2.setId(prospectValidations1.getId());
        assertThat(prospectValidations1).isEqualTo(prospectValidations2);
        prospectValidations2.setId("id2");
        assertThat(prospectValidations1).isNotEqualTo(prospectValidations2);
        prospectValidations1.setId(null);
        assertThat(prospectValidations1).isNotEqualTo(prospectValidations2);
    }
}
