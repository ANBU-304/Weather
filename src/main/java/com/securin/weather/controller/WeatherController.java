package com.securin.weather.controller;

import com.opencsv.exceptions.CsvException;
import com.securin.weather.dto.MonthlyStatsDTO;
import com.securin.weather.dto.TemperatureStatsDTO;
import com.securin.weather.dto.WeatherDetailsDTO;
import com.securin.weather.service.CsvProcessingService;
import com.securin.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class WeatherController {

    private final CsvProcessingService csvProcessingService;
    private final WeatherService weatherService;

    /**
     * Upload and process CSV file
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadCSV(
            @RequestParam("file") MultipartFile file) {

        Map<String, Object> response = new HashMap<>();

        if (file == null || file.isEmpty()) {
            response.put("success", false);
            response.put("message", "File is empty or not provided");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            log.info("Uploading file: {}", file.getOriginalFilename());

            long recordsProcessed = csvProcessingService.processCSVFile(file);

            response.put("success", true);
            response.put("message", "CSV processed successfully");
            response.put("recordsProcessed", recordsProcessed);

            return ResponseEntity.ok(response);

        } catch (IOException | CsvException e) {
            log.error("Error processing CSV file", e);

            response.put("success", false);
            response.put("message", "Error processing CSV: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get weather details for a specific date
     * Example: /api/weather/date/2020-01-15
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<WeatherDetailsDTO>> getWeatherByDate(
            @PathVariable String date) {

        try {
            List<WeatherDetailsDTO> weatherDetails =
                    weatherService.getWeatherByDate(date);

            return ResponseEntity.ok(weatherDetails);

        } catch (Exception e) {
            log.error("Error fetching weather by date", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get weather details for a specific month across all years
     * Example: /api/weather/month/7
     */
    @GetMapping("/month/{month}")
    public ResponseEntity<List<WeatherDetailsDTO>> getWeatherByMonth(
            @PathVariable int month) {

        if (month < 1 || month > 12) {
            return ResponseEntity.badRequest().build();
        }

        List<WeatherDetailsDTO> weatherDetails =
                weatherService.getWeatherByMonth(month);

        return ResponseEntity.ok(weatherDetails);
    }

    /**
     * Get weather details for a specific year and month
     * Example: /api/weather/2020/7
     */
    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<WeatherDetailsDTO>> getWeatherByYearAndMonth(
            @PathVariable int year,
            @PathVariable int month) {

        if (month < 1 || month > 12) {
            return ResponseEntity.badRequest().build();
        }

        List<WeatherDetailsDTO> weatherDetails =
                weatherService.getWeatherByYearAndMonth(year, month);

        return ResponseEntity.ok(weatherDetails);
    }

    /**
     * Get monthly temperature statistics for a given year
     * Example: /api/weather/stats/2020
     */
    @GetMapping("/stats/{year}")
    public ResponseEntity<List<MonthlyStatsDTO>> getMonthlyStats(
            @PathVariable int year) {

        List<MonthlyStatsDTO> stats =
                weatherService.getMonthlyTemperatureStats(year);

        return ResponseEntity.ok(stats);
    }

    /**
     * Get temperature statistics for specific month & year
     * Example: /api/weather/stats/2020/7
     */
    @GetMapping("/stats/{year}/{month}")
    public ResponseEntity<TemperatureStatsDTO> getMonthStats(
            @PathVariable int year,
            @PathVariable int month) {

        if (month < 1 || month > 12) {
            return ResponseEntity.badRequest().build();
        }

        TemperatureStatsDTO stats =
                weatherService.getTemperatureStatsForMonth(year, month);

        return ResponseEntity.ok(stats);
    }

    /**
     * Get total record count
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getRecordCount() {

        Map<String, Long> response = new HashMap<>();
        response.put("totalRecords",
                csvProcessingService.getRecordCount());

        return ResponseEntity.ok(response);
    }

    /**
     * Clear all stored weather data
     */
    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> clearData() {

        csvProcessingService.clearAllData();

        Map<String, String> response = new HashMap<>();
        response.put("message", "All data cleared successfully");

        return ResponseEntity.ok(response);
    }
}