package com.alok.registration.web.rest;

import com.alok.registration.RegistrationServiceApp;
import com.alok.registration.domain.CustomerValidations;
import com.alok.registration.repository.CustomerValidationsRepository;
import com.alok.registration.repository.search.CustomerValidationsSearchRepository;
import com.alok.registration.service.CustomerValidationsService;
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
 * Integration tests for the {@Link CustomerValidationsResource} REST controller.
 */
@SpringBootTest(classes = RegistrationServiceApp.class)
public class CustomerValidationsResourceIT {

    private static final String DEFAULT_VALIDATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_RESULT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALIDATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CustomerValidationsRepository customerValidationsRepository;

    @Autowired
    private CustomerValidationsService customerValidationsService;

    /**
     * This repository is mocked in the com.alok.registration.repository.search test package.
     *
     * @see com.alok.registration.repository.search.CustomerValidationsSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerValidationsSearchRepository mockCustomerValidationsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCustomerValidationsMockMvc;

    private CustomerValidations customerValidations;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerValidationsResource customerValidationsResource = new CustomerValidationsResource(customerValidationsService);
        this.restCustomerValidationsMockMvc = MockMvcBuilders.standaloneSetup(customerValidationsResource)
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
    public static CustomerValidations createEntity() {
        CustomerValidations customerValidations = new CustomerValidations()
            .validationType(DEFAULT_VALIDATION_TYPE)
            .validationResult(DEFAULT_VALIDATION_RESULT)
            .validationDate(DEFAULT_VALIDATION_DATE);
        return customerValidations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerValidations createUpdatedEntity() {
        CustomerValidations customerValidations = new CustomerValidations()
            .validationType(UPDATED_VALIDATION_TYPE)
            .validationResult(UPDATED_VALIDATION_RESULT)
            .validationDate(UPDATED_VALIDATION_DATE);
        return customerValidations;
    }

    @BeforeEach
    public void initTest() {
        customerValidationsRepository.deleteAll();
        customerValidations = createEntity();
    }

    @Test
    public void createCustomerValidations() throws Exception {
        int databaseSizeBeforeCreate = customerValidationsRepository.findAll().size();

        // Create the CustomerValidations
        restCustomerValidationsMockMvc.perform(post("/api/customer-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerValidations)))
            .andExpect(status().isCreated());

        // Validate the CustomerValidations in the database
        List<CustomerValidations> customerValidationsList = customerValidationsRepository.findAll();
        assertThat(customerValidationsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerValidations testCustomerValidations = customerValidationsList.get(customerValidationsList.size() - 1);
        assertThat(testCustomerValidations.getValidationType()).isEqualTo(DEFAULT_VALIDATION_TYPE);
        assertThat(testCustomerValidations.getValidationResult()).isEqualTo(DEFAULT_VALIDATION_RESULT);
        assertThat(testCustomerValidations.getValidationDate()).isEqualTo(DEFAULT_VALIDATION_DATE);

        // Validate the CustomerValidations in Elasticsearch
        verify(mockCustomerValidationsSearchRepository, times(1)).save(testCustomerValidations);
    }

    @Test
    public void createCustomerValidationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerValidationsRepository.findAll().size();

        // Create the CustomerValidations with an existing ID
        customerValidations.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerValidationsMockMvc.perform(post("/api/customer-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerValidations)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerValidations in the database
        List<CustomerValidations> customerValidationsList = customerValidationsRepository.findAll();
        assertThat(customerValidationsList).hasSize(databaseSizeBeforeCreate);

        // Validate the CustomerValidations in Elasticsearch
        verify(mockCustomerValidationsSearchRepository, times(0)).save(customerValidations);
    }


    @Test
    public void getAllCustomerValidations() throws Exception {
        // Initialize the database
        customerValidationsRepository.save(customerValidations);

        // Get all the customerValidationsList
        restCustomerValidationsMockMvc.perform(get("/api/customer-validations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerValidations.getId())))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validationResult").value(hasItem(DEFAULT_VALIDATION_RESULT.toString())))
            .andExpect(jsonPath("$.[*].validationDate").value(hasItem(DEFAULT_VALIDATION_DATE.toString())));
    }
    
    @Test
    public void getCustomerValidations() throws Exception {
        // Initialize the database
        customerValidationsRepository.save(customerValidations);

        // Get the customerValidations
        restCustomerValidationsMockMvc.perform(get("/api/customer-validations/{id}", customerValidations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerValidations.getId()))
            .andExpect(jsonPath("$.validationType").value(DEFAULT_VALIDATION_TYPE.toString()))
            .andExpect(jsonPath("$.validationResult").value(DEFAULT_VALIDATION_RESULT.toString()))
            .andExpect(jsonPath("$.validationDate").value(DEFAULT_VALIDATION_DATE.toString()));
    }

    @Test
    public void getNonExistingCustomerValidations() throws Exception {
        // Get the customerValidations
        restCustomerValidationsMockMvc.perform(get("/api/customer-validations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCustomerValidations() throws Exception {
        // Initialize the database
        customerValidationsService.save(customerValidations);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCustomerValidationsSearchRepository);

        int databaseSizeBeforeUpdate = customerValidationsRepository.findAll().size();

        // Update the customerValidations
        CustomerValidations updatedCustomerValidations = customerValidationsRepository.findById(customerValidations.getId()).get();
        updatedCustomerValidations
            .validationType(UPDATED_VALIDATION_TYPE)
            .validationResult(UPDATED_VALIDATION_RESULT)
            .validationDate(UPDATED_VALIDATION_DATE);

        restCustomerValidationsMockMvc.perform(put("/api/customer-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerValidations)))
            .andExpect(status().isOk());

        // Validate the CustomerValidations in the database
        List<CustomerValidations> customerValidationsList = customerValidationsRepository.findAll();
        assertThat(customerValidationsList).hasSize(databaseSizeBeforeUpdate);
        CustomerValidations testCustomerValidations = customerValidationsList.get(customerValidationsList.size() - 1);
        assertThat(testCustomerValidations.getValidationType()).isEqualTo(UPDATED_VALIDATION_TYPE);
        assertThat(testCustomerValidations.getValidationResult()).isEqualTo(UPDATED_VALIDATION_RESULT);
        assertThat(testCustomerValidations.getValidationDate()).isEqualTo(UPDATED_VALIDATION_DATE);

        // Validate the CustomerValidations in Elasticsearch
        verify(mockCustomerValidationsSearchRepository, times(1)).save(testCustomerValidations);
    }

    @Test
    public void updateNonExistingCustomerValidations() throws Exception {
        int databaseSizeBeforeUpdate = customerValidationsRepository.findAll().size();

        // Create the CustomerValidations

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerValidationsMockMvc.perform(put("/api/customer-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerValidations)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerValidations in the database
        List<CustomerValidations> customerValidationsList = customerValidationsRepository.findAll();
        assertThat(customerValidationsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CustomerValidations in Elasticsearch
        verify(mockCustomerValidationsSearchRepository, times(0)).save(customerValidations);
    }

    @Test
    public void deleteCustomerValidations() throws Exception {
        // Initialize the database
        customerValidationsService.save(customerValidations);

        int databaseSizeBeforeDelete = customerValidationsRepository.findAll().size();

        // Delete the customerValidations
        restCustomerValidationsMockMvc.perform(delete("/api/customer-validations/{id}", customerValidations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerValidations> customerValidationsList = customerValidationsRepository.findAll();
        assertThat(customerValidationsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CustomerValidations in Elasticsearch
        verify(mockCustomerValidationsSearchRepository, times(1)).deleteById(customerValidations.getId());
    }

    @Test
    public void searchCustomerValidations() throws Exception {
        // Initialize the database
        customerValidationsService.save(customerValidations);
        when(mockCustomerValidationsSearchRepository.search(queryStringQuery("id:" + customerValidations.getId())))
            .thenReturn(Collections.singletonList(customerValidations));
        // Search the customerValidations
        restCustomerValidationsMockMvc.perform(get("/api/_search/customer-validations?query=id:" + customerValidations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerValidations.getId())))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE)))
            .andExpect(jsonPath("$.[*].validationResult").value(hasItem(DEFAULT_VALIDATION_RESULT)))
            .andExpect(jsonPath("$.[*].validationDate").value(hasItem(DEFAULT_VALIDATION_DATE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerValidations.class);
        CustomerValidations customerValidations1 = new CustomerValidations();
        customerValidations1.setId("id1");
        CustomerValidations customerValidations2 = new CustomerValidations();
        customerValidations2.setId(customerValidations1.getId());
        assertThat(customerValidations1).isEqualTo(customerValidations2);
        customerValidations2.setId("id2");
        assertThat(customerValidations1).isNotEqualTo(customerValidations2);
        customerValidations1.setId(null);
        assertThat(customerValidations1).isNotEqualTo(customerValidations2);
    }
}
