import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' },
})

api.interceptors.request.use(
  (config) => {
    console.log(`[API] ${config.method?.toUpperCase()} ${config.url}`, config.params)
    return config
  },
  (error) => Promise.reject(error)
)

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const message =
      error.response?.data?.error ||
      error.response?.data?.message ||
      error.message ||
      'Something went wrong'
    return Promise.reject(new Error(message))
  }
)

export const fetchWeatherByCity = (city, units = 'metric') =>
  api.get('/weather', { params: { city, units } }).then((r) => r.data)

export const fetchWeatherByCoords = (lat, lon, units = 'metric') =>
  api.get('/weather/coords', { params: { lat, lon, units } }).then((r) => r.data)

export default api
