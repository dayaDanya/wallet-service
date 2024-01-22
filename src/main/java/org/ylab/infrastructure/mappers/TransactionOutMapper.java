package org.ylab.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.ylab.domain.models.Transaction;
import org.ylab.domain.models.dto.TransactionOutDto;

@Mapper
public interface TransactionOutMapper {
    Transaction dtoToObj(TransactionOutMapper transactionDto);
    TransactionOutDto objToDto(Transaction transaction);
}
