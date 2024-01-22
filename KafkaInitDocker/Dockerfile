# Используйте официальный образ Kafka от Confluent
FROM confluentinc/cp-kafka:latest

# Установите рабочую директорию
WORKDIR /

# Запустите ZooKeeper (предварительный шаг для Kafka)
CMD zookeeper-server-start /etc/kafka/zookeeper.properties & \
    sleep 5 && \
    kafka-server-start /etc/kafka/server.properties
