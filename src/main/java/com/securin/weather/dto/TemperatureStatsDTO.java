package com.securin.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemperatureStatsDTO {
    private Double high;
    private Double median;
    private Double minimum;
}