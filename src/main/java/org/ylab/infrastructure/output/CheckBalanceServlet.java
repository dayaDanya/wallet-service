package org.ylab.infrastructure.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ylab.application.OperationService;
import org.ylab.domain.models.dto.OperationDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckBalanceServlet extends HttpServlet {
    private final ObjectMapper objectMapper;

    private final OperationService operationService;

    public CheckBalanceServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.operationService = new OperationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        List<OperationDto> operations = operationService.checkHistory();
        for (OperationDto dto : operations) {
            resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(
                    dto));
        }

    }
}
