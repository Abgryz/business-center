package com.example.business_center.service;

import com.example.business_center.mapper.ServiceMapper;
import com.example.business_center.model.dto.ServiceDto;
import com.example.business_center.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Transactional
    public ServiceDto create(ServiceDto serviceDto) {
        return serviceMapper.toDto(serviceRepository.save(serviceMapper.toEntity(serviceDto)));
    }

    public ServiceDto getById(Long id) {
        return serviceMapper.toDto(serviceRepository.findById(id).orElseThrow());
    }

    @Transactional
    public ServiceDto update(ServiceDto serviceDto) {
        return serviceMapper.toDto(serviceRepository.save(serviceMapper.toEntity(serviceDto)));
    }

    @Transactional
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }

    public List<ServiceDto> getAll() {
        return serviceMapper.toDto(serviceRepository.findAll());
    }

    public List<ServiceDto> findByClientUsername(String username) {
        return serviceMapper.toDto(serviceRepository.findByClientUsername(username));
    }
}
