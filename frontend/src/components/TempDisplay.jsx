export default function TempDisplay({ weather, unit, theme }) {
  const toF = (c) => Math.round((c * 9) / 5 + 32)
  const display = (c) => (unit === 'C' ? Math.round(c) : toF(c))
  const sym = unit === 'C' ? '°C' : '°F'
  const { main, weather: conditions, sys, name } = weather
  const condition = conditions?.[0]
  const formatTime = (unix) =>
    new Date(unix * 1000).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })

  return (
    <div className="temp-card card-anim">
      <div className="city-row">
        <span className="weather-emoji">{theme.emoji}</span>
        <div>
          <h2 className="city-name">{name}, {sys?.country}</h2>
          <p className="condition-desc">{condition?.description}</p>
        </div>
      </div>

      <div className="big-temp temp-pulse">
        {display(main.temp)}
        <span className="unit-sup">{sym}</span>
      </div>

      <div className="feels-like-pill">
        🤔 Feels like <strong>{display(main.feels_like)}{sym}</strong>
      </div>

      <div className="min-max-row">
        <span>↓ <strong>{display(main.temp_min)}{sym}</strong></span>
        <span className="divider">|</span>
        <span>↑ <strong>{display(main.temp_max)}{sym}</strong></span>
      </div>

      <div className="sun-row">
        <div className="sun-item">
          <span>🌅</span>
          <span className="sun-time">{formatTime(sys?.sunrise)}</span>
          <span className="sun-label">SUNRISE</span>
        </div>
        <div className="sun-divider" />
        <div className="sun-item">
          <span>🌇</span>
          <span className="sun-time">{formatTime(sys?.sunset)}</span>
          <span className="sun-label">SUNSET</span>
        </div>
      </div>
    </div>
  )
}
