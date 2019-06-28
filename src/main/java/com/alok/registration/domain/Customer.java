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
 * The Customer entity.
 */
@ApiModel(description = "The Customer entity.")
@Document(collection = "customer")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("customer_id")
    private Long customerID;

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
    @Field("customerValidations")
    private Set<CustomerValidations> customerValidations = new HashSet<>();

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

    public Long getCustomerID() {
        return customerID;
    }

    public Customer customerID(Long customerID) {
        this.customerID = customerID;
        return this;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Customer middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Customer phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Customer mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Customer dateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public Customer gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Customer addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Customer addAddress(Address address) {
        this.addresses.add(address);
        address.setCustomer(this);
        return this;
    }

    public Customer removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCustomer(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<CustomerValidations> getCustomerValidations() {
        return customerValidations;
    }

    public Customer customerValidations(Set<CustomerValidations> customerValidations) {
        this.customerValidations = customerValidations;
        return this;
    }

    public Customer addCustomerValidations(CustomerValidations customerValidations) {
        this.customerValidations.add(customerValidations);
        customerValidations.setCustomer(this);
        return this;
    }

    public Customer removeCustomerValidations(CustomerValidations customerValidations) {
        this.customerValidations.remove(customerValidations);
        customerValidations.setCustomer(null);
        return this;
    }

    public void setCustomerValidations(Set<CustomerValidations> customerValidations) {
        this.customerValidations = customerValidations;
    }

    public Set<DeviceDetails> getDeviceDetails() {
        return deviceDetails;
    }

    public Customer deviceDetails(Set<DeviceDetails> deviceDetails) {
        this.deviceDetails = deviceDetails;
        return this;
    }

    public Customer addDeviceDetails(DeviceDetails deviceDetails) {
        this.deviceDetails.add(deviceDetails);
        deviceDetails.setCustomer(this);
        return this;
    }

    public Customer removeDeviceDetails(DeviceDetails deviceDetails) {
        this.deviceDetails.remove(deviceDetails);
        deviceDetails.setCustomer(null);
        return this;
    }

    public void setDeviceDetails(Set<DeviceDetails> deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public Set<Registrations> getRegistrations() {
        return registrations;
    }

    public Customer registrations(Set<Registrations> registrations) {
        this.registrations = registrations;
        return this;
    }

    public Customer addRegistrations(Registrations registrations) {
        this.registrations.add(registrations);
        registrations.setCustomer(this);
        return this;
    }

    public Customer removeRegistrations(Registrations registrations) {
        this.registrations.remove(registrations);
        registrations.setCustomer(null);
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
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerID=" + getCustomerID() +
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
