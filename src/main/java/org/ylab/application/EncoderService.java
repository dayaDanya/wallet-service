package org.ylab.application;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Класс-сервис шифрования паролей
 * использует алгоритм BCrypt
 */
public class EncoderService {

    /**
     * Метод шифрования
     * @param password незашифрованный пароль
     * @return зашифрованный пароль
     */
    public String encode(String password){
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    /**
     * Метод сверки зашифрованного и незашифрованного пароля
     * для авторизации
     * @param plainTextPassword незашифрованный пароль
     * @param hashedPassword зашифрованный пароль
     * @return true если совпадают, false если нет
     */

    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }


}
