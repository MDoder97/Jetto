package com.markododer.webproject.bootstrap;

import com.markododer.webproject.repositories.*;
import com.markododer.webproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class BootstrapData implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final CityRepository cityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public BootstrapData(CompanyRepository companyRepository, TicketRepository ticketRepository, UserRepository userRepository, FlightRepository flightRepository, CityRepository cityRepository) {
        this.companyRepository = companyRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.cityRepository = cityRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");

        String[] cityNames = {"Beograd", "Hamburg", "Kopenhagen", "London", "Barcelona", "Madrid", "Paris", "Zurich", "Berlin", "Warsaw", "Prague"};
        String[] usernames = {"marko", "sarko", "darko"};
        String[] companyNames = { "Serbia Air", "Lockheed Martin", "Alaska Airlines", "Frontier Airines", "Boeing", "Asia Travel"};
        String password = "1234";


        Random random = new Random();

        List<MyUser> myUsers = new ArrayList<>();
        for (int i = 0; i < usernames.length; i++) {
            MyUser myUser = new MyUser();
            myUser.setUsername(usernames[i]);
            myUser.setPassword(passwordEncoder.encode(password));
            myUser.setAdmin(i == 0 ? true : false);
            if(myUser.isAdmin()) {
                myUser.getAuthorities().add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            myUsers.add(myUser);
        }
        System.out.println(userRepository.saveAll(myUsers));

        List<Company> companies = new ArrayList<>();

        for(int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            companies.add(company);
        }

        List<City> cities = new ArrayList<>();
        for(int i = 0; i < cityNames.length; i++) {
            City city = new City();
            city.setName(cityNames[i]);
            cities.add(city);
        }
        cityRepository.saveAll(cities);

        List<Flight> flights = new ArrayList<>();
        for (int l = 0,  r = cities.size() - 1; l < r; l++, r--) {
            Flight flight = new Flight();
            flight.setOrigin(cities.get(l));
            flight.setDestination(cities.get(r));
            flights.add(flight);
            flight.setTickets(new ArrayList<>());
        }
        System.out.println(flightRepository.saveAll(flights));

        List<Ticket> tickets = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Ticket ticket = new Ticket();
            int next = random.nextInt(companies.size());

            ticket.setCompany(companies.get(next));
            ticket.setOneWay(next % 2 == 0 ? true : false);
            ticket.setCount(random.nextInt(10) + 1);

            LocalDate departDate = LocalDate.now().plusDays(next);
            LocalDate returnDate = departDate.plusDays(next + 1);

            ticket.setDepartDate(departDate);
            ticket.setReturnDate(returnDate);

            Flight flight = flights.get(random.nextInt(flights.size()));
            flight.getTickets().add(ticket);
            ticket.setFlight(flight);

            Company company = companies.get(random.nextInt(companies.size()));
            if(company.getTickets() == null)
                company.setTickets(new ArrayList<>());

            company.getTickets().add(ticket);
            tickets.add(ticket);
        }
        System.out.println(companyRepository.saveAll(companies));
        System.out.println(ticketRepository.saveAll(tickets));

        System.out.println("Data loaded!");
    }
}
