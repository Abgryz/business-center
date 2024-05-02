package com.example.business_center.service;

import com.example.business_center.mapper.RentMapper;
import com.example.business_center.model.dto.RentDto;
import com.example.business_center.model.entity.Office;
import com.example.business_center.repository.OfficeRepository;
import com.example.business_center.repository.RentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {
    private final RentMapper rentMapper;
    private final RentRepository rentRepository;
    private final OfficeRepository officeRepository;

    @Transactional
    public RentDto create(RentDto rentDto) {
        return rentMapper.toDto(rentRepository.save(rentMapper.toEntity(rentDto)));
    }

    @Transactional
    public RentDto update(RentDto rentDto) {
        return rentMapper.toDto(rentRepository.save(rentMapper.toEntity(rentDto)));
    }

    @Transactional
    public void delete(Long id) {
        Office office = officeRepository.findByRentId(id).orElseThrow();
        office.setIsEmpty(true);
        officeRepository.save(office);
        rentRepository.deleteById(id);

    }

    public RentDto getById(Long id) {
        return rentMapper.toDto(rentRepository.findById(id).orElseThrow());
    }

    public List<RentDto> getAll() {
        return rentMapper.toDto(rentRepository.findAll());
    }
}
