package org.ylab.application;

import org.ylab.domain.models.Player;
import org.ylab.domain.services.RegistrationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author dayaDanya
 * Класс сервис регистрации и авторизации
 */
public class PlayerService {
    /**
     * статическое поле domain сервис регистрации
     */
    private final RegistrationService registrationService;

    private final EncoderService encoderService;
    /**
     * список игроков
     */
    private List<Player> players;

    /**
     * конструктор
     */
    public PlayerService() {
        encoderService = new EncoderService();
        registrationService = new RegistrationService();
        players = new ArrayList<>();
    }

    /**
     * метод регистрации игрока
     * валидирует данные,
     * проверяет username на уникальность
     *
     * @param username пользовательское имя
     * @param password пароль
     * @return возвращает сообщений об (не)успешной регистрации
     */
    public String registerPlayer(String username, String password) {
        if (username.isEmpty())
            return "Username is empty";
        if (password.length() < 8)
            return "Password length must be at least 8 symbols";
        if (!players.stream().map(Player::getUsername)
                .toList()
                .contains(username)) {
            Player player = registrationService.register(username,
                    encoderService.encode(password));
            players.add(player);

            return "Successful registration";
        }
        return "Player with this username already exists";
    }

    /**
     * метод аутентификации игрока по учетным данным
     *
     * @param username пользовательское имя
     * @param password пароль
     * @return optional player
     */
    public Optional<Player> authorizePlayer(String username, String password) {

        Player wanted = new Player(username, password);

        return players.stream()
                .filter(player -> player.getUsername().equals(wanted.getUsername()) &&
                        encoderService.checkPassword(wanted.getPassword(), player.getPassword()))
                .findFirst();
    }
}
