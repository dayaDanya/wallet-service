package org.ylab.infrastructure.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;
import org.ylab.application.BalanceService;
import org.ylab.domain.models.Transaction;
import org.ylab.domain.models.dto.TransactionInputDto;
import org.ylab.infrastructure.mappers.TransactionInputMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/operations/debit")
public class DebitServlet extends HttpServlet {
    private final BalanceService balanceService;
    private final TransactionInputMapper transactionMapper;
    private final ObjectMapper objectMapper;

    public DebitServlet() {
        this.balanceService = new BalanceService();
        this.transactionMapper = Mappers.getMapper(TransactionInputMapper.class);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        TransactionInputDto dto = objectMapper.readValue(jsonBuilder.toString(), TransactionInputDto.class);
        Transaction trans = transactionMapper.dtoToObj(dto);
        trans.setUniqueId(String.valueOf(UUID.randomUUID()));
        String respStr = balanceService.debit(trans);
        if (!(respStr.equals("Withdraw amount must be greater than zero") ||
                respStr.equals("Transaction id is not unique") ||
                respStr.equals("Insufficient funds"))) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("plain/text");
        resp.getOutputStream().write(respStr.getBytes());
    }
}
