package com.julioosilva97.insurancesapi.domain.model;

public class InsurancePartial extends Insurance{
    private final String PARTIAL_DESCRIPTION = "partial";
    public InsurancePartial() {
        this.type = PARTIAL_DESCRIPTION;
        this.cost = 3.0;
    }
}
