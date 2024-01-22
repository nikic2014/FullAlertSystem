package com.AlertSystem.AlertAPI.alerUtil;

import com.AlertSystem.AlertAPI.dto.TelegramAlertDTO;
import com.AlertSystem.AlertAPI.models.Project;
import com.AlertSystem.AlertAPI.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

@Component
public class TelegramAlert {

    private final ProjectService projectService;

    public TelegramAlert(ProjectService projectService) {
        this.projectService = projectService;
    }

    public Boolean sendMessage(int idProject, String Message) throws Exception {
        Project project = projectService.findByIdProject(idProject);
        String chatId = project.getTelegramLink();

        if (chatId == null)
            return Boolean.FALSE;

        this.sendKafkaMessage(chatId, Message);
        return Boolean.TRUE;
    }

    private void sendKafkaMessage(String chatId, String Message) throws JsonProcessingException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "kafka:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Создание экземпляра Kafka продюсера
        Producer<String, String> producer = new KafkaProducer<>(properties);

        TelegramAlertDTO alertDTO = new TelegramAlertDTO(chatId, Message);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(alertDTO);

        // Отправка сообщения в тему
        String topic = "telegram-alert";
        String key = "key";
        String value = jsonValue;

        producer.send(new ProducerRecord<>(topic, key, value));

        // Закрытие Kafka продюсера
        producer.close();
    }
}
