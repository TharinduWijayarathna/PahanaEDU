package edu.pahana.model;

import java.time.LocalDateTime;

/**
 * Activity model class representing system activities and events.
 * Used for tracking user actions and system events in the activity timeline.
 */
public class Activity {
    private int activityId;
    private String activityType;
    private String description;
    private String entityType;
    private int entityId;
    private String entityName;
    private String username;
    private LocalDateTime timestamp;
    private String status;
    
    /**
     * Activity types constants
     */
    public static final String TYPE_BILL_CREATED = "bill_created";
    public static final String TYPE_BILL_UPDATED = "bill_updated";
    public static final String TYPE_BILL_PAID = "bill_paid";
    public static final String TYPE_CUSTOMER_ADDED = "customer_added";
    public static final String TYPE_CUSTOMER_UPDATED = "customer_updated";
    public static final String TYPE_PRODUCT_ADDED = "product_added";
    public static final String TYPE_PRODUCT_UPDATED = "product_updated";
    public static final String TYPE_USER_LOGIN = "user_login";
    public static final String TYPE_SYSTEM_BACKUP = "system_backup";
    public static final String TYPE_SYSTEM_MAINTENANCE = "system_maintenance";
    
    /**
     * Default constructor
     */
    public Activity() {
        this.timestamp = LocalDateTime.now();
        this.status = "completed";
    }
    
    /**
     * Constructor with basic fields
     */
    public Activity(String activityType, String description, String entityType, int entityId, String entityName, String username) {
        this();
        this.activityType = activityType;
        this.description = description;
        this.entityType = entityType;
        this.entityId = entityId;
        this.entityName = entityName;
        this.username = username;
    }
    
    /**
     * Constructor for system activities without entity
     */
    public Activity(String activityType, String description, String username) {
        this();
        this.activityType = activityType;
        this.description = description;
        this.username = username;
    }
    
    // Getters and Setters
    public int getActivityId() {
        return activityId;
    }
    
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
    
    public String getActivityType() {
        return activityType;
    }
    
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getEntityType() {
        return entityType;
    }
    
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    
    public int getEntityId() {
        return entityId;
    }
    
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
    
    public String getEntityName() {
        return entityName;
    }
    
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Get activity color based on type
     */
    public String getActivityColor() {
        switch (activityType) {
            case TYPE_BILL_CREATED:
            case TYPE_BILL_PAID:
                return "green";
            case TYPE_CUSTOMER_ADDED:
            case TYPE_CUSTOMER_UPDATED:
                return "blue";
            case TYPE_PRODUCT_ADDED:
            case TYPE_PRODUCT_UPDATED:
                return "purple";
            case TYPE_SYSTEM_BACKUP:
            case TYPE_SYSTEM_MAINTENANCE:
                return "orange";
            case TYPE_USER_LOGIN:
                return "gray";
            default:
                return "gray";
        }
    }
    
    /**
     * Get activity icon based on type
     */
    public String getActivityIcon() {
        switch (activityType) {
            case TYPE_BILL_CREATED:
            case TYPE_BILL_UPDATED:
            case TYPE_BILL_PAID:
                return "fas fa-file-invoice-dollar";
            case TYPE_CUSTOMER_ADDED:
            case TYPE_CUSTOMER_UPDATED:
                return "fas fa-user";
            case TYPE_PRODUCT_ADDED:
            case TYPE_PRODUCT_UPDATED:
                return "fas fa-book";
            case TYPE_SYSTEM_BACKUP:
                return "fas fa-database";
            case TYPE_SYSTEM_MAINTENANCE:
                return "fas fa-tools";
            case TYPE_USER_LOGIN:
                return "fas fa-sign-in-alt";
            default:
                return "fas fa-info-circle";
        }
    }
    
    /**
     * Get formatted time ago string
     */
    public String getTimeAgo() {
        if (timestamp == null) {
            return "Unknown time";
        }
        
        LocalDateTime now = LocalDateTime.now();
        long seconds = java.time.Duration.between(timestamp, now).getSeconds();
        
        if (seconds < 60) {
            return seconds + " seconds ago";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        } else {
            long days = seconds / 86400;
            return days + " day" + (days > 1 ? "s" : "") + " ago";
        }
    }
    
    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityType='" + activityType + '\'' +
                ", description='" + description + '\'' +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                ", entityName='" + entityName + '\'' +
                ", username='" + username + '\'' +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
