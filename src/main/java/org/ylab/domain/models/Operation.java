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
     * идентификатор
     */
    private long id;
    /**
     * Игрок
     */
    private long playerId;
    /**
     * Действие игрока
     */

    private Action action;


    /**
     * Транзакция
     * Обернута в java.util.Optional, для безопасной работы с null
     */
    private long transaction;

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
    public Operation(long player, Action action, LocalDateTime date) {
        this.playerId = player;
        this.action = action;
        this.transaction = 0;
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
    public Operation(long player, Action action, long transaction, LocalDateTime date) {
        this.playerId = player;
        this.action = action;
        this.transaction = transaction;
        this.date = date;
    }

    /**
     * Конструктор для создания операции при запросе из бд,
     * являющейся транзакцией: дебит или кредит
     * @param player игрок
     * @param action действие
     * @param transaction транзакция
     * @param date дата и время
     */
    public Operation(long id, long player, Action action, long transaction, LocalDateTime date) {
        this.id = id;
        this.playerId = player;
        this.action = action;
        this.transaction = transaction;
        this.date = date;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public long getTransaction() {
        return transaction;
    }

    public void setTransaction(long transaction) {
        this.transaction = transaction;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
