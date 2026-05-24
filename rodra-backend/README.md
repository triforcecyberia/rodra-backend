# РОДРА — Detailing Lab Backend

Spring Boot бэкенд для приёма заявок с сайта и отправки их в Telegram.

## Структура проекта

```
rodra-backend/
├── src/main/java/com/rodra/bot/
│   ├── RodraBotApplication.java       # Точка входа
│   ├── config/
│   │   └── CorsConfig.java            # CORS настройки
│   ├── controller/
│   │   └── LeadController.java        # REST API /api/lead
│   ├── dto/
│   │   └── LeadRequest.java           # DTO заявки
│   ├── service/
│   │   └── LeadService.java           # Бизнес-логика
│   └── telegram/
│       └── RodraBot.java              # Telegram бот
├── src/main/resources/
│   └── application.properties
├── .env.example                       # Пример переменных окружения
├── .gitignore                         # .env не попадает в git
└── pom.xml
```

## Настройка

### 1. Создай .env файл
```bash
cp .env.example .env
```

Заполни `.env`:
```
TELEGRAM_BOT_TOKEN=токен_от_BotFather
TELEGRAM_BOT_USERNAME=username_бота_без_@
TELEGRAM_CHAT_ID=chat_id_заказчика
CORS_ALLOWED_ORIGINS=https://triforcecyberia.github.io
```

### 2. Как получить TELEGRAM_BOT_TOKEN
- Открой Telegram → найди @BotFather
- Напиши `/newbot`
- Следуй инструкции — получишь токен вида `7123456789:AAF...`

### 3. Как получить TELEGRAM_CHAT_ID
- Напиши своему боту любое сообщение
- Открой в браузере:
  `https://api.telegram.org/bot<ТВОЙ_ТОКЕН>/getUpdates`
- Найди `"chat":{"id":XXXXXXX}` — это и есть chat_id

### 4. Запуск
```bash
# Загрузи переменные из .env и запусти
export $(cat .env | xargs) && mvn spring-boot:run
```

Или в IntelliJ IDEA:
- Установи плагин **EnvFile**
- В Run Configuration укажи `.env` файл

## API

### POST /api/lead
Принимает заявку с сайта и отправляет в Telegram.

**Request:**
```json
{
  "name": "Иван",
  "phone": "+7 999 123-45-67",
  "service": "Керамическое покрытие",
  "comment": "BMW X5 2022"
}
```

**Response:**
```json
{
  "status": "ok",
  "message": "Заявка успешно отправлена"
}
```

### GET /api/health
Проверка работоспособности сервера.

## Деплой бэкенда

### Вариант 1 — Railway (рекомендую, бесплатно)
1. Зайди на [railway.app](https://railway.app)
2. New Project → Deploy from GitHub repo
3. В настройках добавь переменные окружения из `.env`
4. Получишь URL вида `https://rodra-bot.up.railway.app`

### Вариант 2 — Render
1. [render.com](https://render.com) → New Web Service
2. Подключи GitHub репо
3. Build Command: `mvn clean package -DskipTests`
4. Start Command: `java -jar target/rodra-bot-1.0.0.jar`
5. Добавь переменные окружения

## После деплоя бэкенда

Обнови в `index.html` строку:
```javascript
const BACKEND_URL = 'https://твой-бэкенд.railway.app';
```

И залей обновлённый `index.html` в GitHub репозиторий.
