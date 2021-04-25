package com.ryanair.interconnectingflights.service.flightsinformation;

import com.ryanair.interconnectingflights.provider.routes.RoutesProvider;
import com.ryanair.interconnectingflights.provider.routes.domain.Routes;
import com.ryanair.interconnectingflights.provider.schedules.SchedulesProvider;
import com.ryanair.interconnectingflights.provider.schedules.domain.Days;
import com.ryanair.interconnectingflights.provider.schedules.domain.Flights;
import com.ryanair.interconnectingflights.provider.schedules.domain.Schedules;
import com.ryanair.interconnectingflights.service.domain.FlightsInformation;
import com.ryanair.interconnectingflights.service.domain.Legs;
import com.ryanair.interconnectingflights.service.flightsinformation.FlightsInformationService;
import com.ryanair.interconnectingflights.service.flightsinformation.directflights.DirectFlightsInformationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightsInformationServiceImpl implements FlightsInformationService {
    private final RoutesProvider routesProvider;
    //    private final SchedulesProvider schedulesProvider;
    private final DirectFlightsInformationService directFlightsInformationService;

    @Autowired
    public FlightsInformationServiceImpl(RoutesProvider routesProvider, DirectFlightsInformationService directFlightsInformationService) {
        this.routesProvider = routesProvider;
        this.directFlightsInformationService = directFlightsInformationService;
    }

    @Override
    public List<FlightsInformation> getFlightsInformation(String departure,
                                                          String arrival,
                                                          String departureDateTime,
                                                          String arrivalDateTime) {
        List<Routes> routes = routesProvider.getRoutes(departure, arrival);

        List<FlightsInformation> flightsInformationList = new ArrayList<>();

        if (!routes.isEmpty()) {
            FlightsInformation directFlightsInformation = directFlightsInformationService
                    .getDirectFlightsInformation(departure, arrival, departureDateTime, arrivalDateTime);

            if (directFlightsInformation != null) {
                flightsInformationList.add(directFlightsInformation);
            }

            List<String> commonInterconnectingAirports = getCommonInterconnectingAirports(routes, departure, arrival);

        } else {

        }

//        Schedules schedules = schedulesProvider.getSchedules(departure, arrival, departureDateTime, arrivalDateTime);

        return flightsInformationList;
    }

    private List<String> getCommonInterconnectingAirports(List<Routes> routes, String departure, String arrival) {
        List<String> departureInterconnectingAirport = new ArrayList<>();
        List<String> arrivalInterconnectingAirport = new ArrayList<>();

        for (Routes route : routes) {
            if (route.getAirportFrom().equals(departure)) {
                departureInterconnectingAirport.add(route.getAirportTo());
            } else if (route.getAirportTo().equals(arrival)) {
                arrivalInterconnectingAirport.add(route.getAirportFrom());
            }
        }

        List<String> commonInterconnectingAirports = new ArrayList<>(departureInterconnectingAirport);
        commonInterconnectingAirports.retainAll(arrivalInterconnectingAirport);

        return commonInterconnectingAirports;
    }
}
