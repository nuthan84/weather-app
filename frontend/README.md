# Weather App — Frontend (React + Vite)

## Setup

### Prerequisites
- Node.js 18+
- npm

### 1. Install dependencies
```bash
npm install
```

### 2. Run (backend must be running on port 8080)
```bash
npm run dev
```
Opens at: **http://localhost:5173**

## Project Structure
```
src/
├── components/
│   ├── SearchBar.jsx     ← Search input + locate button
│   ├── TempDisplay.jsx   ← Big temperature card
│   ├── StatGrid.jsx      ← Humidity, wind, pressure, visibility
│   └── LoadingSpinner.jsx
├── hooks/
│   └── useWeather.js     ← Custom hook for weather state
├── services/
│   └── weatherApi.js     ← Axios API calls to Spring Boot
├── styles/
│   ├── index.css         ← Global reset
│   └── App.css           ← All component styles
├── App.jsx               ← Root component + theme logic
└── main.jsx              ← Entry point
```

## Features
- 🔍 City search via Spring Boot proxy
- 📍 Geolocation support
- 🌡️ °C / °F toggle
- 🎨 Dynamic theme based on weather condition
- ⚡ Axios with request/response interceptors
- 🪝 Custom `useWeather` hook with state management
