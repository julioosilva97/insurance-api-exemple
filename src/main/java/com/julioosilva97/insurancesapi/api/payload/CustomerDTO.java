package com.julioosilva97.insurancesapi.api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    @NotNull
    @Min(value = 1)
    private int age;
    @NotBlank
    private String location;
    @NotNull
    @JsonProperty("valor_veiculo")
    private Double vehicle_value;
}
