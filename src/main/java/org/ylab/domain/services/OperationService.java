package org.ylab.domain.services;

import org.ylab.domain.models.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dayaDanya
 * Класс-сервис для сохранения и просмотра операций(аудита)
 * Реализует интерфейс HistoryService
 */
public class OperationService implements HistoryService{

    /**
     * список операций
     */
    private List<Operation> operations;

    /**
     * конструктор инициализирующий список операций
     */
    public OperationService() {
        this.operations = new ArrayList<>();
    }

    /**
     * Метод добавляющий операцию в начало списка
     * @param operation операция
     */
    public void saveOperation(Operation operation){
        operations.add(0, operation);
    }

    /**
     * Реализация метода checkHistory,
     * Преобразует список operations в String
     * @return строка список операций или сообщение об отсутствии операций
     */
    @Override
    public String checkHistory() {
        if(!operations.isEmpty()) {
            StringBuilder history = new StringBuilder();
            for (Operation operation : operations) {
                history.append(operation.toString()).append("\n");
            }

            return history.toString();
        }
        return "No operations yet";
    }
}
