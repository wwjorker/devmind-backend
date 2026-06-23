# 03 本地运行说明

当前机器环境检查结果：

- 命令行没有 `docker`，所以暂时不能用 `docker compose up -d` 启动 MySQL/Redis。
- 本机 `3306` 端口有 MySQL。
- `root/root` 不能登录本机 MySQL。
- 本机暂时没有 Redis，但当前代码还没有真正访问 Redis，可以先跑 MySQL 相关接口。

## 第一步：创建 DevMind 数据库

在 Navicat、DataGrip、IDEA Database 或 MySQL 命令行里执行：

```sql
CREATE DATABASE IF NOT EXISTS devmind DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

然后执行：

```sql
USE devmind;
```

再执行 `src/main/resources/db/schema.sql` 里的建表语句。

注意：只创建 `devmind` 数据库，不要操作 `cangqiong` 数据库。

## 第二步：在 IDEA 配置运行参数

打开 DevMind 项目后，找到运行配置，设置 Environment variables：

```text
DEVMIND_DB_URL=jdbc:mysql://localhost:3306/devmind?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
DEVMIND_DB_USERNAME=你的MySQL用户名
DEVMIND_DB_PASSWORD=你的MySQL密码
DEVMIND_REDIS_HOST=localhost
DEVMIND_REDIS_PORT=6379
DEVMIND_REDIS_DATABASE=1
```

如果你本机没有 Redis，先不用紧张。当前接口还不会主动访问 Redis。

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

按顺序运行：

1. Register
2. Login
3. Current User
4. Create Knowledge Document
5. List Document Chunks

## 常见错误

### Access denied for user

说明数据库用户名或密码不对。修改 `DEVMIND_DB_USERNAME` 和 `DEVMIND_DB_PASSWORD`。

### Unknown database devmind

说明还没创建 `devmind` 数据库。

### Table does not exist

说明还没执行 `schema.sql`。

### 401 unauthorized

说明没有登录，或者请求头里没有：

```text
Authorization: Bearer <token>
```

### 端口 8081 被占用

修改 `src/main/resources/application.yml` 里的：

```yaml
server:
  port: 8081
```
