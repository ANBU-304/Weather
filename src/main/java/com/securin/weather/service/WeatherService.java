package com.securin.weather.service;

import com.securin.weather.dto.MonthlyStatsDTO;
import com.securin.weather.dto.TemperatureStatsDTO;
import com.securin.weather.dto.WeatherDetailsDTO;
import com.securin.weather.entity.WeatherRecord;
import com.securin.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRecordRepository;

    public List<WeatherDetailsDTO> getWeatherByDate(String dateStr) {
        LocalDateTime date = LocalDateTime.parse(dateStr + "T00:00:00");
        List<WeatherRecord> records = weatherRecordRepository.findByDate(date);
        
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<WeatherDetailsDTO> getWeatherByMonth(int month) {
        List<WeatherRecord> records = weatherRecordRepository.findByMonth(month);
        
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<WeatherDetailsDTO> getWeatherByYearAndMonth(int year, int month) {
        List<WeatherRecord> records = weatherRecordRepository.findByYearAndMonth(year, month);
        
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MonthlyStatsDTO> getMonthlyTemperatureStats(int year) {
        List<MonthlyStatsDTO> monthlyStats = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            TemperatureStatsDTO stats = getTemperatureStatsForMonth(year, month);
            
            MonthlyStatsDTO monthlyDTO = MonthlyStatsDTO.builder()
                    .year(year)
                    .month(month)
                    .monthName(Month.of(month).name())
                    .temperatureStats(stats)
                    .build();
            
            monthlyStats.add(monthlyDTO);
        }

        return monthlyStats;
    }

    public TemperatureStatsDTO getTemperatureStatsForMonth(int year, int month) {
        List<WeatherRecord> records = weatherRecordRepository.findByYearAndMonth(year, month);
        
        if (records.isEmpty()) {
            return TemperatureStatsDTO.builder()
                    .high(null)
                    .median(null)
                    .minimum(null)
                    .build();
        }

        List<Double> temperatures = records.stream()
                .map(WeatherRecord::getTemperature)
                .filter(temp -> temp != null)
                .sorted()
                .collect(Collectors.toList());

        if (temperatures.isEmpty()) {
            return TemperatureStatsDTO.builder()
                    .high(null)
                    .median(null)
                    .minimum(null)
                    .build();
        }

        Double high = temperatures.get(temperatures.size() - 1);
        Double minimum = temperatures.get(0);
        Double median = calculateMedian(temperatures);

        return TemperatureStatsDTO.builder()
                .high(high)
                .median(median)
                .minimum(minimum)
                .build();
    }

    private Double calculateMedian(List<Double> sortedList) {
        int size = sortedList.size();
        if (size == 0) return null;
        
        if (size % 2 == 0) {
            return (sortedList.get(size / 2 - 1) + sortedList.get(size / 2)) / 2.0;
        } else {
            return sortedList.get(size / 2);
        }
    }

    private WeatherDetailsDTO convertToDTO(WeatherRecord record) {
        return WeatherDetailsDTO.builder()
                .dateTime(record.getDateTimeUtc())
                .conditions(record.getConditions())
                .temperature(record.getTemperature())
                .humidity(record.getHumidity())
                .pressure(record.getPressure())
                .build();
    }
}