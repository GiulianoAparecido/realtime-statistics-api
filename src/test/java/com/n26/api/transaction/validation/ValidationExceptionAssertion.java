package com.n26.api.transaction.validation;

import com.n26.api.transaction.data.TransactionData;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ValidationExceptionAssertion {

    private HttpStatus expectedHttpStatus;

    public ValidationExceptionAssertion expectedHttpStatus(HttpStatus expectedHttpStatus){
        this.expectedHttpStatus = expectedHttpStatus;
        return this;
    }

    public void assertException(Consumer<TransactionData> consumer, TransactionData data){
        try{
            consumer.accept(data);
        } catch(ValidationException e){
            assertEquals(expectedHttpStatus, e.getHttpStatus());
            return;
        }
        fail(String.format("Exception expected but not thrown: %s", ValidationException.class));
    }
}
