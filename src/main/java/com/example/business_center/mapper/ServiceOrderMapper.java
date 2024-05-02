package com.example.business_center.mapper;

import com.example.business_center.model.dto.ServiceOrderDto;
import com.example.business_center.model.entity.Employee;
import com.example.business_center.model.entity.ServiceOrder;
import com.example.business_center.repository.ClientRepository;
import com.example.business_center.repository.EmployeeRepository;
import com.example.business_center.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceOrderMapper implements Mapper<ServiceOrder, ServiceOrderDto> {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public ServiceOrderDto toDto(ServiceOrder serviceOrder) {
        Long empId = null;
        if (serviceOrder.getEmployee() != null) {
            empId = serviceOrder.getEmployee().getId();
        }
        return ServiceOrderDto.builder()
                .id(serviceOrder.getId())
                .date(serviceOrder.getDate())
                .clientId(serviceOrder.getClient().getId())
                .serviceId(serviceOrder.getService().getId())
                .employeeId(empId)
                .build();
    }

    @Override
    public ServiceOrder toEntity(ServiceOrderDto serviceOrderDto) {
        Employee emp = null;
        if (serviceOrderDto.getEmployeeId() != null) {
            emp = employeeRepository.findById(serviceOrderDto.getEmployeeId()).orElseThrow();
        }
        return ServiceOrder.builder()
                .id(serviceOrderDto.getId())
                .date(serviceOrderDto.getDate())
                .client(clientRepository.findById(serviceOrderDto.getClientId()).orElseThrow())
                .service(serviceRepository.findById(serviceOrderDto.getServiceId()).orElseThrow())
                .employee(emp)
                .build();
    }
}
