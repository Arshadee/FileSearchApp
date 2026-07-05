@echo off
setlocal enabledelayedexpansion

:: Check if an argument was passed at all
if "%~1"=="" (
    echo [WARNING] No configuration path provided. 
    echo Usage: run-search.bat ^<path-to-json-config^>
    echo.
)

:: Execute the Uber-JAR passing the path parameter securely enclosed in literal quotes
java -jar "C:\demos\FileSearchApp\target\FileSerachApp-1.0-SNAPSHOT-jar-with-dependencies.jar" "%~1"

pause
endlocal