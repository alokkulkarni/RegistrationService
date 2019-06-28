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
 * The Customer Validations entity.
 */
@ApiModel(description = "The Customer Validations entity.")
@Document(collection = "customer_validations")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customervalidations")
public class CustomerValidations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("validation_type")
    private String validationType;

    @Field("validation_result")
    private String validationResult;

    @Field("validation_date")
    private LocalDate validationDate;

    @DBRef
    @Field("customer")
    @JsonIgnoreProperties("customerValidations")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValidationType() {
        return validationType;
    }

    public CustomerValidations validationType(String validationType) {
        this.validationType = validationType;
        return this;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public CustomerValidations validationResult(String validationResult) {
        this.validationResult = validationResult;
        return this;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public CustomerValidations validationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
        return this;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerValidations customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerValidations)) {
            return false;
        }
        return id != null && id.equals(((CustomerValidations) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerValidations{" +
            "id=" + getId() +
            ", validationType='" + getValidationType() + "'" +
            ", validationResult='" + getValidationResult() + "'" +
            ", validationDate='" + getValidationDate() + "'" +
            "}";
    }
}
