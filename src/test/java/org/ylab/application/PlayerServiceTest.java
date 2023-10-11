package org.ylab.application;

import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class PlayerServiceTest {

    PlayerService playerService;

    @BeforeEach
    void setUp() {
      playerService =  new PlayerService();
    }

    @org.junit.jupiter.api.Test
    void registerPlayer() {

        assertEquals("Username is empty",
                playerService.registerPlayer("", "pas"));
        assertEquals("Password length must be at least 8 symbols",
                playerService.registerPlayer("user", ""));
        assertEquals("Password length must be at least 8 symbols",
                playerService.registerPlayer("user", "fdfd"));
        assertEquals("Successful registration",
                playerService.registerPlayer("user", "12345678"));

    }

    @BeforeEach
    void addPlayer(){
        playerService.registerPlayer("user", "password");
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