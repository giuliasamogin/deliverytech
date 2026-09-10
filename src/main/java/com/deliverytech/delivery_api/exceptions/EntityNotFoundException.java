package com.deliverytech.delivery_api.exceptions;

public class EntityNotFoundException extends RuntimeException {
    private String errorCode;
    private String entityName;
    private Object entityId;

    public EntityNotFoundException(String message) {
        super(message);
        this.errorCode = "ENTITY_NOT_FOUND";
    }

    public EntityNotFoundException(String entityName, Object entityId) {
        super(String.format("%s com ID %s não foi encontrado(a)", entityName, entityId));
        this.entityName = entityName;
        this.entityId = entityId;
        this.errorCode = "ENTITY_NOT_FOUND";
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public Object getEntityId() {
        return entityId;
    }
}