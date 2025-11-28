package com.example.demo.repository;

import com.example.demo.domain.WeatherData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "weatherdata", path = "weatherdata")
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    // Metody ze stronicowaniem
    // Wyszukiwanie danych pogodowych wg lokalizacji z stronicowaniem
    Page<WeatherData> findByLocationId(Long locationId, Pageable pageable);

    // Wyszukiwanie danych pogodowych nowszych od podanej daty z stronicowaniem
    Page<WeatherData> findByTimestampAfter(LocalDateTime dateTime, Pageable pageable);

    // Wyszukiwanie danych pogodowych wg lokalizacji i daty (nowszych od podanej) z stronicowaniem
    Page<WeatherData> findByLocationIdAndTimestampAfter(Long locationId, LocalDateTime dateTime, Pageable pageable);

    // Wyszukiwanie danych pogodowych wg lokalizacji w zakresie dat z stronicowaniem
    Page<WeatherData> findByLocationIdAndTimestampBetween(Long locationId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Wyszukiwanie wszystkich danych pogodowych z stronicowaniem
    Page<WeatherData> findAll(Pageable pageable);

    // Zaawansowane wyszukiwanie z @Query i stronicowaniem
    @Query("SELECT wd FROM WeatherData wd WHERE " +
            "wd.location.id = :locationId AND " +
            "wd.timestamp >= :startDate AND " +
            "wd.temperature BETWEEN :minTemp AND :maxTemp")
    Page<WeatherData> findWeatherDataByLocationAndDateRangeAndTemperature(
            @Param("locationId") Long locationId,
            @Param("startDate") LocalDateTime startDate,
            @Param("minTemp") Double minTemp,
            @Param("maxTemp") Double maxTemp,
            Pageable pageable);

    // Wyszukiwanie danych pogodowych z sortowaniem po dacie (najnowsze pierwsze)
    @Query("SELECT wd FROM WeatherData wd WHERE wd.location.id = :locationId ORDER BY wd.timestamp DESC")
    Page<WeatherData> findLatestWeatherData(@Param("locationId") Long locationId, Pageable pageable);

    // Wyszukiwanie danych pogodowych dla wielu lokalizacji
    @Query("SELECT wd FROM WeatherData wd WHERE wd.location.id IN :locationIds AND wd.timestamp >= :dateTime")
    Page<WeatherData> findByLocationIdsAndTimestampAfter(
            @Param("locationIds") List<Long> locationIds,
            @Param("dateTime") LocalDateTime dateTime,
            Pageable pageable);

    // Statystyki - średnia temperatura dla lokalizacji w okresie
    @Query("SELECT AVG(wd.temperature) FROM WeatherData wd WHERE " +
            "wd.location.id = :locationId AND " +
            "wd.timestamp BETWEEN :start AND :end")
    Optional<Double> findAverageTemperatureByLocationAndPeriod(
            @Param("locationId") Long locationId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}