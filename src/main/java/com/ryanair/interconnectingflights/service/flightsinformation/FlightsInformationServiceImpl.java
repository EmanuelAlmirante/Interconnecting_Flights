package com.ryanair.interconnectingflights.service.flightsinformation;

import com.ryanair.interconnectingflights.provider.routes.RoutesProvider;
import com.ryanair.interconnectingflights.provider.routes.domain.Routes;
import com.ryanair.interconnectingflights.service.domain.FlightsInformation;
import com.ryanair.interconnectingflights.service.flightsinformation.directflights.DirectFlightsInformationService;
import com.ryanair.interconnectingflights.service.flightsinformation.indirectflights.IndirectFlightsInformationService;
import com.ryanair.interconnectingflights.utils.exception.NoFlightsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightsInformationServiceImpl implements FlightsInformationService {
    private final RoutesProvider routesProvider;
    private final DirectFlightsInformationService directFlightsInformationService;
    private final IndirectFlightsInformationService indirectFlightsInformationService;

    @Autowired
    public FlightsInformationServiceImpl(RoutesProvider routesProvider,
                                         DirectFlightsInformationService directFlightsInformationService,
                                         IndirectFlightsInformationService indirectFlightsInformationService) {
        this.routesProvider = routesProvider;
        this.directFlightsInformationService = directFlightsInformationService;
        this.indirectFlightsInformationService = indirectFlightsInformationService;
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
            FlightsInformation indirectFlightsInformation =
                    indirectFlightsInformationService.getIndirectFlightsInformation(departure,
                                                                                    arrival,
                                                                                    departureDateTime,
                                                                                    arrivalDateTime,
                                                                                    commonInterconnectingAirports);

            if (indirectFlightsInformation != null) {
                flightsInformationList.add(indirectFlightsInformation);
            }
        } else {
            throw new NoFlightsException();
        }

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
