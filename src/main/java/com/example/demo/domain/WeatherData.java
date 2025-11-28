package com.example.demo.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temperature", nullable = false)
    private Double temperature;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "pressure")
    private Double pressure;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "wind_direction")
    private String windDirection;

    @Column(name = "description")
    private String description;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Relacja wiele-do-jednego z Location
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // Konstruktory
    public WeatherData() {}

    public WeatherData(Double temperature, Integer humidity, Double pressure,
                       String description, LocalDateTime timestamp, Location location) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.description = description;
        this.timestamp = timestamp;
        this.location = location;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", windDirection='" + windDirection + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", location=" + (location != null ? location.getCityName() : "null") +
                '}';
    }
}