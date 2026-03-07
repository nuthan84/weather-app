function StatCard({ emoji, value, label, delay }) {
  return (
    <div className="stat-card card-anim" style={{ animationDelay: delay }}>
      <div className="stat-emoji">{emoji}</div>
      <div className="stat-value">{value}</div>
      <div className="stat-label">{label}</div>
    </div>
  )
}

export default function StatGrid({ weather }) {
  const { main, wind, visibility } = weather
  const windDirs = ['N','NE','E','SE','S','SW','W','NW']
  const windDir  = windDirs[Math.round((wind?.deg ?? 0) / 45) % 8]

  const stats = [
    { emoji: '💧', value: `${main.humidity}%`,                            label: 'HUMIDITY'   },
    { emoji: '🌬️', value: `${Math.round(wind?.speed ?? 0)} m/s · ${windDir}`, label: 'WIND'  },
    { emoji: '📊', value: `${main.pressure} hPa`,                         label: 'PRESSURE'   },
    { emoji: '👁️', value: `${((visibility ?? 0) / 1000).toFixed(1)} km`,  label: 'VISIBILITY' },
  ]

  return (
    <div className="stat-grid">
      {stats.map((s, i) => (
        <StatCard key={s.label} {...s} delay={`${0.1 + i * 0.08}s`} />
      ))}
    </div>
  )
}
