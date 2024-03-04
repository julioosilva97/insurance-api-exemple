package com.julioosilva97.insurancesapi.domain.factory.insurance;

import com.julioosilva97.insurancesapi.domain.model.Customer;
import com.julioosilva97.insurancesapi.domain.model.Insurance;
import com.julioosilva97.insurancesapi.domain.model.InsuranceBasic;
import lombok.Data;

import java.util.Optional;

public class InsuranceBasicFactory implements InsuranceFactory{

    @Override
    public Optional<Insurance> getInsuranceByCustomer(Customer customer) {

        return Optional.of(new InsuranceBasic());
    }
}
