package com.securin.weather.repository;

import com.securin.weather.entity.WeatherRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherRecord, Long> {

    // Find records by specific date
    @Query("SELECT w FROM WeatherRecord w WHERE FUNCTION('DATE', w.dateTimeUtc) = FUNCTION('DATE', :date)")
    List<WeatherRecord> findByDate(@Param("date") LocalDateTime date);

    // Find records by month and year
    @Query("SELECT w FROM WeatherRecord w WHERE FUNCTION('YEAR', w.dateTimeUtc) = :year AND FUNCTION('MONTH', w.dateTimeUtc) = :month")
    List<WeatherRecord> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

    // Find records by month across all years
    @Query("SELECT w FROM WeatherRecord w WHERE FUNCTION('MONTH', w.dateTimeUtc) = :month")
    List<WeatherRecord> findByMonth(@Param("month") int month);

    // Get temperature statistics for a specific month and year
    @Query("SELECT MAX(w.temperature) FROM WeatherRecord w WHERE FUNCTION('YEAR', w.dateTimeUtc) = :year AND FUNCTION('MONTH', w.dateTimeUtc) = :month")
    Double findMaxTemperatureByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT MIN(w.temperature) FROM WeatherRecord w WHERE FUNCTION('YEAR', w.dateTimeUtc) = :year AND FUNCTION('MONTH', w.dateTimeUtc) = :month")
    Double findMinTemperatureByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT AVG(w.temperature) FROM WeatherRecord w WHERE FUNCTION('YEAR', w.dateTimeUtc) = :year AND FUNCTION('MONTH', w.dateTimeUtc) = :month")
    Double findMedianTemperatureByYearAndMonth(@Param("year") int year, @Param("month") int month);

    // Find records by year
    @Query("SELECT w FROM WeatherRecord w WHERE FUNCTION('YEAR', w.dateTimeUtc) = :year ORDER BY w.dateTimeUtc")
    List<WeatherRecord> findByYear(@Param("year") int year);
}