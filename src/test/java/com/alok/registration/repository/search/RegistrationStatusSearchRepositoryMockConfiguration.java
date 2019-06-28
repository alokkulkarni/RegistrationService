package com.alok.registration.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link RegistrationStatusSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RegistrationStatusSearchRepositoryMockConfiguration {

    @MockBean
    private RegistrationStatusSearchRepository mockRegistrationStatusSearchRepository;

}
