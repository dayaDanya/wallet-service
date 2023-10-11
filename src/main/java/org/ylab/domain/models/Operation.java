package org.ylab.domain.models;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author dayaDanya
 * Этот класс представляет сущность - операция.
 * Операцией является действие игрока сохраняемое при аудите
 */

public class Operation {
    /**
     * Игрок
     */
    private Player player;
    /**
     * Действие игрока
     */

    private Action action;


    /**
     * Транзакция
     * Обернута в java.util.Optional, для безопасной работы с null
     */
    private Optional<Transaction> transaction;

    /**
     * Дата и время
     */
    private LocalDateTime date;

    /**
     * Конструктор для создания операции,
     * не являющейся транзакцией
     * @param player игрок
     * @param action действие
     * @param date дата и время
     */
    public Operation(Player player, Action action, LocalDateTime date) {
        this.player = player;
        this.action = action;
        this.transaction = Optional.empty();
        this.date = date;
    }

    /**
     * Конструктор для создания операции,
     * являющейся транзакцией: дебит или кредит
     * @param player игрок
     * @param action действие
     * @param transaction транзакция
     * @param date дата и время
     */
    public Operation(Player player, Action action, Optional<Transaction> transaction, LocalDateTime date) {
        this.player = player;
        this.action = action;
        this.transaction = transaction;
        this.date = date;
    }


    /**
     * Перегруженный метод toString()
     * @return строка со всеми полями операции
     */
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("player=")
                .append(player.getUsername())
                .append(", action=")
                .append(action);
        if (transaction.isPresent()) {
            strBuilder.append(", transaction=")
                    .append(transaction.get());
        }
        strBuilder.append(", date=")
                .append(date)
                .append("\n");
        return strBuilder.toString();
    }
}
