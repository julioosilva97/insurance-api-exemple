package com.julioosilva97.insurancesapi.domain.factory.insurance;

import com.julioosilva97.insurancesapi.domain.model.Customer;
import com.julioosilva97.insurancesapi.domain.model.Insurance;
import com.julioosilva97.insurancesapi.domain.model.InsuranceTotal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class InsuranceTotalFactory implements InsuranceFactory{

    private static final Logger logger = LoggerFactory.getLogger(InsuranceTotalFactory.class);

    @Override
    public Optional<Insurance> getInsuranceByCustomer(Customer customer) {

        if(customer.getVehicleValue() > 100000){
            logger.info("c=InsurancePartialFactory, m=getInsuranceByCustomer, rule={vehicleValue >= 100000}");
            return Optional.of(new InsuranceTotal());
        }
        return Optional.empty();
    }
}
