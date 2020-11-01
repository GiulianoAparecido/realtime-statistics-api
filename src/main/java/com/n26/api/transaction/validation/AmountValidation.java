package com.n26.api.transaction.validation;

import com.n26.api.transaction.data.TransactionData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

@Component
public class AmountValidation implements Validation{

    @Override
    public void validate(TransactionData transactionData) {
        isParsable(transactionData.getAmount());
    }

    private void isParsable(String amount) {
        try{
            new BigDecimal(amount);
        }catch (NumberFormatException ex){
            throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
