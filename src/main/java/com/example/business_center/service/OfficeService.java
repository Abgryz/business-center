package com.example.business_center.service;

import com.example.business_center.mapper.OfficeMapper;
import com.example.business_center.model.dto.OfficeDto;
import com.example.business_center.repository.OfficeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeService {
    private final OfficeRepository officeRepository;
    private final OfficeMapper officeMapper;

    public OfficeDto getById(Long id) {
        return officeMapper.toDto(officeRepository.findById(id).orElseThrow());
    }

    public List<OfficeDto> getAll() {
        return officeMapper.toDto(officeRepository.findAll());
    }

    @Transactional
    public OfficeDto create(OfficeDto officeDto) {
        return officeMapper.toDto(officeRepository.save(officeMapper.toEntity(officeDto)));
    }

    @Transactional
    public OfficeDto update(OfficeDto officeDto) {
        return officeMapper.toDto(officeRepository.save(officeMapper.toEntity(officeDto)));
    }

    @Transactional
    public void delete(Long id) {
        officeRepository.deleteById(id);
    }

    public List<OfficeDto> getEmptyOffices() {
        return officeMapper.toDto(officeRepository.findByIsEmptyTrue());
    }

    public List<OfficeDto> findByClientUsername(String username) {
        return officeMapper.toDto(officeRepository.findByClientUsername(username));
    }

    public boolean isEmpty(Long id) {
        return officeRepository.existsByIdAndIsEmptyTrue(id);
    }
}
