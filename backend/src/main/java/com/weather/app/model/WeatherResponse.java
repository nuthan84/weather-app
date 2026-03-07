package com.weather.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private String name;
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private Sys sys;
    private long visibility;
    private long dt;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }
    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }
    public Wind getWind() { return wind; }
    public void setWind(Wind wind) { this.wind = wind; }
    public Sys getSys() { return sys; }
    public void setSys(Sys sys) { this.sys = sys; }
    public long getVisibility() { return visibility; }
    public void setVisibility(long visibility) { this.visibility = visibility; }
    public long getDt() { return dt; }
    public void setDt(long dt) { this.dt = dt; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private double temp;
        @JsonProperty("feels_like") private double feelsLike;
        @JsonProperty("temp_min")   private double tempMin;
        @JsonProperty("temp_max")   private double tempMax;
        private int humidity;
        private int pressure;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }
        public double getFeelsLike() { return feelsLike; }
        public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
        public double getTempMin() { return tempMin; }
        public void setTempMin(double tempMin) { this.tempMin = tempMin; }
        public double getTempMax() { return tempMax; }
        public void setTempMax(double tempMax) { this.tempMax = tempMax; }
        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
        public int getPressure() { return pressure; }
        public void setPressure(int pressure) { this.pressure = pressure; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String main;
        private String description;
        private String icon;

        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        private double speed;
        private int deg;

        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }
        public int getDeg() { return deg; }
        public void setDeg(int deg) { this.deg = deg; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        private String country;
        private long sunrise;
        private long sunset;

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public long getSunrise() { return sunrise; }
        public void setSunrise(long sunrise) { this.sunrise = sunrise; }
        public long getSunset() { return sunset; }
        public void setSunset(long sunset) { this.sunset = sunset; }
    }
}
