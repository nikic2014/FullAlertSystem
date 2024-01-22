# Используем базовый образ Python
FROM python:3.9

ARG REPO=AlertBotRepository
ARG TAG=test1

# Устанавливаем зависимости
RUN pip install aiogram==2.14 aiokafka requests

# Копируем исходный код в образ
COPY . /bot
WORKDIR /bot

# Запускаем бот при старте контейнера
CMD ["python", "bot.py"]
