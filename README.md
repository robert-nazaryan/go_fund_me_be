# Crowdfunding Platform - Backend

## Технологии
- Java 17
- Spring Boot 3.2.0
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Maven

## Требования
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

## Установка и запуск

### 1. Установка PostgreSQL

Создайте базу данных:
```sql
CREATE DATABASE crowdfunding_db;
```

### 2. Настройка application.properties

Откройте `src/main/resources/application.properties` и измените настройки:
```properties
spring.datasource.username=ваш_пользователь
spring.datasource.password=ваш_пароль
jwt.secret=ваш_секретный_ключ_минимум_256_бит
```

### 3. Сборка проекта

```bash
mvn clean install
```

### 4. Запуск приложения

```bash
mvn spring-boot:run
```

Приложение запустится на `http://localhost:8080`

## API Endpoints

### Авторизация
- `POST /api/auth/register` - Регистрация
- `POST /api/auth/login` - Вход

### Кампании
- `GET /api/campaigns` - Все кампании
- `GET /api/campaigns/active` - Активные кампании
- `GET /api/campaigns/{id}` - Кампания по ID
- `GET /api/campaigns/category/{category}` - Кампании по категории
- `GET /api/campaigns/my` - Мои кампании (требуется авторизация)
- `POST /api/campaigns` - Создать кампанию (требуется авторизация)
- `PUT /api/campaigns/{id}` - Обновить кампанию (требуется авторизация)
- `DELETE /api/campaigns/{id}` - Удалить кампанию (требуется авторизация)

### Донаты
- `POST /api/donations` - Сделать донат (требуется авторизация)
- `GET /api/donations/campaign/{campaignId}` - Донаты кампании
- `GET /api/donations/my` - Мои донаты (требуется авторизация)

## Примеры запросов

### Регистрация
```json
POST /api/auth/register
{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe"
}
```

### Создание кампании
```json
POST /api/campaigns
Headers: Authorization: Bearer {token}
{
  "title": "Help with Medical Bills",
  "description": "I need help with urgent medical treatment...",
  "goalAmount": 5000.0,
  "category": "MEDICAL",
  "imageUrl": "https://example.com/image.jpg"
}
```

### Донат
```json
POST /api/donations
Headers: Authorization: Bearer {token}
{
  "campaignId": 1,
  "amount": 100.0,
  "message": "Good luck!",
  "isAnonymous": false
}
```

## Категории кампаний
- `MEDICAL` - Медицина
- `EDUCATION` - Образование
- `EMERGENCY` - Экстренная помощь
- `CREATIVE` - Творчество
- `COMMUNITY` - Общественные проекты
- `OTHER` - Другое

## Виртуальная система оплаты

Для курсовой работы используется фиктивная система донатов:
- Каждый пользователь получает виртуальный баланс 10000₽ при регистрации
- Донаты списываются с виртуального баланса
- Не требуется интеграция с реальными платежными системами

## Структура проекта
```
src/main/java/com/crowdfunding/
├── config/          # Конфигурация (Security, CORS)
├── controller/      # REST контроллеры
├── dto/             # Data Transfer Objects
├── entity/          # JPA Entities
├── exception/       # Обработка исключений
├── repository/      # JPA Repositories
├── security/        # JWT и Security
└── service/         # Бизнес-логика
```

## Автор
Роберт Назарян
