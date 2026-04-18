# 学生成绩管理系统

基于Spring Boot 3 + SQLite的学生考试成绩管理系统，提供RESTful API接口进行增删改查操作。

## 技术栈

- **框架**: Spring Boot 3.2.5
- **构建工具**: Maven 3.9.x
- **数据库**: SQLite
- **ORM**: Spring Data JPA + Hibernate
- **JDK版本**: Eclipse Temurin JDK 21.0.10+7
- **打包方式**: WAR
- **其他**: Lombok
- **CI/CD**: GitHub Actions

## 项目结构

```
main/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── StudentScoreApplication.java  # 主启动类
│       │           ├── entity/                       # 实体层
│       │           │   ├── Student.java              # 学生实体
│       │           │   └── Score.java                # 成绩实体
│       │           ├── repository/                   # 数据访问层
│       │           │   ├── StudentRepository.java
│       │           │   └── ScoreRepository.java
│       │           ├── service/                      # 业务逻辑层
│       │           │   ├── StudentService.java
│       │           │   └── ScoreService.java
│       │           └── controller/                   # 控制层
│       │               ├── StudentController.java
│       │               └── ScoreController.java
│       └── resources/
│           └── application.yml                       # 配置文件
├── pom.xml                                           # Maven配置
└── student_score.db                                  # SQLite数据库文件(运行后自动生成)
```

## 数据库表结构

### students (学生表)

| 字段名       | 类型        | 说明               |
|------------|-------------|--------------------|
| id         | BIGINT      | 主键，自增         |
| name       | VARCHAR(50) | 学生姓名，非空     |
| student_no | VARCHAR(20) | 学号，唯一，非空   |
| gender     | INT         | 性别(1:男, 2:女)   |
| birthday   | DATE        | 出生日期           |
| major      | VARCHAR(100)| 专业               |
| clazz      | VARCHAR(20) | 班级               |
| create_time| DATE        | 创建时间           |
| update_time| DATE        | 更新时间           |

### scores (成绩表)

| 字段名       | 类型        | 说明                 |
|------------|-------------|----------------------|
| id         | BIGINT      | 主键，自增           |
| student_id | BIGINT      | 学生ID，外键         |
| subject    | VARCHAR(50) | 科目名称，非空       |
| score      | INT         | 分数，非空           |
| exam_type  | VARCHAR(20) | 考试类型(期中/期末等)|
| exam_date  | DATE        | 考试日期             |
| create_time| DATE        | 创建时间             |
| update_time| DATE        | 更新时间             |

## API接口说明

### 学生API (前缀: /api/students)

| 方法   | 路径                          | 说明                     |
|--------|-------------------------------|--------------------------|
| POST   | /api/students                 | 创建学生                 |
| GET    | /api/students/{id}            | 根据ID查询单个学生       |
| GET    | /api/students/no/{studentNo}  | 根据学号查询单个学生     |
| GET    | /api/students                 | 查询所有学生(批量查询)   |
| GET    | /api/students/major/{major}   | 根据专业查询学生         |
| GET    | /api/students/class/{clazz}   | 根据班级查询学生         |
| PUT    | /api/students/{id}            | 更新学生信息             |
| DELETE | /api/students/{id}            | 删除学生                 |

### 成绩API (前缀: /api/scores)

| 方法   | 路径                                      | 说明                         |
|--------|-------------------------------------------|------------------------------|
| POST   | /api/scores                               | 创建成绩                     |
| GET    | /api/scores/{id}                          | 根据ID查询单个成绩           |
| GET    | /api/scores                               | 查询所有成绩(批量查询)       |
| GET    | /api/scores/student/{studentId}           | 根据学生ID查询成绩           |
| GET    | /api/scores/subject/{subject}             | 根据科目查询成绩             |
| GET    | /api/scores/student/{studentId}/subject/{subject} | 根据学生ID和科目查询成绩    |
| PUT    | /api/scores/{id}                          | 更新成绩信息                 |
| DELETE | /api/scores/{id}                          | 删除成绩                     |
| DELETE | /api/scores/student/{studentId}           | 删除某学生的所有成绩         |

## 运行说明

### 前置要求

- **JDK**: Eclipse Temurin 21.0.10+7 或更高版本
- **Maven**: 3.9.x

### 运行步骤

1. **编译项目**
   ```bash
   mvn clean package
   ```

2. **运行项目**
   ```bash
   mvn spring-boot:run
   ```
   或者
   ```bash
   java -jar target/student-score-system-1.0.0.jar
   ```

3. **访问接口**
   - 服务端口: 8080
   - 接口基础地址: http://localhost:8080/api/

4. **WAR包部署**
   ```bash
   mvn clean package -DskipTests
   ```
   生成的WAR包位置: `target/school.war`

---

## CI/CD 自动化部署

### GitHub Actions 流水线配置

