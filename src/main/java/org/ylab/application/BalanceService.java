package org.ylab.application;

import org.ylab.domain.models.*;
import org.ylab.domain.repos.PlayerRepo;
import org.ylab.domain.repos.TransactionRepo;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author dayaDanya
 * Сервис для работы с балансом
 */
public class BalanceService {

    private final PlayerRepo playerRepo;


    /**
     * репозиторий бд транзакций
     */
    private final TransactionRepo transactionRepo;


    /**
     * сервис для аудита
     */
    private final OperationService operationService;

    /**
     * конструктор инициализирующий поля класса
     */
    public BalanceService() {
        operationService = new OperationService();
        transactionRepo = new TransactionRepo();
        playerRepo = new PlayerRepo();
    }
    /**
     * конструктор инициализирующий поля класса
     */
    public BalanceService(PlayerRepo playerRepo, TransactionRepo transactionRepo, OperationService operationService) {
        this.playerRepo = playerRepo;
        this.transactionRepo = transactionRepo;
        this.operationService = operationService;
    }



    /**
     * @return возвращает текущее состояние баланса
     */
    public long checkBalance(Player player) {
        operationService.saveOperation(
                new Operation(
                        player.getId(),
                        Action.CHECK_BALANCE,
                        LocalDateTime.now()
                )
        );
        return playerRepo.findBalanceById(player.getId());
    }


    /**
     * Метод проверки уникальности транзакции по id
     *
     * @param transaction транзакция
     * @return true если транзакции с таким id нет в списке transactions,
     * false в обратном случае
     */
    protected boolean isUnique(Transaction transaction) {
        return transactionRepo.findByUniqueId(transaction.getUniqueId()).isEmpty();
    }

    /**
     * Метод дебетовой транзакции - <b>снятие средств со счета</b>
     * Проверяет транзакцию на уникальность,
     * сумму транзакции на неотрицательность.
     * Сохраняет транзакцию в общий список транзакций,
     * и список транзакций игрока, фиксирует операцию.
     *
     * @param transaction транзакция
     * @return сообщение о статусе транзакции и состояние счета игрока
     */
    public String debit(Transaction transaction) {
        if (isUnique(transaction)) {
            transaction.setTransactionType(TransactionType.DEBIT);
            if (transaction.getAmount() < 0)
                return "Withdraw amount must be greater than zero";
            long curBalance = playerRepo.findBalanceById(transaction.getPlayerId());
            if (curBalance - transaction.getAmount() >= 0) {
                curBalance = curBalance - transaction.getAmount();
                playerRepo.updatePlayerBalance(transaction.getPlayerId(), curBalance);
                transactionRepo.save(transaction);

                operationService.saveOperation(
                        new Operation(
                                transaction.getPlayerId(),
                                Action.TRANSACTION_SUCCESS,
                                transaction.getUniqueId(),
                                LocalDateTime.now()
                        )
                );
                return "Successful! Current amount of funds: " + curBalance;
            }
            operationService.saveOperation(
                    new Operation(
                            transaction.getPlayerId(),
                            Action.TRANSACTION_FAIL,
                            transaction.getUniqueId(),
                            LocalDateTime.now()
                    )
            );
            return "Insufficient funds";
        } else {
            operationService.saveOperation(
                    new Operation(
                            transaction.getPlayerId(),
                            Action.TRANSACTION_FAIL,
                            transaction.getUniqueId(),
                            LocalDateTime.now()
                    )
            );
            return "Transaction id is not unique";
        }
    }

    /**
     * Метод транзакции кредит - <b>пополнение счета</b>
     * Проверяет транзакцию на уникальность,
     * сумму транзакции на неотрицательность.
     * Сохраняет транзакцию в общий список транзакций,
     * и список транзакций игрока, фиксирует операцию.
     *
     * @param transaction транзакция
     * @return возвращает сообщение о статусе транзакции и состояние счета игрока
     */
    public String credit(Transaction transaction) {
        if (isUnique(transaction)) {
            transaction.setTransactionType(TransactionType.CREDIT);
            if (transaction.getAmount() < 0)
                return "The amount must be greater than zero";
            long balance = playerRepo.findBalanceById(transaction.getPlayerId())
                    + transaction.getAmount();
            playerRepo.updatePlayerBalance(transaction.getPlayerId(), balance);
            transactionRepo.save(transaction);
            operationService.saveOperation(
                    new Operation(
                            transaction.getPlayerId(),
                            Action.TRANSACTION_SUCCESS,
                            transaction.getUniqueId(),
                            LocalDateTime.now()
                    )
            );
            return "Successful! Current amount of funds: " + balance;
        } else {
            operationService.saveOperation(
                    new Operation(
                            transaction.getPlayerId(),
                            Action.TRANSACTION_FAIL,
                            transaction.getUniqueId(),
                            LocalDateTime.now()
                    )
            );
            return "Transaction id is not unique";
        }
    }

    /**
     * Метод отображения истории
     *
     * @return Возвращает историю транзакций текущего игрока в виде строки
     * Если текущий пользователь - admin, возвращает аудит действий всех игроков
     */
    public String checkHistory(Player player) {
        if (player.getUsername().equals("admin")) {
            operationService.saveOperation(
                    new Operation(
                            player.getId(),
                            Action.CHECK_HISTORY,
                            LocalDateTime.now()
                    )
            );
            return "";//operationService.checkHistory();
        }
        StringBuilder history = new StringBuilder();
        for (Transaction transaction : transactionRepo.findByPlayerId(player.getId())) {
            history.append(transaction.toString()).append("\n");
        }
        operationService.saveOperation(
                new Operation(
                        player.getId(),
                        Action.CHECK_HISTORY,
                        LocalDateTime.now()
                )
        );
        return history.toString();

    }
}
