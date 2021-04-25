package com.ryanair.interconnectingflights.controller;

import com.ryanair.interconnectingflights.service.flightsinformation.FlightsInformationService;
import com.ryanair.interconnectingflights.service.domain.FlightsInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class FlightsInformationController {
    private final FlightsInformationService flightsInformationService;

    @Autowired
    public FlightsInformationController(FlightsInformationService flightsInformationService) {
        this.flightsInformationService = flightsInformationService;
    }

    @GetMapping("interconnections")
    public List<FlightsInformation> getFlightsInformation(@RequestParam(name = "departure") String departure,
                                                          @RequestParam(name = "arrival") String arrival,
                                                          @RequestParam(name = "departureDateTime") String departureDateTime,
                                                          @RequestParam(name = "arrivalDateTime") String arrivalDateTime) {
        return flightsInformationService.getFlightsInformation(departure, arrival, departureDateTime, arrivalDateTime);
    }
}
