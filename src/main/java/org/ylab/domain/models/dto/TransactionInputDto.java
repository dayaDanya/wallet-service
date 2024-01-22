package org.ylab.domain.models.dto;

public class TransactionInputDto {
    /**
     * ID игрока совершившего транзакцию
     */
    private long playerId;

    /**
     * сумма транзакции
     */
    private long amount;

    public TransactionInputDto(long playerId, long amount) {
        this.playerId = playerId;
        this.amount = amount;
    }



    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
