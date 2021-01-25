package com.markododer.webproject.services;

import com.markododer.webproject.model.Flight;
import com.markododer.webproject.model.Ticket;
import com.markododer.webproject.repositories.CompanyRepository;
import com.markododer.webproject.model.Company;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements IService<Company, Long> {

    private final CompanyRepository companyRepository;
    private final FlightService flightService;

    public CompanyService(CompanyRepository companyRepository, FlightService flightService) {
        this.companyRepository = companyRepository;
        this.flightService = flightService;
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<Company> findAll() {
        return (List<Company>) companyRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public void deleteTickets(Company company) {
        List<Ticket> tickets = company.getTickets();
        for(Ticket ticket : tickets) {
            Flight flight = ticket.getFlight();
            flight.removeTicket(ticket);
            flightService.save(flight);
        }
        companyRepository.deleteById(company.getId());
    }

    public Company updateCompany(Company company, Company updatedCompany) {
        company.setName(updatedCompany.getName());
        companyRepository.save(company);
        return company;
    }
}
