package com.securin.weather.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;


import java.time.LocalDateTime;

@Entity
@Table(name = "weather_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTimeUtc;
    private String conditions;
    private Double temperature;
    private Double dewpoint;
    private Double heatindex;
    private Double windchill;
    private Double humidity;
    private Double pressure;
    private Double precipitation;
    private Double visibility;
    private Integer windDirectionDegrees;
    private String windDirection;
    private Double windSpeed;
    private Double windGust;
    private Boolean fog;
    private Boolean rain;
    private Boolean snow;
    private Boolean hail;
    private Boolean thunder;
    private Boolean tornado;
}
