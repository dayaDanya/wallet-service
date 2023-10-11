package org.ylab.domain.models;

/**
 * @author dayaDanya
 * класс описывающий сущность транзакции
 */
public class Transaction {
    /**
     * уникальный идентификатор транзакции
     */
    private long id;

    /**
     * тип транзакции
     */
    private TransactionType transactionType;
    /**
     * сумма транзакции
     */
    private long amount;

    /**
     * Конструктор для создания транзакции
     * @param id идентификатор
     * @param transactionType тип транзакции
     * @param amount сумма транзакции
     */
    public Transaction(long id, TransactionType transactionType, long amount) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    /**
     *
     * @return идентификатор
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return тип транзакции
     */
    public TransactionType getTransactionType() {
        return transactionType;
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