项目已配置完整的CI/CD自动化流水线，配置文件位于 `.github/workflows/ci.yml`

**触发条件**:
- `public` 分支有代码提交时自动触发

**技术环境**:
- **JDK**: Eclipse Temurin 21.0.10+7
- **Maven**: 3.9.6

**流水线步骤**:
1. 检出代码
2. 配置JDK和Maven环境
3. 版本验证
4. Maven编译打包
5. 生成 `school.war`
6. 通过SSH密钥自动部署到服务器

**部署目标**:
- 服务器: `test.stoprefactoring.com:22`
- 部署路径: `/public/backend/school.war` (覆盖式部署)

**配置Secrets**:
需要在GitHub仓库配置以下Secrets:
- `SSH_USERNAME`: SSH登录用户名
- `SSH_PRIVATE_KEY`: SSH私钥内容

---

## 安全检查报告

### 1. SQL注入检查 ✅ 安全

**检查结果**: 不存在SQL盲注风险

**详细分析**:
- 项目使用Spring Data JPA框架，所有数据库操作均通过方法名查询实现
- 查询方法: `findByStudentNo()`, `findByMajor()`, `findByClazz()`, `findBySubject()` 等
- JPA框架自动实现参数绑定，杜绝了SQL字符串拼接注入风险
- 未发现原生SQL查询(@Query)或JDBC Statement直接操作

### 2. 其他安全检查项与改进建议

#### 🔴 高优先级建议

**2.1 缺少输入验证**
- **风险**: 用户提交的数据未经过滤和验证，可能导致非法数据入库
- **建议**: 添加JSR-380 Bean Validation注解 (`@NotNull`, `@Size`, `@Pattern`等)
- **实现位置**: Entity类字段添加验证注解，Controller添加`@Valid`注解

**2.2 缺少认证授权机制**
- **风险**: 所有API接口公开访问，无权限控制
- **建议**: 集成Spring Security + JWT实现认证授权
- **实现**: 添加角色权限控制，区分管理员/普通用户权限

**2.3 异常信息暴露**
- **风险**: RuntimeException直接抛出，可能暴露敏感信息
- **建议**: 添加全局异常处理器`@RestControllerAdvice`，统一异常返回格式

---

### 🔴 API鉴权专项检查报告: **存在严重问题**

**检查结果**: 所有17个API接口完全公开，无任何鉴权保护

**详细问题清单**:
- **学生模块 (8个接口)**:
  - POST `/api/students` - 匿名创建学生
  - GET `/api/students/{id}` - 匿名查询学生
  - GET `/api/students/no/{studentNo}` - 匿名按学号查询
  - GET `/api/students` - 匿名批量查询所有学生
  - GET `/api/students/major/{major}` - 匿名按专业查询
  - GET `/api/students/class/{clazz}` - 匿名按班级查询
  - PUT `/api/students/{id}` - 匿名修改学生信息
  - DELETE `/api/students/{id}` - 匿名删除学生

- **成绩模块 (9个接口)**:
  - POST `/api/scores` - 匿名创建成绩
  - GET `/api/scores/{id}` - 匿名查询成绩
  - GET `/api/scores` - 匿名批量查询所有成绩
  - GET `/api/scores/student/{studentId}` - 匿名按学生查询成绩
  - GET `/api/scores/subject/{subject}` - 匿名按科目查询成绩
  - GET `/api/scores/student/{studentId}/subject/{subject}` - 匿名组合查询
  - PUT `/api/scores/{id}` - 匿名修改成绩
  - DELETE `/api/scores/{id}` - 匿名删除成绩
  - DELETE `/api/scores/student/{studentId}` - 匿名批量删除成绩

**风险等级**: 🔴 极高风险

**安全建议**:
1. 立即集成Spring Security实现基于角色的访问控制 (RBAC)
2. 实现JWT Token认证机制，所有接口强制校验Token
3. 区分管理员角色(可增删改)和普通用户角色(仅可读)
4. 添加API请求签名校验防止重放攻击

---

#### 🟡 中优先级建议

**2.4 XSS跨站脚本防护**
- **风险**: 返回的JSON数据未做XSS过滤，前端直接渲染可能产生风险
- **建议**: 添加JSON序列化转义配置或输入时XSS过滤

**2.5 CORS跨域配置缺失**
- **风险**: 前端应用可能无法正常调用API
- **建议**: 添加`@CrossOrigin`注解或全局CORS配置

**2.6 速率限制保护**
- **风险**: 接口无请求频率限制，可能遭受CC攻击
- **建议**: 集成Bucket4j或Spring Gateway实现限流

---

#### 🟢 低优先级建议

**2.7 敏感日志审查**
- 建议: 生产环境关闭SQL日志打印，配置日志级别为INFO或WARN

**2.8 SQL注入检测建议**
- 建议: 集成OWASP Dependency-Check进行依赖漏洞扫描
- 建议: 集成SpotBugs进行静态代码安全分析

