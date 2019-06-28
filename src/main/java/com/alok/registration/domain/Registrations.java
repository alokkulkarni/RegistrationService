package com.alok.registration.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * The Registrations entity.
 */
@ApiModel(description = "The Registrations entity.")
@Document(collection = "registrations")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "registrations")
public class Registrations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("registration_type")
    private String registrationType;

    @Field("first_registration_date")
    private LocalDate firstRegistrationDate;

    @Field("registration_date")
    private LocalDate registrationDate;

    @Field("registration_cancelled")
    private Boolean registrationCancelled;

    @Field("registration_cancellation_date")
    private LocalDate registrationCancellationDate;

    @DBRef
    @Field("customer")
    @JsonIgnoreProperties("registrations")
    private Customer customer;

    @DBRef
    @Field("registrationStatus")
    private RegistrationStatus registrationStatus;

    @DBRef
    @Field("deviceDetials")
    private DeviceDetails deviceDetials;

    @DBRef
    @Field("prospect")
    @JsonIgnoreProperties("registrations")
    private Prospect prospect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public Registrations registrationType(String registrationType) {
        this.registrationType = registrationType;
        return this;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public LocalDate getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public Registrations firstRegistrationDate(LocalDate firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
        return this;
    }

    public void setFirstRegistrationDate(LocalDate firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Registrations registrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean isRegistrationCancelled() {
        return registrationCancelled;
    }

    public Registrations registrationCancelled(Boolean registrationCancelled) {
        this.registrationCancelled = registrationCancelled;
        return this;
    }

    public void setRegistrationCancelled(Boolean registrationCancelled) {
        this.registrationCancelled = registrationCancelled;
    }

    public LocalDate getRegistrationCancellationDate() {
        return registrationCancellationDate;
    }

    public Registrations registrationCancellationDate(LocalDate registrationCancellationDate) {
        this.registrationCancellationDate = registrationCancellationDate;
        return this;
    }

    public void setRegistrationCancellationDate(LocalDate registrationCancellationDate) {
        this.registrationCancellationDate = registrationCancellationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Registrations customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public Registrations registrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
        return this;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public DeviceDetails getDeviceDetials() {
        return deviceDetials;
    }

    public Registrations deviceDetials(DeviceDetails deviceDetails) {
        this.deviceDetials = deviceDetails;
        return this;
    }

    public void setDeviceDetials(DeviceDetails deviceDetails) {
        this.deviceDetials = deviceDetails;
    }

    public Prospect getProspect() {
        return prospect;
    }

    public Registrations prospect(Prospect prospect) {
        this.prospect = prospect;
        return this;
    }

    public void setProspect(Prospect prospect) {
        this.prospect = prospect;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Registrations)) {
            return false;
        }
        return id != null && id.equals(((Registrations) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Registrations{" +
            "id=" + getId() +
            ", registrationType='" + getRegistrationType() + "'" +
            ", firstRegistrationDate='" + getFirstRegistrationDate() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", registrationCancelled='" + isRegistrationCancelled() + "'" +
            ", registrationCancellationDate='" + getRegistrationCancellationDate() + "'" +
            "}";
    }
}
