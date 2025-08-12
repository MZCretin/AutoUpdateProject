#!/bin/bash

# AutoUpdate Library AAR构建脚本
# 使用方法: ./build_aar.sh [release|debug]

echo "=== AutoUpdate Library AAR Builder ==="
echo "当前版本: 2.0.9"
echo "======================================"

# 设置构建类型，默认为release
BUILD_TYPE=${1:-release}

echo "构建类型: $BUILD_TYPE"
echo ""

# 清理之前的构建
echo "🧹 清理之前的构建..."
./gradlew clean

if [ $? -ne 0 ]; then
    echo "❌ 清理失败"
    exit 1
fi

# 构建AAR
echo ""
echo "🔨 开始构建 AAR 包..."

if [ "$BUILD_TYPE" = "release" ]; then
    ./gradlew cretinautoupdatelibrary:assembleRelease
else
    ./gradlew cretinautoupdatelibrary:assembleDebug
fi

if [ $? -ne 0 ]; then
    echo "❌ AAR构建失败"
    exit 1
fi

# 创建输出目录
OUTPUT_DIR="aar_outputs"
mkdir -p "$OUTPUT_DIR"

# 复制AAR文件
SOURCE_AAR="cretinautoupdatelibrary/build/outputs/aar/cretinautoupdatelibrary-2.0.9-${BUILD_TYPE}.aar"
TARGET_AAR="$OUTPUT_DIR/cretinautoupdatelibrary-2.0.9-${BUILD_TYPE}.aar"

if [ -f "$SOURCE_AAR" ]; then
    cp "$SOURCE_AAR" "$TARGET_AAR"
    echo ""
    echo "✅ AAR构建成功！"
    echo "📦 输出文件: $(pwd)/$TARGET_AAR"
    echo "📏 文件大小: $(du -h "$TARGET_AAR" | cut -f1)"
    
    # 显示AAR内容信息
    echo ""
    echo "📋 AAR包内容预览:"
    unzip -l "$TARGET_AAR" | head -20
    
    echo ""
    echo "🎉 构建完成！您可以在项目中使用以下方式引入:"
    echo "   1. 将 $TARGET_AAR 复制到您的项目的 libs/ 目录"
    echo "   2. 在 build.gradle 中添加:"
    echo "      implementation files('libs/cretinautoupdatelibrary-2.0.9-${BUILD_TYPE}.aar')"
    echo "      implementation 'com.liulishuo.filedownloader:library:1.7.6'"
    echo ""
else
    echo "❌ AAR文件未找到: $SOURCE_AAR"
    echo "请检查构建日志"
    exit 1
fi
