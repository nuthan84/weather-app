@echo off
setlocal enabledelayedexpansion

title Weather Dashboard Launcher

cd /d "%~dp0"

echo ==============================================================
echo   🌤️  Real-Time Weather Dashboard Launcher
echo ==============================================================
echo.

:: 1. Check Java
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in your PATH.
    echo Please install Java 17 or higher: https://adoptium.net/
    echo.
    pause
    exit /b 1
)

:: 2. Check Node.js and npm
where npm >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Node.js / npm is not installed or not in your PATH.
    echo Please install Node.js: https://nodejs.org/
    echo.
    pause
    exit /b 1
)

:: 3. Check / Install Frontend Dependencies
if not exist "frontend\node_modules\" (
    echo [INFO] Installing frontend dependencies...
    cd /d "%~dp0frontend"
    call npm install
    if %errorlevel% neq 0 (
        echo [ERROR] Failed to install frontend dependencies.
        cd /d "%~dp0"
        pause
        exit /b 1
    )
    cd /d "%~dp0"
    echo [OK] Frontend dependencies installed.
    echo.
)

:: 4. Check OpenWeatherMap API Key
if "%WEATHER_API_KEY%"=="" (
    echo [NOTE] WEATHER_API_KEY environment variable is not set.
    echo        The app will start normally, but live weather requests
    echo        will require an API key from https://openweathermap.org/api
    echo.
) else (
    echo [OK] WEATHER_API_KEY detected.
    echo.
)

:: 5. Launch Backend (Spring Boot) in dedicated window
echo [INFO] Starting Backend (Spring Boot on port 8080)...
if exist "backend\mvnw.cmd" (
    start "Weather App - Backend (Port 8080)" cmd /c "title Weather App - Backend ^& cd /d "%~dp0backend" ^& call mvnw.cmd spring-boot:run"
) else (
    start "Weather App - Backend (Port 8080)" cmd /c "title Weather App - Backend ^& cd /d "%~dp0backend" ^& call mvn spring-boot:run"
)

:: 6. Launch Frontend (Vite) in dedicated window
echo [INFO] Starting Frontend (Vite on port 5173)...
start "Weather App - Frontend (Port 5173)" cmd /c "title Weather App - Frontend ^& cd /d "%~dp0frontend" ^& call npm run dev"

echo.
echo ==============================================================
echo   🌤️  Real-Time Weather Dashboard is up and running!
echo ==============================================================
echo   Frontend : http://localhost:5173
echo   Backend  : http://localhost:8080
echo   Health   : http://localhost:8080/api/weather/health
echo ==============================================================
echo.
echo Opening http://localhost:5173 in your default browser...
timeout /t 3 /nobreak >nul 2>nul
start "" "http://localhost:5173"

echo.
echo Press any key to stop all servers and exit...
pause >nul

echo Stopping servers...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr /r ":8080 .*LISTENING"') do taskkill /f /t /pid %%a >nul 2>nul
for /f "tokens=5" %%a in ('netstat -aon ^| findstr /r ":5173 .*LISTENING"') do taskkill /f /t /pid %%a >nul 2>nul
echo Servers stopped.

