package com.markododer.webproject.controller;


import com.markododer.webproject.exceptions.InvalidAddRequest;
import com.markododer.webproject.exceptions.InvalidDeleteRequest;
import com.markododer.webproject.model.*;
import com.markododer.webproject.services.CompanyService;
import com.markododer.webproject.services.FlightService;
import com.markododer.webproject.services.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final FlightService flightService;
    private final ReservationService reservationService;

    public CompanyController(CompanyService companyService, FlightService flightService, ReservationService reservationService) {
        this.companyService = companyService;
        this.flightService = flightService;
        this.reservationService = reservationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> getCompanies() {
        return companyService.findAll();
    }

    @GetMapping("/{companyId}")
    public List<Ticket> getCompanyTickets(@PathVariable("companyId") Long companyId) {
        Optional<Company> optionalCompany = companyService.findById(companyId);
        if(optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            return company.getTickets();
        }
        return null;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        if(company == null || company.getName() == null)
            throw new InvalidAddRequest("Failed to add a company, please try again");
        companyService.save(company);
        return ResponseEntity.ok(company);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable("companyId") Long companyId) {
        Optional<Company> optionalCompany = companyService.findById(companyId);
        if(optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            companyService.deleteTickets(company);
            return ResponseEntity.ok().build();
        }
        throw new InvalidDeleteRequest("Falied to delete a company, please try again");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public Company updateCompany(@RequestBody Company updatedCompany) {
        Optional<Company> companyOptional = companyService.findById(updatedCompany.getId());
        if(companyOptional.isPresent() && updatedCompany.getName() != null) {
            Company company = companyOptional.get();
            company = companyService.updateCompany(company, updatedCompany);
            return company;
        }
        throw new InvalidAddRequest("Failed to update a company, please try again");
    }

}

