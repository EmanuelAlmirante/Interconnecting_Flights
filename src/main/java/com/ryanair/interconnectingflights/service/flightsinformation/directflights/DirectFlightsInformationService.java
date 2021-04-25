package com.ryanair.interconnectingflights.service.flightsinformation.directflights;

import com.ryanair.interconnectingflights.service.domain.FlightsInformation;

public interface DirectFlightsInformationService {
    FlightsInformation getDirectFlightsInformation(String departure, String arrival, String departureDateTime, String arrivalDateTime);
}
