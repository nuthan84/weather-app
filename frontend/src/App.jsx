import { useState, useEffect } from 'react'
import SearchBar      from './components/SearchBar'
import TempDisplay    from './components/TempDisplay'
import StatGrid       from './components/StatGrid'
import LoadingSpinner from './components/LoadingSpinner'
import { useWeather } from './hooks/useWeather'
import './styles/App.css'

const THEMES = {
  Clear:        { bg: 'linear-gradient(135deg,#FF6B00,#FFD700,#FF8C00)', emoji: '☀️',  text: '#FF6B00' },
  Clouds:       { bg: 'linear-gradient(135deg,#4A5568,#718096,#2D3748)', emoji: '☁️',  text: '#718096' },
  Rain:         { bg: 'linear-gradient(135deg,#1A365D,#2B6CB0,#2C5282)', emoji: '🌧️', text: '#2B6CB0' },
  Drizzle:      { bg: 'linear-gradient(135deg,#2C5282,#4299E1,#3182CE)', emoji: '🌦️', text: '#4299E1' },
  Thunderstorm: { bg: 'linear-gradient(135deg,#1A202C,#553C9A,#2D3748)', emoji: '⛈️', text: '#553C9A' },
  Snow:         { bg: 'linear-gradient(135deg,#EBF8FF,#BEE3F8,#90CDF4)', emoji: '❄️',  text: '#4299E1' },
  Mist:         { bg: 'linear-gradient(135deg,#CBD5E0,#E2E8F0,#A0AEC0)', emoji: '🌫️', text: '#4A5568' },
  default:      { bg: 'linear-gradient(135deg,#6B21A8,#DB2777,#EA580C)', emoji: '🌡️', text: '#DB2777' },
}

export default function App() {
  const { weather, loading, error, searchByCity, getLocation } = useWeather()
  const [unit,    setUnit]    = useState('C')
  const [animKey, setAnimKey] = useState(0)
  const [theme,   setTheme]   = useState(THEMES.default)

  useEffect(() => {
    if (weather) {
      const cond = weather.weather?.[0]?.main
      setTheme(THEMES[cond] ?? THEMES.default)
      setAnimKey((k) => k + 1)
    }
  }, [weather])

  const handleSearch = (city) =>
    searchByCity(city, unit === 'C' ? 'metric' : 'imperial')

  return (
    <div className="app" style={{ background: theme.bg }}>
      <div className="blob blob-1" />
      <div className="blob blob-2" />

      <header className="app-header">
        <p className="app-subtitle">REAL-TIME</p>
        <h1 className="app-title" style={{ textShadow: `0 4px 30px ${theme.text}` }}>
          WEATHER NOW
        </h1>
      </header>

      <div className="controls">
        <SearchBar onSearch={handleSearch} onLocate={getLocation} loading={loading} />
        <button className="unit-btn" onClick={() => setUnit((u) => (u === 'C' ? 'F' : 'C'))}>
          Switch to {unit === 'C' ? '°F' : '°C'}
        </button>
      </div>

      {error && <div className="error-box">⚠️ {error}</div>}
      {loading && <LoadingSpinner />}

      {weather && !loading && (
        <main key={animKey} className="weather-content">
          <TempDisplay weather={weather} unit={unit} theme={theme} />
          <StatGrid weather={weather} />
          <p className="powered-by">Powered by OpenWeatherMap · Spring Boot proxy</p>
        </main>
      )}

      {!weather && !loading && !error && (
        <div className="empty-state">
          <span className="empty-emoji">🌍</span>
          <p>Search a city or use 📍 for your location</p>
        </div>
      )}
    </div>
  )
}
