package org.ylab.application;

import org.ylab.domain.models.Action;
import org.ylab.domain.models.Operation;
import org.ylab.domain.models.Player;
import org.ylab.domain.models.Transaction;
import org.ylab.domain.services.BalanceRepository;
import org.ylab.domain.services.OperationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author dayaDanya
 * Сервис для работы с балансом
 */
public class BalanceService {
    /**
     * репозиторий для работы с балансом
     */
    private BalanceRepository balanceRepository;

    /**
     * список всех транзакций в приложении
     */
    protected List<Transaction> transactions;

    /**
     * сервис для аудита
     */
    private OperationService operationService;

    /**
     * конструктор инициализирующий поля класса
     */
    public BalanceService() {
        balanceRepository = new BalanceRepository(null);
        transactions = new ArrayList<>();
        operationService = new OperationService();
    }

    /**
     * @return возвращает текущее состояние баланса
     */
    public long checkBalance() {
        operationService.saveOperation(
                new Operation(
                        balanceRepository.getPlayer(),
                        Action.CHECK_BALANCE,
                        LocalDateTime.now()
                )
        );
        return balanceRepository.checkBalance();
    }

    /**
     * Метод инициализирующий balanceRepository текущим игроком
     * и фиксирующий авторизацию в список операций
     *
     * @param player текущий игрок
     * @return сообщение об успешной авторизации
     * если полученный объект не null
     * или не успешной авторизации в обратном случае
     */
    public String login(Optional<Player> player) {
        if (player.isPresent()) {
            balanceRepository.setPlayer(player.get());

            operationService.saveOperation(new Operation(
                    balanceRepository.getPlayer(),
                    Action.AUTHORIZATION,
                    LocalDateTime.now()));
            return "Success authorization";
        }
        return "Bad credentials, try again";
    }

    /**
     * Метод возвращающий пустой Optional
     * Так же записывает в balanceRepository null
     * и фиксирует данную операцию
     *
     * @return пустой Optional
     */
    public Optional<Player> logout() {
        operationService.saveOperation(new Operation(
                balanceRepository.getPlayer(),
                Action.LOGOUT,
                LocalDateTime.now()));
        balanceRepository.setPlayer(null);
        return Optional.empty();

    }

    /**
     * Метод проверки уникальности транзакции по id
     *
     * @param transaction транзакция
     * @return true если транзакции с таким id нет в списке transactions,
     * false в обратном случае
     */
    protected boolean isUnique(Transaction transaction) {
        return !transactions.stream()
                .map(Transaction::getId)
                .toList()
                .contains(transaction.getId());
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
            if (transaction.getAmount() < 0)
                return "Withdraw amount must be greater than zero";
            if (balanceRepository.checkBalance() - transaction.getAmount() >= 0) {
                balanceRepository.debit(transaction.getAmount());
                balanceRepository.getHistory().add(transaction);
                transactions.add(transaction);
                operationService.saveOperation(
                        new Operation(
                                balanceRepository.getPlayer(),
                                Action.TRANSACTION_SUCCESS,
                                Optional.of(transaction),
                                LocalDateTime.now()
                        )
                );
                return "Successful! Current amount of funds: " + balanceRepository.checkBalance();
            }
            operationService.saveOperation(
                    new Operation(
                            balanceRepository.getPlayer(),
                            Action.TRANSACTION_FAIL,
                            Optional.of(transaction),
                            LocalDateTime.now()
                    )
            );
            return "Insufficient funds";
        } else {
            operationService.saveOperation(
                    new Operation(
                            balanceRepository.getPlayer(),
                            Action.TRANSACTION_FAIL,
                            Optional.of(transaction),
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
            if (transaction.getAmount() < 0)
                return "The amount must be greater than zero";
            balanceRepository.credit(transaction.getAmount());
            balanceRepository.getHistory().add(transaction);
            transactions.add(transaction);
            operationService.saveOperation(
                    new Operation(
                            balanceRepository.getPlayer(),
                            Action.TRANSACTION_SUCCESS,
                            Optional.of(transaction),
                            LocalDateTime.now()
                    )
            );
            return "Successful! Current amount of funds: " + balanceRepository.checkBalance();
        } else {
            operationService.saveOperation(
                    new Operation(
                            balanceRepository.getPlayer(),
                            Action.TRANSACTION_FAIL,
                            Optional.of(transaction),
                            LocalDateTime.now()
                    )
            );
            return "Transaction id is not unique";
        }
    }

    /**
     * Метод отображения истории
     * @return Возвращает историю транзакций текущего игрока в виде строки
     * Если текущий пользователь - admin, возвращает аудит действий всех игроков
     */
    public String checkHistory() {
        if (balanceRepository.getPlayer().getUsername().equals("admin")) {
            operationService.saveOperation(
                    new Operation(
                            balanceRepository.getPlayer(),
                            Action.CHECK_HISTORY,
                            LocalDateTime.now()
                    )
            );
            return operationService.checkHistory();
        }
        StringBuilder history = new StringBuilder();
        for (Transaction transaction : balanceRepository.getHistory()) {
            history.append(transaction.toString()).append("\n");
        }
        operationService.saveOperation(
                new Operation(
                        balanceRepository.getPlayer(),
                        Action.CHECK_HISTORY,
                        LocalDateTime.now()
                )
        );
        return history.toString();

    }
}
