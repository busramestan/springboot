package com.busramestan.springboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class ProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private int price;
}
