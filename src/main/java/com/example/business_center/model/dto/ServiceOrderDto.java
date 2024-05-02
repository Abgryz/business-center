package com.example.business_center.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrderDto {
    private Long id;
    private LocalDate date;
    private Long clientId;
    private Long serviceId;
    private Long employeeId;
}
