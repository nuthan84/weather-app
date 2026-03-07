# Weather App — Backend (Spring Boot)

## Setup

### Prerequisites
- Java 17+
- Maven (or use included `./mvnw`)

### 1. Add your API key
Edit `src/main/resources/application.properties`:
```
weather.api.key=YOUR_OPENWEATHERMAP_API_KEY_HERE
```
Get a free key at: https://openweathermap.org/api

### 2. Run
```bash
./mvnw spring-boot:run
```
Server starts at: **http://localhost:8080**

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/weather?city=London&units=metric` | Weather by city |
| GET | `/api/weather/coords?lat=51.5&lon=-0.1` | Weather by coordinates |
| GET | `/api/weather/health` | Health check |

## Units
- `metric` → Celsius, m/s  
- `imperial` → Fahrenheit, mph
