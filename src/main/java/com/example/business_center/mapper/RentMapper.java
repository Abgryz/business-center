package com.example.business_center.mapper;

import com.example.business_center.model.dto.RentDto;
import com.example.business_center.model.entity.Employee;
import com.example.business_center.model.entity.Rent;
import com.example.business_center.repository.ClientRepository;
import com.example.business_center.repository.EmployeeRepository;
import com.example.business_center.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentMapper implements Mapper<Rent, RentDto> {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;
    @Override
    public RentDto toDto(Rent rent) {
        Long empId = null;
        if (rent.getEmployee() != null) {
            empId = rent.getEmployee().getId();
        }
        return RentDto.builder()
                .id(rent.getId())
                .startDate(rent.getStartDate())
                .endDate(rent.getEndDate())
                .clientId(rent.getClient().getId())
                .employeeId(empId)
                .officeId(rent.getOffice().getId())
                .build();
    }

    @Override
    public Rent toEntity(RentDto rentDto) {
        Employee emp = null;
        if (rentDto.getEmployeeId() != null) {
            emp = employeeRepository.findById(rentDto.getEmployeeId()).orElseThrow();
        }
        return Rent.builder()
                .endDate(rentDto.getEndDate())
                .startDate(rentDto.getStartDate())
                .id(rentDto.getId())
                .office(officeRepository.findById(rentDto.getOfficeId()).orElseThrow())
                .client(clientRepository.findById(rentDto.getClientId()).orElseThrow())
                .employee(emp)
                .build();
    }
}
