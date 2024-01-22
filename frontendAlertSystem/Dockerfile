# Используем официальный образ Node.js с Alpine
FROM node:alpine

# Устанавливаем http-server глобально через npm
RUN npm install -g http-server

# Создаем директорию и копируем статические файлы вашего фронтенда
WORKDIR /frontendAlertApi
COPY . /frontendAlertApi

# Экспонируем порт 5500
EXPOSE 5500

# Команда для запуска http-server при старте контейнера
CMD ["http-server", "--port", "5500"]
