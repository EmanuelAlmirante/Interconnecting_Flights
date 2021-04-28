package com.ryanair.interconnectingflights.service.flightsinformation.indirectflights;

import com.ryanair.interconnectingflights.service.domain.FlightsInformation;

import java.util.List;

public interface IndirectFlightsInformationService {
    FlightsInformation getIndirectFlightsInformation(String departure,
                                                     String arrival,
                                                     String departureDateTime,
                                                     String arrivalDateTime,
                                                     List<String> commonInterconnectingAirports);
}
