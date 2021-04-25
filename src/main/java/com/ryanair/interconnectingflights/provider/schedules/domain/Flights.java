package com.ryanair.interconnectingflights.provider.schedules.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flights {
    private String carrierCode;
    private String number;
    private String departureTime;
    private String arrivalTime;
}
