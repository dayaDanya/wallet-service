package org.ylab.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ylab.domain.models.Player;
import org.ylab.domain.models.Transaction;
import org.ylab.domain.models.TransactionType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BalanceServiceTest {

    BalanceService balanceService;

    @BeforeEach
    void setUp() {
        balanceService = new BalanceService();
        balanceService.login(Optional.of(
                new Player("user", "password")
        ));

        balanceService.credit(
                new Transaction(
                        1,
                        TransactionType.DEBIT,
                        1000
                )
        );

        balanceService.debit(
                new Transaction(
                        2,
                        TransactionType.DEBIT,
                        100
                )
        );
    }


    @Test
    void login() {
        assertEquals("Success authorization",
                balanceService.login(Optional.of(new Player("user", "password"))));
        assertEquals("Bad credentials, try again",
                balanceService.login(Optional.empty()));
    }

    @Test
    void logout() {
        assertEquals(Optional.empty(), balanceService.logout());
    }

    @Test
    void debit() {
        assertEquals("Insufficient funds",
                balanceService.debit(
                        new Transaction(
                                3,
                                TransactionType.DEBIT,
                                10000)
                ));
        assertEquals("Withdraw amount must be greater than zero",
                balanceService.debit(
                        new Transaction(
                                4,
                                TransactionType.DEBIT,
                                -10000)
                )
                );
    }

    @Test
    void credit() {
        assertEquals("Transaction id is not unique",
                balanceService.credit(
                        new Transaction(
                                1,
                                TransactionType.CREDIT,
                                102)
                ));
        assertEquals("The amount must be greater than zero",
                balanceService.credit(
                        new Transaction(
                                3,
                                TransactionType.CREDIT,
                                -100)
                ));

    }

    @Test
    void isUnique() {
        assertFalse(balanceService.isUnique(new Transaction(1, TransactionType.DEBIT, 100)));

    }


}