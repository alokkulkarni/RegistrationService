package com.alok.registration.repository;

import com.alok.registration.domain.Registrations;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Registrations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationsRepository extends MongoRepository<Registrations, String> {

}
