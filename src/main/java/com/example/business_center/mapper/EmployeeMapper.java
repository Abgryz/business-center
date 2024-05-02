package com.example.business_center.mapper;

import com.example.business_center.model.dto.EmployeeDto;
import com.example.business_center.model.entity.Employee;
import com.example.business_center.model.entity.Rent;
import com.example.business_center.model.entity.ServiceOrder;
import com.example.business_center.repository.RentRepository;
import com.example.business_center.repository.ServiceOrderRepository;
import com.example.business_center.security.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper implements Mapper<Employee, EmployeeDto> {
    private final RentRepository rentRepository;
    private final ServiceOrderRepository serviceOrderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .name(employee.getName())
                .jobTittle(employee.getJobTitle())
                .subdivision(employee.getSubdivision())
                .password(employee.getPassword())
                .serviceOrders(employee.getServiceOrders()
                        .stream()
                        .map(ServiceOrder::getId)
                        .toList())
                .rents(employee.getRents()
                        .stream()
                        .map(Rent::getId)
                        .toList())
                .role(Role.ADMIN)
                .build();
    }

    @Override
    public Employee toEntity(EmployeeDto employeeDto) {
        return Employee.builder()
                .jobTitle(employeeDto.getJobTittle())
                .serviceOrders(employeeDto.getServiceOrders().stream()
                        .map((id) -> serviceOrderRepository.findById(id).orElseThrow())
                        .toList())
                .subdivision(employeeDto.getSubdivision())
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .rents(employeeDto.getRents().stream()
                        .map((id) -> rentRepository.findById(id).orElseThrow())
                        .toList())
                .username(employeeDto.getUsername())
                .build();
    }
}
