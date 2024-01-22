# Используйте официальный образ PostgreSQL
FROM postgres:15

# Скопируйте SQL-скрипт в директорию инициализации
COPY init.sql /docker-entrypoint-initdb.d/
