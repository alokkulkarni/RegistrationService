package com.alok.registration.repository;

import com.alok.registration.domain.RegistrationStatus;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the RegistrationStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationStatusRepository extends MongoRepository<RegistrationStatus, String> {

}
