package com.alok.registration.web.rest;

import com.alok.registration.RegistrationServiceApp;
import com.alok.registration.domain.DeviceDetails;
import com.alok.registration.repository.DeviceDetailsRepository;
import com.alok.registration.repository.search.DeviceDetailsSearchRepository;
import com.alok.registration.service.DeviceDetailsService;
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
 * Integration tests for the {@Link DeviceDetailsResource} REST controller.
 */
@SpringBootTest(classes = RegistrationServiceApp.class)
public class DeviceDetailsResourceIT {

    private static final String DEFAULT_DEVICE_AID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_AID = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_TYPE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_TYPE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_OS = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_OS = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_O_DVERSION = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_O_DVERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_COUNTRY_ISO = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_COUNTRY_ISO = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_SERVICE_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_SERVICE_PROVIDER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEVICE_MASTER = false;
    private static final Boolean UPDATED_DEVICE_MASTER = true;

    private static final String DEFAULT_DEVICE_APPLICATION_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_APPLICATION_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_APPLICATION_BUILD_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_APPLICATION_BUILD_VERSION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEVICE_APPLICATION_INSTALLATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEVICE_APPLICATION_INSTALLATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DeviceDetailsRepository deviceDetailsRepository;

    @Autowired
    private DeviceDetailsService deviceDetailsService;

