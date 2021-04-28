package com.ryanair.interconnectingflights.service.flightsinformation.indirectflights;

import com.ryanair.interconnectingflights.provider.schedules.SchedulesProvider;
import com.ryanair.interconnectingflights.service.domain.FlightsInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndirectFlightsInformationServiceImpl implements IndirectFlightsInformationService {
    private final SchedulesProvider schedulesProvider;

    @Autowired
    public IndirectFlightsInformationServiceImpl(SchedulesProvider schedulesProvider) {
        this.schedulesProvider = schedulesProvider;
    }

    @Override
    public FlightsInformation getIndirectFlightsInformation(String departure,
                                                            String arrival,
                                                            String departureDateTime,
                                                            String arrivalDateTime,
                                                            List<String> commonInterconnectingAirports) {
        return null;
    }
}
