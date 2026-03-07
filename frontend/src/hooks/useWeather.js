import { useState, useCallback } from 'react'
import { fetchWeatherByCity, fetchWeatherByCoords } from '../services/weatherApi'

export function useWeather() {
  const [weather, setWeather] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError]   = useState('')

  const searchByCity = useCallback(async (city, units = 'metric') => {
    if (!city.trim()) return
    setLoading(true)
    setError('')
    try {
      const data = await fetchWeatherByCity(city, units)
      setWeather(data)
    } catch (err) {
      setError(err.message)
      setWeather(null)
    } finally {
      setLoading(false)
    }
  }, [])

  const searchByCoords = useCallback(async (lat, lon, units = 'metric') => {
    setLoading(true)
    setError('')
    try {
      const data = await fetchWeatherByCoords(lat, lon, units)
      setWeather(data)
    } catch (err) {
      setError(err.message)
      setWeather(null)
    } finally {
      setLoading(false)
    }
  }, [])

  const getLocation = useCallback(() => {
    if (!navigator.geolocation) {
      setError('Geolocation not supported by your browser.')
      return
    }
    setLoading(true)
    navigator.geolocation.getCurrentPosition(
      ({ coords }) => searchByCoords(coords.latitude, coords.longitude),
      () => { setLoading(false); setError('Location access denied.') }
    )
  }, [searchByCoords])

  return { weather, loading, error, searchByCity, getLocation }
}
