package org.ylab.application;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.ylab.domain.repos.PlayerRepo;
import org.ylab.infrastructure.input.MigrationConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
class PlayerServiceTest {

    @Container
    private static PostgreSQLContainer postgres =
            new PostgreSQLContainer<>("postgres:13.3");
    static PlayerService playerService;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        MigrationConfig migrationConfig = new MigrationConfig(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        migrationConfig.migrate();
        playerService =  new PlayerService(new PlayerRepo(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        ));
    }



    @org.junit.jupiter.api.Test
    void registerPlayer() {

        assertEquals("Username is empty",
                playerService.registerPlayer("", "pas"));
        assertEquals("Password length must be at least 8 symbols",
                playerService.registerPlayer("user", ""));
        assertEquals("Password length must be at least 8 symbols",
                playerService.registerPlayer("user", "fdfd"));
        assertEquals("Player with this username already exists",
                playerService.registerPlayer("user", "password"));
        assertEquals("Successful registration",
                playerService.registerPlayer("user", "12345678"));

    }


    @org.junit.jupiter.api.Test
    void authorizePlayer() {


        assertNotNull(playerService.authorizePlayer("user", "password"));
        assertEquals(
                Optional.empty(),
                playerService.authorizePlayer("user", "p"));
        assertEquals(Optional.empty(),
                playerService.authorizePlayer("", ""));
        assertEquals(Optional.empty(),
                playerService.authorizePlayer("1", "1"));

    }
}