package com.julioosilva97.insurancesapi.api.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private CustomerResponseDTO customer;
}
