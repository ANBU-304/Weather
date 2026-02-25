package com.securin.weather.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.securin.weather.entity.WeatherRecord;
import com.securin.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvProcessingService {

    private final WeatherRepository weatherRecordRepository;

    @Value("${weather.csv.batch-size:1000}")
    private int batchSize;

    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm[:ss]"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };

    // =============================
    // Process CSV from file path
    // =============================
    @Transactional
    public long processCSVFile(String filePath)
            throws IOException, CsvValidationException {

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return processCsvData(reader);
        }
    }

    // =============================
    // Process CSV from upload
    // =============================
    @Transactional
    public long processCSVFile(MultipartFile file)
            throws IOException, CsvValidationException {

        try (CSVReader reader =
                     new CSVReader(new InputStreamReader(file.getInputStream()))) {
            return processCsvData(reader);
        }
    }

    // =============================
    // Core Processing
    // =============================
    private long processCsvData(CSVReader reader)
            throws IOException, CsvValidationException {

        List<WeatherRecord> batch = new ArrayList<>();
        long totalRecords = 0;
        int skipped = 0;

        // Skip header
        reader.readNext();

        String[] row;
        int rowNumber = 1;

        while ((row = reader.readNext()) != null) {
            rowNumber++;

            try {
                WeatherRecord record = mapRowToWeatherRecord(row);
                batch.add(record);

                if (batch.size() >= batchSize) {
                    weatherRecordRepository.saveAll(batch);
                    totalRecords += batch.size();
                    batch.clear();
                }

            } catch (Exception e) {
                log.error("Error processing row {}: {}", rowNumber, e.getMessage());
                skipped++;
            }
        }

        if (!batch.isEmpty()) {
            weatherRecordRepository.saveAll(batch);
            totalRecords += batch.size();
        }

        log.info("Completed. Total: {}, Skipped: {}", totalRecords, skipped);

        return totalRecords;
    }

    private WeatherRecord mapRowToWeatherRecord(String[] row) {
        return WeatherRecord.builder()
                .dateTimeUtc(parseDateTime(row[0]))
                .conditions(parseString(row[1]))
                .temperature(parseDouble(row[2]))
                .dewpoint(parseDouble(row[3]))
                .heatindex(parseDouble(row[4]))
                .windchill(parseDouble(row[5]))
                .humidity(parseDouble(row[6]))
                .pressure(parseDouble(row[7]))
                .precipitation(parseDouble(row[8]))
                .visibility(parseDouble(row[9]))
                .windDirectionDegrees(parseInteger(row[10]))
                .windDirection(parseString(row[11]))
                .windSpeed(parseDouble(row[12]))
                .windGust(parseDouble(row[13]))
                .fog(parseBoolean(row[14]))
                .rain(parseBoolean(row[15]))
                .snow(parseBoolean(row[16]))
                .hail(parseBoolean(row[17]))
                .thunder(parseBoolean(row[18]))
                .tornado(parseBoolean(row[19]))
                .build();
    }

    private LocalDateTime parseDateTime(String value) {
        if (value == null || value.trim().isEmpty()) return null;

        String trimmed = value.trim();

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDateTime.parse(trimmed, formatter);
            } catch (Exception ignored) {}
        }

        throw new IllegalArgumentException("Unable to parse date: " + value);
    }

    private String parseString(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null"))
            return null;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null"))
            return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null"))
            return false;
        return value.trim().equalsIgnoreCase("true")
                || value.trim().equals("1");
    }

    @Transactional
    public void clearAllData() {
        weatherRecordRepository.deleteAll();
    }

    public long getRecordCount() {
        return weatherRecordRepository.count();
    }
}