---

## API 详细说明

### 学生API详细示例

#### 1. 创建学生
```
POST /api/students
Content-Type: application/json
```
**请求体:**
```json
{
  "name": "张三",
  "studentNo": "2024001",
  "gender": 1,
  "birthday": "2002-05-15",
  "major": "计算机科学与技术",
  "clazz": "2024级1班"
}
```
**响应:** 201 Created，返回创建的学生对象

---

#### 2. 根据ID查询单个学生
```
GET /api/students/1
```
**响应:** 200 OK
```json
{
  "id": 1,
  "name": "张三",
  "studentNo": "2024001",
  "gender": 1,
  "birthday": "2002-05-15",
  "major": "计算机科学与技术",
  "clazz": "2024级1班",
  "createTime": "2024-04-19",
  "updateTime": "2024-04-19"
}
```

---

#### 3. 根据学号查询单个学生
```
GET /api/students/no/2024001
```
**响应:** 返回学生对象（格式同上）

---

#### 4. 查询所有学生（批量查询）
```
GET /api/students
```
**响应:** 200 OK，返回学生数组
```json
[
  {
    "id": 1,
    "name": "张三",
    "studentNo": "2024001",
    "major": "计算机科学与技术",
    "clazz": "2024级1班"
  },
  {
    "id": 2,
    "name": "李四",
    "studentNo": "2024002",
    "major": "软件工程",
    "clazz": "2024级2班"
  }
]
```

---

#### 5. 根据专业查询学生
```
GET /api/students/major/计算机科学与技术
```
**响应:** 返回该专业下所有学生的数组

---

#### 6. 根据班级查询学生
```
GET /api/students/class/2024级1班
```
**响应:** 返回该班级下所有学生的数组

---

#### 7. 更新学生信息
```
PUT /api/students/1
Content-Type: application/json
```
**请求体:**
```json
{
  "name": "张三三",
  "gender": 1,
  "birthday": "2002-05-15",
  "major": "软件工程",
  "clazz": "2024级2班"
}
```
**响应:** 200 OK，返回更新后的学生对象

---

#### 8. 删除学生
```
DELETE /api/students/1
```
**响应:** 204 No Content，无返回内容

---

### 成绩API详细示例

#### 1. 创建成绩
```
POST /api/scores
Content-Type: application/json
```
**请求体:**
```json
{
  "studentId": 1,
  "subject": "高等数学",
  "score": 95,
  "examType": "期末考试",
  "examDate": "2024-01-15"
}
```
**响应:** 201 Created，返回创建的成绩对象

---

#### 2. 根据ID查询单个成绩
```
GET /api/scores/1
```
**响应:** 200 OK
```json
{
  "id": 1,
  "studentId": 1,
  "subject": "高等数学",
  "score": 95,
  "examType": "期末考试",
  "examDate": "2024-01-15",
  "createTime": "2024-04-19",
  "updateTime": "2024-04-19"
}
```

---

#### 3. 查询所有成绩（批量查询）
```
GET /api/scores
```
**响应:** 200 OK，返回所有成绩数组

---

#### 4. 根据学生ID查询所有成绩
```
GET /api/scores/student/1
```
**响应:** 返回该学生的所有成绩数组
```json
[
  {
    "id": 1,
    "studentId": 1,
    "subject": "高等数学",
    "score": 95,
    "examType": "期末考试"
  },
  {
    "id": 2,
    "studentId": 1,
    "subject": "大学英语",
    "score": 88,
    "examType": "期末考试"
  }
]
```

---

#### 5. 根据科目查询所有成绩
```
GET /api/scores/subject/高等数学
```
**响应:** 返回该科目的所有学生成绩数组

---

#### 6. 查询某学生某科目的成绩
```
GET /api/scores/student/1/subject/高等数学
```
**响应:** 返回该学生该科目的所有成绩记录数组

---

#### 7. 更新成绩信息
```
PUT /api/scores/1
Content-Type: application/json
```
**请求体:**
```json
{
  "subject": "高等数学",
  "score": 98,
  "examType": "期末考试",
  "examDate": "2024-01-15"
}
```
**响应:** 200 OK，返回更新后的成绩对象

---

#### 8. 删除单条成绩
```
DELETE /api/scores/1
```
**响应:** 204 No Content，无返回内容

---

#### 9. 删除某学生的所有成绩
```
DELETE /api/scores/student/1
```
**响应:** 204 No Content，无返回内容

## 注意事项

1. 首次运行后会自动在项目根目录下创建 `student_score.db` SQLite数据库文件
2. Hibernate配置为 `ddl-auto: update`，会自动创建和更新表结构
3. 学号具有唯一性约束，重复学号会抛出异常
4. 开启了SQL日志打印，可在控制台查看执行的SQL语句
