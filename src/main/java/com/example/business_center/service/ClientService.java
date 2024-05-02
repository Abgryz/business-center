package com.example.business_center.service;

import com.example.business_center.mapper.ClientMapper;
import com.example.business_center.model.dto.ClientDto;
import com.example.business_center.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientDto create(ClientDto clientDto) {
        return clientMapper.toDto(clientRepository.save(clientMapper.toEntity(clientDto)));
    }

    public ClientDto getById(Long id) {
        return clientMapper.toDto(clientRepository.findById(id).orElseThrow());
    }

    @Transactional
    public ClientDto update(ClientDto clientDto) {
        return clientMapper.toDto(clientRepository.save(clientMapper.toEntity(clientDto)));
    }

    @Transactional
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public List<ClientDto> getAll() {
        return clientMapper.toDto(clientRepository.findAll());
    }

    public ClientDto getByUsername(String username) {
        return clientMapper.toDto(clientRepository.findByUsername(username).orElseThrow());
    }

    @Transactional
    public void register(ClientDto clientDto) {
        if (clientRepository.existsByUsername(clientDto.getUsername())) {
            throw new IllegalArgumentException("Client with login " + clientDto.getUsername() + " already exists");
        } else {
            create(clientDto);
            log.info("User registered: " + clientDto.getUsername());
        }
    }
}
