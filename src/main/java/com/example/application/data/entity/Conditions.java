package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Table
@Entity
public class Conditions extends AbstractEntity {

    @NotEmpty
    private String entityTypeName = "";
    @NotEmpty
    private String eventTypeCode = "";
    @NotEmpty
    private String accountingLineCode = "";
    @NotEmpty
    private String sourceName = "";

    @NotEmpty
    @Lob
    private String conditionField = "";

    @Override
    public String toString() {
        return entityTypeName + " " + accountingLineCode;
    }

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getAccountingLineCode() {
        return accountingLineCode;
    }

    public void setAccountingLineCode(String accountingLineCode) {
        this.accountingLineCode = accountingLineCode;
    }

    public String getConditionField() {
        return conditionField;
    }

    public void setConditionField(String conditionField) {
        this.conditionField = conditionField;
    }
}