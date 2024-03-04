package com.julioosilva97.insurancesapi.domain.service;

import com.julioosilva97.insurancesapi.api.payload.CustomerDTO;
import com.julioosilva97.insurancesapi.api.payload.InsuranceDTO;
import com.julioosilva97.insurancesapi.domain.model.Customer;
import com.julioosilva97.insurancesapi.domain.model.Insurance;

import java.util.List;

public interface InsuranceService {
    List<InsuranceDTO> getInsurancesByCustomer(CustomerDTO customer);
}
