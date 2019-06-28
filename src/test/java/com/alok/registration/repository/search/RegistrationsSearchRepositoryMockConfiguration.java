package com.alok.registration.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link RegistrationsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RegistrationsSearchRepositoryMockConfiguration {

    @MockBean
    private RegistrationsSearchRepository mockRegistrationsSearchRepository;

}
