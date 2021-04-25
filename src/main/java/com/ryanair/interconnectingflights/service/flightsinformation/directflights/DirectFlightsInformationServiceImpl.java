package com.ryanair.interconnectingflights.service.flightsinformation.directflights;

import com.ryanair.interconnectingflights.provider.schedules.SchedulesProvider;
import com.ryanair.interconnectingflights.provider.schedules.domain.Days;
import com.ryanair.interconnectingflights.provider.schedules.domain.Flights;
import com.ryanair.interconnectingflights.provider.schedules.domain.Schedules;
import com.ryanair.interconnectingflights.service.domain.FlightsInformation;
import com.ryanair.interconnectingflights.service.domain.Legs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectFlightsInformationServiceImpl implements DirectFlightsInformationService {
    private final SchedulesProvider schedulesProvider;

    @Autowired
    public DirectFlightsInformationServiceImpl(SchedulesProvider schedulesProvider) {
        this.schedulesProvider = schedulesProvider;
    }

    @Override
    public FlightsInformation getDirectFlightsInformation(String departure, String arrival, String departureDateTime, String arrivalDateTime) {
        List<Flights> directFlights = getDirectFlights(departure, arrival, departureDateTime, arrivalDateTime);

        List<Legs> legs = new ArrayList<>();

        if (!directFlights.isEmpty()) {
            for (Flights directFlight : directFlights) {
                Legs leg = parseLegs(directFlight, departure, arrival, departureDateTime);

                legs.add(leg);
            }

            return parseDirectFlightsInformation(legs);
        }

        return null;
    }

    private List<Flights> getDirectFlights(String departure, String arrival, String departureDateTime, String arrivalDateTime) {
        Schedules directSchedules =
                schedulesProvider.getSchedules(departure, arrival, departureDateTime, arrivalDateTime);

        filterSchedules(directSchedules, departureDateTime, arrivalDateTime);

        List<Days> daysWithFlights = directSchedules.getDaysList();

        if (!daysWithFlights.isEmpty()) {
            List<Flights> flights = new ArrayList<>();

            for (Days day : daysWithFlights) {
                flights.addAll(day.getFlightsList());
            }

            return flights;
        } else {
            return new ArrayList<>();
        }
    }

    private void filterSchedules(Schedules schedules, String departureDateTime, String arrivalDateTime) {
        LocalDateTime parsedDepartureDateTime = parseDate(departureDateTime);
        LocalDateTime parsedArrivalDateTime = parseDate(arrivalDateTime);

        filterSchedulesByDayOfDeparture(schedules, parsedDepartureDateTime, parsedArrivalDateTime);
        filterSchedulesByTimeOfDepartureAndTimeOfArrival(schedules, parsedDepartureDateTime, parsedArrivalDateTime);
        filterSchedulesByEmptyFlights(schedules);
    }

    private LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        return LocalDateTime.parse(date, formatter);
    }

    private void filterSchedulesByDayOfDeparture(Schedules schedules, LocalDateTime parsedDepartureDateTime, LocalDateTime parsedArrivalDateTime) {
        int departureDay = parsedDepartureDateTime.getDayOfMonth();

        schedules.getDaysList().removeIf(days -> days.getDay() != departureDay);
    }

    private void filterSchedulesByTimeOfDepartureAndTimeOfArrival(Schedules schedules, LocalDateTime parsedDepartureDateTime, LocalDateTime parsedArrivalDateTime) {
        int departureDay = parsedDepartureDateTime.getDayOfMonth();
        int arrivalDay = parsedArrivalDateTime.getDayOfMonth();

        if (departureDay == arrivalDay) {
            LocalTime departureTime = parsedDepartureDateTime.toLocalTime();
            LocalTime arrivalTime = parsedArrivalDateTime.toLocalTime();

            schedules.getDaysList().forEach(days -> days.getFlightsList().removeIf(
                    flights -> LocalTime.parse(flights.getDepartureTime()).isBefore(departureTime)
                               || LocalTime.parse(flights.getArrivalTime()).isAfter(arrivalTime)));
        }
    }

    private void filterSchedulesByEmptyFlights(Schedules schedules) {
        schedules.getDaysList().removeIf(days -> days.getFlightsList().isEmpty());
    }

    private Legs parseLegs(Flights flights, String departure, String arrival, String departureDateTime) {
        Legs leg = new Legs();

        leg.setDepartureAirport(departure);
        leg.setArrivalAirport(arrival);

        String departureTime = parseDepartureLocalDateTime(flights, departureDateTime);
        String arrivalTime = parseArrivalLocalDateTime(flights, departureDateTime);

        leg.setDepartureDateTime(departureTime);
        leg.setArrivalDateTime(arrivalTime);

        return leg;
    }

    private String parseDepartureLocalDateTime(Flights flights, String departureDateTime) {
        String date = StringUtils.substringBefore(departureDateTime, "T");

        return date + "T" + flights.getDepartureTime();
    }

    private String parseArrivalLocalDateTime(Flights flights, String departureDateTime) {
        String date = StringUtils.substringBefore(departureDateTime, "T");

        return date + "T" + flights.getArrivalTime();
    }

    private FlightsInformation parseDirectFlightsInformation(List<Legs> legs) {
        FlightsInformation flightsInformation = new FlightsInformation();

        flightsInformation.setStops(0);
        flightsInformation.setLegsList(legs);

        return flightsInformation;
    }
}
