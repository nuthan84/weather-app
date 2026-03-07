package com.weather.app.service;

import com.weather.app.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse getWeatherByCity(String city, String units) {
        String url = String.format(
            "%s/weather?q=%s&units=%s&appid=%s",
            baseUrl, city, units, apiKey
        );
        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("City not found: " + city);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new RuntimeException("Invalid API key.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather: " + e.getMessage());
        }
    }

    public WeatherResponse getWeatherByCoords(double lat, double lon, String units) {
        String url = String.format(
            "%s/weather?lat=%s&lon=%s&units=%s&appid=%s",
            baseUrl, lat, lon, units, apiKey
        );
        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather: " + e.getMessage());
        }
    }
}
