package com.example.business_center.controller.rest;

import com.example.business_center.model.dto.ClientDto;
import com.example.business_center.model.dto.RentDto;
import com.example.business_center.model.dto.ServiceOrderDto;
import com.example.business_center.service.ClientService;
import com.example.business_center.service.OfficeService;
import com.example.business_center.service.RentService;
import com.example.business_center.service.ServiceOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ClientController {
    private final ClientService clientService;
    private final RentService rentService;
    private final OfficeService officeService;
    private final ServiceOrderService serviceOrderService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ClientDto clientDto) {
        try {
            clientService.register(clientDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/user")
    public ResponseEntity<String> update(@RequestParam String name, Authentication authentication) {
        try {
            clientService.update(
                    clientService.getByUsername(authentication.getName()).toBuilder()
                            .name(name)
                            .build()
            );
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/rent/{officeId}")
    public ResponseEntity<String> rent(@PathVariable Long officeId, Authentication authentication, @RequestParam int months) {
        log.info("officeId = " + officeId + " months = " + months);
        try {
            if (officeService.isEmpty(officeId)) {
                rentService.create(
                        RentDto.builder()
                                .officeId(officeId)
                                .clientId(clientService.getByUsername(authentication.getName()).getId())
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusMonths(months))
                                .build()
                );
                officeService.update(
                        officeService.getById(officeId).toBuilder()
                                .isEmpty(false)
                                .build()
                );
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Цей офіс вже зайнятий");
            }
        } catch (RuntimeException e) {
            log.info(e.getMessage());
//            throw e;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Дані введені невірно!");
        }
    }

    @PostMapping("/service/{serviceId}")
    public ResponseEntity<String> serviceOrder(@PathVariable Long serviceId, Authentication authentication) {
        try {
            serviceOrderService.create(
                    ServiceOrderDto.builder()
                            .serviceId(serviceId)
                            .date(LocalDate.now())
                            .clientId(clientService.getByUsername(authentication.getName()).getId())
                            .build()
            );
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Неможливо купити цю послугу!");
        }
    }

    @DeleteMapping("/rent/{rentId}")
    public ResponseEntity<String> deleteRent(@PathVariable Long rentId) {
        try {
            rentService.delete(rentId);
            log.info("Deleted rent " + rentId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Вибрану аренду неможливо видалити!");
        }
    }

    @DeleteMapping("/service/{serviceOrderId}")
    public ResponseEntity<String> deleteService(@PathVariable Long serviceOrderId) {
        try {
            serviceOrderService.delete(serviceOrderId);
            log.info("Deleted order " + serviceOrderId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Вибрану послугу неможливо видалити!");
        }
    }
}
