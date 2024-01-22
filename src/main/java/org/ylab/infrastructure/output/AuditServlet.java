package org.ylab.infrastructure.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ylab.application.OperationService;
import org.ylab.domain.models.dto.OperationDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/operations")
public class AuditServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    private final OperationService operationService;

    public AuditServlet() {
        this.objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // Hack time module to allow 'Z' at the end of string (i.e. javascript json's)
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        this.objectMapper.registerModule(javaTimeModule);
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.operationService = new OperationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
//        List<OperationDto> operations = operationService.checkHistory();
//        for (OperationDto dto : operations) {
//            resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(
//                    dto));
//        }
        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(
                "dto"));

    }
}
