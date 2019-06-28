package com.alok.registration.repository;

import com.alok.registration.domain.CustomerValidations;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CustomerValidations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerValidationsRepository extends MongoRepository<CustomerValidations, String> {

}
