package org.ylab.domain.models.dto;

import org.ylab.domain.models.Action;
import org.ylab.domain.models.TransactionType;

import java.time.LocalDateTime;
import java.util.Optional;

public class OperationDTO {
    private String playerName;
    private Action action;
    private Optional<TransactionType> transType;
    private LocalDateTime date;

    public OperationDTO(String playerName, Action action, Optional<TransactionType> transType, LocalDateTime date) {
        this.playerName = playerName;
        this.action = action;
        this.transType = transType;
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Action getAction() {
        return action;
    }

    public Optional<TransactionType> getTransType() {
        return transType;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
