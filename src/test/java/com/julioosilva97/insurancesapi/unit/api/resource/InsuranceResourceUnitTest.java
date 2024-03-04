package com.julioosilva97.insurancesapi.unit.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julioosilva97.insurancesapi.api.payload.CustomerDTO;
import com.julioosilva97.insurancesapi.api.payload.CustomerRequest;
import com.julioosilva97.insurancesapi.api.payload.InsuranceDTO;
import com.julioosilva97.insurancesapi.domain.model.Insurance;
import com.julioosilva97.insurancesapi.domain.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
public class InsuranceResourceUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private InsuranceService insuranceServiceMock;

    static String API = "/insurances/customer";

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


        when(insuranceServiceMock.getInsurancesByCustomer(customerDTO)).thenReturn(insurances);

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
