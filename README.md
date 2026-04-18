# 学生成绩管理系统

基于Spring Boot 3 + SQLite的学生考试成绩管理系统，提供RESTful API接口进行增删改查操作。

## 技术栈

- **框架**: Spring Boot 3.2.5
- **构建工具**: Maven
- **数据库**: SQLite
- **ORM**: Spring Data JPA + Hibernate
- **JDK版本**: Java 17
- **其他**: Lombok

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

- JDK 21
- Maven 3.6+

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
