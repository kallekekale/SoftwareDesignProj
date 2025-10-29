package fi.tuni.softwaredesign.openbrewerydb;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for Open Brewery DB API responses. Maps to the brewery data returned by the
 * Open Brewery DB API.
 */
public class OpenBreweryDbDto {

  private String id;
  private String name;

  @JsonProperty("brewery_type")
  private String breweryType;

  @JsonProperty("address_1")
  private String address1;

  @JsonProperty("address_2")
  private String address2;

  @JsonProperty("address_3")
  private String address3;

  private String city;

  @JsonProperty("state_province")
  private String stateProvince;

  @JsonProperty("postal_code")
  private String postalCode;

  private String country;
  private Double longitude;
  private Double latitude;
  private String phone;

  @JsonProperty("website_url")
  private String websiteUrl;

  private String state;
  private String street;

  // Getters and Setters

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBreweryType() {
    return breweryType;
  }

  public void setBreweryType(String breweryType) {
    this.breweryType = breweryType;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getAddress3() {
    return address3;
  }

  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStateProvince() {
    return stateProvince;
  }

  public void setStateProvince(String stateProvince) {
    this.stateProvince = stateProvince;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getWebsiteUrl() {
    return websiteUrl;
  }

  public void setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }
}
