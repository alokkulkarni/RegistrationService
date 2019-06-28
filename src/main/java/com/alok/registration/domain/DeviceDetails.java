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
 * The Device Detials entity.
 */
@ApiModel(description = "The Device Detials entity.")
@Document(collection = "device_details")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "devicedetails")
public class DeviceDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("device_aid")
    private String deviceAID;

    @Field("device_name")
    private String deviceName;

    @Field("device_type")
    private String deviceType;

    @Field("device_type_version")
    private String deviceTypeVersion;

    @Field("device_os")
    private String deviceOS;

    @Field("device_o_dversion")
    private String deviceODversion;

    @Field("device_country")
    private String deviceCountry;

    @Field("device_country_iso")
    private String deviceCountryISO;

    @Field("device_service_provider")
    private String deviceServiceProvider;

    @Field("device_master")
    private Boolean deviceMaster;

    @Field("device_application_version")
    private String deviceApplicationVersion;

    @Field("device_application_build_version")
    private String deviceApplicationBuildVersion;

    @Field("device_application_installation_date")
    private LocalDate deviceApplicationInstallationDate;

    @DBRef
    @Field("customer")
    @JsonIgnoreProperties("deviceDetails")
    private Customer customer;

    @DBRef
    @Field("prospect")
    @JsonIgnoreProperties("deviceDetails")
    private Prospect prospect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceAID() {
        return deviceAID;
    }

    public DeviceDetails deviceAID(String deviceAID) {
        this.deviceAID = deviceAID;
        return this;
    }

    public void setDeviceAID(String deviceAID) {
        this.deviceAID = deviceAID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public DeviceDetails deviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public DeviceDetails deviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTypeVersion() {
        return deviceTypeVersion;
    }

    public DeviceDetails deviceTypeVersion(String deviceTypeVersion) {
        this.deviceTypeVersion = deviceTypeVersion;
        return this;
    }

    public void setDeviceTypeVersion(String deviceTypeVersion) {
        this.deviceTypeVersion = deviceTypeVersion;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public DeviceDetails deviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
        return this;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceODversion() {
        return deviceODversion;
    }

    public DeviceDetails deviceODversion(String deviceODversion) {
        this.deviceODversion = deviceODversion;
        return this;
    }

    public void setDeviceODversion(String deviceODversion) {
        this.deviceODversion = deviceODversion;
    }

    public String getDeviceCountry() {
        return deviceCountry;
    }

    public DeviceDetails deviceCountry(String deviceCountry) {
        this.deviceCountry = deviceCountry;
        return this;
    }

    public void setDeviceCountry(String deviceCountry) {
        this.deviceCountry = deviceCountry;
    }

    public String getDeviceCountryISO() {
        return deviceCountryISO;
    }

    public DeviceDetails deviceCountryISO(String deviceCountryISO) {
        this.deviceCountryISO = deviceCountryISO;
        return this;
    }

    public void setDeviceCountryISO(String deviceCountryISO) {
        this.deviceCountryISO = deviceCountryISO;
    }

    public String getDeviceServiceProvider() {
        return deviceServiceProvider;
    }

    public DeviceDetails deviceServiceProvider(String deviceServiceProvider) {
        this.deviceServiceProvider = deviceServiceProvider;
        return this;
    }

    public void setDeviceServiceProvider(String deviceServiceProvider) {
        this.deviceServiceProvider = deviceServiceProvider;
    }

    public Boolean isDeviceMaster() {
        return deviceMaster;
    }

    public DeviceDetails deviceMaster(Boolean deviceMaster) {
        this.deviceMaster = deviceMaster;
        return this;
    }

    public void setDeviceMaster(Boolean deviceMaster) {
        this.deviceMaster = deviceMaster;
    }

    public String getDeviceApplicationVersion() {
        return deviceApplicationVersion;
    }

    public DeviceDetails deviceApplicationVersion(String deviceApplicationVersion) {
        this.deviceApplicationVersion = deviceApplicationVersion;
        return this;
    }

    public void setDeviceApplicationVersion(String deviceApplicationVersion) {
        this.deviceApplicationVersion = deviceApplicationVersion;
    }

    public String getDeviceApplicationBuildVersion() {
        return deviceApplicationBuildVersion;
    }

    public DeviceDetails deviceApplicationBuildVersion(String deviceApplicationBuildVersion) {
        this.deviceApplicationBuildVersion = deviceApplicationBuildVersion;
        return this;
    }

    public void setDeviceApplicationBuildVersion(String deviceApplicationBuildVersion) {
        this.deviceApplicationBuildVersion = deviceApplicationBuildVersion;
    }

    public LocalDate getDeviceApplicationInstallationDate() {
        return deviceApplicationInstallationDate;
    }

    public DeviceDetails deviceApplicationInstallationDate(LocalDate deviceApplicationInstallationDate) {
        this.deviceApplicationInstallationDate = deviceApplicationInstallationDate;
        return this;
    }

    public void setDeviceApplicationInstallationDate(LocalDate deviceApplicationInstallationDate) {
        this.deviceApplicationInstallationDate = deviceApplicationInstallationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public DeviceDetails customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Prospect getProspect() {
        return prospect;
    }

    public DeviceDetails prospect(Prospect prospect) {
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
        if (!(o instanceof DeviceDetails)) {
            return false;
        }
        return id != null && id.equals(((DeviceDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeviceDetails{" +
            "id=" + getId() +
            ", deviceAID='" + getDeviceAID() + "'" +
            ", deviceName='" + getDeviceName() + "'" +
            ", deviceType='" + getDeviceType() + "'" +
            ", deviceTypeVersion='" + getDeviceTypeVersion() + "'" +
            ", deviceOS='" + getDeviceOS() + "'" +
            ", deviceODversion='" + getDeviceODversion() + "'" +
            ", deviceCountry='" + getDeviceCountry() + "'" +
            ", deviceCountryISO='" + getDeviceCountryISO() + "'" +
            ", deviceServiceProvider='" + getDeviceServiceProvider() + "'" +
            ", deviceMaster='" + isDeviceMaster() + "'" +
            ", deviceApplicationVersion='" + getDeviceApplicationVersion() + "'" +
            ", deviceApplicationBuildVersion='" + getDeviceApplicationBuildVersion() + "'" +
            ", deviceApplicationInstallationDate='" + getDeviceApplicationInstallationDate() + "'" +
            "}";
    }
}
