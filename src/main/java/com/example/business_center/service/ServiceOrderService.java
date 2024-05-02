package com.example.business_center.service;

import com.example.business_center.mapper.ServiceOrderMapper;
import com.example.business_center.model.dto.ServiceOrderDto;
import com.example.business_center.repository.ServiceOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceOrderService {
    private final ServiceOrderRepository serviceOrderRepository;
    private final ServiceOrderMapper serviceOrderMapper;

    @Transactional
    public ServiceOrderDto create(ServiceOrderDto serviceOrderDto) {
        return serviceOrderMapper.toDto(serviceOrderRepository.save(serviceOrderMapper.toEntity(serviceOrderDto)));
    }

    @Transactional
    public ServiceOrderDto update(ServiceOrderDto serviceOrderDto) {
        return serviceOrderMapper.toDto(serviceOrderRepository.save(serviceOrderMapper.toEntity(serviceOrderDto)));
    }

    @Transactional
    public void delete(Long id) {
        serviceOrderRepository.deleteById(id);
    }

    public ServiceOrderDto getById(Long id) {
        return serviceOrderMapper.toDto(serviceOrderRepository.findById(id).orElseThrow());
    }

    public List<ServiceOrderDto> getAll() {
        return serviceOrderMapper.toDto(serviceOrderRepository.findAll());
    }
}
