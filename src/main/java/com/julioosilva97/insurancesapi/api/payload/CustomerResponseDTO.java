package com.julioosilva97.insurancesapi.api.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerResponseDTO {

    private String name;
    private List<InsuranceDTO> insurances;
}
