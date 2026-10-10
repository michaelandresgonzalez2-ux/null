@echo off
setlocal

set APP_HOME=%~dp0

if exist "%APP_HOME%gradlew.bat" (
    call "%APP_HOME%gradlew.bat" %*
    exit /b %ERRORLEVEL%
)

where gradle >nul 2>nul
if not errorlevel 1 (
    gradle %*
    exit /b %ERRORLEVEL%
)

echo Gradle wrapper not found and no Gradle installation was detected.
echo Install Gradle or run: gradle wrapper
exit /b 1
