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
       // List<OperationDto> operations =
        return operationRepo.findAllOperations();
//        long startTime = System.currentTimeMillis();
//        if (!operations.isEmpty()) {
//            StringBuilder history = new StringBuilder();
//            for (OperationDto operation : operations) {
//                StringBuilder strBuilder = new StringBuilder();
//                strBuilder.append("player=")
//                        .append(operation.getPlayerName())
//                        .append(", action=")
//                        .append(operation.getAction());
//                if (operation.getTransType().isPresent()) {
//                    strBuilder.append(", transaction=")
//                            .append(operation.getTransType());
//                }
//
//
////                if (!operation.getTransUID().isEmpty()) {
////                    strBuilder.append(", transaction=")
////                            .append(transactionRepo.findByUniqueId(
////                                    operation.getTransUID()
////                            ));
////                }
//                strBuilder.append(", date=")
//                        .append(operation.getDate())
//                        .append("\n");
//
//                history.append(strBuilder).append("\n");
//            }
//            long endTime = System.currentTimeMillis();
//            long executionTime = endTime - startTime;
//            System.out.println("Время выполнения: " + executionTime + " миллисекунд");
//            return history.toString();
//        }
//        return "No operations yet";
    }
}
