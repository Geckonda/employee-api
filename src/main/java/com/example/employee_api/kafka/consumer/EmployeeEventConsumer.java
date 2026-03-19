package com.example.employee_api.kafka.consumer;

import com.example.employee_api.kafka.event.EmployeeCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * EmployeeEventConsumer слушает события о сотрудниках из Kafka
 * Когда приходит новое событие, выполняет какие-то действия (логирование, уведомления и т.д.)
 */
@Slf4j
@Service
public class EmployeeEventConsumer {

    /**
     * Слушаем топик employee-events в группе employee-group
     * Каждое сообщение будет автоматически десериализовано в EmployeeCreatedEvent
     * 
     * @param event событие о создании сотрудника
     */
    @KafkaListener(
            topics = "employee-events",
            groupId = "employee-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleEmployeeCreatedEvent(EmployeeCreatedEvent event) {
        log.info("📨 Получено событие employee.created:");
        log.info("   → Сотрудник: {} {}", event.getFirstName(), event.getLastName());
        log.info("   → Email: {}", event.getEmail());
        log.info("   → Должность: {}", event.getPosition());
        log.info("   → ID отделения: {}", event.getDepartmentId());
        log.info("   → Создано: {}", event.getCreatedAt());

        // Здесь можно выполнять различные действия:
        // 1. Отправить email уведомление
        // 2. Записать событие в аналитику
        // 3. Обновить кэш
        // 4. Отправить пуш-уведомление
        // и т.д.

        log.info("✅ Событие обработано успешно!");
    }
}
