package com.example.business_center.mapper;

import com.example.business_center.model.dto.ServiceDto;
import com.example.business_center.model.entity.Service;
import com.example.business_center.model.entity.ServiceOrder;
import com.example.business_center.repository.ServiceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceMapper implements Mapper<Service, ServiceDto> {
    private final ServiceOrderRepository serviceOrderRepository;
    @Override
    public ServiceDto toDto(Service service) {
        if (service.getServiceOrders() == null) {
            service.setServiceOrders(List.of());
        }
        return ServiceDto.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .serviceOrders(service.getServiceOrders().stream()
                        .map(ServiceOrder::getId)
                        .toList())
                .build();
    }

    @Override
    public Service toEntity(ServiceDto serviceDto) {
        if (serviceDto.getServiceOrders() == null) {
            serviceDto.setServiceOrders(List.of());
        }
        return Service.builder()
                .id(serviceDto.getId())
                .name(serviceDto.getName())
                .description(serviceDto.getDescription())
                .price(serviceDto.getPrice())
                .serviceOrders(serviceDto.getServiceOrders().stream()
                        .map((id) -> serviceOrderRepository.findById(id).orElseThrow())
                        .toList())
                .build();
    }
}
