package org.ylab.domain.repos;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ylab.application.OperationService;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OperationServiceTest {

    OperationService operationService;

    @BeforeEach
    void setUp(){
        operationService = new OperationService();
    }


    @Test
    void checkHistory() {
        assertEquals("No operations yet",
                operationService.checkHistory());
    }
}