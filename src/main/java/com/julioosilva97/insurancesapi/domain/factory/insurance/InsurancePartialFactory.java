package com.julioosilva97.insurancesapi.domain.factory.insurance;

import com.julioosilva97.insurancesapi.InsurancesApiApplication;
import com.julioosilva97.insurancesapi.domain.model.Customer;
import com.julioosilva97.insurancesapi.domain.model.Insurance;
import com.julioosilva97.insurancesapi.domain.model.InsurancePartial;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class InsurancePartialFactory implements InsuranceFactory{

    private static final Logger logger = LoggerFactory.getLogger(InsurancePartialFactory.class);

    private final String SP_STATE = "SP";

    @Override
    public Optional<Insurance> getInsuranceByCustomer(Customer customer) {

        if(customer.getVehicleValue() <= 70000
                && (customer.getAge() < 30 && customer.getLocation().equals(SP_STATE))){
            return createInsurancePartial("vehicleValue <= 70000 && age < 30 && location ="+SP_STATE);
        }

        if((customer.getVehicleValue() > 70000 && customer.getVehicleValue() <100000)
                && customer.getLocation().equals(SP_STATE)){
            return createInsurancePartial("vehicleValue > 70000 && vehicleValue < 10000 && location ="+SP_STATE);
        }

        if(customer.getVehicleValue() >= 100000 && customer.getAge() < 30){
            return createInsurancePartial("vehicleValue >= 100000 && age <30 ");
        }

        return Optional.empty();
    }

    private Optional<Insurance> createInsurancePartial(String rule){
        logger.info("c=InsurancePartialFactory, m=getInsuranceByCustomer, rule={}",rule);
        return Optional.of(new InsurancePartial());
    }
}
