package com.example.business_center.controller;

import com.example.business_center.model.dto.OfficeDto;
import com.example.business_center.model.dto.ServiceDto;
import com.example.business_center.repository.RentRepository;
import com.example.business_center.repository.ServiceOrderRepository;
import com.example.business_center.security.user.Role;
import com.example.business_center.security.user.UserDetailsServiceImpl;
import com.example.business_center.service.OfficeService;
import com.example.business_center.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final OfficeService officeService;
    private final ServiceService serviceService;
    private final UserDetailsServiceImpl userDetailsService;


    private final ServiceOrderRepository serviceOrderRepository;
    private final RentRepository rentRepository;

    @GetMapping("/")
    public String mainPage(Model model) {
        return "main";
    }

    @GetMapping("/offices")
    public String officesPage(Model model, Authentication authentication) {
        if (authentication != null) {
            boolean isAdmin = userDetailsService.isAdmin(authentication);
            if (isAdmin) {
                model.addAttribute("offices", officeService.getAll());
                model.addAttribute("role", Role.ADMIN.name());
            } else {
                model.addAttribute("offices", officeService.getEmptyOffices());
                model.addAttribute("role", Role.CLIENT.name());
            }
        } else {
            model.addAttribute("offices", officeService.getEmptyOffices());
            model.addAttribute("role", Role.CLIENT.name());
        }

        return "offices";
    }

    @GetMapping("/services")
    public String servicesPage(Model model, Authentication authentication) {
        model.addAttribute("services", serviceService.getAll());
        if (authentication != null) {
            boolean isAdmin = userDetailsService.isAdmin(authentication);
            if (isAdmin) {
                model.addAttribute("role", Role.ADMIN.name());
            } else {
                model.addAttribute("role", Role.CLIENT.name());
            }
        } else {
            model.addAttribute("offices", officeService.getEmptyOffices());
            model.addAttribute("role", Role.CLIENT.name());
        }
        return "services";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/registration")
    public String register(Model model){
        return "register";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        model.addAttribute("rents", rentRepository.findAllByClientUsername(authentication.getName()));
        model.addAttribute("serviceOrders", serviceOrderRepository.findAllByClientUsername(authentication.getName()));
        model.addAttribute("name", userDetailsService.findByUsername(authentication.getName()).getName());
        if (userDetailsService.isAdmin(authentication)) {
            model.addAttribute("role", Role.ADMIN.name());
        } else {
            model.addAttribute("role", Role.CLIENT.name());
        }
        return "profile";
    }

    @GetMapping("/admin/offices/{officeId}")
    public String officePage(Model model, @PathVariable Long officeId) {
        model.addAttribute("office", officeService.getById(officeId));
        return "admin-office";
    }

    @GetMapping("/admin/offices")
    public String officesPage(Model model) {
        model.addAttribute("office", OfficeDto.builder().build());
        return "admin-office";
    }

    @GetMapping("/admin/services/{serviceId}")
    public String servicePage(Model model, @PathVariable Long serviceId) {
        model.addAttribute("service", serviceService.getById(serviceId));
        return "admin-service";
    }

    @GetMapping("/admin/services")
    public String servicesPage(Model model) {
        model.addAttribute("service", ServiceDto.builder().build());
        return "admin-service";
    }
}
