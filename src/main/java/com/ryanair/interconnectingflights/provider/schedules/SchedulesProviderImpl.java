package com.ryanair.interconnectingflights.provider.schedules;

import com.ryanair.interconnectingflights.provider.schedules.domain.Schedules;
import com.ryanair.interconnectingflights.provider.schedules.utils.SchedulesProperties;
import com.ryanair.interconnectingflights.utils.exception.SchedulesProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class SchedulesProviderImpl implements SchedulesProvider {
    private static final Integer MAXIMUM_NUMBER_OF_INTERCONNECTING_FLIGHTS = 1;
    private static final Integer MAXIMUM_HOURS_OF_DIFFERENCE_BETWEEN_ARRIVAL_AND_DEPARTURE_OF_INTERCONNECTING_FLIGHTS =
            2;

    private final WebClient client;
    private final SchedulesProperties properties;

    @Autowired
    public SchedulesProviderImpl(WebClient client, SchedulesProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    public Schedules getSchedules(String departure, String arrival, String departureDateTime, String arrivalDateTime) {
        LocalDateTime parsedDepartureDateTime = parseDate(departureDateTime);
        LocalDateTime parsedArrivalDateTime = parseDate(arrivalDateTime);

        final String uri = getSchedulesUri(departure, arrival, parsedDepartureDateTime);

        Schedules schedules = client.get()
                                    .uri(uri)
                                    .retrieve()
                                    .bodyToMono(Schedules.class)
                                    .blockOptional()
                                    .orElseThrow(SchedulesProviderException::new);

//        filterSchedules(schedules, parsedDepartureDateTime, parsedArrivalDateTime);

        return schedules;
    }

    private LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        return LocalDateTime.parse(date, formatter);
    }

    private String getSchedulesUri(String departure, String arrival, LocalDateTime parsedDate) {
        String year = String.valueOf(parsedDate.getYear());
        String month = String.valueOf(parsedDate.getMonthValue());

        return MessageFormat.format(properties.getUrl(), departure, arrival, year, month);
    }

//    private void filterSchedules(Schedules schedules, LocalDateTime parsedDepartureDateTime, LocalDateTime parsedArrivalDateTime) {
//        filterSchedulesByDayOfDeparture(schedules, parsedDepartureDateTime);
////        filterSchedulesByNumberOfInterconnectingFlights(schedules);
////        filterSchedulesOfInterconnectingFlightsByDifferenceBetweenArrivalAndDeparture(schedules,
////                                                                                      parsedDepartureDateTime,
////                                                                                      parsedArrivalDateTime);
//        filterSchedulesByEmptyFlights(schedules);
//    }

//    private void filterSchedulesByDayOfDeparture(Schedules schedules, LocalDateTime parsedDepartureDateTime) {
//        int departureDay = parsedDepartureDateTime.toLocalDate().getDayOfMonth();
//
//        schedules.getDaysList().removeIf(days -> days.getDay() != departureDay);
//    }

//    private void filterSchedulesByNumberOfInterconnectingFlights(Schedules schedules) {
//        schedules.getDaysList().removeIf(days -> days.getFlightsList().size() > MAXIMUM_NUMBER_OF_INTERCONNECTING_FLIGHTS);
//    }
//
//    private void filterSchedulesOfInterconnectingFlightsByDifferenceBetweenArrivalAndDeparture(Schedules schedules, LocalDateTime parsedDepartureDateTime, LocalDateTime parsedArrivalDateTime) {
//        LocalTime departureTime = parsedDepartureDateTime.toLocalTime();
//        LocalTime arrivalTime = parsedArrivalDateTime.toLocalTime();
//
//        schedules.getDaysList().forEach(days -> days.getFlightsList().removeIf(
//                flights -> LocalTime.parse(flights.getDepartureTime()).isBefore(departureTime)
//                           || LocalTime.parse(flights.getArrivalTime()).isAfter(arrivalTime)));
//    }

//    private void filterSchedulesByEmptyFlights(Schedules schedules) {
//        schedules.getDaysList().removeIf(days -> days.getFlightsList().isEmpty());
//    }
}
