package com.n26.api.transaction.validation;

import com.n26.api.transaction.data.TransactionData;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;

import java.util.Optional;

public class AmountValidationTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void throwExceptionWith422WhenNotParsable(){
        Validation sut = new AmountValidation();
        TransactionData notParsableAmount = new TransactionData.TransactionDataBuilder().amount("not_parsable").build();

        new ValidationExceptionAssertion()
                .expectedHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                .assertException(sut::validate, notParsableAmount);
    }

    @Test
    public void throwNoExceptionWhenParsable(){
        Validation sut = new AmountValidation();
        TransactionData notParsableAmount = new TransactionData.TransactionDataBuilder().amount("123.45").build();

        sut.validate(notParsableAmount);
    }
}
