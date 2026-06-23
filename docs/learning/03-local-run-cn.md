# 03 本地运行说明

## 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 5.7+/8.0+
- IntelliJ IDEA 2024.1.2 或兼容版本

## 第一步：创建数据库

在 DBeaver、DataGrip、IDEA Database 或 MySQL 命令行中执行：

```sql
CREATE DATABASE IF NOT EXISTS devmind DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

然后执行：

```sql
USE devmind;
```

再执行：

```text
src/main/resources/db/schema.sql
```

如果你的数据库是早期版本建的，还需要按顺序执行：

```text
docs/sql/
```

目录下的迁移脚本。

## 第二步：配置 IDEA 运行环境变量

打开 `DevMindApplication` 的运行配置，设置 Environment variables。

最小配置：

```text
DEVMIND_DB_URL=jdbc:mysql://localhost:3306/devmind?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
DEVMIND_DB_USERNAME=your_mysql_username
DEVMIND_DB_PASSWORD=your_mysql_password
DEVMIND_AI_PROVIDER=mock
```

如果要调用真实 DeepSeek：

```text
DEVMIND_AI_PROVIDER=deepseek
DEVMIND_DEEPSEEK_API_KEY=your_api_key
DEVMIND_DEEPSEEK_MODEL=deepseek-v4-flash
```

不要把真实 API Key 写进代码、README、`.http` 文件或 GitHub。

## 第三步：启动后端

运行主类：

```text
com.devmind.DevMindApplication
```

启动成功后访问：

```text
http://localhost:8081/swagger-ui.html
```

## 第四步：用 IDEA HTTP Client 联调

打开：

```text
docs/api/devmind-api.http
```

建议按顺序运行：

1. Register
2. Login
3. Create Knowledge Document
4. Ask AI
5. Submit AI Ask Feedback
6. Evaluation Summary

## 常见错误

### Access denied for user

说明数据库用户名或密码不正确。检查：

```text
DEVMIND_DB_USERNAME
DEVMIND_DB_PASSWORD
```

### Unknown database devmind

说明还没有创建 `devmind` 数据库。

### Table does not exist

说明还没有执行 `schema.sql`，或者早期数据库缺少后续迁移脚本。

### 401 unauthorized

说明没有登录，或者请求头里没有：

```text
Authorization: Bearer <token>
```

### 端口 8081 被占用

修改 `src/main/resources/application.yml`：

```yaml
server:
  port: 8081
```
