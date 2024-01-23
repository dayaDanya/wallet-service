package org.ylab.domain.models;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import org.ylab.aop.annotations.Default;

/**
 * @author dayaDanya
 * класс описывающий сущность транзакции
 */
public class Transaction {
    /**
     * ID БД
     */
    private long id;
    /**
     * уникальный идентификатор транзакции
     */
    private String uniqueId;
    /**
     * ID игрока совершившего транзакцию
     */
    private long playerId;

    /**
     * тип транзакции
     */
    private TransactionType transactionType;
    /**
     * сумма транзакции
     */
    private long amount;

    /**
     * Конструктор для загрузки транзакции из БД
     * @param id идентификатор
     * @param transactionType тип транзакции
     * @param amount сумма транзакции
     */
    public Transaction(long id, String uniqueId, long playerId,
                       TransactionType transactionType, long amount) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.uniqueId = uniqueId;
        this.playerId = playerId;
    }

    /**
     * Конструктор для создания транзакции
     * @param transactionType тип транзакции
     * @param amount сумма транзакции
     */
    @Default
    public Transaction(String uniqueId, long playerId,
                       TransactionType transactionType, long amount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.uniqueId = uniqueId;
        this.playerId = playerId;
    }

    /**
     *
     * @return идентификатор
     */
    public long getId() {
        return id;
    }

    /**
     * @return тип транзакции
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }
    /**
     *  тип транзакции
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     *
     * @return сумма транзакции
     */
    public long getAmount(){
        return amount;
    }

    /**
     *
     * @return id игрока
     */
    public long getPlayerId() {
        return playerId;
    }

    public String getUniqueId(){
        return uniqueId;
    }
    /**
     *
     * uid транзакции
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     *
     * @return строка с параметрами транзакции
     */
    @Override
    public String toString() {
        return
                "id=" + id +
                ", transactionType=" + transactionType +
                ", amount=" + amount;
    }
}
