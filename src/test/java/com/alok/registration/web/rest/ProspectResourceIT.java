package com.alok.registration.web.rest;

import com.alok.registration.RegistrationServiceApp;
import com.alok.registration.domain.Prospect;
import com.alok.registration.repository.ProspectRepository;
import com.alok.registration.repository.search.ProspectSearchRepository;
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
 * Integration tests for the {@Link ProspectResource} REST controller.
 */
@SpringBootTest(classes = RegistrationServiceApp.class)
public class ProspectResourceIT {

    private static final Long DEFAULT_PROSPECT_ID = 1L;
    private static final Long UPDATED_PROSPECT_ID = 2L;

    private static final String DEFAULT_PROSPECT_ARN = "AAAAAAAAAA";
    private static final String UPDATED_PROSPECT_ARN = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    @Autowired
    private ProspectRepository prospectRepository;

    /**
     * This repository is mocked in the com.alok.registration.repository.search test package.
     *
     * @see com.alok.registration.repository.search.ProspectSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProspectSearchRepository mockProspectSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProspectMockMvc;

    private Prospect prospect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProspectResource prospectResource = new ProspectResource(prospectRepository, mockProspectSearchRepository);
        this.restProspectMockMvc = MockMvcBuilders.standaloneSetup(prospectResource)
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
    public static Prospect createEntity() {
        Prospect prospect = new Prospect()
            .prospectID(DEFAULT_PROSPECT_ID)
            .prospectARN(DEFAULT_PROSPECT_ARN)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .gender(DEFAULT_GENDER);
        return prospect;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prospect createUpdatedEntity() {
        Prospect prospect = new Prospect()
            .prospectID(UPDATED_PROSPECT_ID)
            .prospectARN(UPDATED_PROSPECT_ARN)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER);
        return prospect;
    }

    @BeforeEach
    public void initTest() {
        prospectRepository.deleteAll();
        prospect = createEntity();
    }

    @Test
    public void createProspect() throws Exception {
        int databaseSizeBeforeCreate = prospectRepository.findAll().size();

        // Create the Prospect
        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospect)))
            .andExpect(status().isCreated());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeCreate + 1);
        Prospect testProspect = prospectList.get(prospectList.size() - 1);
        assertThat(testProspect.getProspectID()).isEqualTo(DEFAULT_PROSPECT_ID);
        assertThat(testProspect.getProspectARN()).isEqualTo(DEFAULT_PROSPECT_ARN);
        assertThat(testProspect.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProspect.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testProspect.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProspect.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProspect.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProspect.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testProspect.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testProspect.getGender()).isEqualTo(DEFAULT_GENDER);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(1)).save(testProspect);
    }

    @Test
    public void createProspectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prospectRepository.findAll().size();

        // Create the Prospect with an existing ID
        prospect.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospect)))
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeCreate);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(0)).save(prospect);
    }


    @Test
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = prospectRepository.findAll().size();
        // set the field null
        prospect.setFirstName(null);

        // Create the Prospect, which fails.

        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospect)))
            .andExpect(status().isBadRequest());

        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = prospectRepository.findAll().size();
        // set the field null
        prospect.setLastName(null);

        // Create the Prospect, which fails.

        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospect)))
            .andExpect(status().isBadRequest());

        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = prospectRepository.findAll().size();
        // set the field null
        prospect.setEmail(null);

        // Create the Prospect, which fails.

        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospect)))
            .andExpect(status().isBadRequest());

        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = prospectRepository.findAll().size();
        // set the field null
        prospect.setMobileNumber(null);

        // Create the Prospect, which fails.

        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospect)))
            .andExpect(status().isBadRequest());

        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = prospectRepository.findAll().size();
        // set the field null
        prospect.setDateOfBirth(null);

        // Create the Prospect, which fails.

        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospect)))
            .andExpect(status().isBadRequest());

        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProspects() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);

        // Get all the prospectList
        restProspectMockMvc.perform(get("/api/prospects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospect.getId())))
            .andExpect(jsonPath("$.[*].prospectID").value(hasItem(DEFAULT_PROSPECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].prospectARN").value(hasItem(DEFAULT_PROSPECT_ARN.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }
    
    @Test
    public void getProspect() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);

        // Get the prospect
        restProspectMockMvc.perform(get("/api/prospects/{id}", prospect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prospect.getId()))
            .andExpect(jsonPath("$.prospectID").value(DEFAULT_PROSPECT_ID.intValue()))
            .andExpect(jsonPath("$.prospectARN").value(DEFAULT_PROSPECT_ARN.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    public void getNonExistingProspect() throws Exception {
        // Get the prospect
        restProspectMockMvc.perform(get("/api/prospects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProspect() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);

        int databaseSizeBeforeUpdate = prospectRepository.findAll().size();

        // Update the prospect
        Prospect updatedProspect = prospectRepository.findById(prospect.getId()).get();
        updatedProspect
            .prospectID(UPDATED_PROSPECT_ID)
            .prospectARN(UPDATED_PROSPECT_ARN)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER);

        restProspectMockMvc.perform(put("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProspect)))
            .andExpect(status().isOk());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeUpdate);
        Prospect testProspect = prospectList.get(prospectList.size() - 1);
        assertThat(testProspect.getProspectID()).isEqualTo(UPDATED_PROSPECT_ID);
        assertThat(testProspect.getProspectARN()).isEqualTo(UPDATED_PROSPECT_ARN);
        assertThat(testProspect.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProspect.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testProspect.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProspect.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProspect.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProspect.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testProspect.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProspect.getGender()).isEqualTo(UPDATED_GENDER);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(1)).save(testProspect);
    }

    @Test
    public void updateNonExistingProspect() throws Exception {
        int databaseSizeBeforeUpdate = prospectRepository.findAll().size();

        // Create the Prospect

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProspectMockMvc.perform(put("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospect)))
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(0)).save(prospect);
    }

    @Test
    public void deleteProspect() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);

        int databaseSizeBeforeDelete = prospectRepository.findAll().size();

        // Delete the prospect
        restProspectMockMvc.perform(delete("/api/prospects/{id}", prospect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(1)).deleteById(prospect.getId());
    }

    @Test
    public void searchProspect() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);
        when(mockProspectSearchRepository.search(queryStringQuery("id:" + prospect.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prospect), PageRequest.of(0, 1), 1));
        // Search the prospect
        restProspectMockMvc.perform(get("/api/_search/prospects?query=id:" + prospect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospect.getId())))
            .andExpect(jsonPath("$.[*].prospectID").value(hasItem(DEFAULT_PROSPECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].prospectARN").value(hasItem(DEFAULT_PROSPECT_ARN)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prospect.class);
        Prospect prospect1 = new Prospect();
        prospect1.setId("id1");
        Prospect prospect2 = new Prospect();
        prospect2.setId(prospect1.getId());
        assertThat(prospect1).isEqualTo(prospect2);
        prospect2.setId("id2");
        assertThat(prospect1).isNotEqualTo(prospect2);
        prospect1.setId(null);
        assertThat(prospect1).isNotEqualTo(prospect2);
    }
}
