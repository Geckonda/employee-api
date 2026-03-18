# Employee API 👨‍💼

**REST API для управления сотрудниками компании.**
---

## ⚡ Ключевые возможности

✅ **Управление сотрудниками** - создание, чтение, обновление, удаление
✅ **Аутентификация & Авторизация** - JWT токены, ролевая система (ADMIN/USER)
✅ **Фильтрация и пагинация** - по отделам, статусу, зарплате
✅ **Кеширование** - Redis для оптимизации производительности
✅ **API документация** - Swagger/OpenAPI UI
✅ **Полная контейнеризация** - Docker & Docker Compose
✅ **Интеграционные тесты** - TestContainers

---

## 🛠 Технологический стек

| Компонент | Технология | Версия |
|-----------|-----------|--------|
| **Язык** | Java | 21 |
| **Фреймворк** | Spring Boot | 3.5.11 |
| **БД** | PostgreSQL | 16 |
| **Кеш** | Redis | 7 |
| **Аутентификация** | JWT (JJWT) | 0.11.5 |
| **API Docs** | SpringDoc OpenAPI | 2.8.16 |
| **Миграции** | Flyway | Latest |
| **Сборка** | Maven | 3.9 |

---

## 📋 Структура проекта

```
employee-api/
├── src/main/java/com/example/employee_api/
│   ├── controller/          # REST контролиры
│   │   ├── EmployeeController.java
│   │   ├── AuthController.java
│   │   └── DepartmentController.java
│   ├── service/             # Бизнес-логика
│   │   ├── EmployeeService.java
│   │   └── impl/
│   ├── model/               # Сущности JPA
│   │   ├── Employee.java
│   │   ├── Department.java
│   │   ├── Position.java
│   │   └── User.java
│   ├── repository/          # JPA репозитории
│   ├── security/            # JWT, Security конфиг
│   │   ├── JwtService.java
│   │   ├── JwtFilter.java
│   │   └── SecurityConfig.java
│   ├── kafka/               # Event-driven
│   │   ├── producer/        # KafkaProducer
│   │   ├── consumer/        # KafkaConsumer
│   │   └── event/
│   ├── dto/                 # Data Transfer Objects
│   ├── exception/           # Global exception handling
│   └── config/              # Spring конфиги
├── src/main/resources/
│   ├── application.yml      # Конфигурация приложения
│   ├── db/migration/        # Flyway SQL миграции
│   └── db/data.sql          # Начальные данные
├── Dockerfile               # Docker image
├── docker-compose.yml       # Оркестрация сервисов
├── pom.xml                  # Maven зависимости
└── README.md               # Этот файл
```

---

## 🚀 Быстрый старт

### Предварительные требования

- **Docker** & **Docker Compose** (или локально установленные PostgreSQL, Redis, Kafka)
- **Java 21+**
- **Maven 3.9+**

### 1️⃣ Клонирование и сборка

```bash
git clone https://github.com/your-github/employee-api.git
cd employee-api

# Сборка проекта
mvn clean package
```

### 2️⃣ Запуск с Docker Compose (рекомендуется)

```bash
# Запуск всех сервисов
docker-compose down -v  # очистка старых данных
docker-compose up --build

# API будет доступно на http://localhost:8080
# Swagger доступен на http://localhost:8080/swagger-ui.html
```

### 3️⃣ Локальный запуск

Запуск приложения локально (нужны PostgreSQL, Redis):

```bash
# Запуск приложения
mvn spring-boot:run

# Или импортируй в IDE (IntelliJ, VSCode) и запусти как обычное приложение
```

---

## 📚 API Endpoints

### 🔐 Аутентификация

```bash
# Регистрация
POST /auth/register
Content-Type: application/json

{
  "email": "user@ya.ru",
  "password": "212121"
}

# Вход
POST /auth/login
Content-Type: application/json

{
  "email": "admin@ya.ru",
  "password": "212121"
}

# Ответ
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 👥 Сотрудники

```bash
# Получить всех сотрудников (с фильтрацией и пагинацией)
GET /employees?page=0&size=10&minSalary=50000&maxSalary=150000
Authorization: Bearer {token}

# Получить сотрудника по ID
GET /employees/{id}
Authorization: Bearer {token}

# Создать сотрудника
POST /employees
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstName": "Иван",
  "lastName": "Петров",
  "email": "ivan@company.com",
  "phone": "+79991234567",
  "salary": 100000,
  "departmentId": "uuid-here",
  "positionId": "uuid-here"
}

# Обновить сотрудника
PUT /employees/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstName": "Иван",
  "lastName": "Иванов",
  "salary": 120000
  // остальные поля...
}

# Удалить сотрудника
DELETE /employees/{id}
Authorization: Bearer {token}
```

### 🏢 Отделы

```bash
GET /departments
POST /departments
PUT /departments/{id}
DELETE /departments/{id}
```

---

## ⚙️ Конфигурация

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/employees
    username: postgres
    password: postgres

  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: employee-group
      auto-offset-reset: earliest

  data:
    redis:
      host: localhost
      port: 6379

  cache:
    type: redis
```

