package com.weather.app.controller;

import com.weather.app.model.WeatherResponse;
import com.weather.app.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // GET /api/weather?city=London&units=metric
    @GetMapping
    public ResponseEntity<?> getWeatherByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "metric") String units) {
        try {
            WeatherResponse weather = weatherService.getWeatherByCity(city, units);
            return ResponseEntity.ok(weather);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/weather/coords?lat=51.5&lon=-0.1&units=metric
    @GetMapping("/coords")
    public ResponseEntity<?> getWeatherByCoords(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "metric") String units) {
        try {
            WeatherResponse weather = weatherService.getWeatherByCoords(lat, lon, units);
            return ResponseEntity.ok(weather);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/weather/health
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "Weather API"));
    }
}
