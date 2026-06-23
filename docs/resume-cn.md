# DevMind 简历项目描述

## 简历版本

DevMind 是一个基于 Spring Boot 的个人开发知识库与 RAG 问答系统，支持用户认证、知识文档管理、自动文档切片、关键词检索、Prompt 构造、可插拔 LLM 调用、引用来源返回和问答日志记录。项目将 AI 问答能力作为完整后端系统的一部分实现，而不是简单套壳聊天接口。

## 技术栈

Java 17、Spring Boot 3、Spring Security、JWT、MyBatis-Plus、MySQL、Redis、Maven、Springdoc OpenAPI、DeepSeek API。

## 项目亮点

- 设计 JWT 登录认证和 BCrypt 密码加密，实现用户维度的数据隔离。
- 实现知识文档 CRUD、软删除归档和分页查询，避免误删导致历史数据不可追踪。
- 实现文档自动切片机制，文档创建和更新时自动生成/重建 chunk，为 RAG 检索提供基础数据。
- 实现关键词检索 v0，按当前用户和 active 状态过滤 chunk，返回相关片段和可解释 score。
- 实现 RAG Ask 流程：问题解析、chunk 检索、Prompt 构造、LLM 调用、citations 引用来源返回。
- 抽象 `LlmClient` 接口，支持 Mock 和 DeepSeek provider，避免模型调用逻辑与业务编排耦合。
- 设计 `ai_ask_log` 问答日志，记录 question、retrievalKeyword、retrievedChunkIds、answer、provider、耗时，为 bad case 分析和效果评估打基础。

## 面试讲法

### 为什么不是普通 CRUD？

这个项目不是只做文档增删改查。CRUD 只是知识库的数据入口，核心在于后面的 RAG 链路：

```text
文档入库 -> 自动切片 -> 检索 chunk -> 构造 prompt -> LLM answer -> citations -> ask log
```

面试时可以强调：我把 AI 能力嵌入到一个完整后端系统里，考虑了认证、数据隔离、状态管理、可观测性和模型 provider 解耦。

### 为什么要切片？

长文档不适合直接交给大模型。切片后可以只召回相关片段，减少 token 浪费，也方便后续做 embedding、向量检索、rerank 和 bad case 分析。

### 为什么要 citations？

RAG 系统需要让用户知道答案依据来自哪里。`citations` 返回 chunk id、document id、标题和 score，可以追踪答案来源，也方便开发者检查召回是否准确。

### 为什么要日志？

真实 AI 应用不能只看“有没有回答”，还要能分析回答质量。问答日志记录了问题、检索词、召回 chunk、答案、模型来源和耗时，后续可以用于 bad case 分析、效果评估和成本监控。

### 为什么要 LlmClient 抽象？

模型 provider 可能变化，比如 mock、DeepSeek、通义千问或 OpenAI。如果把模型调用写死在业务 Service 里，后期维护困难。通过 `LlmClient` 接口和 router，可以在不改 RAG 主流程的情况下切换 provider。

## 可继续迭代

- 接入真实 DeepSeek API 并记录 token 用量。
- 引入 embedding 和向量数据库。
- 实现混合检索：关键词 + 向量。
- 增加 rerank。
- 增加问答评价、bad case 标签和召回命中率统计。
- 使用 Flyway 管理数据库迁移。
