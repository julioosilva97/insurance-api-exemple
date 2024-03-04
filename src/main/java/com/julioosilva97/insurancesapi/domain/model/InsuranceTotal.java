package com.julioosilva97.insurancesapi.domain.model;

public class InsuranceTotal extends Insurance{
    private final String TOTAL_DESCRIPTION = "total";
    public InsuranceTotal() {
        this.type = TOTAL_DESCRIPTION;
        this.cost = 4.0;
    }
}
