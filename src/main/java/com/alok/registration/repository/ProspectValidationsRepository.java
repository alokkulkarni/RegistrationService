package com.alok.registration.repository;

import com.alok.registration.domain.ProspectValidations;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ProspectValidations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProspectValidationsRepository extends MongoRepository<ProspectValidations, String> {

}