### Переменные окружения (для Docker)

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/employees
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_REDIS_HOST=redis
```

---

## 🔐 Безопасность

### JWT Аутентификация

- Все endpoints защищены JWT токенами
- Время жизни токена: 24 часа (настраивается)
- Двухуровневая система ролей: **ADMIN** & **USER**

### Spring Security

```java
// Примеры защиты endpoints
@PreAuthorize("hasAuthority('ADMIN')")  // Только администраторы
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")  // Оба типа
```

---

## 💾 Кеширование

Redis используется для кеширования:

- ✅ Результаты запросов сотрудников по ID
- ✅ Списки отделов и должностей
- ✅ Результаты фильтрованных запросов

**TTL:** 1 час (настраивается в конфиге)

---

## 🧪 Тестирование

### Запуск тестов

```bash
# Все тесты
mvn test

# Интеграционные тесты (используют TestContainers)
mvn test -Dgroups=integration
```

### Структура тестов

```
src/test/java/
├── integration/
│   ├── EmployeeIntegrationTest.java
│   ├── AuthenticationTest.java
│   └── KafkaIntegrationTest.java
└── unit/
    ├── EmployeeServiceTest.java
    └── JwtServiceTest.java
```

---

## 📊 Миграции БД (Flyway)

```
src/main/resources/db/migration/
├── V1__init_schema.sql
```

Миграции выполняются **автоматически** при запуске приложения.

---

## 🐳 Docker Compose сервисы

```yaml
services:
  postgres:      # PostgreSQL БД (порт 5432)
  redis:         # Redis кеш (порт 6379)
  employee-api:  # Spring Boot приложение (порт 8080)
```

**Проверка статуса:**

```bash
docker-compose ps

docker-compose logs -f employee-api    # Логи приложения
```

---

## 📖 Документация API

После запуска приложения откройте в браузере:

📍 **Swagger UI:** http://localhost:8080/swagger-ui.html

Можно:
- ✅ Видеть все endpoints
- ✅ Читать описания параметров
- ✅ Тестировать API прямо из браузера
- ✅ Получить JSON схемы

---

## 🚨 Обработка ошибок

API возвращает четкие JSON ошибки:

```json
// 404 - Сотрудник не найден
{
  "status": 404,
  "message": "Работник с таким Id не найден",
  "timestamp": "2024-03-17T18:00:00"
}

// 400 - Ошибка валидации
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "email": "должен быть корректный email"
  }
}

// 401 - Не авторизован
{
  "status": 401,
  "message": "Требуется JWT токен"
}

// 403 - Нет доступа
{
  "status": 403,
  "message": "Недостаточно прав для выполнения операции"
}
```

---

## 📈 Производительность

- ✅ **Кеширование** - Redis для популярных запросов
- ✅ **Пагинация** - Не загружаем тысячи записей за раз
- ✅ **Индексы БД** - На email, ID сотрудников
- ✅ **Connection Pool** - HikariCP для оптимальной работы с БД

---

## 🔧 Полезные команды

```bash
# Сборка
mvn clean package -DskipTests

# Запуск с профилем
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Docker Compose
docker-compose up -d              # Запуск в фоне
docker-compose down               # Остановка
docker-compose logs -f            # Логи всех сервисов
docker-compose exec postgres psql -U postgres -d employees  # Вход в БД

# PostgreSQL (если локально установлен)
psql -h localhost -U postgres -d employees
```

---

## 📱 Примеры использования

### cURL примеры

```bash
# Вход
TOKEN=$(curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"admin"}' \
  | jq -r '.token')

# Получить всех сотрудников
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/employees

# Создать сотрудника
curl -X POST http://localhost:8080/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Иван",
    "lastName": "Петров",
    "email": "ivan@company.com",
    "phone": "+79991234567",
    "salary": 100000
  }'
```


---

## 🎯 MVP особенности

Этот проект демонстрирует:

✨ **Spring Boot Best Practices:**
- Clean Architecture (Controller → Service → Repository)
- DTO pattern для безопасности и производительности
- Global Exception Handling
- Validation annotations

✨ **Production-Ready:**
- Логирование
- Метрики (готово для добавления Prometheus)
- Health checks
- Graceful shutdown

✨ **Modern Java:**
- Java 21 features (Records, Virtual Threads ready)
- Lombok для сокращения boilerplate кода
- UUID для уникальных идентификаторов

✨ **DevOps:**
- Полная Docker контейнеризация
- Docker Compose для локальной разработки
- Готово для Kubernetes

---

## 📝 Примечания для интервью

**Что хорошо реализовано:**
- ✅ Ролевая система авторизации (ADMIN/USER)
- ✅ Кеширование с Redis
- ✅ Миграции БД с Flyway
- ✅ Полная контейнеризация
- ✅ API документация через Swagger

**Что можно улучшить (идеи для расширения):**
- 📌 Добавить аудит изменений (кто изменил, когда)
- 📌 Implementing GraphQL вместе с REST
- 📌 Full-text search через Elasticsearch
- 📌 Message queue для асинхронной отправки уведомлений
- 📌 Мониторинг с Prometheus/Grafana
- 📌 CI/CD pipeline через GitHub Actions

---

## 🔗 Полезные ссылки

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org/)
- [Redis](https://redis.io/)
- [JWT Guide](https://tools.ietf.org/html/rfc7519)
- [OpenAPI 3.0 Spec](https://swagger.io/specification/)

---
