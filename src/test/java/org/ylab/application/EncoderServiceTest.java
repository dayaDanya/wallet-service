package org.ylab.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncoderServiceTest {

    EncoderService encoderService;
    String pas;

    @BeforeEach
    void setUp(){
        encoderService = new EncoderService();
        pas = encoderService.encode("12345678");
    }

    @Test
    void checkPassword() {
        assertTrue(encoderService.checkPassword("12345678", pas));
    }
}