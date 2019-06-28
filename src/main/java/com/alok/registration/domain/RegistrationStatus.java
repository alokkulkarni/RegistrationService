package com.alok.registration.domain;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * The RegistrationStatus entity.
 */
@ApiModel(description = "The RegistrationStatus entity.")
@Document(collection = "registration_status")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "registrationstatus")
public class RegistrationStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("registration_status_code")
    private Integer registrationStatusCode;

    @Field("registrations_status_desc")
    private String registrationsStatusDesc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRegistrationStatusCode() {
        return registrationStatusCode;
    }

    public RegistrationStatus registrationStatusCode(Integer registrationStatusCode) {
        this.registrationStatusCode = registrationStatusCode;
        return this;
    }

    public void setRegistrationStatusCode(Integer registrationStatusCode) {
        this.registrationStatusCode = registrationStatusCode;
    }

    public String getRegistrationsStatusDesc() {
        return registrationsStatusDesc;
    }

    public RegistrationStatus registrationsStatusDesc(String registrationsStatusDesc) {
        this.registrationsStatusDesc = registrationsStatusDesc;
        return this;
    }

    public void setRegistrationsStatusDesc(String registrationsStatusDesc) {
        this.registrationsStatusDesc = registrationsStatusDesc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistrationStatus)) {
            return false;
        }
        return id != null && id.equals(((RegistrationStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegistrationStatus{" +
            "id=" + getId() +
            ", registrationStatusCode=" + getRegistrationStatusCode() +
            ", registrationsStatusDesc='" + getRegistrationsStatusDesc() + "'" +
            "}";
    }
}
