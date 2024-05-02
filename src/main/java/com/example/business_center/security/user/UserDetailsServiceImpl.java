package com.example.business_center.security.user;

import com.example.business_center.service.ClientService;
import com.example.business_center.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientService clientService;
    private final EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDto employee = employeeService.getByUsername(username);
            return new User(
                employee.getUsername(),
                employee.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(employee.getRole().toString()))
            );
        } catch (RuntimeException e) {
            try {
                UserDto client = clientService.getByUsername(username);
                return new User(
                        client.getUsername(),
                        client.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(client.getRole().toString()))
                );
            } catch (RuntimeException ex) {
                log.info("User " + username + " not found");
                throw new UsernameNotFoundException("User " + username + " not found");
            }
        }
    }

    public UserDto findByUsername(String username) {
        try {
            return employeeService.getByUsername(username);
        } catch (RuntimeException e) {
            return clientService.getByUsername(username);
        }
    }

    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

    }
}