    /**
     * This repository is mocked in the com.alok.registration.repository.search test package.
     *
     * @see com.alok.registration.repository.search.DeviceDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private DeviceDetailsSearchRepository mockDeviceDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDeviceDetailsMockMvc;

    private DeviceDetails deviceDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceDetailsResource deviceDetailsResource = new DeviceDetailsResource(deviceDetailsService);
        this.restDeviceDetailsMockMvc = MockMvcBuilders.standaloneSetup(deviceDetailsResource)
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
    public static DeviceDetails createEntity() {
        DeviceDetails deviceDetails = new DeviceDetails()
            .deviceAID(DEFAULT_DEVICE_AID)
            .deviceName(DEFAULT_DEVICE_NAME)
            .deviceType(DEFAULT_DEVICE_TYPE)
            .deviceTypeVersion(DEFAULT_DEVICE_TYPE_VERSION)
            .deviceOS(DEFAULT_DEVICE_OS)
            .deviceODversion(DEFAULT_DEVICE_O_DVERSION)
            .deviceCountry(DEFAULT_DEVICE_COUNTRY)
            .deviceCountryISO(DEFAULT_DEVICE_COUNTRY_ISO)
            .deviceServiceProvider(DEFAULT_DEVICE_SERVICE_PROVIDER)
            .deviceMaster(DEFAULT_DEVICE_MASTER)
            .deviceApplicationVersion(DEFAULT_DEVICE_APPLICATION_VERSION)
            .deviceApplicationBuildVersion(DEFAULT_DEVICE_APPLICATION_BUILD_VERSION)
            .deviceApplicationInstallationDate(DEFAULT_DEVICE_APPLICATION_INSTALLATION_DATE);
        return deviceDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceDetails createUpdatedEntity() {
        DeviceDetails deviceDetails = new DeviceDetails()
            .deviceAID(UPDATED_DEVICE_AID)
            .deviceName(UPDATED_DEVICE_NAME)
            .deviceType(UPDATED_DEVICE_TYPE)
            .deviceTypeVersion(UPDATED_DEVICE_TYPE_VERSION)
            .deviceOS(UPDATED_DEVICE_OS)
            .deviceODversion(UPDATED_DEVICE_O_DVERSION)
            .deviceCountry(UPDATED_DEVICE_COUNTRY)
            .deviceCountryISO(UPDATED_DEVICE_COUNTRY_ISO)
            .deviceServiceProvider(UPDATED_DEVICE_SERVICE_PROVIDER)
            .deviceMaster(UPDATED_DEVICE_MASTER)
            .deviceApplicationVersion(UPDATED_DEVICE_APPLICATION_VERSION)
            .deviceApplicationBuildVersion(UPDATED_DEVICE_APPLICATION_BUILD_VERSION)
            .deviceApplicationInstallationDate(UPDATED_DEVICE_APPLICATION_INSTALLATION_DATE);
        return deviceDetails;
    }

    @BeforeEach
    public void initTest() {
        deviceDetailsRepository.deleteAll();
        deviceDetails = createEntity();
    }

    @Test
    public void createDeviceDetails() throws Exception {
        int databaseSizeBeforeCreate = deviceDetailsRepository.findAll().size();

        // Create the DeviceDetails
        restDeviceDetailsMockMvc.perform(post("/api/device-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDetails)))
            .andExpect(status().isCreated());

        // Validate the DeviceDetails in the database
        List<DeviceDetails> deviceDetailsList = deviceDetailsRepository.findAll();
        assertThat(deviceDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceDetails testDeviceDetails = deviceDetailsList.get(deviceDetailsList.size() - 1);
        assertThat(testDeviceDetails.getDeviceAID()).isEqualTo(DEFAULT_DEVICE_AID);
        assertThat(testDeviceDetails.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);
        assertThat(testDeviceDetails.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testDeviceDetails.getDeviceTypeVersion()).isEqualTo(DEFAULT_DEVICE_TYPE_VERSION);
        assertThat(testDeviceDetails.getDeviceOS()).isEqualTo(DEFAULT_DEVICE_OS);
        assertThat(testDeviceDetails.getDeviceODversion()).isEqualTo(DEFAULT_DEVICE_O_DVERSION);
        assertThat(testDeviceDetails.getDeviceCountry()).isEqualTo(DEFAULT_DEVICE_COUNTRY);
        assertThat(testDeviceDetails.getDeviceCountryISO()).isEqualTo(DEFAULT_DEVICE_COUNTRY_ISO);
        assertThat(testDeviceDetails.getDeviceServiceProvider()).isEqualTo(DEFAULT_DEVICE_SERVICE_PROVIDER);
        assertThat(testDeviceDetails.isDeviceMaster()).isEqualTo(DEFAULT_DEVICE_MASTER);
        assertThat(testDeviceDetails.getDeviceApplicationVersion()).isEqualTo(DEFAULT_DEVICE_APPLICATION_VERSION);
        assertThat(testDeviceDetails.getDeviceApplicationBuildVersion()).isEqualTo(DEFAULT_DEVICE_APPLICATION_BUILD_VERSION);
        assertThat(testDeviceDetails.getDeviceApplicationInstallationDate()).isEqualTo(DEFAULT_DEVICE_APPLICATION_INSTALLATION_DATE);

        // Validate the DeviceDetails in Elasticsearch
        verify(mockDeviceDetailsSearchRepository, times(1)).save(testDeviceDetails);
    }

    @Test
    public void createDeviceDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceDetailsRepository.findAll().size();

        // Create the DeviceDetails with an existing ID
        deviceDetails.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceDetailsMockMvc.perform(post("/api/device-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDetails)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceDetails in the database
        List<DeviceDetails> deviceDetailsList = deviceDetailsRepository.findAll();
        assertThat(deviceDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the DeviceDetails in Elasticsearch
        verify(mockDeviceDetailsSearchRepository, times(0)).save(deviceDetails);
    }


    @Test
    public void getAllDeviceDetails() throws Exception {
        // Initialize the database
        deviceDetailsRepository.save(deviceDetails);

        // Get all the deviceDetailsList
        restDeviceDetailsMockMvc.perform(get("/api/device-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceDetails.getId())))
            .andExpect(jsonPath("$.[*].deviceAID").value(hasItem(DEFAULT_DEVICE_AID.toString())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deviceTypeVersion").value(hasItem(DEFAULT_DEVICE_TYPE_VERSION.toString())))
            .andExpect(jsonPath("$.[*].deviceOS").value(hasItem(DEFAULT_DEVICE_OS.toString())))
            .andExpect(jsonPath("$.[*].deviceODversion").value(hasItem(DEFAULT_DEVICE_O_DVERSION.toString())))
            .andExpect(jsonPath("$.[*].deviceCountry").value(hasItem(DEFAULT_DEVICE_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].deviceCountryISO").value(hasItem(DEFAULT_DEVICE_COUNTRY_ISO.toString())))
            .andExpect(jsonPath("$.[*].deviceServiceProvider").value(hasItem(DEFAULT_DEVICE_SERVICE_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].deviceMaster").value(hasItem(DEFAULT_DEVICE_MASTER.booleanValue())))
            .andExpect(jsonPath("$.[*].deviceApplicationVersion").value(hasItem(DEFAULT_DEVICE_APPLICATION_VERSION.toString())))
            .andExpect(jsonPath("$.[*].deviceApplicationBuildVersion").value(hasItem(DEFAULT_DEVICE_APPLICATION_BUILD_VERSION.toString())))
            .andExpect(jsonPath("$.[*].deviceApplicationInstallationDate").value(hasItem(DEFAULT_DEVICE_APPLICATION_INSTALLATION_DATE.toString())));
    }
    
    @Test
    public void getDeviceDetails() throws Exception {
        // Initialize the database
        deviceDetailsRepository.save(deviceDetails);

        // Get the deviceDetails
        restDeviceDetailsMockMvc.perform(get("/api/device-details/{id}", deviceDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceDetails.getId()))
            .andExpect(jsonPath("$.deviceAID").value(DEFAULT_DEVICE_AID.toString()))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME.toString()))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE.toString()))
            .andExpect(jsonPath("$.deviceTypeVersion").value(DEFAULT_DEVICE_TYPE_VERSION.toString()))
            .andExpect(jsonPath("$.deviceOS").value(DEFAULT_DEVICE_OS.toString()))
            .andExpect(jsonPath("$.deviceODversion").value(DEFAULT_DEVICE_O_DVERSION.toString()))
            .andExpect(jsonPath("$.deviceCountry").value(DEFAULT_DEVICE_COUNTRY.toString()))
            .andExpect(jsonPath("$.deviceCountryISO").value(DEFAULT_DEVICE_COUNTRY_ISO.toString()))
            .andExpect(jsonPath("$.deviceServiceProvider").value(DEFAULT_DEVICE_SERVICE_PROVIDER.toString()))
            .andExpect(jsonPath("$.deviceMaster").value(DEFAULT_DEVICE_MASTER.booleanValue()))
            .andExpect(jsonPath("$.deviceApplicationVersion").value(DEFAULT_DEVICE_APPLICATION_VERSION.toString()))
            .andExpect(jsonPath("$.deviceApplicationBuildVersion").value(DEFAULT_DEVICE_APPLICATION_BUILD_VERSION.toString()))
            .andExpect(jsonPath("$.deviceApplicationInstallationDate").value(DEFAULT_DEVICE_APPLICATION_INSTALLATION_DATE.toString()));
    }

    @Test
    public void getNonExistingDeviceDetails() throws Exception {
        // Get the deviceDetails
        restDeviceDetailsMockMvc.perform(get("/api/device-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDeviceDetails() throws Exception {
        // Initialize the database
        deviceDetailsService.save(deviceDetails);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDeviceDetailsSearchRepository);

        int databaseSizeBeforeUpdate = deviceDetailsRepository.findAll().size();

        // Update the deviceDetails
        DeviceDetails updatedDeviceDetails = deviceDetailsRepository.findById(deviceDetails.getId()).get();
        updatedDeviceDetails
            .deviceAID(UPDATED_DEVICE_AID)
            .deviceName(UPDATED_DEVICE_NAME)
            .deviceType(UPDATED_DEVICE_TYPE)
            .deviceTypeVersion(UPDATED_DEVICE_TYPE_VERSION)
            .deviceOS(UPDATED_DEVICE_OS)
            .deviceODversion(UPDATED_DEVICE_O_DVERSION)
            .deviceCountry(UPDATED_DEVICE_COUNTRY)
            .deviceCountryISO(UPDATED_DEVICE_COUNTRY_ISO)
            .deviceServiceProvider(UPDATED_DEVICE_SERVICE_PROVIDER)
            .deviceMaster(UPDATED_DEVICE_MASTER)
            .deviceApplicationVersion(UPDATED_DEVICE_APPLICATION_VERSION)
            .deviceApplicationBuildVersion(UPDATED_DEVICE_APPLICATION_BUILD_VERSION)
            .deviceApplicationInstallationDate(UPDATED_DEVICE_APPLICATION_INSTALLATION_DATE);

        restDeviceDetailsMockMvc.perform(put("/api/device-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeviceDetails)))
            .andExpect(status().isOk());

        // Validate the DeviceDetails in the database
        List<DeviceDetails> deviceDetailsList = deviceDetailsRepository.findAll();
        assertThat(deviceDetailsList).hasSize(databaseSizeBeforeUpdate);
        DeviceDetails testDeviceDetails = deviceDetailsList.get(deviceDetailsList.size() - 1);
        assertThat(testDeviceDetails.getDeviceAID()).isEqualTo(UPDATED_DEVICE_AID);
        assertThat(testDeviceDetails.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testDeviceDetails.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testDeviceDetails.getDeviceTypeVersion()).isEqualTo(UPDATED_DEVICE_TYPE_VERSION);
        assertThat(testDeviceDetails.getDeviceOS()).isEqualTo(UPDATED_DEVICE_OS);
        assertThat(testDeviceDetails.getDeviceODversion()).isEqualTo(UPDATED_DEVICE_O_DVERSION);
        assertThat(testDeviceDetails.getDeviceCountry()).isEqualTo(UPDATED_DEVICE_COUNTRY);
        assertThat(testDeviceDetails.getDeviceCountryISO()).isEqualTo(UPDATED_DEVICE_COUNTRY_ISO);
        assertThat(testDeviceDetails.getDeviceServiceProvider()).isEqualTo(UPDATED_DEVICE_SERVICE_PROVIDER);
        assertThat(testDeviceDetails.isDeviceMaster()).isEqualTo(UPDATED_DEVICE_MASTER);
        assertThat(testDeviceDetails.getDeviceApplicationVersion()).isEqualTo(UPDATED_DEVICE_APPLICATION_VERSION);
        assertThat(testDeviceDetails.getDeviceApplicationBuildVersion()).isEqualTo(UPDATED_DEVICE_APPLICATION_BUILD_VERSION);
        assertThat(testDeviceDetails.getDeviceApplicationInstallationDate()).isEqualTo(UPDATED_DEVICE_APPLICATION_INSTALLATION_DATE);

        // Validate the DeviceDetails in Elasticsearch
        verify(mockDeviceDetailsSearchRepository, times(1)).save(testDeviceDetails);
    }

    @Test
    public void updateNonExistingDeviceDetails() throws Exception {
        int databaseSizeBeforeUpdate = deviceDetailsRepository.findAll().size();

        // Create the DeviceDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceDetailsMockMvc.perform(put("/api/device-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDetails)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceDetails in the database
        List<DeviceDetails> deviceDetailsList = deviceDetailsRepository.findAll();
        assertThat(deviceDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DeviceDetails in Elasticsearch
        verify(mockDeviceDetailsSearchRepository, times(0)).save(deviceDetails);
    }

    @Test
    public void deleteDeviceDetails() throws Exception {
        // Initialize the database
        deviceDetailsService.save(deviceDetails);

        int databaseSizeBeforeDelete = deviceDetailsRepository.findAll().size();

        // Delete the deviceDetails
        restDeviceDetailsMockMvc.perform(delete("/api/device-details/{id}", deviceDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceDetails> deviceDetailsList = deviceDetailsRepository.findAll();
        assertThat(deviceDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DeviceDetails in Elasticsearch
        verify(mockDeviceDetailsSearchRepository, times(1)).deleteById(deviceDetails.getId());
    }

    @Test
    public void searchDeviceDetails() throws Exception {
        // Initialize the database
        deviceDetailsService.save(deviceDetails);
        when(mockDeviceDetailsSearchRepository.search(queryStringQuery("id:" + deviceDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(deviceDetails), PageRequest.of(0, 1), 1));
        // Search the deviceDetails
        restDeviceDetailsMockMvc.perform(get("/api/_search/device-details?query=id:" + deviceDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceDetails.getId())))
            .andExpect(jsonPath("$.[*].deviceAID").value(hasItem(DEFAULT_DEVICE_AID)))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME)))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE)))
            .andExpect(jsonPath("$.[*].deviceTypeVersion").value(hasItem(DEFAULT_DEVICE_TYPE_VERSION)))
            .andExpect(jsonPath("$.[*].deviceOS").value(hasItem(DEFAULT_DEVICE_OS)))
            .andExpect(jsonPath("$.[*].deviceODversion").value(hasItem(DEFAULT_DEVICE_O_DVERSION)))
            .andExpect(jsonPath("$.[*].deviceCountry").value(hasItem(DEFAULT_DEVICE_COUNTRY)))
            .andExpect(jsonPath("$.[*].deviceCountryISO").value(hasItem(DEFAULT_DEVICE_COUNTRY_ISO)))
            .andExpect(jsonPath("$.[*].deviceServiceProvider").value(hasItem(DEFAULT_DEVICE_SERVICE_PROVIDER)))
            .andExpect(jsonPath("$.[*].deviceMaster").value(hasItem(DEFAULT_DEVICE_MASTER.booleanValue())))
            .andExpect(jsonPath("$.[*].deviceApplicationVersion").value(hasItem(DEFAULT_DEVICE_APPLICATION_VERSION)))
            .andExpect(jsonPath("$.[*].deviceApplicationBuildVersion").value(hasItem(DEFAULT_DEVICE_APPLICATION_BUILD_VERSION)))
            .andExpect(jsonPath("$.[*].deviceApplicationInstallationDate").value(hasItem(DEFAULT_DEVICE_APPLICATION_INSTALLATION_DATE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceDetails.class);
        DeviceDetails deviceDetails1 = new DeviceDetails();
        deviceDetails1.setId("id1");
        DeviceDetails deviceDetails2 = new DeviceDetails();
        deviceDetails2.setId(deviceDetails1.getId());
        assertThat(deviceDetails1).isEqualTo(deviceDetails2);
        deviceDetails2.setId("id2");
        assertThat(deviceDetails1).isNotEqualTo(deviceDetails2);
        deviceDetails1.setId(null);
        assertThat(deviceDetails1).isNotEqualTo(deviceDetails2);
    }
}
