package org.ylab.domain.services;

import org.ylab.domain.models.Player;
import org.ylab.domain.models.Transaction;

import java.util.List;

/**
 * @author dayaDanya
 * Класс репозиторий для работы с денежным балансом,
 * работает с единственным игроком одновременно
 */

public class BalanceRepository {
    /**
     * Текущий игрок
     */

    private Player player;

    /**
     *
     * @param player текущий игрок
     */
    public BalanceRepository(Player player) {
        this.player = player;
    }

    /**
     * сеттер
     * @param player игрок
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * геттер
     * @return игрок
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * кредитная операция
     * увеличивает баланс на сумму транзакции
     * @param amount сумма
     */
    public void credit(long amount){
        player.setBalance(player.getBalance() + amount);
    }

    /**
     * Дебетовая операция
     * уменьшает баланс на сумму
     * @param amount сумма
     */

    public void debit(long amount){
        player.setBalance(player.getBalance() - amount);
    }

    /**
     *
     * @return long возвращает текущее состояние баланса
     */
    public long checkBalance() {
        return player.getBalance();
    }

    /**
     *
     * @return список транзакций игрока
     */
    public List<Transaction> getHistory(){
        return player.getPlayerTransactions();
    }
}
