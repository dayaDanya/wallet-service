package org.ylab.application;

import org.ylab.domain.models.Action;
import org.ylab.domain.models.Operation;
import org.ylab.domain.models.Player;
import org.ylab.domain.repos.PlayerRepo;

import java.time.LocalDateTime;
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
     * сервис для аудита
     */
    private final OperationService operationService;

    /**
     * конструктор
     */
    public PlayerService() {
        encoderService = new EncoderService();
        playerRepo = new PlayerRepo();
        operationService = new OperationService();
    }

    public PlayerService(PlayerRepo playerRepo, OperationService operationService) {
        encoderService = new EncoderService();
        this.playerRepo = playerRepo;
        this.operationService = operationService;
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
    /**
     * Метод инициализирующий balanceRepository текущим игроком
     * и фиксирующий авторизацию в список операций
     *
     * @param player текущий игрок
     * @return сообщение об успешной авторизации
     * если полученный объект не null
     * или не успешной авторизации в обратном случае
     */
    public String login(Optional<Player> player) {
        if (player.isPresent()) {

            operationService.saveOperation(new Operation(
                    player.get().getId(),
                    Action.AUTHORIZATION,
                    LocalDateTime.now()));
            return "Success authorization";
        }
        return "Bad credentials, try again";
    }
    /**
     * Метод возвращающий пустой Optional
     * <p>
     * и фиксирующий данную операцию
     *
     * @return пустой Optional
     */
    public Optional<Player> logout(Player player) {
        player = playerRepo.findByUsername(player.getUsername()).get();
        operationService.saveOperation(new Operation(
                player.getId(),
                Action.LOGOUT,
                LocalDateTime.now()));
        return Optional.empty();

    }
}
