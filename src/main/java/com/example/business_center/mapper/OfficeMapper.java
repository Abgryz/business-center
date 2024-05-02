package com.example.business_center.mapper;

import com.example.business_center.model.dto.OfficeDto;
import com.example.business_center.model.entity.Office;
import com.example.business_center.model.entity.Rent;
import com.example.business_center.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OfficeMapper implements Mapper<Office, OfficeDto> {
    private final RentRepository rentRepository;
    @Override
    public OfficeDto toDto(Office office) {
        if (office.getRents() == null) {
            office.setRents(List.of());
        }
        return OfficeDto.builder()
                .id(office.getId())
                .price(office.getPrice())
                .square(office.getSquare())
                .name(office.getName())
                .address(office.getAddress())
                .isEmpty(office.getIsEmpty())
                .rents(office.getRents().stream()
                        .map(Rent::getId)
                        .toList())
                .build();
    }

    @Override
    public Office toEntity(OfficeDto officeDto) {
        if (officeDto.getRents() == null) {
            officeDto.setRents(List.of());
        }
        return Office.builder()
                .id(officeDto.getId())
                .rents(officeDto.getRents().stream()
                        .map((id) -> rentRepository.findById(id).orElseThrow())
                        .toList())
                .square(officeDto.getSquare())
                .isEmpty(officeDto.getIsEmpty())
                .name(officeDto.getName())
                .address(officeDto.getAddress())
                .price(officeDto.getPrice())
                .build();
    }
}
