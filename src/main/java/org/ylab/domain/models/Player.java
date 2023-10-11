package org.ylab.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author dayaDanya
 * Класс описывающий игрока
 */
public class Player {

    /**
     * пользовательское имя
     */
    private String username;

    /**
     * пароль игрока
     */
    private String password;
    /**
     * денежный баланс
     */
    private long balance;

    /**
     * список транзакций игрока
     */
    private List<Transaction> playerTransactions;
    /**
     * дата и время создания пользователя
     */
    private LocalDateTime dateOfRegistration;

    /**
     * Конструктор для создания игрока
     * @param username пользовательское имя
     * @param password пароль
     */
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        playerTransactions = new ArrayList<>();
        dateOfRegistration = LocalDateTime.now();
    }

    /**
     *
     * @return пользовательское имя
     */
    public String getUsername() {
        return username;
    }

    /**
     * сеттер для изменения имени
     * @param username пользовательское имя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * сеттер для изменения пароля
     * @param password пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return баланс игрока
     */
    public long getBalance() {
        return balance;
    }

    /**
     * сеттер для изменения баланса
     * @param balance баланс
     */
    public void setBalance(long balance) {
        this.balance = balance;
    }

    /**
     *
     * @return список транзакций игрока
     */
    public List<Transaction> getPlayerTransactions() {
        return playerTransactions;
    }

    /**
     *
     * @return дата создания игрока
     */
    public LocalDateTime getDateOfRegistration() {
        return dateOfRegistration;
    }

    /**
     * метод equals() для сравнения игроков
     * @param o сравниваемый объект
     * @return true если объекты equals,
     * false если нет
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username) && password.equals(player.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
