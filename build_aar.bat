@echo off
REM AutoUpdate Library AAR构建脚本 (Windows)
REM 使用方法: build_aar.bat [release|debug]

echo === AutoUpdate Library AAR Builder ===
echo 当前版本: 2.0.9
echo ======================================

REM 设置构建类型，默认为release
set BUILD_TYPE=%1
if "%BUILD_TYPE%"=="" set BUILD_TYPE=release

echo 构建类型: %BUILD_TYPE%
echo.

REM 清理之前的构建
echo 🧹 清理之前的构建...
call gradlew.bat clean

if %ERRORLEVEL% neq 0 (
    echo ❌ 清理失败
    exit /b 1
)

REM 构建AAR
echo.
echo 🔨 开始构建 AAR 包...

if "%BUILD_TYPE%"=="release" (
    call gradlew.bat cretinautoupdatelibrary:assembleRelease
) else (
    call gradlew.bat cretinautoupdatelibrary:assembleDebug
)

if %ERRORLEVEL% neq 0 (
    echo ❌ AAR构建失败
    exit /b 1
)

REM 创建输出目录
set OUTPUT_DIR=aar_outputs
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"

REM 复制AAR文件
set SOURCE_AAR=cretinautoupdatelibrary\build\outputs\aar\cretinautoupdatelibrary-2.0.9-%BUILD_TYPE%.aar
set TARGET_AAR=%OUTPUT_DIR%\cretinautoupdatelibrary-2.0.9-%BUILD_TYPE%.aar

if exist "%SOURCE_AAR%" (
    copy "%SOURCE_AAR%" "%TARGET_AAR%"
    echo.
    echo ✅ AAR构建成功！
    echo 📦 输出文件: %CD%\%TARGET_AAR%
    
    echo.
    echo 🎉 构建完成！您可以在项目中使用以下方式引入:
    echo    1. 将 %TARGET_AAR% 复制到您的项目的 libs/ 目录
    echo    2. 在 build.gradle 中添加:
    echo       implementation files('libs/cretinautoupdatelibrary-2.0.9-%BUILD_TYPE%.aar'^)
    echo       implementation 'com.liulishuo.filedownloader:library:1.7.6'
    echo.
) else (
    echo ❌ AAR文件未找到: %SOURCE_AAR%
    echo 请检查构建日志
    exit /b 1
)

pause
