package org.ylab.application;

import org.ylab.domain.models.Operation;
import org.ylab.domain.models.dto.OperationDTO;
import org.ylab.domain.repos.HistoryService;
import org.ylab.domain.repos.OperationRepo;
import org.ylab.domain.repos.PlayerRepo;
import org.ylab.domain.repos.TransactionRepo;

import java.util.List;

/**
 * @author dayaDanya
 * Класс-сервис для сохранения и просмотра операций(аудита)
 * Реализует интерфейс HistoryService
 */
public class OperationService implements HistoryService {

    private final OperationRepo operationRepo;

    private final PlayerRepo playerRepo;

    private final TransactionRepo transactionRepo;


    /**
     * конструктор инициализирующий список операций
     */
    public OperationService() {
        operationRepo = new OperationRepo();
        transactionRepo = new TransactionRepo();
        playerRepo = new PlayerRepo();
    }

    public OperationService(OperationRepo operationRepo) {
        this.operationRepo = operationRepo;
        transactionRepo = new TransactionRepo();
        playerRepo = new PlayerRepo();
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
    @Override
    public String checkHistory() {
        List<OperationDTO> operations = operationRepo.findAllOperations();
        long startTime = System.currentTimeMillis();
        if (!operations.isEmpty()) {
            StringBuilder history = new StringBuilder();
            for (OperationDTO operation : operations) {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append("player=")
                        .append(operation.getPlayerName())
                        .append(", action=")
                        .append(operation.getAction());
                if (operation.getTransType().isPresent()) {
                    strBuilder.append(", transaction=")
                            .append(operation.getTransType());
                }


//                if (!operation.getTransUID().isEmpty()) {
//                    strBuilder.append(", transaction=")
//                            .append(transactionRepo.findByUniqueId(
//                                    operation.getTransUID()
//                            ));
//                }
                strBuilder.append(", date=")
                        .append(operation.getDate())
                        .append("\n");

                history.append(strBuilder).append("\n");
            }
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("Время выполнения: " + executionTime + " миллисекунд");
            return history.toString();
        }
        return "No operations yet";
    }
}
