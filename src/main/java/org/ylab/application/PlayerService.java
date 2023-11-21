package org.ylab.application;

import org.ylab.domain.models.Player;
import org.ylab.domain.repos.PlayerRepo;

import java.util.Optional;

/**
 * @author dayaDanya
 * Класс сервис регистрации и авторизации
 */
public class PlayerService {
    /**
     * репо
     */
    private final PlayerRepo playerRepo;
    /**
     * шифрование
     */
    private final EncoderService encoderService;
    /**
     * список игроков
     */

    /**
     * конструктор
     */
    public PlayerService() {
        encoderService = new EncoderService();
        playerRepo = new PlayerRepo();
    }

    public PlayerService(PlayerRepo playerRepo) {
        encoderService = new EncoderService();
        this.playerRepo = playerRepo;
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
        if (playerRepo.findByUsername(username).isEmpty()) {
            playerRepo.save(username,
                    encoderService.encode(password));

            return "Successful registration";
        }
        return "Player with this username already exists";
    }

    /**
     * метод аутентификации игрока по учетным данным
     *
     * @param username пользовательское имя
     * @return optional player
     */
    public Optional<Player> authorizePlayer(String username, String password) {
        Optional<Player> wanted = playerRepo.findByUsername(username);
        if (wanted.isPresent())
            if(encoderService.checkPassword(password, wanted.get().getPassword())) {
                System.out.println(wanted.get().getId());
                return wanted;
            }
        return Optional.empty();
    }
}
