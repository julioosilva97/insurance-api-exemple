package com.julioosilva97.insurancesapi.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julioosilva97.insurancesapi.api.payload.CustomerDTO;
import com.julioosilva97.insurancesapi.api.payload.CustomerRequest;
import com.julioosilva97.insurancesapi.api.payload.InsuranceDTO;
import com.julioosilva97.insurancesapi.domain.model.Insurance;
import com.julioosilva97.insurancesapi.domain.model.InsuranceBasic;
import com.julioosilva97.insurancesapi.domain.model.InsurancePartial;
import com.julioosilva97.insurancesapi.domain.model.InsuranceTotal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasEntry;

@AutoConfigureMockMvc
@SpringBootTest
public class InsuranceResourceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static String API = "/insurances/customer";

    @Test
    void shouldReturnInsuranceBasic() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(29)
                .location("BH")
                .vehicle_value(70000.0)
                .build();

        Insurance insurance = new InsuranceBasic();

        List<InsuranceDTO> insuranceList = List.of(insurance.toDto());

        validResponse(customerDTO,insuranceList,insurance);
    }

    @Test
    void shouldReturnInsurancePartialSpecificRule1() throws Exception {

        //RULE vehicle_value >=100000  and age <30

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(29) // age <30
                .location("SP")
                .vehicle_value(100000.0) //>=100000
                .build();

        Insurance insuranceBasic = new InsuranceBasic();
        Insurance insurancePartial = new InsurancePartial();

        List<InsuranceDTO> insuranceList = List.of(insuranceBasic.toDto(),insurancePartial.toDto());

        validResponse(customerDTO,insuranceList,insurancePartial);
    }

    @Test
    void shouldReturnInsurancePartialSpecificRule2() throws Exception {

        //RULE vehicle_value >70000 and vehicle_value <100000 and location =SP

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(30)
                .location("SP") //SP
                .vehicle_value(90000.0) //>70000 and <100000
                .build();

        CustomerRequest customerRequest = CustomerRequest
                .builder()
                .customer(customerDTO)
                .build();

        Insurance insuranceBasic = new InsuranceBasic();
        Insurance insurancePartial = new InsurancePartial();

        List<InsuranceDTO> insuranceList = List.of(insuranceBasic.toDto(),insurancePartial.toDto());

        validResponse(customerDTO,insuranceList,insurancePartial);
    }

    @Test
    void shouldReturnInsurancePartialSpecificRule3() throws Exception {

        //RULE vehicle_value <=70000 and location =SP and age <30

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(29) //< 30
                .location("SP") //SP
                .vehicle_value(70000.0) //<=70000
                .build();

        Insurance insuranceBasic = new InsuranceBasic();
        Insurance insurancePartial = new InsurancePartial();

        List<InsuranceDTO> insuranceList = List.of(insuranceBasic.toDto(),insurancePartial.toDto());

        validResponse(customerDTO,insuranceList,insurancePartial);

    }

    @Test
    void shouldReturnInsuranceTotal() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(29)
                .location("BH")
                .vehicle_value(120000.0)
                .build();

        Insurance insurance = new InsuranceTotal();

        List<InsuranceDTO> insuranceList = List.of(insurance.toDto());

        validResponse(customerDTO,insuranceList,insurance);
    }

    @Test
    void shouldReturnErrorNameIsNull() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .cpf("123.456.789-10")
                .age(29)
                .location("BH")
                .vehicle_value(120000.0)
                .build();

        validationError(customerDTO);
    }

    @Test
    void shouldReturnErrorNameIsBlank() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("")
                .cpf("123.456.789-10")
                .age(29)
                .location("BH")
                .vehicle_value(120000.0)
                .build();

        validationError(customerDTO);
    }

    @Test
    void shouldReturnErrorCpfIsNull() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .age(29)
                .location("BH")
                .vehicle_value(120000.0)
                .build();

        validationError(customerDTO);
    }

    @Test
    void shouldReturnErrorCpfIsBlank() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("")
                .age(29)
                .location("BH")
                .vehicle_value(120000.0)
                .build();

        validationError(customerDTO);
    }

    @Test
    void shouldReturnErrorAgeIsNull() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .location("BH")
                .vehicle_value(120000.0)
                .build();

        validationError(customerDTO);
    }

    @Test
    void shouldReturnErrorAgeIsInvalid() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(0)
                .location("BH")
                .vehicle_value(120000.0)
                .build();

        validationError(customerDTO);
    }

    @Test
    void shouldReturnErrorLocationIsBlank() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(25)
                .location("")
                .vehicle_value(120000.0)
                .build();

        validationError(customerDTO);
    }

    @Test
    void shouldReturnErrorLocationIsNull() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(25)
                .vehicle_value(120000.0)
                .build();

        validationError(customerDTO);
    }

    @Test
    void shouldReturnErrorVehicleValueIsNull() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("João")
                .cpf("123.456.789-10")
                .age(25)
                .location("BH")
                .build();

        validationError(customerDTO);
    }

    private void validResponse(CustomerDTO customerDTO, List<InsuranceDTO> insurances, Insurance validInsurance) throws Exception {

        CustomerRequest customerRequest = CustomerRequest
                .builder()
                .customer(customerDTO)
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertObjectToJson(customerRequest));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("customer.name").value(customerDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("customer.insurances[*]").value(hasItem(allOf(
                        hasEntry("type", (Object) validInsurance.type),
                        hasEntry("cost", (Object) validInsurance.cost)
                ))));
    }
    private void validationError(CustomerDTO customerDTO) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertObjectToJson(CustomerRequest.builder().customer(customerDTO).build()));

        ResultActions resultActions = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors").isArray());
    }

    private String convertObjectToJson(CustomerRequest obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
