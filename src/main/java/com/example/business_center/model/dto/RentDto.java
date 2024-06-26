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
public class RentDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long clientId;
    private Long officeId;
    private Long employeeId;
}
