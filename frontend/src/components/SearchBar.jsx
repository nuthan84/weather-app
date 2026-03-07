import { useState } from 'react'

export default function SearchBar({ onSearch, onLocate, loading }) {
  const [query, setQuery] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()
    if (query.trim()) onSearch(query.trim())
  }

  return (
    <div className="search-wrapper">
      <form className="search-form" onSubmit={handleSubmit}>
        <input
          className="search-input"
          type="text"
          placeholder="Search city…"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          disabled={loading}
        />
        <button className="search-btn" type="submit" disabled={loading || !query.trim()}>
          {loading ? '…' : 'GO'}
        </button>
      </form>
      <button className="locate-btn" onClick={onLocate} disabled={loading} title="Use my location">
        📍
      </button>
    </div>
  )
}
