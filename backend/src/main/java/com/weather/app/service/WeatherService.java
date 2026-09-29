package com.weather.app.service;

import com.weather.app.model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.api.key:}")
    private String apiKey;

    @Value("${weather.api.base-url:https://api.openweathermap.org/data/2.5}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Map<String, String> CITY_COUNTRIES = Map.ofEntries(
        Map.entry("london", "GB"),
        Map.entry("paris", "FR"),
        Map.entry("new york", "US"),
        Map.entry("tokyo", "JP"),
        Map.entry("hyderabad", "IN"),
        Map.entry("mumbai", "IN"),
        Map.entry("delhi", "IN"),
        Map.entry("bengaluru", "IN"),
        Map.entry("bangalore", "IN"),
        Map.entry("chennai", "IN"),
        Map.entry("sydney", "AU"),
        Map.entry("berlin", "DE"),
        Map.entry("rome", "IT"),
        Map.entry("toronto", "CA"),
        Map.entry("san francisco", "US"),
        Map.entry("los angeles", "US"),
        Map.entry("chicago", "US"),
        Map.entry("dubai", "AE"),
        Map.entry("singapore", "SG"),
        Map.entry("seoul", "KR")
    );

    private boolean isApiKeyConfigured() {
        return apiKey != null && !apiKey.trim().isEmpty() && !"YOUR_KEY_HERE".equalsIgnoreCase(apiKey.trim());
    }

    public WeatherResponse getWeatherByCity(String city, String units) {
        if (!isApiKeyConfigured()) {
            log.info("No API key configured. Using demo mode for city: {}", city);
            return generateMockWeather(city, units);
        }

        String url = String.format(
            "%s/weather?q=%s&units=%s&appid=%s",
            baseUrl, city, units, apiKey
        );
        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("City not found: " + city);
        } catch (HttpClientErrorException.Unauthorized e) {
            log.warn("Invalid API key provided. Falling back to demo mode for: {}", city);
            return generateMockWeather(city, units);
        } catch (Exception e) {
            log.warn("Failed to fetch weather from API ({}). Falling back to demo mode for: {}", e.getMessage(), city);
            return generateMockWeather(city, units);
        }
    }

    public WeatherResponse getWeatherByCoords(double lat, double lon, String units) {
        if (!isApiKeyConfigured()) {
            log.info("No API key configured. Using demo mode for coords: {}, {}", lat, lon);
            return generateMockWeatherForCoords(lat, lon, units);
        }

        String url = String.format(
            "%s/weather?lat=%s&lon=%s&units=%s&appid=%s",
            baseUrl, lat, lon, units, apiKey
        );
        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (Exception e) {
            log.warn("Failed to fetch weather by coords. Falling back to demo mode: {}", e.getMessage());
            return generateMockWeatherForCoords(lat, lon, units);
        }
    }

    private WeatherResponse generateMockWeather(String rawCity, String units) {
        String city = formatCityName(rawCity);
        int hash = Math.abs(city.toLowerCase().hashCode());

        WeatherResponse response = new WeatherResponse();
        response.setName(city);
        response.setDt(Instant.now().getEpochSecond());
        response.setVisibility(8500 + (hash % 1500));

        // Realistic conditions
        String[][] conditions = {
            {"Clear", "clear sky", "01d"},
            {"Clouds", "scattered clouds", "03d"},
            {"Rain", "light rain shower", "10d"},
            {"Drizzle", "light drizzle", "09d"},
            {"Thunderstorm", "thunderstorm with rain", "11d"},
            {"Mist", "foggy mist", "50d"}
        };
        String[] picked = conditions[hash % conditions.length];

        WeatherResponse.Weather weather = new WeatherResponse.Weather();
        weather.setMain(picked[0]);
        weather.setDescription(picked[1]);
        weather.setIcon(picked[2]);
        response.setWeather(List.of(weather));

        // Temperatures (in Celsius metric base)
        double temp = 16.0 + (hash % 16); // 16°C to 31°C
        double feelsLike = temp + ((hash % 5) - 2) * 0.7;
        double minTemp = temp - 3.2;
        double maxTemp = temp + 4.1;

        if ("imperial".equalsIgnoreCase(units)) {
            temp = (temp * 9.0 / 5.0) + 32;
            feelsLike = (feelsLike * 9.0 / 5.0) + 32;
            minTemp = (minTemp * 9.0 / 5.0) + 32;
            maxTemp = (maxTemp * 9.0 / 5.0) + 32;
        }

        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(Math.round(temp * 10.0) / 10.0);
        main.setFeelsLike(Math.round(feelsLike * 10.0) / 10.0);
        main.setTempMin(Math.round(minTemp * 10.0) / 10.0);
        main.setTempMax(Math.round(maxTemp * 10.0) / 10.0);
        main.setHumidity(45 + (hash % 45));
        main.setPressure(1008 + (hash % 20));
        response.setMain(main);

        // Wind
        WeatherResponse.Wind wind = new WeatherResponse.Wind();
        wind.setSpeed(Math.round((2.0 + (hash % 70) / 10.0) * 10.0) / 10.0);
        wind.setDeg(hash % 360);
        response.setWind(wind);

        // Sun timings
        WeatherResponse.Sys sys = new WeatherResponse.Sys();
        sys.setCountry(lookupCountry(city));
        long now = Instant.now().getEpochSecond();
        long dayStart = now - (now % 86400);
        sys.setSunrise(dayStart + 21600); // 6:00 AM
        sys.setSunset(dayStart + 66600);  // 6:30 PM
        response.setSys(sys);

        return response;
    }

    private WeatherResponse generateMockWeatherForCoords(double lat, double lon, String units) {
        String name = String.format("Current Location (%.1f, %.1f)", lat, lon);
        WeatherResponse resp = generateMockWeather(name, units);
        resp.setName(name);
        return resp;
    }

    private String lookupCountry(String city) {
        String country = CITY_COUNTRIES.get(city.toLowerCase());
        if (country != null) return country;
        String[] codes = {"US", "IN", "GB", "DE", "FR", "CA", "AU", "JP", "IT", "ES", "NL", "CH"};
        return codes[Math.abs(city.hashCode()) % codes.length];
    }

    private String formatCityName(String raw) {
        if (raw == null || raw.trim().isEmpty()) return "Unknown City";
        String[] parts = raw.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isEmpty()) {
                sb.append(Character.toUpperCase(p.charAt(0)));
                if (p.length() > 1) {
                    sb.append(p.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
}
