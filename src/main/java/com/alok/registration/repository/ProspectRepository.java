package com.alok.registration.repository;

import com.alok.registration.domain.Prospect;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Prospect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProspectRepository extends MongoRepository<Prospect, String> {

}
