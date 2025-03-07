package com.example.auth.model;

import java.util.List;

/**
 * Represents a user in the system with personal information and communication details.
 * This class holds user identity, contact information, and Azure Communication Services integration details.
 */
public class User {
    /** Unique identifier for the user in the database */
    String id;
    /** User's identifier, potentially from an external system */
    String userId;
    /** User's family name/last name */
    String familyName;
    /** User's given name/first name */
    String givenName;
    /** User's postal code/ZIP code */
    String postalCode;
    /** User's state or province */
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
     * Gets the user's unique identifier.
     * @return The unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's unique identifier.
     * @param id The unique identifier to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user's identifier.
     * @return The user identifier
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user's identifier.
     * @param userId The user identifier to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's family name.
     * @return The family name
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Sets the user's family name.
     * @param familyName The family name to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Gets the user's given name.
     * @return The given name
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the user's given name.
     * @param givenName The given name to set
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Gets the user's postal code.
     * @return The postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the user's postal code.
     * @param postalCode The postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the user's state or province.
     * @return The state or province
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the user's state or province.
     * @param state The state or province to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the user's city.
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the user's city.
     * @param city The city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the user's street address.
     * @return The street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the user's street address.
     * @param streetAddress The street address to set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Gets the list of email addresses associated with the user.
     * @return The list of email addresses
     */
    public List<String> getEmails() {
        return emails;
    }

    /**
     * Sets the list of email addresses associated with the user.
     * @param emails The list of email addresses to set
     */
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    /**
     * Gets the user's Azure Communication Services user ID.
     * @return The ACS user ID
     */
    public String getAcsUserId() {
        return acsUserId;
    }

    /**
     * Sets the user's Azure Communication Services user ID.
     * @param acsUserId The ACS user ID to set
     */
    public void setAcsUserId(String acsUserId) {
        this.acsUserId = acsUserId;
    }

    /**
     * Gets the user's device token for push notifications.
     * @return The device token
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * Sets the user's device token for push notifications.
     * @param deviceToken The device token to set
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}