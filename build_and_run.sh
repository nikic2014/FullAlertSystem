#!/bin/bash

# Шаг 1: Выполнить mvn clean install
echo "Шаг 1: Выполняем mvn clean install"
mvn clean install

# Проверяем успешность выполнения команды mvn
if [ $? -eq 0 ]; then
  # Шаг 2: Выполнить docker-compose build
  echo "Шаг 2: Выполняем docker-compose build"
  docker-compose build

  # Проверяем успешность выполнения команды docker-compose build
  if [ $? -eq 0 ]; then
    # Шаг 3: Выполнить docker-compose up
    echo "Шаг 3: Выполняем docker-compose up"
    docker-compose up
  else
    echo "Ошибка: Не удалось выполнить docker-compose build"
  fi
else
  echo "Ошибка: Не удалось выполнить mvn clean install"
fi
