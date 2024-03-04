package com.julioosilva97.insurancesapi.domain.model;

public class InsuranceBasic extends Insurance {

    private final String BASIC_DESCRIPTION = "basic";

    public InsuranceBasic() {
        this.type = BASIC_DESCRIPTION;
        this.cost = 2.0;
    }
}
