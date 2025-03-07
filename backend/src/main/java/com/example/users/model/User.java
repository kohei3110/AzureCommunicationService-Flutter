package com.example.users.model;

import java.util.List;

/**
 * Represents a user entity in the system.
 * This class contains user identification, personal information, address details,
 * and communication-related data for integration with Azure Communication Services.
 */
public class User {
    /** Unique identifier for the user record */
    String id;
    /** User identifier for application purposes */
    String userId;
    /** User's family name/last name */
    String familyName;
    /** User's given name/first name */
    String givenName;
    /** User's postal/zip code */
    String postalCode;
    /** User's state/province/region */
    String state;
    /** User's city */
    String city;
    /** User's street address */
    String streetAddress;
    /** List of email addresses associated with the user */
    List<String> emails;
    /** Azure Communication Services user identifier */
    String acsUserId;
    /** Device token for push notifications */
    String deviceToken;

    /**
     * Gets the unique identifier of the user.
     * 
     * @return The user's unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the user.
     * 
     * @param id The unique identifier to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user identifier.
     * 
     * @return The user identifier
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user identifier.
     * 
     * @param userId The user identifier to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's family name/last name.
     * 
     * @return The user's family name
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Sets the user's family name/last name.
     * 
     * @param familyName The family name to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Gets the user's given name/first name.
     * 
     * @return The user's given name
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the user's given name/first name.
     * 
     * @param givenName The given name to set
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Gets the user's postal/zip code.
     * 
     * @return The user's postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the user's postal/zip code.
     * 
     * @param postalCode The postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the user's state/province/region.
     * 
     * @return The user's state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the user's state/province/region.
     * 
     * @param state The state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the user's city.
     * 
     * @return The user's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the user's city.
     * 
     * @param city The city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the user's street address.
     * 
     * @return The user's street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the user's street address.
     * 
     * @param streetAddress The street address to set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Gets the list of email addresses associated with the user.
     * 
     * @return The list of user's email addresses
     */
    public List<String> getEmails() {
        return emails;
    }

    /**
     * Sets the list of email addresses associated with the user.
     * 
     * @param emails The list of email addresses to set
     */
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    /**
     * Gets the Azure Communication Services user identifier.
     * 
     * @return The ACS user identifier
     */
    public String getAcsUserId() {
        return acsUserId;
    }

    /**
     * Sets the Azure Communication Services user identifier.
     * 
     * @param acsUserId The ACS user identifier to set
     */
    public void setAcsUserId(String acsUserId) {
        this.acsUserId = acsUserId;
    }

    /**
     * Gets the device token used for push notifications.
     * 
     * @return The user's device token
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * Sets the device token used for push notifications.
     * 
     * @param deviceToken The device token to set
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}