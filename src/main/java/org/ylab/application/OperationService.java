package org.ylab.application;

import org.ylab.domain.models.Operation;
import org.ylab.domain.models.dto.OperationDto;
import org.ylab.domain.repos.OperationRepo;

import java.util.List;

/**
 * @author dayaDanya
 * Класс-сервис для сохранения и просмотра операций(аудита)
 * Реализует интерфейс HistoryService
 */
public class OperationService  {

    private final OperationRepo operationRepo;



    /**
     * конструктор инициализирующий список операций
     */
    public OperationService() {
        operationRepo = new OperationRepo();
    }

    public OperationService(OperationRepo operationRepo) {
        this.operationRepo = operationRepo;
    }

    /**
     * Метод добавляющий операцию в начало списка
     *
     * @param operation операция
     */
    public void saveOperation(Operation operation) {
        operationRepo.save(operation);
    }

    /**
     * Реализация метода checkHistory,
     * Преобразует список operations в String
     *
     * @return строка список операций или сообщение об отсутствии операций
     */

    public List<OperationDto> checkHistory() {
        return operationRepo.findAllOperations();

    }
}
