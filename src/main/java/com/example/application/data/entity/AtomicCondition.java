package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Table
@Entity
public class AtomicCondition extends AbstractEntity{

    @NotEmpty
    private String milesField = "";
    @NotEmpty
    private String equalityOperator = "";
    @NotEmpty
    private String valueToCheck = "";
    @NotEmpty
    private String groupOperator = "";

    public String getMilesField() {
        return milesField;
    }

    public void setMilesField(String milesField) {
        this.milesField = milesField;
    }

    public String getEqualityOperator() {
        return equalityOperator;
    }

    public void setEqualityOperator(String equalityOperator) {
        this.equalityOperator = equalityOperator;
    }

    public String getValueToCheck() {
        return valueToCheck;
    }

    public void setValueToCheck(String valueToCheck) {
        this.valueToCheck = valueToCheck;
    }

    public String getGroupOperator() {
        return groupOperator;
    }

    public void setGroupOperator(String groupOperator) {
        this.groupOperator = groupOperator;
    }
}
