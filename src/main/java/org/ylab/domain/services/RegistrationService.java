package org.ylab.domain.services;

import org.ylab.domain.models.Player;

/**
 * @author dayaDanya
 * Класс отвечающий за регистрацию игрока
 */
public class RegistrationService {
    /**
     *
     * @param username пользовательское имя
     * @param password пароль
     * @return новый объект Player с заданными параметрами
     */

    public Player register(String username, String password){
        return new Player(username, password);
    }

}
