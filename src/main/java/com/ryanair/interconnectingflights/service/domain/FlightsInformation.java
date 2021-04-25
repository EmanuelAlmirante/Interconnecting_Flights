package com.ryanair.interconnectingflights.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightsInformation {
    private Integer stops;
    private List<Legs> legsList;
}
