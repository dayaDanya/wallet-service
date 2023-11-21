package org.ylab.application;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.ylab.domain.models.Player;
import org.ylab.domain.models.Transaction;
import org.ylab.domain.models.TransactionType;
import org.ylab.domain.repos.OperationRepo;
import org.ylab.domain.repos.PlayerRepo;
import org.ylab.domain.repos.TransactionRepo;
import org.ylab.infrastructure.input.MigrationConfig;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
class BalanceServiceTest {

    BalanceService balanceService;
    Player testPlayer;

    @Container
    private static PostgreSQLContainer postgres =
            new PostgreSQLContainer<>("postgres:13.3");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        MigrationConfig migrationConfig = new MigrationConfig(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        migrationConfig.migrate();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        PlayerRepo playerRepo = new PlayerRepo(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());
        OperationRepo operationRepo = new OperationRepo(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());
        TransactionRepo transactionRepo = new TransactionRepo(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());
        balanceService = new BalanceService(playerRepo, transactionRepo, new OperationService(operationRepo));

        testPlayer = new Player(1, "user", "password", 0, LocalDateTime.now());
        balanceService.login(Optional.of(testPlayer));

        balanceService.credit(testPlayer,
                new Transaction(
                        1,
                        1,
                        testPlayer.getId(),
                        TransactionType.CREDIT,
                        1000
                )
        );

        balanceService.debit(testPlayer,
                new Transaction(
                        2,
                        2,
                        testPlayer.getId(),
                        TransactionType.DEBIT,
                        100
                )
        );
    }

    @Test
    void login() {
        assertEquals("Success authorization",
                balanceService.login(Optional.of(testPlayer)));
        assertEquals("Bad credentials, try again",
                balanceService.login(Optional.empty()));
    }

    @Test
    void logout() {
        assertEquals(Optional.empty(), balanceService.logout(testPlayer));
    }

    @Test
    void debit() {
        assertEquals("Insufficient funds",
                balanceService.debit(testPlayer,
                        new Transaction(3,
                                3,

                                testPlayer.getId(),
                                TransactionType.DEBIT,
                                10000)
                ));
        assertEquals("Withdraw amount must be greater than zero",
                balanceService.debit(testPlayer,
                        new Transaction(4,
                                4,
                                testPlayer.getId(),
                                TransactionType.DEBIT,
                                -10000)
                )
        );
    }

    @Test
    void credit() {
        assertEquals("Transaction id is not unique",
                balanceService.credit(testPlayer,
                        new Transaction(1,
                                1,
                                testPlayer.getId(),
                                TransactionType.CREDIT,
                                102)
                ));
        assertEquals("The amount must be greater than zero",
                balanceService.credit(testPlayer,
                        new Transaction(5,
                                5,
                                testPlayer.getId(),
                                TransactionType.CREDIT,
                                -100)
                ));

    }

    @Test
    void isUnique() {
        assertFalse(balanceService.isUnique(new Transaction(1, 1, testPlayer.getId(),
                TransactionType.DEBIT, 100)));

    }


}