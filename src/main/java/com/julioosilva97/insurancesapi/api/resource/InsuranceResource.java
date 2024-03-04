package com.julioosilva97.insurancesapi.api.resource;

import com.julioosilva97.insurancesapi.InsurancesApiApplication;
import com.julioosilva97.insurancesapi.api.payload.*;
import com.julioosilva97.insurancesapi.domain.service.InsuranceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InsuranceResource {

    private static final Logger logger = LoggerFactory.getLogger(InsurancesApiApplication.class);

    @Autowired
    private InsuranceService insuranceService;

    @PostMapping("/insurances/customer") //TODO: validar se POST é melhor verbo nessa situação de pesquisa
    public ResponseEntity<CustomerResponse> getInsurancesByCustomer(@Valid @RequestBody CustomerRequest customerRequest){

        logger.info("c=InsuranceResource, m=getInsurancesByCustomer, url=/insurances/customer, customer={}",customerRequest.toString());
        CustomerDTO customerDTO = customerRequest.getCustomer();

        List<InsuranceDTO> insuranceDTO = insuranceService.getInsurancesByCustomer(customerDTO);

        CustomerResponse customerResponse = CustomerResponse.builder()
                .customer(
                        CustomerResponseDTO.builder()
                                .name(customerDTO.getName())
                                .insurances(insuranceDTO)
                                .build())
                .build();

        logger.info("c=InsuranceResource, m=getInsurancesByCustomer, customerResponse={}", customerResponse.toString());

        return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
    }
}
