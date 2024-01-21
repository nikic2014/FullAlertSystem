# Используем официальный образ OpenJDK
FROM openjdk:17-oracle

# Устанавливаем рабочую директорию
WORKDIR /AlertApi

# Копируем JAR-файл из сборки проекта в контейнер
COPY target/AlertAPI-0.0.1-SNAPSHOT.jar /AlertApi

# Экспонируем порт, на котором работает ваше приложение
EXPOSE 8181

# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "AlertAPI-0.0.1-SNAPSHOT.jar"]
# command: ["./wait-for-it.sh", "postgres:5432", "--", "java", "-jar", "your-application.jar"]