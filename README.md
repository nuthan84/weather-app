# 🌤️ Real-Time Weather Dashboard

Full-stack weather app — React.js frontend + Spring Boot backend proxy.

## Project Structure
```
weather-app/
├── frontend/        ← React.js app (Vite)
└── backend/         ← Spring Boot API proxy
```

## Quick Start

### 1. Backend (Spring Boot)
```bash
cd backend
# Add your OpenWeatherMap API key in src/main/resources/application.properties
./mvnw spring-boot:run
# Runs on http://localhost:8080
```

### 2. Frontend (React)
```bash
cd frontend
npm install
npm run dev
# Runs on http://localhost:5173
```

## API Key
Get a free key at: https://openweathermap.org/api
Put it in `backend/src/main/resources/application.properties`:
```
weather.api.key=YOUR_KEY_HERE
```
