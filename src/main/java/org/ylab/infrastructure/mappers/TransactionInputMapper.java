package org.ylab.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.ylab.domain.models.Transaction;
import org.ylab.domain.models.dto.TransactionInputDto;

@Mapper
public interface TransactionInputMapper {

    Transaction dtoToObj(TransactionInputDto transactionDto);
    TransactionInputDto objToDto(Transaction transaction);


}
