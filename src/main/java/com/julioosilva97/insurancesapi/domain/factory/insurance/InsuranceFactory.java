package com.julioosilva97.insurancesapi.domain.factory.insurance;

import com.julioosilva97.insurancesapi.domain.model.Customer;
import com.julioosilva97.insurancesapi.domain.model.Insurance;

import java.util.Optional;

public interface InsuranceFactory {
    Optional<Insurance> getInsuranceByCustomer(Customer customer);
}
