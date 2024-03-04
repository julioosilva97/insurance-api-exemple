package com.julioosilva97.insurancesapi.api.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuranceDTO {

    private String type;
    private Double cost;
}
