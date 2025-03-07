package com.example.calls.model;

import java.util.Date;
import java.util.Map;

/**
 * Represents an event in the system.
 * This class is used to model event data received from event sources or sent to event handlers.
 */
public class Event {
    /** The source of the event or the domain where the event occurred. */
    public String topic;
    
    /** The specific resource or entity that the event is about. */
    public String subject;
    
    /** The type of event, indicating what action or change occurred. */
    public String eventType;
    
    /** The timestamp when the event occurred. */
    public Date eventTime;
    
    /** Unique identifier for the event. */
    public String id;
    
    /** Version of the data schema used in the event. */
    public String dataVersion;
    
    /** Version of the event metadata schema. */
    public String metadataVersion;
    
    /** The payload of the event containing event-specific information. */
    public Map<String, Object> data;
}
