package com.alok.registration.web.rest;

import com.alok.registration.RegistrationServiceApp;
import com.alok.registration.domain.Registrations;
import com.alok.registration.repository.RegistrationsRepository;
import com.alok.registration.repository.search.RegistrationsSearchRepository;
import com.alok.registration.service.RegistrationsService;
import com.alok.registration.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * Integration tests for the {@Link RegistrationsResource} REST controller.
 */
@SpringBootTest(classes = RegistrationServiceApp.class)
public class RegistrationsResourceIT {

    private static final String DEFAULT_REGISTRATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FIRST_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_REGISTRATION_CANCELLED = false;
    private static final Boolean UPDATED_REGISTRATION_CANCELLED = true;

    private static final LocalDate DEFAULT_REGISTRATION_CANCELLATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTRATION_CANCELLATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RegistrationsRepository registrationsRepository;

    @Autowired
    private RegistrationsService registrationsService;

    /**
     * This repository is mocked in the com.alok.registration.repository.search test package.
     *
     * @see com.alok.registration.repository.search.RegistrationsSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegistrationsSearchRepository mockRegistrationsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restRegistrationsMockMvc;

    private Registrations registrations;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistrationsResource registrationsResource = new RegistrationsResource(registrationsService);
        this.restRegistrationsMockMvc = MockMvcBuilders.standaloneSetup(registrationsResource)
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
    public static Registrations createEntity() {
        Registrations registrations = new Registrations()
            .registrationType(DEFAULT_REGISTRATION_TYPE)
            .firstRegistrationDate(DEFAULT_FIRST_REGISTRATION_DATE)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .registrationCancelled(DEFAULT_REGISTRATION_CANCELLED)
            .registrationCancellationDate(DEFAULT_REGISTRATION_CANCELLATION_DATE);
        return registrations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Registrations createUpdatedEntity() {
        Registrations registrations = new Registrations()
            .registrationType(UPDATED_REGISTRATION_TYPE)
            .firstRegistrationDate(UPDATED_FIRST_REGISTRATION_DATE)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .registrationCancelled(UPDATED_REGISTRATION_CANCELLED)
            .registrationCancellationDate(UPDATED_REGISTRATION_CANCELLATION_DATE);
        return registrations;
    }

    @BeforeEach
    public void initTest() {
        registrationsRepository.deleteAll();
        registrations = createEntity();
    }

    @Test
    public void createRegistrations() throws Exception {
        int databaseSizeBeforeCreate = registrationsRepository.findAll().size();

        // Create the Registrations
        restRegistrationsMockMvc.perform(post("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrations)))
            .andExpect(status().isCreated());

        // Validate the Registrations in the database
        List<Registrations> registrationsList = registrationsRepository.findAll();
        assertThat(registrationsList).hasSize(databaseSizeBeforeCreate + 1);
        Registrations testRegistrations = registrationsList.get(registrationsList.size() - 1);
        assertThat(testRegistrations.getRegistrationType()).isEqualTo(DEFAULT_REGISTRATION_TYPE);
        assertThat(testRegistrations.getFirstRegistrationDate()).isEqualTo(DEFAULT_FIRST_REGISTRATION_DATE);
        assertThat(testRegistrations.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testRegistrations.isRegistrationCancelled()).isEqualTo(DEFAULT_REGISTRATION_CANCELLED);
        assertThat(testRegistrations.getRegistrationCancellationDate()).isEqualTo(DEFAULT_REGISTRATION_CANCELLATION_DATE);

        // Validate the Registrations in Elasticsearch
        verify(mockRegistrationsSearchRepository, times(1)).save(testRegistrations);
    }

    @Test
    public void createRegistrationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registrationsRepository.findAll().size();

        // Create the Registrations with an existing ID
        registrations.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationsMockMvc.perform(post("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrations)))
            .andExpect(status().isBadRequest());

        // Validate the Registrations in the database
        List<Registrations> registrationsList = registrationsRepository.findAll();
        assertThat(registrationsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Registrations in Elasticsearch
        verify(mockRegistrationsSearchRepository, times(0)).save(registrations);
    }


    @Test
    public void getAllRegistrations() throws Exception {
        // Initialize the database
        registrationsRepository.save(registrations);

        // Get all the registrationsList
        restRegistrationsMockMvc.perform(get("/api/registrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrations.getId())))
            .andExpect(jsonPath("$.[*].registrationType").value(hasItem(DEFAULT_REGISTRATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].firstRegistrationDate").value(hasItem(DEFAULT_FIRST_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].registrationCancelled").value(hasItem(DEFAULT_REGISTRATION_CANCELLED.booleanValue())))
            .andExpect(jsonPath("$.[*].registrationCancellationDate").value(hasItem(DEFAULT_REGISTRATION_CANCELLATION_DATE.toString())));
    }
    
    @Test
    public void getRegistrations() throws Exception {
        // Initialize the database
        registrationsRepository.save(registrations);

        // Get the registrations
        restRegistrationsMockMvc.perform(get("/api/registrations/{id}", registrations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registrations.getId()))
            .andExpect(jsonPath("$.registrationType").value(DEFAULT_REGISTRATION_TYPE.toString()))
            .andExpect(jsonPath("$.firstRegistrationDate").value(DEFAULT_FIRST_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.registrationCancelled").value(DEFAULT_REGISTRATION_CANCELLED.booleanValue()))
            .andExpect(jsonPath("$.registrationCancellationDate").value(DEFAULT_REGISTRATION_CANCELLATION_DATE.toString()));
    }

    @Test
    public void getNonExistingRegistrations() throws Exception {
        // Get the registrations
        restRegistrationsMockMvc.perform(get("/api/registrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRegistrations() throws Exception {
        // Initialize the database
        registrationsService.save(registrations);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRegistrationsSearchRepository);

        int databaseSizeBeforeUpdate = registrationsRepository.findAll().size();

        // Update the registrations
        Registrations updatedRegistrations = registrationsRepository.findById(registrations.getId()).get();
        updatedRegistrations
            .registrationType(UPDATED_REGISTRATION_TYPE)
            .firstRegistrationDate(UPDATED_FIRST_REGISTRATION_DATE)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .registrationCancelled(UPDATED_REGISTRATION_CANCELLED)
            .registrationCancellationDate(UPDATED_REGISTRATION_CANCELLATION_DATE);

        restRegistrationsMockMvc.perform(put("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistrations)))
            .andExpect(status().isOk());

        // Validate the Registrations in the database
        List<Registrations> registrationsList = registrationsRepository.findAll();
        assertThat(registrationsList).hasSize(databaseSizeBeforeUpdate);
        Registrations testRegistrations = registrationsList.get(registrationsList.size() - 1);
        assertThat(testRegistrations.getRegistrationType()).isEqualTo(UPDATED_REGISTRATION_TYPE);
        assertThat(testRegistrations.getFirstRegistrationDate()).isEqualTo(UPDATED_FIRST_REGISTRATION_DATE);
        assertThat(testRegistrations.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testRegistrations.isRegistrationCancelled()).isEqualTo(UPDATED_REGISTRATION_CANCELLED);
        assertThat(testRegistrations.getRegistrationCancellationDate()).isEqualTo(UPDATED_REGISTRATION_CANCELLATION_DATE);

        // Validate the Registrations in Elasticsearch
        verify(mockRegistrationsSearchRepository, times(1)).save(testRegistrations);
    }

    @Test
    public void updateNonExistingRegistrations() throws Exception {
        int databaseSizeBeforeUpdate = registrationsRepository.findAll().size();

        // Create the Registrations

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationsMockMvc.perform(put("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrations)))
            .andExpect(status().isBadRequest());

        // Validate the Registrations in the database
        List<Registrations> registrationsList = registrationsRepository.findAll();
        assertThat(registrationsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Registrations in Elasticsearch
        verify(mockRegistrationsSearchRepository, times(0)).save(registrations);
    }

    @Test
    public void deleteRegistrations() throws Exception {
        // Initialize the database
        registrationsService.save(registrations);

        int databaseSizeBeforeDelete = registrationsRepository.findAll().size();

        // Delete the registrations
        restRegistrationsMockMvc.perform(delete("/api/registrations/{id}", registrations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Registrations> registrationsList = registrationsRepository.findAll();
        assertThat(registrationsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Registrations in Elasticsearch
        verify(mockRegistrationsSearchRepository, times(1)).deleteById(registrations.getId());
    }

    @Test
    public void searchRegistrations() throws Exception {
        // Initialize the database
        registrationsService.save(registrations);
        when(mockRegistrationsSearchRepository.search(queryStringQuery("id:" + registrations.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(registrations), PageRequest.of(0, 1), 1));
        // Search the registrations
        restRegistrationsMockMvc.perform(get("/api/_search/registrations?query=id:" + registrations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrations.getId())))
            .andExpect(jsonPath("$.[*].registrationType").value(hasItem(DEFAULT_REGISTRATION_TYPE)))
            .andExpect(jsonPath("$.[*].firstRegistrationDate").value(hasItem(DEFAULT_FIRST_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].registrationCancelled").value(hasItem(DEFAULT_REGISTRATION_CANCELLED.booleanValue())))
            .andExpect(jsonPath("$.[*].registrationCancellationDate").value(hasItem(DEFAULT_REGISTRATION_CANCELLATION_DATE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Registrations.class);
        Registrations registrations1 = new Registrations();
        registrations1.setId("id1");
        Registrations registrations2 = new Registrations();
        registrations2.setId(registrations1.getId());
        assertThat(registrations1).isEqualTo(registrations2);
        registrations2.setId("id2");
        assertThat(registrations1).isNotEqualTo(registrations2);
        registrations1.setId(null);
        assertThat(registrations1).isNotEqualTo(registrations2);
    }
}
