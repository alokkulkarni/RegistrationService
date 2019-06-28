package com.alok.registration.domain;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Prospect entity.
 */
@ApiModel(description = "The Prospect entity.")
@Document(collection = "prospect")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prospect")
public class Prospect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("prospect_id")
    private Long prospectID;

    @Field("prospect_arn")
    private String prospectARN;

    @NotNull
    @Pattern(regexp = "[A-Z]+")
    @Field("first_name")
    private String firstName;

    @Field("middle_name")
    private String middleName;

    @NotNull
    @Pattern(regexp = "[A-Z]+")
    @Field("last_name")
    private String lastName;

    @NotNull
    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    @NotNull
    @Field("mobile_number")
    private String mobileNumber;

    @NotNull
    @Field("date_of_birth")
    private String dateOfBirth;

    @Field("gender")
    private String gender;

    @DBRef
    @Field("address")
    private Set<Address> addresses = new HashSet<>();

    @DBRef
    @Field("prospectValidations")
    private Set<ProspectValidations> prospectValidations = new HashSet<>();

    @DBRef
    @Field("deviceDetails")
    private Set<DeviceDetails> deviceDetails = new HashSet<>();

    @DBRef
    @Field("registrations")
    private Set<Registrations> registrations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getProspectID() {
        return prospectID;
    }

    public Prospect prospectID(Long prospectID) {
        this.prospectID = prospectID;
        return this;
    }

    public void setProspectID(Long prospectID) {
        this.prospectID = prospectID;
    }

    public String getProspectARN() {
        return prospectARN;
    }

    public Prospect prospectARN(String prospectARN) {
        this.prospectARN = prospectARN;
        return this;
    }

    public void setProspectARN(String prospectARN) {
        this.prospectARN = prospectARN;
    }

    public String getFirstName() {
        return firstName;
    }

    public Prospect firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Prospect middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Prospect lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Prospect email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Prospect phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Prospect mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Prospect dateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public Prospect gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Prospect addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Prospect addAddress(Address address) {
        this.addresses.add(address);
        address.setProspect(this);
        return this;
    }

    public Prospect removeAddress(Address address) {
        this.addresses.remove(address);
        address.setProspect(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<ProspectValidations> getProspectValidations() {
        return prospectValidations;
    }

    public Prospect prospectValidations(Set<ProspectValidations> prospectValidations) {
        this.prospectValidations = prospectValidations;
        return this;
    }

    public Prospect addProspectValidations(ProspectValidations prospectValidations) {
        this.prospectValidations.add(prospectValidations);
        prospectValidations.setProspect(this);
        return this;
    }

    public Prospect removeProspectValidations(ProspectValidations prospectValidations) {
        this.prospectValidations.remove(prospectValidations);
        prospectValidations.setProspect(null);
        return this;
    }

    public void setProspectValidations(Set<ProspectValidations> prospectValidations) {
        this.prospectValidations = prospectValidations;
    }

    public Set<DeviceDetails> getDeviceDetails() {
        return deviceDetails;
    }

    public Prospect deviceDetails(Set<DeviceDetails> deviceDetails) {
        this.deviceDetails = deviceDetails;
        return this;
    }

    public Prospect addDeviceDetails(DeviceDetails deviceDetails) {
        this.deviceDetails.add(deviceDetails);
        deviceDetails.setProspect(this);
        return this;
    }

    public Prospect removeDeviceDetails(DeviceDetails deviceDetails) {
        this.deviceDetails.remove(deviceDetails);
        deviceDetails.setProspect(null);
        return this;
    }

    public void setDeviceDetails(Set<DeviceDetails> deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public Set<Registrations> getRegistrations() {
        return registrations;
    }

    public Prospect registrations(Set<Registrations> registrations) {
        this.registrations = registrations;
        return this;
    }

    public Prospect addRegistrations(Registrations registrations) {
        this.registrations.add(registrations);
        registrations.setProspect(this);
        return this;
    }

    public Prospect removeRegistrations(Registrations registrations) {
        this.registrations.remove(registrations);
        registrations.setProspect(null);
        return this;
    }

    public void setRegistrations(Set<Registrations> registrations) {
        this.registrations = registrations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prospect)) {
            return false;
        }
        return id != null && id.equals(((Prospect) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prospect{" +
            "id=" + getId() +
            ", prospectID=" + getProspectID() +
            ", prospectARN='" + getProspectARN() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
