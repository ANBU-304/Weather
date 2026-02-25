package com.securin.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyStatsDTO {
    private int year;
    private int month;
    private String monthName;
    private TemperatureStatsDTO temperatureStats;
}