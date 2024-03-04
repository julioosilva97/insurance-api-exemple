package com.julioosilva97.insurancesapi.unit.domain.service.impl;

import com.julioosilva97.insurancesapi.api.payload.CustomerDTO;
import com.julioosilva97.insurancesapi.api.payload.InsuranceDTO;
import com.julioosilva97.insurancesapi.domain.model.Insurance;
import com.julioosilva97.insurancesapi.domain.model.InsuranceBasic;
import com.julioosilva97.insurancesapi.domain.model.InsurancePartial;
import com.julioosilva97.insurancesapi.domain.model.InsuranceTotal;
import com.julioosilva97.insurancesapi.domain.service.impl.InsuranceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class InsuranceServiceImplTest {

    @InjectMocks
    private InsuranceServiceImpl insuranceService;

    @Test
    void shouldReturnInsuranceBasic(){

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(29)
                .location("BH")
                .vehicle_value(70000.0)
                .build();

        List<InsuranceDTO> insuranceDTOList = insuranceService.getInsurancesByCustomer(customerDTO);

        Insurance insuranceBasic = new InsuranceBasic();
        InsuranceDTO expectedInsuranceDTO = insuranceBasic.toDto();

        Assertions.assertTrue(insuranceDTOList.contains(expectedInsuranceDTO));
    }

    @Test
    void shouldReturnInsurancePartialSpecificRule1(){

        //RULE vehicle_value >=100000 and age<30

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(29) // age <30
                .location("SP")
                .vehicle_value(100000.0) //>=100000
                .build();

        List<InsuranceDTO> insuranceDTOList = insuranceService.getInsurancesByCustomer(customerDTO);

        Insurance insurancePartial = new InsurancePartial();
        InsuranceDTO expectedInsuranceDTO = insurancePartial.toDto();

        Assertions.assertTrue(insuranceDTOList.contains(expectedInsuranceDTO));
    }

    @Test
    void shouldReturnInsurancePartialSpecificRule2(){

        //RULE vehicle_value >70000 and vehicle_value <100000 and location =SP

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(30)
                .location("SP") //SP
                .vehicle_value(90000.0) //>70000 and <100000
                .build();

        List<InsuranceDTO> insuranceDTOList = insuranceService.getInsurancesByCustomer(customerDTO);

        Insurance insurancePartial = new InsurancePartial();
        InsuranceDTO expectedInsuranceDTO = insurancePartial.toDto();

        Assertions.assertTrue(insuranceDTOList.contains(expectedInsuranceDTO));
    }

    @Test
    void shouldReturnInsurancePartialSpecificRule3(){

        //RULE vehicle_value <=70000 and location =SP and age <30

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(29) //< 30
                .location("SP") //SP
                .vehicle_value(70000.0) //<=70000
                .build();

        List<InsuranceDTO> insuranceDTOList = insuranceService.getInsurancesByCustomer(customerDTO);

        Insurance insurancePartial = new InsurancePartial();
        InsuranceDTO expectedInsuranceDTO = insurancePartial.toDto();

        Assertions.assertTrue(insuranceDTOList.contains(expectedInsuranceDTO));
    }

    @Test
    void shouldReturnInsuranceTotal(){

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(29)
                .location("BH")
                .vehicle_value(120000.0)
                .build();

        List<InsuranceDTO> insuranceDTOList = insuranceService.getInsurancesByCustomer(customerDTO);

        Insurance insuranceTotal = new InsuranceTotal();
        InsuranceDTO expectedInsuranceDTO = insuranceTotal.toDto();

        Assertions.assertTrue(insuranceDTOList.contains(expectedInsuranceDTO));
    }
}
