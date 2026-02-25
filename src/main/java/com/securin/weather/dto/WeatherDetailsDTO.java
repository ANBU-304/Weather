package com.securin.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDetailsDTO {
    private LocalDateTime dateTime;
    private String conditions;
    private Double temperature;
    private Double humidity;
    private Double pressure;
}