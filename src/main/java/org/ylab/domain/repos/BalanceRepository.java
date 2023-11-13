package org.ylab.domain.repos;

import org.ylab.domain.models.Player;
import org.ylab.domain.models.Transaction;

import java.util.List;

/**
 * @author dayaDanya
 * Класс репозиторий для работы с денежным балансом,
 * работает с единственным игроком одновременно,
 * в дальнейшем можно заменить на @AuthenticationPrincipal в контроллере, к примеру
 */

public class BalanceRepository {

    public BalanceRepository() {
    }

    /**
     * кредитная операция
     * увеличивает баланс на сумму транзакции
     * @param amount сумма
     */
    public void credit(Player player, long amount){
        player.setBalance(player.getBalance() + amount);
    }

    /**
     * Дебетовая операция
     * уменьшает баланс на сумму
     * @param amount сумма
     */

    public void debit(Player player ,long amount){
        player.setBalance(player.getBalance() - amount);
    }

    /**
     *
     * @return long возвращает текущее состояние баланса
     */
    public long checkBalance() {
        return 0;
    }

    /**
     *
     * @return список транзакций игрока
     */
    public List<Transaction> getHistory(){
        return null ;//player.getPlayerTransactions();
    }
}
