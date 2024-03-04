package com.julioosilva97.insurancesapi.domain.service.impl;

import com.julioosilva97.insurancesapi.api.payload.CustomerDTO;
import com.julioosilva97.insurancesapi.api.payload.InsuranceDTO;
import com.julioosilva97.insurancesapi.domain.model.Customer;
import com.julioosilva97.insurancesapi.domain.model.Insurance;
import com.julioosilva97.insurancesapi.domain.factory.insurance.InsuranceEnum;
import com.julioosilva97.insurancesapi.domain.service.InsuranceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    @Override
    public List<InsuranceDTO> getInsurancesByCustomer(CustomerDTO customerDto) {

        List<Insurance> insurances = new ArrayList<>();

        for(InsuranceEnum e: InsuranceEnum.values()){
            e.getFactory().getInsuranceByCustomer(new Customer().toEntity(customerDto))
                    .ifPresent(insurance -> insurances.add(insurance));
        }

        return insurances.stream().map(insurance -> insurance.toDto()).toList();
    }
}
