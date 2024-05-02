package com.example.business_center.controller.rest;

import com.example.business_center.model.dto.OfficeDto;
import com.example.business_center.model.dto.ServiceDto;
import com.example.business_center.service.OfficeService;
import com.example.business_center.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {
    private final OfficeService officeService;
    private final ServiceService serviceService;

    @PostMapping("/offices")
    public ResponseEntity<String> createOffice(@RequestBody OfficeDto officeDto) {
        try {
            officeService.create(officeDto);
            log.info("officeDto = " + officeDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/offices/{officeId}")
    public ResponseEntity<String> updateOffice(@RequestBody OfficeDto officeDto, @PathVariable Long officeId) {
        try {
            officeDto.setId(officeId);
            log.info("officeDto = " + officeDto);
            officeService.update(officeDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/offices/{officeId}")
    public ResponseEntity<String> deleteOffice(@PathVariable Long officeId) {
        try {
            officeService.delete(officeId);
            log.info("officeId = " + officeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/services")
    public ResponseEntity<String> createService(@RequestBody ServiceDto serviceDto) {
        try {
            serviceService.create(serviceDto);
            log.info("serviceDto = " + serviceDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/services/{serviceId}")
    public ResponseEntity<String> updateService(@RequestBody ServiceDto serviceDto, @PathVariable Long serviceId) {
        try {
            serviceDto.setId(serviceId);
            log.info("serviceDto = " + serviceDto);
            serviceService.update(serviceDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/services/{serviceId}")
    public ResponseEntity<String> deleteService(@PathVariable Long serviceId) {
        try {
            serviceService.delete(serviceId);
            log.info("serviceId = " + serviceId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
