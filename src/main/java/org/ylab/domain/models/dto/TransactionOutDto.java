package org.ylab.domain.models.dto;

import org.ylab.domain.models.TransactionType;

public class TransactionOutDto {
    private String playerName;
    private long amount;
    private TransactionType transactionType;

    public TransactionOutDto(String playerName, long amount, TransactionType transactionType) {
        this.playerName = playerName;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
