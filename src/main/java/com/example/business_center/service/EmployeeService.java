package com.example.business_center.service;

import com.example.business_center.mapper.EmployeeMapper;
import com.example.business_center.model.dto.EmployeeDto;
import com.example.business_center.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public EmployeeDto create(EmployeeDto employeeDto) {
        return employeeMapper.toDto(employeeRepository.save(employeeMapper.toEntity(employeeDto)));
    }

    @Transactional
    public EmployeeDto update(EmployeeDto employeeDto) {
        return employeeMapper.toDto(employeeRepository.save(employeeMapper.toEntity(employeeDto)));
    }

    @Transactional
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDto getById(Long id) {
        return employeeMapper.toDto(employeeRepository.findById(id).orElseThrow());
    }

    public List<EmployeeDto> getAll() {
        return employeeMapper.toDto(employeeRepository.findAll());
    }

    public EmployeeDto getByUsername(String username) {
        return employeeMapper.toDto(employeeRepository.findByUsername(username).orElseThrow());
    }
}
