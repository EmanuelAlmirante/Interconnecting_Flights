package com.ryanair.interconnectingflights.service.flightsinformation;

import com.ryanair.interconnectingflights.service.domain.FlightsInformation;

import java.util.List;

public interface FlightsInformationService {
    List<FlightsInformation> getFlightsInformation(String departure,
                                                   String arrival,
                                                   String departureDateTime,
                                                   String arrivalDateTime);
}
