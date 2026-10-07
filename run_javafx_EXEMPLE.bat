@echo off
setlocal

REM ===== CHEMIN VERS JAVAFX LIB =====
set "JAVAFX_LIB=C:PATH\JAVAFX\SDK"

REM ===== DOSSIERS =====
set "SRC_DIR=src\main\java"
set "OUT_DIR=out"

echo Compilation...
if exist "%OUT_DIR%" rmdir /S /Q "%OUT_DIR%"
mkdir "%OUT_DIR%"

javac --module-path "%JAVAFX_LIB%" --add-modules=javafx.controls -d "%OUT_DIR%" %SRC_DIR%\com\example\*.java

if %errorlevel% neq 0 (
    echo.
    echo Erreur de compilation !
    pause
    exit /b %errorlevel%
)

echo.
echo Lancement de l'application...
java --module-path "%JAVAFX_LIB%" --add-modules=javafx.controls -cp "%OUT_DIR%" com.example.Main

echo.
echo Fin du programme.
pause


