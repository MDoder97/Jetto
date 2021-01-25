package com.markododer.webproject.services;

import com.markododer.webproject.repositories.FlightRepository;
import com.markododer.webproject.model.Flight;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FlightService implements IService<Flight, Long> {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public List<Flight> findAll() {
        return (List<Flight>) flightRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }
}
