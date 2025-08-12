# 📦 JitPack 发布指南

## 🚀 如何发布新版本到 JitPack

### 1. 提交代码到 GitHub

```bash
# 添加所有修改的文件
git add .

# 提交修改
git commit -m "Release version 2.0.1: Fix Android 14+ compatibility and modernize build"

# 推送到 GitHub
git push origin master
```

### 2. 创建版本标签

```bash
# 创建版本标签
git tag v2.0.6

# 推送标签到 GitHub
git push origin v2.0.6
```

### 3. 验证 JitPack 构建

访问 https://jitpack.io/#MZCretin/AutoUpdateProject 查看构建状态

## 📱 其他开发者如何使用

### 在项目根目录的 `build.gradle` 中添加 JitPack 仓库：

```groovy
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }  // 添加这一行
    }
}
```

### 在 app 模块的 `build.gradle` 中添加依赖：

```groovy
dependencies {
    implementation 'com.github.MZCretin:AutoUpdateProject:v2.0.6'
}
```

## 🔄 版本管理

### 发布新版本的步骤：

1. **更新版本号**：
   - 修改 `cretinautoupdatelibrary/build.gradle` 中的 `versionCode` 和 `versionName`

2. **提交并标签**：
   ```bash
   git add .
   git commit -m "Release version x.x.x"
   git push origin master
   git tag vx.x.x
   git push origin vx.x.x
   ```

3. **通知用户更新依赖**：
   ```groovy
   implementation 'com.github.MZCretin:AutoUpdateProject:vx.x.x'
   ```

## 🎯 JitPack 的优势

- ✅ **免费使用**：完全免费的服务
- ✅ **自动构建**：从 GitHub 标签自动构建
- ✅ **版本管理**：支持分支、标签、提交哈希
- ✅ **快速发布**：推送标签后几分钟内可用
- ✅ **全球CDN**：快速的依赖下载速度

## 📋 最佳实践

1. **语义化版本**：使用 `vMajor.Minor.Patch` 格式
2. **发布说明**：在 GitHub Release 中添加更新日志
3. **向后兼容**：避免在 Minor 版本中引入破坏性变更
4. **测试充分**：发布前确保在多个 Android 版本上测试

## 🔍 故障排除

如果 JitPack 构建失败：

1. 检查项目是否能在本地正常编译
2. 确保所有依赖都是公开可用的
3. 查看 JitPack 构建日志获取详细错误信息
4. 确认 `maven-publish` 插件已应用

---

当前库信息：
- **GroupId**: com.github.MZCretin
- **ArtifactId**: AutoUpdateProject  
- **当前版本**: v2.0.6
- **JitPack地址**: https://jitpack.io/#MZCretin/AutoUpdateProject
