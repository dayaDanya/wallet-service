package org.ylab.domain.models;

/**
 * @author dayaDanya
 * Это перечисление представляет возможные действия игрока,
 * которые будут сохранены при аудите
 */
public enum Action {
    REGISTRATION,
    AUTHORIZATION,
    TRANSACTION_SUCCESS,
    TRANSACTION_FAIL,
    CHECK_BALANCE,
    CHECK_HISTORY,
    LOGOUT
}
