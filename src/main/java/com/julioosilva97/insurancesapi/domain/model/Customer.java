package com.julioosilva97.insurancesapi.domain.model;

import com.julioosilva97.insurancesapi.api.payload.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Customer {

    private String name;
    private String cpf;
    private int age;
    private String location;
    private Double vehicleValue;

    public Customer() {

    }

    public Customer toEntity(CustomerDTO dto){
        return Customer.builder()
                .name(dto.getName())
                .cpf(dto.getCpf())
                .age(dto.getAge())
                .location(dto.getLocation())
                .vehicleValue(dto.getVehicle_value())
                .build();
    }
}
