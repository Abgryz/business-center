package com.example.business_center.mapper;


import com.example.business_center.model.dto.ClientDto;
import com.example.business_center.model.entity.Client;
import com.example.business_center.model.entity.Rent;
import com.example.business_center.model.entity.ServiceOrder;
import com.example.business_center.repository.RentRepository;
import com.example.business_center.repository.ServiceOrderRepository;
import com.example.business_center.security.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientMapper implements Mapper<Client, ClientDto> {
    private final RentRepository rentRepository;
    private final ServiceOrderRepository serviceOrderRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .rents(client.getRents()
                        .stream()
                        .map(Rent::getId)
                        .toList())
                .serviceOrders(client.getServiceOrders()
                        .stream()
                        .map(ServiceOrder::getId)
                        .toList())
                .username(client.getUsername())
                .password(client.getPassword())
                .role(Role.CLIENT)
                .build();
    }

    @Override
    public Client toEntity(ClientDto clientDto) {
        if (clientDto.getServiceOrders() == null) {
            clientDto.setServiceOrders(List.of());
        }
        if (clientDto.getRents() == null) {
            clientDto.setRents(List.of());
        }
        if (clientDto.getId() == null) {
            clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        }
        return Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .password(clientDto.getPassword())
                .username(clientDto.getUsername())
                .serviceOrders(clientDto.getServiceOrders().stream()
                        .map((id) -> serviceOrderRepository.findById(id).orElseThrow())
                        .toList())
                .rents(clientDto.getRents().stream()
                        .map((id) -> rentRepository.findById(id).orElseThrow())
                        .toList())
                .build();
    }
}
