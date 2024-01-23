package org.ylab.domain.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionInputDto {
    /**
     * ID игрока совершившего транзакцию
     */
    @JsonProperty("playerId")
    private long playerId;

    /**
     * сумма транзакции
     */
    @JsonProperty("amount")
    private long amount;

    public TransactionInputDto() {
    }

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
