package com.example.business_center.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDto {
    private Long id;
    private String name;
    private String address;
    private double square;
    private double price;
    private Boolean isEmpty;
    private List<Long> rents;
}
