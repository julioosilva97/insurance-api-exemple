package com.julioosilva97.insurancesapi.domain.model;

import com.julioosilva97.insurancesapi.api.payload.InsuranceDTO;

import java.util.Objects;

public abstract class Insurance {
    public String type;
    public Double cost;

    public InsuranceDTO toDto(){
        return InsuranceDTO.builder().cost(this.cost).type(this.type).build();
    }
}
