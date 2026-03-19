package com.example.employee_api.kafka.producer;

import com.example.employee_api.kafka.event.EmployeeCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * EmployeeEventProducer отправляет события о сотрудниках в Kafka
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeEventProducer {

    private final KafkaTemplate<String, EmployeeCreatedEvent> kafkaTemplate;

    // Название топика (очереди) в Kafka
    private static final String EMPLOYEE_TOPIC = "employee-events";

    /**
     * Отправить событие о создании сотрудника
     * @param event событие с информацией о новом сотруднике
     */
    public void sendEmployeeCreatedEvent(EmployeeCreatedEvent event) {
        try {
            // Создаём сообщение с помощью MessageBuilder
            // TOPIC_KEY — это специальный header, который указывает Kafka в какой топик отправить
            Message<EmployeeCreatedEvent> message = MessageBuilder
                    .withPayload(event)
                    .setHeader(KafkaHeaders.TOPIC, EMPLOYEE_TOPIC)
                    // Используем ID сотрудника как ключ для распределения на партициях
                    .setHeader("kafka_messageKey", event.getEmployeeId().toString())
                    .build();

            // Отправляем сообщение асинхронно и логируем результат
            kafkaTemplate.send(message).whenComplete((result, exception) -> {
                if (exception == null) {
                    log.info("✅ Событие employee.created отправлено: employeeId={}, topic={}, partition={}",
                            event.getEmployeeId(),
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition());
                } else {
                    log.error("❌ Ошибка при отправке события employee.created: ", exception);
                }
            });

        } catch (Exception e) {
            log.error("❌ Непредвиденная ошибка при отправке события employee.created", e);
        }
    }
}
